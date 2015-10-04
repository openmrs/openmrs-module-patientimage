/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0 + Health disclaimer. If a copy of the MPL was not distributed with
 * this file, You can obtain one at http://license.openmrs.org
 */
package org.openmrs.module.patientimage.filter;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sunbiz
 */
public class PatientImageFilter implements Filter {
	
	@Override
	public void init(FilterConfig fc) throws ServletException {
		
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String requestURI = request.getRequestURI();
		if (requestURI.endsWith("patient.page")) {
			PrintWriter out = res.getWriter();
			CharResponseWrapper responseWrapper = new CharResponseWrapper((HttpServletResponse) res);
			chain.doFilter(request, responseWrapper);
			String servletResponse = new String(responseWrapper.toString());
			out.write(servletResponse + " filtered");
			
		} else {
			chain.doFilter(req, res);
		}
	}
	
	@Override
	public void destroy() {
	}
	
}
