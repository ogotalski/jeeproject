function checkInput() {
	var name_val = document.getElementById("login");
	var pas1_val = document.getElementById("password");
	var pas2_val = document.getElementById("password2");

	var email_val = document.getElementById("email");

	var phone_val = document.getElementById("phone");

	var login_pat = /^[_a-zA-Z][_a-zA-Z0-9]{3,8}$/;
	var password_pat = /^\w{3,}$/;
	var email_pat = /^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+$/;
	var phone_pat = /^[1-9]{1}[0-9]+$/;
	markErrorField(false);
	var errMess = "";
	if (!name_val.value) {
		errMess += "Your Login is empty.\n";
		markErrorField(name_val);
	} else if (!login_pat.test(name_val.value)) {
		errMess += "Your Login is incorrect.\n";
		markErrorField(name_val);
	}
	if (!email_val.value) {
		errMess += "Your Email is empty.\n";
		markErrorField(email_val);
	} else if (!email_pat.test(email_val.value)) {
		errMess += "Your Email is incorrect.\n";
		markErrorField(email_val);
	}
	if (!phone_val.value) {
		errMess += "Your Phone number is empty.\n";
		markErrorField(phone_val);
	} else if (!phone_pat.test(phone_val.value)) {
		errMess += "Your Phone number is incorrect.\n";
		markErrorField(phone_val);
	}
	if (!pas1_val.value) {
		errMess += "Your Password is empty.\n";
		markErrorField(pas1_val);
	} else if (!pas2_val.value) {
		errMess += "Your Password confirmation is empty.\n";
		markErrorField(pas2_val);
	} else if (pas1_val.value != pas2_val.value) {
		errMess += "Your Password confirmation does not equal to your Password.\n";
		markErrorField(pas1_val);
		markErrorField(pas2_val);
	} else if (!password_pat.test(pas1_val.value)) {
		errMess += "Your Password is incorrect.\n";
		markErrorField(pas1_val);
		markErrorField(pas2_val);
	}

	if (errMess) {
		var err = document.getElementById("errorLabel");
		err.innerHTML = "<i>" + errMess + "</i>";
		return false;
	}
	var err = document.getElementById("errorLabel");
	err.innerHTML = "";
	return true;
}
function markErrorField(_element) {
	var allLabels = document.getElementsByTagName("LABEL");
	if (_element) {
		for ( var i = 0; i < allLabels.length; i++) {
			if (allLabels[i].htmlFor == _element.id)
				allLabels[i].style.color = "red";
		}
	} else {
		for ( var i = 0; i < allLabels.length; i++) {
			allLabels[i].style.color = "black";
		}
	}
}
function sendFormSignUP(sAction) {

	if (checkInput()) {
		document.signUp.action.value = sAction;
		document.signUp.submit();
	}
}
function sendFormPlay(sIDForm, sAction, sId) {

	var form = document.forms[sIDForm];
	form.action.value = sAction;
	form.dateId.value = sId;
	form.submit();
}
function sendForm(sIDForm, sAction) {
	var form = document.forms[sIDForm];
	form.action.value = sAction;
	form.submit();

}
function showAuthorInfo(sAuthor) {
	var searchEngine = 'https://www.google.com/search?q=';
	window.open(searchEngine + sAuthor, '_blank');
}

function change(obj) {

	var nextDiv = obj.nextSibling;
	if (nextDiv.style.display == 'block') {
		obj.innerText = 'Description'
		nextDiv.style.display = 'none';
	} else {
		nextDiv.style.display = 'block';
		obj.innerText = 'Hide description'

	}
}
function changeSum(obj, cost) {
	var count = obj.value;
	var sum = 0;
	if (!isNaN(count)) {
		
		var allLabels = document.getElementsByName("label");
		for ( var i = 0; i < allLabels.length; i++) {
			if (allLabels[i].htmlFor == obj.id) {
				allLabels[i].innerHTML = Number(count) * cost;
			}
			sum += Number(allLabels[i].innerHTML);
		}

		var sumlabel = document.getElementById("sumLabel");
		sumlabel.innerHTML = sum;
	} else {
		alert("Illegal input");
	}
}
function changeSumOrder(obj, cost) {
	var count = obj.value;
	if (!isNaN(count)) {
		
		var allLabels = document.getElementsByName("label");
		for ( var i = 0; i < allLabels.length; i++) {
			if (allLabels[i].htmlFor == obj.id) {
				allLabels[i].innerHTML = Number(count) * cost;
			}
		}
} else {
		alert("Illegal input");
	}
}