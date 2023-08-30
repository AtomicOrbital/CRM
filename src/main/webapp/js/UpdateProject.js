$(document).ready(function() {
	$(".edit-project").click(function() {

		// lấy dòng chứa nút sửa được nhấp
		let row = $(this).closest("tr");

		// Trich xuat gia tri tu cac cot
		let projectId = row.find('td:nth-child(1)').text();
		let projectName = row.find('td:nth-child(2)').text();
		let startDate = row.find('td:nth-child(3)').text();
		let endDate = row.find('td:nth-child(4)').text();

		/*console.log({
			projectId,
			projectName,
			startDate,
			endDate
		});*/

		//Đổ giá trị vào vào các ô input trong modal
		$('.editProjectId').val(projectId);
		$('.editProjectName').val(projectName);
		$('.editStartDate').val(startDate);
		$('.editEndDate').val(endDate);

		//Bật modal	
		$("#projectModal").modal("show");
	});

	$("#updateProject").click(function(event) {
		event.preventDefault();
		// thực hiện dom đến các ô input lấy giá trị call API

		function convertDate(inputFormat) {
			const [day, month, year] = inputFormat.split("-");
			return `${year}-${month}-${day}`;
		}

		let projectId = $('.editProjectId').val();
		let projectName = $('.editProjectName').val();
		let startDate = convertDate($('.editStartDate').val());
		let endDate = convertDate($('.editEndDate').val());
		console.log(startDate);
		console.log(endDate);
		$.ajax({
			url: "http://localhost:8080/ProjectCRM/api/update-project",
			method: "POST",
			data: {
				ProjectID: projectId,
				ProjectName: projectName,
				StartDate: startDate,
				EndDate: endDate
			},
			success: function(response) {
				console.log("response", response);
				if (response.statusCode === 200) {
					alert('Cập nhật thành công! ' + response.message);
					window.location.reload();
				} else {
					alert('Cập nhật thất bại: ' + response.message);
				}
			},
			error: function(error) {
				alert('Có lỗi xảy ra: ' + error.responseText);
			}
		})
	});
});

