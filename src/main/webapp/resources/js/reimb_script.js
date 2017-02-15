/**
 * 
 */

// Get the modals
var modal = document.getElementById('newReimb');
var receiptModal = document.getElementById('viewReceipt');

// Get the button that opens the modal
var addReimb = document.getElementById("addReimb");
var approveReimb = document.getElementById("approveReimb");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

var receipt = document.getElementById("receipt");

window.onload = function() {
	document.getElementById("receipt").addEventListener("change", mew, false);
	
	// When the user clicks on the button, open the modal
	addReimb.onclick = function() {
		modal.style.display = "block";
	}

	console.log(approveReimb);
	approveReimb.onclick = function() {
		updateReimbCallback();
	}
	
	// When the user clicks on <span> (x), close the modal
	span.onclick = function() {
		modal.style.display = "none";
	}
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
	if (event.target == modal) {
		modal.style.display = "none";
	}
	if (event.target == receiptModal) {
		receiptModal.style.display = "none";
	}
}

function updateReimbursements() {
	var statuses = ["Approved", "Denied", "Pending"];
	
	var reimbTable = $(".reimb-table")[0].children[0];
	var row;
	var statusCell;
	var status;
	
	// Select
	var r_id;
	var select;
	var option;
	
	// Column for Status
	var status = 5;
	var selected;
	var statusCell;
	
	if (reimbTable.children.length < 1) {
		return null;
	}
	
	// Loop through all the rows (skip the header row)
	for(var i = 1; i < reimbTable.children.length; i++) {
		row = reimbTable.children[i];
		
		r_id = row.children[0].innerText;
		statusCell = row.children[status];
		
		selected = statusCell.innerText; 
		
		statusCell.innerText = "";
		
		// Make the select
		select = document.createElement("select");
		select.onchange = function(id) { return function() {updateStatus(id); }; }(r_id);
		
		for(var j = 0; j < statuses.length; j++) {
			option = document.createElement("option");
			option.value = statuses[j];
			option.innerText = statuses[j];
			
			if(selected == statuses[j]) {
				option.selected = true;
			}
			
			select.appendChild(option);
		}
		
		statusCell.appendChild(select);
	}
}

function updateStatus(id) {
	alert("Status Update: " + id);
	var select = event.target;
	var status = "";
	
	for(var i = 0; i < select.length; i++) {
		if(select[i].selected) {
			status = select[i].value;
		}
	}
	
	updateReimbCallback(id, status, function(result){alert(result);});
}

function mew() {
	console.log(receipt);
	var file = receipt.value;
}


function reimbsByEmployee() {
	console.log($("#employees")[0]);
	var select = $("#employees")[0].children;
	var option;
	// Loop through the options and find the one selected
	for (var i = 0; i < select.length; i++) {
		option = select[i];

		if (option.selected) {
			selectReimbsCallback("employees", option.value, addReimbsToTable, option.value);
			break;
		}
	}
}

function reimbsByStatus() {
	var select = $("#status")[0].children;
	var option;

	// Loop through the options and find the one selected
	for (var i = 0; i < select.length; i++) {
		option = select[i];
		
		if (option.selected) {
			selectReimbsCallback("status", option.value, addReimbsToTable);		
			break;
		}
	}
}

function reimbsByType(userid) {
	var select = $("#type")[0].children;
	var option;

	// Loop through the options and find the one selected
	for (var i = 0; i < select.length; i++) {
		option = select[i];

		if (option.selected) {
			selectReimbsCallback("type", option.value, addReimbsToTable);	
			break;
		}
	}
}

function addReimbsToTable(reimbs) {
	var reimbTable = $(".reimb-table")[0].children[0]; // Table body
	
	// First empty the table
	while (reimbTable.children.length > 1) {
		reimbTable.removeChild(reimbTable.lastChild);
	}
	
	var reimbArray; // Will be loop through in subsequent loop
	var row;
	var cell;
	
	// Loop for every reimbursement returned
	for(var j = 0; j < reimbs.length; j++) {
		// Get the array form of the reimbursement
		reimbArray = reimbToArray(reimbs[j]);
		
		row = document.createElement("tr"); // Make a new row for the reimbursement
		
		row.innerHtml = ""; // Set this to something beforehand or errors
		
		// Add each reimbursement to the table
		for(var k = 0; k < reimbArray.length; k++) {
			cell = document.createElement("td");
			
			if(k == reimbArray.length - 1) {
				var button = document.createElement("button");

				button.className = "receipt-" + reimbs[j].id;
				button.onclick = function(id) { return function() {launchReceiptModal(id); }; }(reimbs[j].id);
				button.innerText = "View Receipt";
				cell.appendChild(button);
			} 
			else {
				cell.innerText = reimbArray[k];
			}
			
			row.appendChild(cell);
		}
		
		// Add the finished row to the table;
		
		reimbTable.appendChild(row);
	}
}

function fetchReceiptCallback(id) {
	$.ajax({
		url : "modReimb",
		data : {
			"reimbId" : id
		},
		dataType : "json",
		type : "GET",
		success : function(data) {
			console.log(data);
			
			$("#receipt")[0].src = "data:image/jpeg;base64," + data.result;
		}
	});
}

function selectReimbsCallback(type, param, callback) {
	var empId = "";
	
	if(arguments.length == 4) {
		console.log(arguments);
		empId = arguments[3];
	}
	
	$.ajax({
		url : "fetchReimb",
		data : {
			"requestType" : type, 
			"param" : param,
			"empId" : empId
		},
		dataType : "json",
		type : "GET",
		success : function(data) {
			console.log(data);
			callback(data);
		}
	});
}

function updateReimbCallback(reimbId, status, callback) {
	$.ajax({
		url : "resolve",
		data : {
			"reimbId" : reimbId,
			"status" : status
		},
		dataType : "json",
		type : "GET",
		success : function(data) {
			alert("Baa");
			console.log(data);
			callback(data);
		}
	});
}


// Utility functions

// Simple function to turn reimbursement parameters to an array 
// that can be easily added to a table row
function reimbToArray(reimb) {
	var reimbArray = [];
	
	// Push
	// 1. ID, 2. Author, 3. Description, 4. Submitted Date
	// 5. Type, 6. Status, 7. Resolved Date, 8. Resolver, 9. Receipt
	reimbArray.push(reimb.id);
	reimbArray.push(reimb.author);
	reimbArray.push(reimb.description);
	reimbArray.push(reimb.submittedDate);
	reimbArray.push(reimb.type);
	reimbArray.push(reimb.status);
	reimbArray.push(reimb.resolvedDate);
	reimbArray.push(reimb.resolver);
	reimbArray.push(reimb.receipt);
	
	return reimbArray;
}

function launchReceiptModal(id) {
	fetchReceiptCallback(id);
	
	receiptModal.style.display = "block";
}