$(document).ready(function() {
  // Gọi API để lấy danh sách người dùng
  $.ajax({
    url: 'http://your-api-endpoint/users',
    type: 'GET',
    success: function(data) {
      // Xóa các dòng trong tbody hiện tại
      $('#example tbody').empty();
      
      // Thêm các dòng mới từ dữ liệu API
      data.forEach(function(user, index) {
        $('#example tbody').append(`
          <tr id="user-${user.id}">
            <td>${index + 1}</td>
            <td>${user.email}</td>
            <td>${user.password}</td>
            <td>${user.fullName}</td>
            <td>${user.address}</td>
            <td>${user.phone}</td>
            <td>${user.role.roleName}</td>
            <td>
              <a data-id="${user.id}" href="#" class="btn btn-sm btn-primary btn-edit">Sửa</a>
              <a data-id="${user.id}" href="#" class="btn btn-sm btn-danger delete-user-btn">Xóa</a>
              <a href="user-details.html" class="btn btn-sm btn-info">Xem</a>
            </td>
          </tr>
        `);
      });
      
      // Khởi tạo lại DataTable
      $('#example').DataTable().destroy();
      $('#example').DataTable();
    }
  });
});
