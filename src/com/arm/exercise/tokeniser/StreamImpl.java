// Copyright (C) 2017, ARM Limited or its affiliates. All rights reserved.
package com.arm.exercise.tokeniser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * String-backed implementation of the {@link Stream} interface.
 */
public class StreamImpl implements Stream {

    private InputStream inputStream;

    public StreamImpl(String s) {
        inputStream = new ByteArrayInputStream(s.getBytes());
    }

    @Override
    public char read()
            throws EndOfStreamException {
        char ch;
        int i;
        try {
            i = inputStream.read();
            ch = (char) i;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (i != -1) {
            return ch;
        } else {
            throw new EndOfStreamException();
        }
    }
}