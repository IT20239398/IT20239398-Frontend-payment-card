<%@page import="com.Card"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %>
<% Class.forName("com.mysql.cj.jdbc.Driver"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment card Management</title>
<link rel="stylesheet" href="Views/css/bootstrap.min.css">
<script src="Components/jquery.min.js"></script>
<script src="Components/cards.js"></script>
<script src="Views/card.css"></script>
</head>
<body>

<div class="container"><div class="row"><div class="col-6"> 

<h1>Payment card Management</h1>

	<form id="formCards" name="formCards" method="post" action="Card.jsp">
		 Account Number: 
		 <input type="text" id="txtAccNo" name="txtAccNo" 
		 class="form-control form-control-sm">
		 
		 <br> 
		 Card Number: 
		 <input type="text" id="txtCardNo" name="txtCardNo" 
		 class="form-control form-control-sm">
		 
		 <br> 
		 Expire date:
		 <input type="date" id="txtExpDate" name="txtExpDate" 
		 class="form-control form-control-sm">
		 
		 <br> 
		 CVC: 
		 <input type="text" id="txtCVC" name="txtCVC" 
		 class="form-control form-control-sm">
		 
		 <br>
		 <input id="btnSave" name="btnSave" type="button" value="Save" 
		 class="btn btn-primary">
		 <input type="hidden" id="hidCardIDSave" 
		 name="hidCardIDSave" value="">	
		 
		 <div id="alertSuccess" class="alert alert-success"></div>
		 <div id="alertError" class="alert alert-danger"></div>
	</form>
	

<br>

	<div id="divCardsGrid">
	 <%
	 Card cardObj = new Card(); 
	 out.print(cardObj.readCard()); 
	 %>
	</div>
</div> </div> </div>
</body>
</html>