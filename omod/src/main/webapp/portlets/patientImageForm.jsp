<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/moduleResources/patientimage/css/patientimage.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/patientimage/scripts/patientimage.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/patientimage/scripts/canvas-to-blob.min.js"></script>

<h2><spring:message code="patientimage.link.name" /></h2>
<br/>
<div style="display: none" id="patientimage_showOnDashboard">${model.showOnDashboard}</div>
<div style="display: none" id="patientimage_usePersonImageEndpoint">${model.usePersonImageEndpoint}</div>
<div style="display: none" id="requestUrl">${model.requestURL}</div>
<div style="display: none" id="patientUuid">${patient.uuid}</div>

<div>
    <table align="center">
        <tr>
            <td style="position: relative">
                <b class="boxHeader imageHeader">Patient Image</b>
                <div class="box imageBox" id="imageBox" style="cursor: pointer">
                    <c:choose>
                        <c:when test="${model.patient_image == null}">
                            <img alt="" id="patientimg" src="${pageContext.request.contextPath}/moduleResources/patientimage/images/${patient.gender}.png" />
                            <div class="textOverlay">No Image Available</div>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${model.usePersonImageEndpoint}">
                                    <img alt="" id="patientimg" height="200" src="${model.requestURL}/${patient.uuid}" />
                                </c:when>
                                <c:otherwise>
                                    <img alt="" id="patientimg" height="200" src="${model.requestURL}/ImageServlet?image=${model.patient_image}" />
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                    <br/>
                    <input type="file" id="fileUpload" name="Upload image" accept="image/*" /> <br/><br/>
                    <input type="button" name="takePhoto" id="startCameraButton" value="Take Photo (Webcam required)" onclick="startCam()" />
                    <div id="photoDiv" style="display: none">
                        <input type="button" name="photoButton" id="photoButton" value="Snap Photo" />
                        <input type="button" name="uploadButton" id="uploadButton" value="Upload" onclick="uploadCanvas()"/>
                    </div>
                    <c:choose>
                        <c:when test="${model.usePersonImageEndpoint}">
                            <div id="uploadUrl" style="display:none">${model.requestURL}</div>
                        </c:when>
                        <c:otherwise>
                            <div id="uploadUrl" style="display:none">${model.requestURL}/PatientImageUpload?identifier=<%=request.getParameter("identifier")%>&patientId=<%=request.getParameter("patientId")%></div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </td>
        </tr>
    </table>
</div>
<script>
    document.getElementById('fileUpload').addEventListener('change', function () {
        var usePersonImageEndpoint = ($j("#patientimage_usePersonImageEndpoint").html() == 'true');
        var fd = new FormData();
        var url;
        var file = this.files[0];
        var xhr = new XMLHttpRequest();
        if (usePersonImageEndpoint) {
            var patientUuid = $j("#patientUuid").html();
            url = $j("#uploadUrl").html() + "/" + patientUuid;
            var base64file;
            var reader = new FileReader();
            reader.readAsDataURL(file);
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
        } else {
            url = $j("#uploadUrl").html().replace('&amp;', '&');
            fd.append("upload_file", file);
            xhr.open("POST", url, true);
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    location.reload();
                }
            };
            xhr.send(fd);
        }

    }, false);
</script>