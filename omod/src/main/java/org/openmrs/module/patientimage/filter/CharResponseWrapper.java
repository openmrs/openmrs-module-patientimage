/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0 + Health disclaimer. If a copy of the MPL was not distributed with
 * this file, You can obtain one at http://license.openmrs.org
 */
package org.openmrs.module.patientimage.filter;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CharResponseWrapper extends HttpServletResponseWrapper {
	
	private CharArrayWriter output;
	
	@Override
	public String toString() {
		return output.toString();
	}
	
	public CharResponseWrapper(HttpServletResponse response) {
		super(response);
		output = new CharArrayWriter();
	}
	
	@Override
	public PrintWriter getWriter() {
		return new PrintWriter(output);
	}
}
