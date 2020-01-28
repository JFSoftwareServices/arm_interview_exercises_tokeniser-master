package com.arm.exercise.tokeniser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.arm.exercise.tokeniser.TestHelper.*;
import static java.lang.String.format;

@RunWith(JUnit4.class)
public class TokenReaderSadPathTest {

    @Test(expected = NullPointerException.class)
    public void nullPointerExceptionIsThrownWhenStreamIsNull() throws EndOfStreamException {
        new TokenReaderImpl().readToken(null, START_MARKER, END_MARKER);
    }

    @Test(expected = NullPointerException.class)
    public void nullPointerExceptionIsThrownWhenStartMarkerIsNull() throws EndOfStreamException {
        new TokenReaderImpl().readToken(new StringStream(EMPTY_STRING), null, END_MARKER);
    }

    @Test(expected = NullPointerException.class)
    public void nullPointerExceptionIsThrownWhenEndMarkerIsNull() throws EndOfStreamException {
        new TokenReaderImpl().readToken(new StringStream(EMPTY_STRING), START_MARKER, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalArgumentExceptionIsThrownWhenStartMarkerIsEmpty() throws EndOfStreamException {
        new TokenReaderImpl().readToken(new StringStream(EMPTY_STRING), EMPTY_STRING, END_MARKER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalArgumentExceptionIsThrownWhenEndMarkerIsEmpty() throws EndOfStreamException {
        new TokenReaderImpl().readToken(new StringStream(EMPTY_STRING), START_MARKER, EMPTY_STRING);
    }

    @Test(expected = EndOfStreamException.class)
    public void endOfStreamExceptionIsThrownWhenStartMarkerFailsToMath() throws EndOfStreamException {
        new TokenReaderImpl().readToken(new StringStream(format("qjfws%smy string%splthrp", START_MARKER_X, END_MARKER)),
                START_MARKER, END_MARKER);
    }
}