/* global $j */

/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0 + Health disclaimer. If a copy of the MPL was not distributed with
 * this file, You can obtain one at http://license.openmrs.org
 */

$j(document).ready(function () {
    var patientimage_showOnDashboard = $j("#patientimage_showOnDashboard").html();
    if (patientimage_showOnDashboard === 'true') {
        var html = $j("#patientDashboardHeader").html();
        var imgHeight = $j("#patientDashboardHeader").height();
        var imgTable = document.createElement("table");
        imgTable.width = "99%";
        var headerRow = imgTable.insertRow(0);
        var headerCell = headerRow.insertCell(0);
        headerCell.width = "95%";
        var imgCell = headerRow.insertCell(1);
        headerCell.innerHTML = html;
        var imgThumbnail = $j('<div>').append($j('#patientimg').clone()).remove();
        $j(imgCell).append($j(imgThumbnail.html()).attr('id', 'imgThumbnail').attr('height', imgHeight).attr('style', 'border: 1px solid #8FABC7'));
        $j("#patientDashboardHeader").html(imgTable);
    }
});