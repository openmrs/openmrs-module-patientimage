<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/moduleResources/patientimage/css/patientimage.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/patientimage/scripts/patientimage.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/patientimage/scripts/canvas-to-blob.min.js"></script>

<h2><spring:message code="patientimage.link.name" /></h2>
<br/>
<div style="display: none" id="patientimage_showOnDashboard">${model.showOnDashboard}</div>
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
                            <img alt="" id="patientimg" height="200" src="${pageContext.request.contextPath}/moduleServlet/patientimage/ImageServlet?image=${model.patient_image}" />
                        </c:otherwise>
                    </c:choose>
                    <br/>
                    <input type="file" id="fileUpload" name="Upload image" accept="image/*" /> <br/><br/>
                    <input type="button" name="takePhoto" id="startCameraButton" value="Take Photo (Webcam required)" onclick="startCam()" />
                    <div id="photoDiv" style="display: none">
                        <input type="button" name="photoButton" id="photoButton" value="Snap Photo" />
                        <input type="button" name="uploadButton" id="uploadButton" value="Upload" onclick="uploadCanvas()"/>
                    </div>
                    <div id="uploadUrl" style="display:none">${pageContext.request.contextPath}/moduleServlet/patientimage/PatientImageUpload?identifier=<%=request.getParameter("identifier")%>&patientId=<%=request.getParameter("patientId")%></div>
                </div>
            </td>
        </tr>
    </table>
</div>
<script>
    document.getElementById('fileUpload').addEventListener('change', function () {
        var file = this.files[0];
        var url = $j("#uploadUrl").html().replace('&amp;', '&');
        var xhr = new XMLHttpRequest();
        var fd = new FormData();
        xhr.open("POST", url, true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                location.reload();
            }
        };
        fd.append("upload_file", file);
        xhr.send(fd);
    }, false);
</script>