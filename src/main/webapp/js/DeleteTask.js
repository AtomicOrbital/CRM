$(document).ready(function(){
	// Thuc hien call API xoa task
	$(document).on('click', '.edit-delete',function(event){
		event.preventDefault();
		
		let taskId = $(this).data('id');
		let This = $(this);
		console.log("taskId", taskId);
		$.ajax({
			url: "http://localhost:8080/ProjectCRM/api/delete-task?TaskID=" + taskId,
			method: "DELETE",
			success: function(response){
				if(response.statusCode === 200){
					alert(response.message);
					
					// Xóa project đó ra khỏi giao diện
					//This.closest('tr').remove();
					//This.closest('tr').next().remove(); // Xóa dòng kế tiếp
					window.location.reload(); 
				} else {
					alert(response.message);
					window.location.reload();				
				}
			},
			error: function(response){
				alert("Error, Please try again");
			}
		})
	});
})