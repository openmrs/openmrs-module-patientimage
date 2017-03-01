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

var localMediaStream = null;
var video = null;
var canvas = null;

function startCam() {
    navigator.getUserMedia = (navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia);
    if (navigator.getUserMedia) {
        navigator.getUserMedia(
                {
                    video: true,
                    audio: false
                },
        function (stream) {
            localMediaStream = stream;
            $j("#startCameraButton").hide();
            $j("#fileUpload").hide();
            $j("#photoDiv").show();
            $j("#uploadButton").prop('disabled', true);
            video = document.createElement("video");
            video.setAttribute('id', 'video');
            video.setAttribute('autoplay', 'true');
            video.height = 200;
            video.width = 266.66;
            $j("#patientimg").replaceWith(video);
            var url = window.URL || window.webkitURL;
            video.src = url ? url.createObjectURL(stream) : stream;
            document.getElementById("photoButton").addEventListener("click", function () {
                canvas = document.createElement("canvas");
                canvas.setAttribute('id', 'canvas');
                var ctx = canvas.getContext("2d");
                canvas.height = 200;
                canvas.width = 266.66;
                $j("#video").replaceWith(canvas);
                ctx.drawImage(video, 0, 0, 266.66, 200);
                localMediaStream.getTracks()[0].stop();
                $j("#uploadButton").prop('disabled', false);
            });
        },
                function (error) {
                    console.log("Could not start camera" + error);
                }
        );
    }
    else {
        alert('Sorry, the browser you are using doesn\'t support getUserMedia');
        return;
    }
}

function uploadCanvas() {
    var usePersonImageEndpoint = ($j("#patientimage_usePersonImageEndpoint").html() == 'true');
    var xhr = new XMLHttpRequest();
    canvas.toBlob(function (blob) {
            if (usePersonImageEndpoint) {
                var patientUuid = $j("#patientUuid").html();
                url = $j("#uploadUrl").html() + "/" + patientUuid;
                var base64file;
                var reader = new FileReader();
                reader.readAsDataURL(blob);
                reader.onload = function () {
                    var base64UriTypeSuffix = "base64,";
                    var base64Uri = reader.result;
                    base64file = base64Uri.substring(base64Uri.indexOf(base64UriTypeSuffix) + base64UriTypeSuffix.length);
                    var requestBody = JSON.stringify({person: patientUuid, base64EncodedImage: base64file});
                    xhr.open("POST", url, true);
                    xhr.setRequestHeader("Content-Type", "application/json");
                    xhr.onreadystatechange = function () {
                        if (xhr.readyState == 4 && xhr.status == 200) {
                            location.reload();
                        }
                    };
                    xhr.send(requestBody);
                };
            }
            else {
                var fd = new FormData();
                var url = $j("#uploadUrl").html().replace('&amp;', '&');
                fd.append('patientimage', blob, 'patientimage.jpg');
                xhr.open("POST", url, true);
                xhr.onreadystatechange = function () {
                    if (xhr.readyState == 4 && xhr.status == 200) {
                        location.reload();
                    }
                };
                xhr.send(fd);
            }
        }, 'image/jpeg');

}