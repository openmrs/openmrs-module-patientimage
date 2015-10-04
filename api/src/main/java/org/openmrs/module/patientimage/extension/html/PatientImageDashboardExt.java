/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0 + Health disclaimer. If a copy of the MPL was not distributed with
 * this file, You can obtain one at http://license.openmrs.org
 */
package org.openmrs.module.patientimage.extension.html;

import org.openmrs.module.web.extension.PatientDashboardTabExt;

public class PatientImageDashboardExt extends PatientDashboardTabExt {

    @Override
    public String getTabName() {
        return "patientimage.title";
    }

    @Override
    public String getTabId() {
        return "patientImageForm";
    }

    @Override
    public String getRequiredPrivilege() {
        return "Patient Dashboard - Manage Patient Image";
    }

    @Override
    public String getPortletUrl() {
        return "patientImageForm";
    }
}
