$(document).ready(function() {
	let dataTable = $('#example').DataTable();

	//Lưu trữ danh sách project,executor,user
	let executors = [];
	let projects = [];
	let statuses = [];
	// Call Api lấy danh sách người dùng
	$.ajax({
		url: "http://localhost:8080/ProjectCRM/api/get-users",
		method: "GET",
		success: function(response) {
			executors = response;
			//console.log("executors", executors);
		}
	});

	// Call API lấy danh sách projects
	$.ajax({
		url: "http://localhost:8080/ProjectCRM/api/get-projects",
		method: "GET",
		success: function(response) {
			projects = response;
			//console.log("projects", projects);
		}
	});

	// Call API lấy danh sách statusTask
	$.ajax({
		url: "http://localhost:8080/ProjectCRM/api/get-statusTask",
		method: "GET",
		success: function(response) {
			statuses = response;
			//console.log("status", statuses);
		}
	});

	function updateModal(taskId) {
		$.ajax({
			url: `http://localhost:8080/ProjectCRM/api/get-task-by-id?taskId=${taskId}`,
			type: "GET",
			success: function(task) {
				//console.log("task", task);
				// Cập nhật thông tin ô input của modal
				$('.userId').val(task.taskId);
				$('.editTaskName').val(task.taskName);
				$('.editStartDate').val(task.startDate);
				$('.editEndDate').val(task.endDate);

				// Xóa các option hiện có trong thẻ select
				$("#editExecutorId").empty();
				executors.forEach(function(executor) {
					$('#editExecutorId').append(new Option(executor.fullName, executor.id));
				});
				// Hiện executor hiện tại đang click
				$('.editExecutorName').text(task.user.fullName);
				// Chọn executor hiện tại cho task
				$('#editExecutorId').val(task.executorId);

				// Xóa các option hiện có trong thẻ select
				$("#editProjectId").empty();
				projects.forEach(function(project) {
					$("#editProjectId").append(new Option(project.projectName, project.projectId));
				});
				//Hiện project hiện tại đang click
				$('.editProjectName').text(task.project.projectName);
				//Chọn project hiện tại cho task
				$('#editProjectId').val(task.projectId);

				// Xóa các option hiện có trong thẻ select
				$("#editStatusId").empty();
				// Thêm các option mới
				statuses.forEach(function(status) {
					$("#editStatusId").append(new Option(status.statusName, status.statusID));
				});
				// Hiện status hiện tại đang click
				$('.editStatusName').text(task.statusTask.statusName);
				// Chọn statusTask hiện tại
				$('#editStatusId').val(task.statusId);
			},
			error: function(error) {
				console.log('Error:', error);
			}
		})
	}

	$(document).on('click', '.edit-button', function() {
		const taskId = $(this).data('id');
		//console.log("taskId: " + taskId);
		updateModal(taskId);

		$("#updateTask").click(function(event) {
			event.preventDefault();
			const taskId = $('.userId').val();
			const taskName = $('.editTaskName').val();
			const startDate = $('.editStartDate').val();
			const endDate = $('.editEndDate').val();
			const executorId = $('#editExecutorId').val();
			const projectId = $('#editProjectId').val();
			const statusId = $('#editStatusId').val();

			console.log({
				TaskName: taskName,
				StartDate: startDate,
				EndDate: endDate,
				ExecutorID: executorId,
				ProjectId: projectId,
				StatusId: statusId,
				TaskID: taskId
			})

			// Gọi API để cập nhật công việc
			$.ajax({
				url: "http://localhost:8080/ProjectCRM/api/update-task",
				method: "POST",
				data: {
					TaskName: taskName,
					StartDate: startDate,
					EndDate: endDate,
					ExecutorID: executorId,
					ProjectID: projectId,
					StatusID: statusId,
					TaskID: taskId
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
			});
		});

	});




	$.ajax({
		url: "http://localhost:8080/ProjectCRM/api/get-tasks",
		type: "GET",
		success: function(response) {
			dataTable.clear();
			const taskRows = response.map((task, index) => {
				return [
					task.taskId,
					task.taskName,
					task.project.projectName,
					task.user.fullName,
					task.startDate,
					task.endDate,
					task.statusTask.statusName,
					`
            			<a href="#" data-id="${task.taskId}" class="btn btn-sm btn-primary edit-button" data-toggle="modal" data-target="#editTaskModal">Sửa</a>
            			<a href="#" id="task-${task.taskId}" data-id="${task.taskId}" class="btn btn-sm btn-danger edit-delete">Xóa</a>	
  						<a href="#" data-id="${task.taskId}" class="btn btn-sm btn-info">Xem</a>
          			`
				];
			});

			dataTable.rows.add(taskRows).draw();

			// Thêm thuộc tính CSS vào các ô
			//$("#example td").css("white-space", "nowrap");
		},
		error: function(error) {
			console.log(error);
		}
	});
	$(document).on('click', '.btn-info', function() {
		const taskId = $(this).data('id');
		window.location.href = `groupwork-details.jsp?taskId=${taskId}`;
	});

});
