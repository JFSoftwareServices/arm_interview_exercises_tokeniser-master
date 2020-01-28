
// Copyright (C) 2017, ARM Limited or its affiliates. All rights reserved.
package com.arm.exercise.tokeniser;

import static java.util.Objects.requireNonNull;


public class TokenReaderImpl implements TokenReader {

    @Override
    public String readToken(Stream stream, String startMarker, String endMarker) throws EndOfStreamException {
        requireNonNull(stream);
        requireNonNullAndNonEmpty(startMarker);
        requireNonNullAndNonEmpty(endMarker);

        String token;
        if (startMarkerMatched(stream, startMarker)) {
            token = tokenIfEndMarkerMatched(stream, requireNonNullAndNonEmpty(endMarker));
        } else {
            throw new IllegalArgumentException(startMarker);
        }
        return token;
    }

    private String requireNonNullAndNonEmpty(String marker) {
        requireNonNull(marker);
        if (marker.isEmpty())
            throw new IllegalArgumentException("marker");
        return marker;
    }

    /**
     * Tests if the stream contains the specified startMaker.
     */
    private boolean startMarkerMatched(Stream stream, String startMarker) {
        /*
         * position indicates the position of a matched character of the startMarker.
         * For example if startMarker is {start} then:
         *
         * {  has position 1
         * s  has position 2
         * t  has position 3
         * a  has position 4
         * r  has position 5
         * t  has position 6
         * }  has position 7
         *
         * All other characters have position 0.
         *
         */
        int position = 0;
        for (; ; ) {
            char character = 0;
            if (position < startMarker.length()) {
                try {
                    character = stream.read();
                } catch (EndOfStreamException e) {
                    return false;
                }
                if (charMatched(character, position, startMarker)) {
                    position++;
                } else {
                    position = 0;
                }
            } else {
                return true;
            }
        }
    }

    /**
     * Returns the token between the startMarker and endMarker. This method throws an EndOfStreamException if the
     * endMarker is not present in the stream.
     */
    private String tokenIfEndMarkerMatched(Stream stream, String endMarker) throws EndOfStreamException {
        StringBuilder token = new StringBuilder();
        StringBuilder tmp = null;
        int position = 0;
        for (; ; ) {
            if (position < endMarker.length()) {
                char character = stream.read();
                if (tmp == null)
                    tmp = new StringBuilder();
                tmp.append(character);
                if (charMatched(character, position, endMarker)) {
                    position++;
                } else {
                    position = 0;
                    token.append(tmp.toString());
                    tmp = null;
                }
            } else {
                break;
            }
        }
        return token.toString();
    }

    private boolean charMatched(char character, int position, String marker) {
        return position < marker.length() && marker.charAt(position) == character;
    }
}