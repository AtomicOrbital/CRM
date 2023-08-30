$(document).ready(function() {
    $('#addUserForm').submit(function(event) { 
        event.preventDefault();

        let fullName = $("#Fullname").val();
        let email = $("#email").val();
        let password = $("#password").val();
        let phone = $("#Phone").val();
        let address = $("#Address").val();
        console.log("Address",address);  
        let roleId = $("#Role").val();  

        $.ajax({
            url: 'http://localhost:8080/ProjectCRM/api/add-user',
            method: 'POST',
            data: {
                Fullname: fullName,  
                email: email,
                password: password,
                Phone: phone,
                Address: address,
                RoleID: roleId  
            },
            success: function(response) {
                if (response.statusCode === 200) {  
                    alert("Thêm người dùng thành công!");
                } else {
                    alert("Thêm người dùng không thành công")
                }
            },
           error: function() {
                alert("error, Please try again");
            }
        });
    });
});
