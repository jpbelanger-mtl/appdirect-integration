<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>OpenID Login form</title>
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
	</head>
	<body>
		<c:url var="openIDLoginUrl" value="/openid" />
		<form action="${openIDLoginUrl}" method="post">
   			<input name="openid_identifier" type="hidden" value="http://jpbelanger-test.byappdirect.com/openid/id"/>
		</form>
 	</body>
 	
	<script type="text/javascript">
  	$(document).ready(function() {
	    window.document.forms[0].submit();
  	});
</script> 	
</html>