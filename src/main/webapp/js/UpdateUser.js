$(document).ready(function() {
	$('.btn-edit').click(function() {
		// Lấy dòng chứa nút "Sửa" được nhấp
		let row = $(this).closest('tr');

		// Trích xuất giá trị từ các cột
		let userId = row.find('td:nth-child(1)').text();
		let email = row.find('td:nth-child(2)').text();
		let password = row.find('td:nth-child(3)').text();
		let fullName = row.find('td:nth-child(4)').text();
		let address = row.find('td:nth-child(5)').text();
		let phone = row.find('td:nth-child(6)').text();
		let roleName = row.find('td:nth-child(7)').text();
		console.log({
			userId: userId,
			email: email,
			password: password,
			fullname: fullName,
			phone: phone,
			address: address,
			roleName: roleName
		});
		// Ánh xạ value tương ứng của option
		const roleMapping = {
			"ADMIN": "1",
			"USER": "2",
			"LEADER": "3"
		}

		let roleNameValue = roleMapping[roleName];
		console.log("roleNameValue",roleNameValue);
		// Đổ giá trị vào các input trong modal
		$('.userId').val(userId);
		$('.editEmail').val(email);
		$('.editPassword').val(password);
		$('.editFullName').val(fullName);
		$('.editPhone').val(phone);
		$('.editAddress').val(address);
		$('.editRole').val(roleNameValue);

		
		// bật modal
		$('#editUserModal').modal('show');
	});

	$("#updateUser").click(function(event) {
		event.preventDefault();
		 // Lấy giá trị từ các input trong modal
        let userId = $('.userId').val();
        let email = $('.editEmail').val();
        let password = $('.editPassword').val();
        let fullName = $('.editFullName').val();
        let phone = $('.editPhone').val();
        let address = $('.editAddress').val();
        let roleNameValue = $('.editRole').val();
		console.log("Sending UserID:", userId);

		$.ajax({
			url: "http://localhost:8080/ProjectCRM/api/update-user",
			method: "POST",
			data: {
				UserID: userId,
				email: email,
				password: password,
				Fullname: fullName,
				Address: address,
				Phone: phone,
				RoleID: roleNameValue
			},
			success: function(response) {
				if (response.statusCode === 200) {
					alert('Cập nhật thành công!' + response.message);
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
