$(document).ready(function() {
	$("#alertSuccess").hide();
	$("#alertError").hide();
});

$(document).on("click", "#btnSave", function(event) {
	// Clear status msges---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
/*	var status = validateformCards();
	// If not valid
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	} */
	
	//if valid
	var type = ($("#hidCardIDSave").val() == "") ? "POST" : "PUT";
	$.ajax(
	{
		url : "CardsAPI",
		type : type,
		data : $("#formCards").serialize(),
		dataType : "text",
		complete : function(response, status)
		{
			onCardSaveComplete(response.responseText, status);
		}
	});
	
	
	

});

function onCardSaveComplete(response, status) 
{ 
 	if(status == "success")
 	{
		var resultSet = JSON.parse(response);
		
		if(resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully saved."); 
			$("#alertSuccess").show(); 
			
			$("#divCardsGrid").html(resultSet.data); 
		}else if(resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data); 
 			$("#alertError").show();
		}
	}else if(status == "error")
	{
		 $("#alertError").text("Error while saving status."); 
		 $("#alertError").show();
	}else
	{
		 $("#alertError").text("Error while saving."); 
		 $("#alertError").show();
	}
		$("#hidCardIDSave").val(""); 
		$("#formCards")[0].reset(); 
}

//Update-------
$(document).on("click", "#btnUpdate", function(event)
	{
		$("#hidcardIDSave").val($(this).data("itemid"));
		$("#txtAccNo").val($(this).closest("tr").find('td:eq(0)').text()); 
		$("#txtCardNo").val($(this).closest("tr").find('td:eq(1)').text()); 
		$("#txtExpDate").val($(this).closest("tr").find('td:eq(2)').text());
		$("#txtCVC").val($(this).closest("tr").find('td:eq(3)').text()); 
	});

//delete	
$(document).on("click", ".btnRemove", function(event) {

	
	$.ajax(
	{
		url : "CardsAPI",
		type : "DELETE",
		data : "id=" + $(this).data("itemid"),
		dataType : "text",
		complete : function(response, status)
		{
			onCardDeleteComplete(response.responseText, status);
		}
	});
	
});	

function onCardDeleteComplete(response, status)
{ 
if (status == "success") 
 { 
 var resultSet = JSON.parse(response); 
 if (resultSet.status.trim() == "success") 
 { 
 $("#alertSuccess").text("Successfully deleted."); 
 $("#alertSuccess").show(); 
 $("#divCardsGrid").html(resultSet.data); 
 } else if (resultSet.status.trim() == "error") 
 { 
 $("#alertError").text(resultSet.data); 
 $("#alertError").show(); 
 } 
 } else if (status == "error") 
 { 
 $("#alertError").text("Error while deleting."); 
 $("#alertError").show(); 
 } else
 { 
 $("#alertError").text("Unknown error while deleting.."); 
 $("#alertError").show(); 
 } 
}


/*function validateformCards() {
	//Validations 
	//Account number
	if ($("#accNo").val().trim() == "") {
		return "Insert Acoount number!";
	}

	//Card Number
	if ($("#cardNo").val().trim() == "") {
		return "PLease Insert Card Number!";
	}

	return true;

}*/