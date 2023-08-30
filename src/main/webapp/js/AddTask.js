$(document).ready(function() {
	$("#submitTask").submit(function(event) {
		event.preventDefault();

		function convertDate(inputFormat) {
			const [datePart, timePart] = inputFormat.split(' ');
			const [day, month, year] = datePart.split('/');
			return `${year}-${month}-${day} ${timePart}`;
		}

		// Thu thập dữ liệu từ form
		const projectId = $('[name="ProjectId"]').val();
		const taskName = $('.add-task').val();
		const executorId = $('#executorId').val();
		const startDate = convertDate($('.startDate').val());
		const endDate = convertDate($('.endDate').val());
		const statusId = $('[name="statusId"]').val();

		const data = {
			TaskName: taskName,
			StartDate: startDate,
			EndDate: endDate,
			ExcutorID: executorId,
			ProjectID: projectId,
			StatusID: statusId
		}
		console.log(data);

		$.ajax({
			url: "http://localhost:8080/ProjectCRM/api/add-task",
			type: "POST",
			data: {
				TaskName: taskName,
				StartDate: startDate,
				EndDate: endDate,
				ExecutorID: executorId,
				ProjectID: projectId,
				StatusID: statusId
			},
			success: function(response) {
				console.log("Response from server: ", response);
				if (response.statusCode === 200) {
					alert("Thêm TaskTask thành công! " + response.message);
					window.location.reload();
				} else {
					alert("Thêm Task không thành công " + response.message);
					window.location.reload();
				}
			},
			error: function() {
				alert("error, Please try again");
			}
		})
	})
})