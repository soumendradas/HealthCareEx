$(document).ready(function () {
    $("#firstNameError").hide();
    $("#lastNameError").hide();
    $("#genderError").hide();
    $("#mobileError").hide();
    $("#dateOfBirthError").hide();
    $("#martialStatusError").hide();
    $("#presentAddressError").hide();
    $("#communicationAddressError").hide();
    $("#mediHistoryError").hide();
    $("#noteError").hide();
    $("#ifOther").attr('readonly','true')

    var firstNameError = false;
    var lastNameError = false;
    var genderError = false;
    var mobileError = false;
    //var dateOfBirthError = false;
    var martialStatusError = false;
    var presentAddressError = false;
    var communicationAddressError = false;
    var mediHistoryError = false;
    //var noteError = false;
//---------------------------------------------------------------------------------------
    function firstName_validation() {
      var val = $("#firstName").val();
      var exp = /^[A-Za-z\s]{1,25}$/;

      if (val == "") {
        $("#firstNameError").show();
        $("#firstNameError").html("<b>First Name</b> not to be empty");
        $("#firstNameError").css("color", "red");
        firstNameError = false;
      } else if (!exp.test(val)) {
        $("#firstNameError").show();
        $("#firstNameError").html(
          "<b>First Name</b> must be alphabet only and 1 to 25 chars"
        );
        $("#firstNameError").css("color", "red");
        firstNameError = false;
      } else {
        $("#firstNameError").hide();
        firstNameError = true;
      }

      return firstNameError;
    }

    function lastName_validation() {
      var val = $("#lastName").val();
      var exp = /^[A-Za-z]{1,20}$/;

      if (val == "") {
        $("#lastNameError").show();
        $("#lastNameError").html("<b>Last Name</b> not to be empty");
        $("#lastNameError").css("color", "red");
        lastNameError = false;
      } else if (!exp.test(val)) {
        $("#lastNameError").show();
        $("#lastNameError").html(
          "<b>Last Name</b> must be alphabet only and 1 to 20 chars"
        );
        $("#lastNameError").css("color", "red");
        lastNameError = false;
      } else {
        $("#lastNameError").hide();
        lastNameError = true;
      }

      return lastNameError;
    }

    function gender_validation(){
      var val = $("#gender").val();

      if(val ==""){
        $("#genderError").show();
        $("#genderError").html("Please select <b>Gender</b>");
        $("#genderError").css("color", "red");
        genderError = false;
      }else{
        $("#genderError").hide();
        genderError = true;
      }

      return genderError;
    }

    function mobile_validation(){
      var val = $("#mobile").val();
      var exp = /^[0-9]{10}$/;

      if (val == "") {
        $("#mobileError").show();
        $("#mobileError").html("<b>Mobile Number</b> not to be empty");
        $("#mobileError").css("color", "red");
        mobileError = false;
      } else if (!exp.test(val)) {
        $("#mobileError").show();
        $("#mobileError").html(
          "<b>Mobile Number</b> must be 10 numbers only"
        );
        $("#mobileError").css("color", "red");
        mobileError = false;
      } else {
        $("#mobileError").hide();
        mobileError = true;
      }

      return mobileError;

    }

    //date of Birth validation pending

    function martialStatus_validation(){
      var len = $("input[name='martialStatus']:checked").length;

      if(len == 0){
        $("#martialStatusError").show();
        $("#martialStatusError").html("Please select <b>Martial Status</b>");
        $("#martialStatusError").css("color", "red");
        martialStatusError = false;
      }else{
        $("#martialStatusError").hide();
        martialStatusError = true;
      }

      return martialStatusError;
    }

    function presentAddress_validation(){
      var val = $("#presentAddress").val();

      if(val == ""){
        $("#presentAddressError").show();
        $("#presentAddressError").html("Please Enter <b>Present Address</b>");
        $("#presentAddressError").css("color", "red");
        presentAddressError = false;
      }else{
        $("#presentAddressError").hide();
        presentAddressError = true;
      }

      return presentAddressError;
      
    }

    function presentAddress_validation(){
      var val = $("#presentAddress").val();

      if(val == ""){
        $("#presentAddressError").show();
        $("#presentAddressError").html("Please Enter <b>Present Address</b>");
        $("#presentAddressError").css("color", "red");
        presentAddressError = false;
      }else{
        $("#presentAddressError").hide();
        presentAddressError = true;
      }

      return presentAddressError;
      
    }

    function communicationAddress_validation(){
      var val = $("#communicationAddress").val();

      if(val == ""){
        $("#communicationAddressError").show();
        $("#communicationAddressError").html("Please Enter <b>Communication Address</b>");
        $("#communicationAddressError").css("color", "red");
        communicationAddressError = false;
      }else{
        $("#communicationAddressError").hide();
        communicationAddressError = true;
      }

      return communicationAddressError;
      
    }

    function mediHistory_validation(){
      var len = $("input[name='mediHistory']:checked").length;

      if(len == 0){
        $("#mediHistoryError").show();
        $("#mediHistoryError").html("Please Select <b>Medical History</b>");
        $("#mediHistoryError").css("color", "red");
        mediHistoryError = false;
      }else{
        $("#mediHistoryError").hide();
        mediHistoryError = true;
      }

      return mediHistoryError;
    }




    //--------------------------------------------------------------------------------
    $("#firstName").keyup(function () {
      firstName_validation();
    });

    $("#lastName").keyup(function () {
      lastName_validation();
    });

    $("#gender").change(function(){
      gender_validation();
    });

    $("#mobile").keyup(function(){
      mobile_validation();
    });

    $("input[name='martialStatus']").click(function(){
      martialStatus_validation();
    });

    $("#address").click(function(){
      if(this.checked){
      $("#communicationAddress").val($("#presentAddress").val());
      }else{
        $("#communicationAddress").val("");
      }
    });

    $("#presentAddress").keyup(function (){
      presentAddress_validation();
    });

    $("#communicationAddress").keyup(function (){
      communicationAddress_validation();
    });

    $("input[name='mediHistory']").click(function(){
      mediHistory_validation();
    })

    $("#mediHistory7").click(function(){
      if(this.checked){
        $("#ifOther").removeAttr('readonly');
      }else{
        $("#ifOther").attr('readonly','true');
      }
    });

    $("#pat_form").submit(function (){
      firstName_validation();
      lastName_validation();
      gender_validation();
      martialStatus_validation();
      presentAddress_validation();
      communicationAddress_validation();
      mediHistory_validation();
      mobile_validation();

      if(firstNameError&&
        lastNameError&&
        genderError&&
        martialStatusError&&
        presentAddressError&&
        communicationAddressError&&
        mediHistoryError&&
        mobileError){
          return true;
        }else return false;
    });


  });