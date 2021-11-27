$(document).ready(function (){
    $("#docObError").hide();
    var docObError = false;

    function validate_docObRequired(){
        var val = $("#docOb").val();
        if(val == ""){
            $("#docObError").show();
            $("#docObError").html("Please select one File!")
            $("#docObError").css('color', 'red');
            docObError =false;
        }
        return docObError;
    }

    function validate_docOb(ob){
        //full fileName object
        // console.log(ob);

        var fname = ob.name;

        var fsize = ob.size;

        var fext = fname.substring(fname.lastIndexOf(".")+1);

        var allowedExt = ["png","jpg","jpeg","doc","docx","zip"];

        if($.inArray(fext, allowedExt)==-1){
            $("#docObError").show();
            $("#docObError").html("Allowed Files "+allowedExt)
            $("#docObError").css('color', 'red');
            docObError =false;
        }else if(fsize <= 1024*100){    //min 100kb
            $("#docObError").show();
            $("#docObError").html("Files not allowed less than 100kb")
            $("#docObError").css('color', 'red');
            docObError =false;
        }else if(fsize > 1024*400){     //max 400kb
            $("#docObError").show();
            $("#docObError").html("Files not allowed greater than 400kb")
            $("#docObError").css('color', 'red');
            docObError =false;
        }else{
            $("#docObError").hide();
            docObError = true;
        }

        return docObError;
    }

    $("input[type = 'file'][name='docOb']").change(function(){
        validate_docOb(this.files[0]);
    })

    $("#docForm").submit(function () {
        validate_docObRequired();
        if(docObError) return true;
        else return false;
    })

    
})