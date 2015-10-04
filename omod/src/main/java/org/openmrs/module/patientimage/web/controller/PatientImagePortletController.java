/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0 + Health disclaimer. If a copy of the MPL was not distributed with
 * this file, You can obtain one at http://license.openmrs.org
 */
package org.openmrs.module.patientimage.web.controller;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.openmrs.Patient;
import org.openmrs.PersonAttribute;
import org.openmrs.PersonAttributeType;
import org.openmrs.api.PersonService;
import org.openmrs.api.context.Context;

import org.openmrs.util.OpenmrsUtil;
import org.openmrs.web.controller.PortletController;

public class PatientImagePortletController extends PortletController {
	
	@Override
	protected void populateModel(HttpServletRequest request, Map<String, Object> model) {
		PersonService ps = Context.getPersonService();
		if (ps.getPersonAttributeTypeByName("Patient Image") == null) {
			PersonAttributeType pat = new PersonAttributeType();
			pat.setName("Patient Image");
			pat.setFormat("java.lang.String");
			pat.setDescription("Stores the filename for the patient image");
			ps.savePersonAttributeType(pat);
			log.info("Created New Person Attribute: Patient Image");
		} else {
			log.info("Person Attribute: Patient Image already exists");
		}
		
		model.put("showOnDashboard", Context.getAdministrationService().getGlobalProperty(
		    "patientimage.showOnLegacyDashboard"));
		
		String patientId = request.getParameter("patientId");
		if (patientId != null) {
			int id = Integer.parseInt(patientId);
			Patient patient = Context.getPatientService().getPatient(id);
			if (patient != null) {
				PersonAttribute attribute = patient.getAttribute(ps.getPersonAttributeTypeByName("Patient Image"));
				if (attribute != null) {
					File imgFolder = new File(OpenmrsUtil.getApplicationDataDirectory(), "patient_images");
					if (imgFolder.exists()) {
						if (!attribute.getValue().trim().equals("")) {
							File image = new File(imgFolder, attribute.getValue());
							if (image.exists()) {
								model.put("patient_image", image.getName());
							}
						}
					}
				}
			}
		}
	}
}
