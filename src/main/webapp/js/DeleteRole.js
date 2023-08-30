$(document).ready(function() {
	$('.btn-xoa').click(function() {

		$(this) // Đại diện cho thẻ đang click

		let id = $(this).attr("id-role");
		let This = $(this);
		//alert("Hello world " + id); 
		$.ajax({
			method: "GET",
			url: `http://localhost:8080/ProjectCRM/api/role/delete?id=${id}`, // string template

		})
			.done(function(result) {
				console.log(result, "data ", result.data);
				if (result.data == true) {
					This.closest('tr').remove();
				}
			});
	});
});


