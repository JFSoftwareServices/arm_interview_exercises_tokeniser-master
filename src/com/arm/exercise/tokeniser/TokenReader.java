// Copyright (C) 2017, ARM Limited or its affiliates. All rights reserved.
package com.arm.exercise.tokeniser;

public interface TokenReader {

	/**
	 * Returns a string delimited by markers, reading from the given stream. For example, assuming
	 * <code>stream</code> contains <code>"qjfws{start}my string{end}plthrp"</code>, 
	 * <code>readToken(stream, "{start}", "{end}")</code> returns <code>"my string"</code>.
	 * @param stream stream of characters to read from
	 * @param startMarker string indicating the start of the token string to return
	 * @param endMarker string indicating the end of the token string to return
	 * @return string between start and end markers.
	 * @throws EndOfStreamException if the end of the stream is reached.
	 */
	public String readToken(Stream stream, String startMarker, String endMarker)
	throws EndOfStreamException;
}
