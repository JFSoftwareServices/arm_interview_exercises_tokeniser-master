package com.arm.exercise.tokeniser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;

import static com.arm.exercise.tokeniser.TestHelper.*;
import static java.lang.String.format;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TokenReaderHappyPathTest {

    private String input;
    private String expectedOutput;

    public TokenReaderHappyPathTest(String input, String expectedOutput) {
        this.input = input;
        this.expectedOutput = expectedOutput;
    }

    @Parameters(name = "Run {index}: input={0}, expectedOutput={1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {format("qjfws%smy string%splthrp", START_MARKER, END_MARKER), "my string"},
                {format("%smy string%splthrp", START_MARKER, END_MARKER), "my string"},
                {format("%smy string%s", START_MARKER, END_MARKER), "my string"},
                {format("qjfws%smy string%s%splthrp", START_MARKER, END_MARKER, END_MARKER), "my string"},
                {format("%s%s", START_MARKER, END_MARKER), EMPTY_STRING},
                {format("%s %s", START_MARKER, END_MARKER), SPACE_STRING},
        });
    }

    @Test
    public void correctTokenReturned() throws EndOfStreamException {
        String actualOutput = new TokenReaderImpl().readToken(new StringStream(input), START_MARKER, END_MARKER);
        assertEquals(expectedOutput, actualOutput);
    }
}