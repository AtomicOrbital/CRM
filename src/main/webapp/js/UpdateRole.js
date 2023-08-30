$(document).ready(function() {
	$('.btn-sua').click(function() {
		let row = $(this).closest('tr');
		let roleId = row.find('td:nth-child(1)').text();
		let roleName = row.find('td:nth-child(2)').text();
		let description = row.find('td:nth-child(3)').text();

		console.log({
			roleId: roleId,
			roleName: roleName,
			description: description
		});

		//Đổ dữ liệu ra modal
		$('.roleId').val(roleId);
		$('.roleName').val(roleName);
		$('.description').val(description);

		// bật modal
		$('#exampleModal').modal('show');
	});
	$('#updateRole').click(function(event) {
		event.preventDefault();

		let roleId = $('.roleId').val();
		let roleName = $('.roleName').val();
		let description = $('.description').val();

		$.ajax({
			url: "http://localhost:8080/ProjectCRM/api/role/edit-role",
			method: "POST",
			data: {
				RoleName: roleName,
				Description: description,
				RoleID: roleId
			},
			success: function(response) {
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
	})
});


