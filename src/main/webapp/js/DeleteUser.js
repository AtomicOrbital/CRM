$(document).ready(function(){
	$(".delete-user-btn").click(function(event){
		event.preventDefault();
		
		let userId = $(this).data('id');
		let This = $(this);
		console.log("userId", userId);
		$.ajax({
			url: "http://localhost:8080/ProjectCRM/api/delete-user?UserID=" + userId,
			method: "DELETE",
			success: function(response){
				if(response.statusCode === 200){
					alert(response.message);
					
					// Xóa người dùng đó ra khỏi giao diện
					let userId = This.attr('data-id');
					$(`#user-${userId}`).remove();
					//window.location.reload(); 
				} else {
					alert(response.message);				
				}
			},
			error: function(response){
				alert("Error, Please try again");
			}
		})
	})
})