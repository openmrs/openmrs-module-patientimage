/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0 + Health disclaimer. If a copy of the MPL was not distributed with
 * this file, You can obtain one at http://license.openmrs.org
 */
package org.openmrs.module.patientimage;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.BaseModuleActivator;
import org.openmrs.util.OpenmrsUtil;

/**
 * This class contains the logic that is run every time this module is either started or shutdown
 */
public class PatientImageActivator extends BaseModuleActivator {

    private final Log log = LogFactory.getLog(this.getClass());

    /**
     * @see org.openmrs.module.Activator#startup()
     */
    public void startup() {
        log.info("Starting Patient Image Module");
        File imgFolder = new File(OpenmrsUtil.getApplicationDataDirectory(), "/patient_images");
        if (!imgFolder.exists()) {
            try {
                FileUtils.forceMkdir(imgFolder);
                log.info("Created Folder to Store patient_images");
            } catch (IOException ex) {
                log.error(ex);
            }
        } else {
            log.info("Folder for patient_images Already Exists");
        }
    }

    /**
     * @see org.openmrs.module.Activator#shutdown()
     */
    public void shutdown() {
        log.info("Shutting down Patient Image Module");
    }
}
