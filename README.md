# openmrs-module-patientimage

## Overview

The patient image module allows adding an image of the patient in the patient dashboard in both the legacy UI as well as 2.x reference application UI.
The module adds a tab to the patient dashboard to manage the patient image. By default, if no image is available, the module will display a silhouette 
as the patient image and clicking on the Patient Image tab will allow managing this image.
The image can either be captured through a webcam or an image can be uploaded. The portrait of the patient is saved in the OpenMRS Application Data directory under 
the patient_images folder. A person attribute (Patient Image) is used to associate the filename with the image. Currently the filename is the preferred identifier 
of the patient, but in later version will be customizable through a global property.

## Installation

For a detailed guide on how to install and configure this module see
https://wiki.openmrs.org/display/docs/Patient+Image+Module

###Build

To ensure that your commit builds fine run
```
mvn clean package
```
before opening a new pull request.

###Coding conventions

This module adheres to the OpenMRS coding conventions, please read

https://wiki.openmrs.org/display/docs/Coding+Conventions

####Code style

Help us to keep the code consistent!
This will produce readable diffs and make merging easier and quicker!

This module uses the Eclipse formatter plugin to automatically format *.java
files. This plugin is automatically executed when you build the module.

To manually run the formatter plugin, do
```
mvn java-formatter:format
```



