$(document).ready(function () {
  $("#firstNameError").hide();
  $("#lastNameError").hide();
  $("#emailError").hide();
  $("#addressError").hide();
  $("#mobileError").hide();
  $("#genderError").hide();
  $("#noteError").hide();
  $("#photoError").hide();
  $("#specializationError").hide();

  var firstNameError = false;
  var lastNameError = false;
  var emailError = false;
  var addressError = false;
  var mobileError = false;
  var genderError = false;
  var noteError = false;
  var specializationError = false;

  function validate_firstName() {
    var val = $("#firstName").val();
    var exp = /^[A-Za-z]{1,15}$/;

    if (val == "") {
      $("#firstNameError").show();
      $("#firstNameError").html("<b>First Name</b> not be empty");
      $("#firstNameError").css("color", "red");
      firstNameError = false;
    } else if (!exp.test(val)) {
      $("#firstNameError").show();
      $("#firstNameError").html("<b>First Name</b> must be 1-15 alphabet");
      $("#firstNameError").css("color", "red");
      firstNameError = false;
    } else {
      $("#firstNameError").hide();
      firstNameError = true;
    }

    return firstNameError;
  }
  function validate_lastName() {
    var val = $("#lastName").val();
    var exp = /^[A-Za-z]{1,15}$/;

    if (val == "") {
      $("#lastNameError").show();
      $("#lastNameError").html("<b>Last Name</b> not be empty");
      $("#lastNameError").css("color", "red");
      lastNameError = false;
    } else if (!exp.test(val)) {
      $("#lastNameError").show();
      $("#lastNameError").html("<b>Last Name</b> must be 1-15 alphabet");
      $("#lastNameError").css("color", "red");
      lastNameError = false;
    } else {
      $("#lastNameError").hide();
      lastNameError = true;
    }

    return lastNameError;
  }

  function validate_email() {
    var val = $("#email").val();
    var exp =
      /^[A-Za-z0-9\.\-\_]{1,20}[@][A-Za-z0-9\.]{1,15}[.][A-Za-z]{1,10}$/;
    if (val == "") {
      $("#emailError").show();
      $("#emailError").html("<b>Email</b> not be empty");
      $("#emailError").css("color", "red");
      emailError = false;
    } else if (!exp.test(val)) {
      $("#emailError").show();
      $("#emailError").html("Please enter valid <b>Email</b>");
      $("#emailError").css("color", "red");
      emailError = false;
    } else {
      var id = 0;
      if ($("#id").val() != undefined) {
        id = $("#id").val();
        emailError = true;
      }
      $.ajax({
        url: "checkEmail",
        data: {"email": val, "id": id},
        success: function (response) {
          if (response != "") {
            $("#emailError").show();
            $("#emailError").html(response);
            $("#emailError").css("color", "red");
            emailError = false;
          } else {
            $("#emailError").hide();
            emailError = true;
          }
        }
      });
    }
    return emailError;
  }

  function validate_address() {
    var val = $("#address").val();
    var exp = /^[A-Za-z\s\,\;\:\/\&\-]{5,40}$/;

    if (val == "") {
      $("#addressError").show();
      $("#addressError").html("<b>Address</b> not be empty");
      $("#addressError").css("color", "red");
      addressError = false;
    } else if (!exp.test(val)) {
      $("#addressError").show();
      $("#addressError").html("<b>Address</b> must be 5-40 characters");
      $("#addressError").css("color", "red");
      addressError = false;
    } else {
      $("#addressError").hide();
      addressError = true;
    }
    return addressError;
  }

  function validate_mobile() {
    var val = $("#mobile").val();
    var exp = /^[0-9]{10}$/;

    if (val == "") {
      $("#mobileError").show();
      $("#mobileError").html("<b>Mobile Number</b> not be empty");
      $("#mobileError").css("color", "red");
      mobileError = false;
    } else if (!exp.test(val)) {
      $("#mobileError").show();
      $("#mobileError").html("<b>Mobile Number</b> must be 10 numbers");
      $("#mobileError").css("color", "red");
      mobileError = false;
    } else {
      var id = 0;
      if ($("#id").val() != undefined) {
        id = $("#id").val();
        mobileError = true;
      }
      $.ajax({
        url: "checkMobile",
        data: { "mobile": val, "id": id },
        success: function (response) {
          if (response != "") {
            $("#mobileError").show();
            $("#mobileError").html(response);
            $("#mobileError").css("color", "red");
            mobileError = false;
          } else {
            $("#mobileError").hide();
            mobileError = true;
          }
        }
      });
    }
    return mobileError;
  }

  function validate_gender() {
    var len = $("input[name='gender']:checked").length;
    if (len == 0) {
      $("#genderError").show();
      $("#genderError").html("please select <b>Gender</b>");
      $("#genderError").css("color", "red");
      genderError = false;
    } else {
      $("#genderError").hide();
      genderError = true;
    }
    return genderError;
  }

  function validate_note() {
    var val = $("#note").val();
    var exp = /^[A-Za-z0-9\s\.\,\;\:\'\"\#\*\?\`]{10,250}$/;
    if (val == "") {
      $("#noteError").show();
      $("#noteError").html("<b>Note </b>to be empty");
      $("#noteError").css("color", "red");
      noteError = false;
    } else if (!exp.test(val)) {
      $("#noteError").show();
      $("#noteError").html("<b>Note </b>must be 10-250 characters");
      $("#noteError").css("color", "red");
      noteError = false;
    } else {
      $("#noteError").hide();
      noteError = true;
    }

    return noteError;
  }

  function validate_specialization(){
    var val = $("#specialization").val();

    if(val == ""){
      $("#specializationError").show();
      $("#specializationError").html("please select <b>Specialization </b>");
      $("#specializationError").css("color", "red");
      specializationError = false;
    }else{
      $("#specializationError").hide();
      specializationError = true;
    }
    return specializationError;
  }
  
  $("#firstName").keyup(function () {
    validate_firstName();
  });

  $("#lastName").keyup(function () {
    validate_lastName();
  });

  $("#email").keyup(function () {
    validate_email();
  });

  $("#address").keyup(function () {
    validate_address();
  });

  $("#mobile").keyup(function () {
    validate_mobile();
  });

  $("input[name='gender']").click(function () {
    validate_gender();
  });

  $("#note").keyup(function () {
    validate_note();
  });

  $("#specialization").click(function () {
    validate_specialization()
  })

  $("#doc_form").submit(function () {
    validate_firstName();
    validate_lastName();
    validate_email();
    validate_address();
    validate_mobile();
    validate_gender();
    validate_note();
    validate_specialization();
    if (
      firstNameError &&
      lastNameError &&
      emailError &&
      addressError &&
      mobileError &&
      genderError &&
      noteError &&
      specializationError
    ) {
      return true;
    } else return false;
  });
});
