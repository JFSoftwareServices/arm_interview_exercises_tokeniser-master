
// Copyright (C) 2017, ARM Limited or its affiliates. All rights reserved.
package com.arm.exercise.tokeniser;

import static java.util.Objects.requireNonNull;


public class TokenReaderImpl implements TokenReader {

    @Override
    public String readToken(Stream stream, String startMarker, String endMarker) throws EndOfStreamException {
        requireNonNull(stream);
        requireNonNullAndNonEmpty(startMarker);
        requireNonNullAndNonEmpty(endMarker);

        String token = "";
        if (startMarkerMatched(stream, startMarker)) {
            token = tokenIfEndMarkerMatched(stream, requireNonNullAndNonEmpty(endMarker));
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
     * If it does, returns true, otherwise return false.
     */
    private boolean startMarkerMatched(Stream stream, String startMarker) throws EndOfStreamException {
        /*
         * position indicates the position of the matched part of the startMarker. The first matched position is 1
         * and the next matched position is 2 etc. For example if startMarker is {start} then:
         *
         * {  has position 1
         * s  has position 2
         * t  has position 3
         * ..    ..       ..
         * ..    ..       ..
         * } has position 7
         *
         * All other characters have position 0
         *
         */
        int position = 0;
        for (; ; ) {
            if (position < startMarker.length()) {
                char character = stream.read();
                if (charMatched(position, character, startMarker)) {
                    position++;
                } else {
                    position = 0;
                }
            } else return true;
        }
    }

    /**
     *
     */
    private String tokenIfEndMarkerMatched(Stream stream, String endMarker) throws EndOfStreamException {
        StringBuilder token = new StringBuilder();
        StringBuilder tmp = null;
        int pos = 0;
        for (; ; ) {
            if (pos < endMarker.length()) {
                char c = stream.read();
                if (tmp == null)
                    tmp = new StringBuilder();
                tmp.append(c);
                if (charMatched(pos, c, endMarker)) {
                    pos++;
                } else {
                    pos = 0;
                    token.append(tmp.toString());
                    tmp = null;
                }
            } else {
                break;
            }
        }
        return token.toString();
    }

    private boolean charMatched(int pos, char c, String marker) {
        return pos < marker.length() && marker.charAt(pos) == c;
    }
}