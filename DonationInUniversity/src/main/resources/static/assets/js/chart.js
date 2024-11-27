

/**
 * Dashboard Analytics
 */

'use strict';

(function () {
    let cardColor, headingColor, legendColor, labelColor, shadeColor, borderColor;

    cardColor = config.colors.cardColor;
    headingColor = config.colors.headingColor;
    legendColor = config.colors.bodyColor;
    labelColor = config.colors.textMuted;
    borderColor = config.colors.borderColor;
    const chartOrderStatistics1 = document.querySelector('#projectChart');

// Lấy các giá trị từ data-* attributes
    const stoppedProject = chartOrderStatistics1.dataset.stopped;
    const pendingProject = chartOrderStatistics1.dataset.pending;
    const completedProject = chartOrderStatistics1.dataset.completed;
// Cấu hình biểu đồ tròn
    const orderChartConfig1 = {
        chart: {
            height: 400,  // Tăng chiều cao của biểu đồ
            width: '100%',
            type: 'pie' ,
            fontFamily: 'Public Sans',// Biểu đồ tròn (pie chart)
        },
        labels: ['Chiến dịch đã hủy', 'Chiến dịch đang tiến hành', 'Chiến dịch đã hoàn thành'], // Nhãn cho từng phần của biểu đồ
        series: [
            parseInt(stoppedProject), // Dữ liệu cho phần "Chiến dịch đã hủy"
            parseInt(pendingProject), // Dữ liệu cho phần "Chiến dịch đang tiến hành"
            parseInt(completedProject) // Dữ liệu cho phần "Chiến dịch đã hoàn thành"
        ],
        colors: [config.colors.danger, config.colors.warning, config.colors.success], // Màu sắc cho từng phần
        stroke: {
            width: 5,
            colors: ['#fff'] // Màu sắc viền cho các phần của biểu đồ
        },

        legend: {
            position: 'bottom', // Đặt vị trí legend dưới biểu đồ
            labels: {
                useSeriesColors: true // Sử dụng màu sắc tương ứng với từng phần của biểu đồ
            }
        },
        title: {
            text: 'Số lượng chiến dịch', // Tiêu đề cho biểu đồ
            align: 'center',
            style: {
                fontFamily: 'Public Sans', // Thêm thuộc tính fontFamily cho title
                fontWeight: 'bold',
                fontSize: '16px',

            }
        }

    };

// Kiểm tra phần tử trước khi vẽ biểu đồ
    if (chartOrderStatistics1) {
        const statisticsChart1 = new ApexCharts(chartOrderStatistics1, orderChartConfig1);
        statisticsChart1.render();
    }

})();

