$(document).ready(function() {
    const urlParams = new URLSearchParams(window.location.search);
    const taskId = urlParams.get('taskId');
    
    if (taskId) {
        // Gọi API để lấy thông tin của task
        $.ajax({
            url: `http://localhost:8080/ProjectCRM/api/get-task-by-id?taskId=${taskId}`,
            type: "GET",
            success: function(task) {
				console.log("task",task);
                // Đổ dữ liệu vào các phần tử HTML
                $('.taskId').text(task.taskId);
                $('.taskName').text(task.taskName);
                $('.projectName').text(task.project.projectName);
                $('.executorName').text(task.user.fullName);
                $('.startDate').text(task.startDate);
                $('.endDate').text(task.endDate);
                $('.statusName').text(task.statusTask.statusName);
            },
            error: function(error) {
                console.log('Error:', error);
            }
        });
    }
});
