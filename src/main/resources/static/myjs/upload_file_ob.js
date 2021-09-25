function upload(){
    var file = document.getElementById("image");
    var formData = new FormData();
    formData.append("image", file.files[0]);

    var input = {
        url: "https://api.imgbb.com/1/upload?key=9aeb243a991c9fa850430a58851f6ba7",
        method: "POST",
        timeout: 0,
        processData: false,
        mimetype: "multipart/form-data",
        contentType: false,
        data: formData
    }

    $.ajax(input).done(function(response){
        var job = JSON.parse(JSON.stringify(response));
        $("#photoLoc").val(job.data.url);
    })
}