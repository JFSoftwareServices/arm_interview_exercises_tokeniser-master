
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
     * Returns true if the stream contains the specified startMarker, but throws an EndOfStreamException otherwise.
     */
    private boolean startMarkerMatched(Stream stream, String startMarker) throws EndOfStreamException {
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
            char character;
            if (position < startMarker.length()) {
                character = stream.read();
                if (characterMatched(character, position, startMarker)) {
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
        /*
         * position indicates the position of a matched character of the endMarker.
         * For example if startMarker is {end} then:
         *
         * {  has position 1
         * e  has position 2
         * n  has position 3
         * d  has position 4
         * }  has position 5
         *
         * All other characters have position 0.
         *
         */
        int position = 0;
        StringBuilder tmpToken = new StringBuilder();
        StringBuilder token = new StringBuilder();
        for (; ; ) {
            char character;
            if (position < endMarker.length()) {
                character = stream.read();
                clearStringBuilder(tmpToken);
                tmpToken.append(character);
                if (characterMatched(character, position, endMarker)) {
                    position++;
                } else {
                    position = 0;
                    token.append(tmpToken.toString());
                }
            } else {
                break;
            }
        }
        return token.toString();
    }

    private void clearStringBuilder(StringBuilder sb) {
        sb.setLength(0);
    }

    private boolean characterMatched(char character, int position, String marker) {
        return position < marker.length() && marker.charAt(position) == character;
    }
}