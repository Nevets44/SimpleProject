/**
 * 
 */

function showForm() {
	var display = $(".account-display")[0];
	var form = $(".account-form")[0];
	$("#showForm").hide(); // Hide the button used to show this since the form will show up
	
	
	display.style.display = "none";
	form.style.display = "block";
}