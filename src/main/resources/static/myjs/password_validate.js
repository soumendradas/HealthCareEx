$(document).ready(function () {
  $("#currentPasswordError").hide();
  $("#newPasswordError").hide();
  $("#reEnterPassError").hide();

  let currentPasswordError = false;
  let newPasswordError = false;
  let reEnterPassError = false;

  function validate_currentPass() {
    let val = $("#currentPassword").val();

    if (val == "") {
      $("#currentPasswordError").show();
      $("#currentPasswordError").html("Please Enter your Current Password");
      $("#currentPasswordError").css("color", "red");
      currentPasswordError = false;
    } else {
      $("#currentPasswordError").hide();
      currentPasswordError = true;
    }

    return currentPasswordError;
  }

  function validate_newPass() {
    let val = $("#newPassword").val();
    if (val == "") {
      $("#newPasswordError").show();
      $("#newPasswordError").html("Please Enter New Password");
      $("#newPasswordError").css("color", "red");
      newPasswordError = false;
    }else{
        $("#newPasswordError").hide();
        newPasswordError = true;
    }
    return newPasswordError;
  }

  function validate_reenterPass(){
      let val = $("#reEnterPass").val();
      if(val == ""){
        $("#reEnterPassError").show();
        $("#reEnterPassError").html("Please ReEnter New Password");
        $("#reEnterPassError").css("color", "red");
        reEnterPassError = false;
      }else if(val != $("#newPassword").val()){
        $("#reEnterPassError").show();
        $("#reEnterPassError").html("Password did not match");
        $("#reEnterPassError").css("color", "red");
        reEnterPassError = false;
      }else{
        $("#reEnterPassError").hide();
        reEnterPassError= true;
      }
      return reEnterPassError;
  }

  $("#currentPassword").keyup(function () {
      validate_currentPass();
  })

  $("#newPassword").keyup(function (){
      validate_newPass();
  })

  $("#reEnterPass").keyup(function (){
      validate_reenterPass();
  })

  $("#pass_form").submit(function () {
      validate_currentPass();
      validate_newPass();
      validate_reenterPass();

      if(currentPasswordError
        && newPasswordError
        && reEnterPassError){
            return true;
        } return false;
  })
});
