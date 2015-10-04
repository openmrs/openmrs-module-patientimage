/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0 + Health disclaimer. If a copy of the MPL was not distributed with
 * this file, You can obtain one at http://license.openmrs.org
 */
package org.openmrs.module.patientimage.rest.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/rest/v1/patientimage/patientimage")
public class PatientImageController {
	
	/**
	 * @param patientid int
	 * @param pageid int
	 * @return ResponseEntity<byte[]> containing image binary data with JPEG
	 * 	image header.
	 * @throws ResponseException
	 * @throws IOException 
	 */
	@RequestMapping(value = "/{patientid}/{pageid}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> retrieve(@PathVariable("patientid") String patientIdStr,
	        @PathVariable("pageid") String pageIdStr, HttpServletRequest request) throws IOException {
		//RequestContext context = RestUtil.getRequestContext(request);
		int patientId = Integer.parseInt(patientIdStr);
		int pageId = Integer.parseInt(pageIdStr);
		final HttpHeaders headers = new HttpHeaders();
		byte[] imageData = null;
		HttpStatus status = null;
		headers.setContentType(MediaType.IMAGE_JPEG);
		status = HttpStatus.OK;
		return new ResponseEntity<byte[]>(imageData, headers, status);
	}
}
