'use strict';

(function () {
    // Kiểm tra nếu mã JS đang chạy
    console.log("JavaScript is running");

    const chartOrderStatistics = document.querySelector('#projectChart');
    const monthSelect = document.querySelector('#monthSelect');

    if (monthSelect) {
        console.log("Select box found");
    }

    // Dữ liệu mặc định cho biểu đồ
    let stoppedProject = chartOrderStatistics.dataset.stopped;
    let pendingProject = chartOrderStatistics.dataset.pending;
    let completedProject = chartOrderStatistics.dataset.completed;

    // Cấu hình biểu đồ tròn
    const orderChartConfig = {
        chart: {
            height: 400,
            width: '100%',
            type: 'pie',
            fontFamily: 'Public Sans',
        },
        labels: ['Chiến dịch đã hủy', 'Chiến dịch đang tiến hành', 'Chiến dịch đã hoàn thành'],
        series: [
            parseInt(stoppedProject),
            parseInt(pendingProject),
            parseInt(completedProject)
        ],
        colors: [config.colors.danger, config.colors.warning, config.colors.success],
        stroke: {
            width: 5,
            colors: ['#fff']
        },
        legend: {
            position: 'bottom',
            labels: {
                useSeriesColors: true
            }
        },
        title: {
            text: 'Số lượng chiến dịch',
            align: 'center',
            style: {
                fontFamily: 'Public Sans',
                fontWeight: 'bold',
                fontSize: '16px',
            }
        }
    };

    // Khởi tạo đối tượng ApexCharts
    let statisticsChart = new ApexCharts(chartOrderStatistics, orderChartConfig);
    statisticsChart.render();

    // Lắng nghe sự kiện thay đổi tháng từ selectbox
    monthSelect.addEventListener('change', function () {
        const selectedMonth = monthSelect.value;

        // Kiểm tra xem sự kiện có kích hoạt không
        console.log("Month selected:", selectedMonth);

        // Gửi yêu cầu POST để lấy dữ liệu cho tháng đã chọn
        fetch('/admin/count', {
            method: 'POST',  // Sử dụng POST để gửi dữ liệu trong body
            headers: {
                'Content-Type': 'application/json',  // Đảm bảo gửi dữ liệu dưới dạng JSON
            },
            body: JSON.stringify({ month: selectedMonth })  // Gửi dữ liệu tháng trong body
        })
            .then(response => {
                // Kiểm tra nếu response có vấn đề
                console.log("Response received:", response);
                return response.json();
            })
            .then(data => {
                // Kiểm tra dữ liệu nhận được
                console.log("Data received from backend: ", data);

                const { completed, stopped, pending } = data;

                console.log("Completed Projects: ", completed);
                console.log("Stopped Projects: ", stopped);
                console.log("Pending Projects: ", pending);

                // Cập nhật biểu đồ tròn với dữ liệu mới
                statisticsChart.updateOptions({
                    series: [completed, stopped, pending]
                });
            })
            .catch(error => console.error('Error:', error));
    });
})();
