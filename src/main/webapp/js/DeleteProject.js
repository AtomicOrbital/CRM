$(document).ready(function(){
	$(".delete-user-btn").click(function(event){
		event.preventDefault();
		
		let projectId = $(this).data('id');
		let This = $(this);
		console.log("projectId", projectId);
		$.ajax({
			url: "http://localhost:8080/ProjectCRM/api/delete-project?ProjectID=" + projectId,
			method: "DELETE",
			success: function(response){
				if(response.statusCode === 200){
					alert(response.message);
					
					// Xóa project đó ra khỏi giao diện
					let projectId = This.attr('data-id');
					$(`#project-${projectId}`).remove();
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