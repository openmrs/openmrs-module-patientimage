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
import org.openmrs.PatientIdentifier;

import org.openmrs.api.context.Context;

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
		Boolean showOn2xDashboard = Boolean.valueOf(Context.getAdministrationService().getGlobalProperty(
		    "patientimage.showOn2xDashboard"));
		if (requestURI.endsWith("patient.page") && showOn2xDashboard) {
			PrintWriter out = res.getWriter();
			CharResponseWrapper responseWrapper = new CharResponseWrapper((HttpServletResponse) res);
			chain.doFilter(request, responseWrapper);
			PatientIdentifier id = Context.getPatientService().getPatientByUuid(request.getParameter("patientId"))
			        .getPatientIdentifier();
			String responseText = responseWrapper.toString();
			if (null != id) {
				StringBuilder servletResponse = new StringBuilder(responseWrapper.toString());
				int indexOf = servletResponse.indexOf("<div class=\"patient-header \">");
				String imgTag = "<img alt=\"\" id=\"imgThumbnail\" height=\"145\" src=\"/openmrs/moduleServlet/patientimage/ImageServlet?image="
				        + id.getIdentifier()
				        + ".jpg\" style=\"border: 1px solid #8FABC7; float:right\" onError=\"this.onerror = '';this.style.display='none';\">";
				responseText = servletResponse.insert(indexOf, imgTag).toString();
			}
			out.write(responseText);
		} else {
			chain.doFilter(req, res);
		}
	}
	
	@Override
	public void destroy() {
	}
	
}
