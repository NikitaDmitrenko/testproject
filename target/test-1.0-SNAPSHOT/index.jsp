<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page session="false" %>
<html>
<head>
    <title>Upload Multiple File Request Page</title>
</head>
<body>
<form method="POST" action="hello/uploadMultipleFile" enctype="multipart/form-data">
    File1 to upload:<br>
    <input multiple type="file" name="file"><br>
    <input type="submit" value="Upload"> Press here to upload the file!
</form>
</body>
</html>