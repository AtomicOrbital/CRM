$(document).ready(function() {
	$("#addProjectForm").submit(function(event) {
		event.preventDefault();

		function convertDate(inputFormat) {
			const [day, month, year] = inputFormat.split('/');
			return `${year}-${month}-${day}`;
		}

		const projectName = $(".ProjectName").val();
		const startDate = convertDate($(".StartDate").val());
		const endDate = convertDate($(".EndDate").val());

		const dummy = {
			ProjectName: projectName,
			StartDate: startDate,
			EndDate: endDate
		}
		console.log(dummy);
		$.ajax({
			url: "http://localhost:8080/ProjectCRM/add-project",
			type: "POST",
			data: {
				ProjectName: projectName,
				StartDate: startDate,
				EndDate: endDate
			},
			success: function(response) {
				console.log("Response from server: ", response);
				if (response.statusCode === 200) {
					alert("Thêm project thành công! " + response.message);
					window.location.reload();
				} else {
					alert("Thêm project không thành công " + response.message);
					window.location.reload();
				}
			},
			error: function() {
				alert("error, Please try again");
			}
		})
	})
})