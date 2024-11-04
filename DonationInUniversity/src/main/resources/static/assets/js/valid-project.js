// Hàm kiểm tra xem một giá trị có phải là số hợp lệ không
function isNumber(value) {
    return !isNaN(value) && value.trim() !== ''; // Kiểm tra không phải NaN và không phải chuỗi rỗng
}

// Kiểm tra khi người dùng nhập liệu vào trường goalAmount
document.getElementById('goalAmount').addEventListener('input', function () {
    const goalAmountInput = this;
    const goalAmount = goalAmountInput.value;

    // Kiểm tra nếu Goal Amount không phải là số hợp lệ
    if (!isNumber(goalAmount)) {
        goalAmountInput.classList.add('is-invalid');
        document.getElementById('goal-error').style.display = 'block'; // Hiển thị lỗi nếu Goal Amount không phải số
    } else {
        goalAmountInput.classList.remove('is-invalid');
        document.getElementById('goal-error').style.display = 'none'; // Ẩn thông báo lỗi nếu hợp lệ
    }
});

// Kiểm tra khi người dùng nhập liệu vào trường currentAmount
document.getElementById('currentAmount').addEventListener('input', function () {
    const goalAmountInput = document.getElementById('goalAmount');
    const currentAmountInput = this;
    const goalAmount = goalAmountInput.value;
    const currentAmount = currentAmountInput.value;

    // Kiểm tra cả hai trường đều là số hợp lệ
    const isGoalAmountValid = isNumber(goalAmount);
    const isCurrentAmountValid = isNumber(currentAmount);

    // Lỗi số không hợp lệ cho "Current Amount"
    if (!isCurrentAmountValid) {
        currentAmountInput.classList.add('is-invalid');
        document.getElementById('amount-number-error').style.display = 'block'; // Hiển thị lỗi nếu Current Amount không phải là số
    } else {
        currentAmountInput.classList.remove('is-invalid');
        document.getElementById('amount-number-error').style.display = 'none'; // Ẩn thông báo lỗi số không hợp lệ nếu hợp lệ
    }

    // Kiểm tra nếu cả hai đều là số và Current Amount lớn hơn Goal Amount
    if (isGoalAmountValid && isCurrentAmountValid) {
        const goalAmountValue = parseFloat(goalAmount);
        const currentAmountValue = parseFloat(currentAmount);

        // Lỗi nếu Current Amount lớn hơn Goal Amount
        if (currentAmountValue > goalAmountValue) {
            currentAmountInput.classList.add('is-invalid');
            document.getElementById('amount-goal-error').style.display = 'block'; // Hiển thị lỗi lớn hơn Goal Amount
        } else {
            currentAmountInput.classList.remove('is-invalid');
            document.getElementById('amount-goal-error').style.display = 'none'; // Ẩn thông báo lỗi nếu hợp lệ
        }
    } else {
        // Ẩn lỗi "greater than Goal Amount" nếu số không hợp lệ
        document.getElementById('amount-goal-error').style.display = 'none';
    }
});

// Kiểm tra lại trước khi form được submit
document.querySelector('form').addEventListener('submit', function (event) {
    const goalAmountInput = document.getElementById('goalAmount');
    const currentAmountInput = document.getElementById('currentAmount');
    const goalAmount = goalAmountInput.value;
    const currentAmount = currentAmountInput.value;

    // Kiểm tra nếu cả hai đều là số hợp lệ
    const isGoalAmountValid = isNumber(goalAmount);
    const isCurrentAmountValid = isNumber(currentAmount);

    // Nếu không phải số hợp lệ, ngăn form gửi đi
    if (!isGoalAmountValid) {
        goalAmountInput.classList.add('is-invalid');
        document.getElementById('goal-error').style.display = 'block'; // Hiển thị lỗi nếu Goal Amount không phải số
        event.preventDefault(); // Ngăn form không được gửi đi nếu có lỗi
    }

    if (!isCurrentAmountValid) {
        currentAmountInput.classList.add('is-invalid');
        document.getElementById('amount-number-error').style.display = 'block'; // Hiển thị lỗi không phải số
        event.preventDefault(); // Ngăn form không được gửi đi nếu có lỗi
    }

    // Kiểm tra nếu Current Amount lớn hơn Goal Amount
    if (isGoalAmountValid && isCurrentAmountValid) {
        const goalAmountValue = parseFloat(goalAmount);
        const currentAmountValue = parseFloat(currentAmount);

        if (currentAmountValue > goalAmountValue) {
            currentAmountInput.classList.add('is-invalid');
            document.getElementById('amount-goal-error').style.display = 'block';
            event.preventDefault(); // Ngăn form không được gửi đi nếu Current Amount lớn hơn Goal Amount
        }
    }
});
document.getElementById('endDate').addEventListener('input', function () {
    const startDateInput = document.getElementById('startDate');
    const endDateInput = this;
    const startDate = startDateInput.value;
    const endDate = endDateInput.value;

    // Kiểm tra nếu cả Start Date và End Date đều đã được nhập
    if (startDate && endDate) {
        const startDateValue = new Date(startDate);
        const endDateValue = new Date(endDate);

        // Kiểm tra nếu Start Date lớn hơn End Date
        if (startDateValue > endDateValue) {
            endDateInput.classList.add('is-invalid');
            document.getElementById('date-error').style.display = 'block'; // Hiển thị lỗi nếu Start Date lớn hơn End Date
        } else {
            endDateInput.classList.remove('is-invalid');
            document.getElementById('date-error').style.display = 'none'; // Ẩn thông báo lỗi nếu hợp lệ
        }
    }
});

// Kiểm tra lại trước khi form được submit
document.querySelector('form').addEventListener('submit', function (event) {
    const startDateInput = document.getElementById('startDate');
    const endDateInput = document.getElementById('endDate');
    const startDate = startDateInput.value;
    const endDate = endDateInput.value;

    // Nếu cả Start Date và End Date đã được nhập
    if (startDate && endDate) {
        const startDateValue = new Date(startDate);
        const endDateValue = new Date(endDate);

        // Kiểm tra nếu Start Date lớn hơn End Date
        if (startDateValue > endDateValue) {
            endDateInput.classList.add('is-invalid');
            document.getElementById('date-error').style.display = 'block';
            event.preventDefault(); // Ngăn form không được gửi đi nếu có lỗi
        }
    }
});