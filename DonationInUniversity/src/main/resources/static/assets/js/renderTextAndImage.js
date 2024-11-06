const dynamicInputsContainer = document.getElementById('dynamic-inputs');

document.querySelectorAll('.btn-primary').forEach(button => {
    button.addEventListener('click', function() {
        const textarea = document.querySelectorAll('#dynamic-inputs textarea');
        const textareaCount = textarea.length;
        const fileInputs = document.querySelectorAll('#dynamic-inputs input[type="file"]');
        const fileInputCount = fileInputs.length;
        let inputElement;
        const wrapper = document.createElement('div'); // Tạo wrapper ngay từ đầu
        wrapper.classList.add('input-wrapper', 'd-flex', 'align-items-center', 'mb-2'); // Kiểu flex giúp căn chỉnh đẹp hơn
        wrapper.setAttribute('draggable', 'true'); // Thiết lập cho wrapper là draggable

        // Khai báo cho input ẩn
        const hiddenInputElement = document.createElement('input');
        hiddenInputElement.type = 'hidden'; // Đặt loại là hidden
        hiddenInputElement.classList.add('input-index'); // Thêm lớp cho dễ dàng quản lý

        if (this.textContent.includes("Thêm văn bản")) {
            inputElement = document.createElement('textarea');
            inputElement.placeholder = 'Nhập nội dung chiến dịch...';
            inputElement.classList.add('form-control', 'mt-2');

            inputElement.style.overflow = 'hidden'; // Tắt cuộn dọc
            inputElement.style.resize = 'none'; // Không cho phép thay đổi kích thước bằng cách kéo

            // Tự động điều chỉnh chiều cao của textarea dựa trên nội dung
            inputElement.addEventListener('input', function() {
                this.style.height = 'auto'; // Đặt chiều cao ban đầu là 'auto' để tính lại
                this.style.height = this.scrollHeight + 'px'; // Thiết lập chiều cao dựa trên scrollHeight
            });
            hiddenInputElement.name = `newListText[${textareaCount}].display_order`; // Số thứ tự cho textarea
            inputElement.name = `newListText[${textareaCount}].content`; // Nội dung của textarea
            inputElement.setAttribute('th:field', `*{newListText[${textareaCount}].content}`); // Thêm th:field cho textarea
        } else if (this.textContent.includes("Thêm ảnh")) {
            inputElement = document.createElement('input');
            inputElement.type = 'file';
            inputElement.accept = 'image/*';
            inputElement.classList.add('form-control', 'mt-2');
            hiddenInputElement.name = `newListImage[${fileInputCount}].display_order`; // Số thứ tự cho hình ảnh
            inputElement.name = `newListImage[${fileInputCount}].file`; // Đường dẫn của hình ảnh
            inputElement.setAttribute('th:field', `*{newListImage[${fileInputCount}].file}`); // Thêm th:field cho hình ảnh
            // Thêm sự kiện để hiển thị hình ảnh sau khi chọn tệp
            inputElement.addEventListener('change', function(event) {
                const file = event.target.files[0];
                if (file) {
                    const reader = new FileReader();
                    reader.onload = function(e) {
                        const imgWrapper = document.createElement('div'); // Tạo một div để chứa hình ảnh
                        imgWrapper.classList.add('img-wrapper', 'mt-2'); // Thêm lớp để dễ dàng quản lý CSS và làm cho nó xuống dòng
                        const img = document.createElement('img');
                        img.src = e.target.result;
                        img.classList.add('img-thumbnail'); // Sử dụng Bootstrap để thêm khung hình đẹp
                        img.style.maxWidth = '250px'; // Giới hạn kích thước hiển thị

                        // Thêm hình ảnh vào div
                        imgWrapper.appendChild(img);
                        wrapper.appendChild(imgWrapper); // Thêm div hình ảnh vào wrapper
                    };
                    reader.readAsDataURL(file); // Đọc file và chuyển thành URL để hiển thị
                }
            });
        }

        // Thêm input ẩn vào wrapper
        wrapper.appendChild(hiddenInputElement); // Thêm input ẩn vào wrapper

        // Tạo nút Delete
        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'Xóa';
        deleteButton.classList.add('btn', 'btn-sm', 'btn-danger', 'ms-2'); // Thêm kiểu Bootstrap

        // Xử lý sự kiện xóa input
        deleteButton.addEventListener('click', function() {
            dynamicInputsContainer.removeChild(wrapper); // Xóa phần tử chứa input khi nhấn nút Delete
            updateInputIndexes(); // Cập nhật lại số thứ tự sau khi xóa
        });

        // Thêm các sự kiện cho tính năng kéo thả
        wrapper.addEventListener('dragstart', function(event) {
            event.dataTransfer.setData('text/plain', null); // Thiết lập dữ liệu khi bắt đầu kéo
            event.dataTransfer.effectAllowed = 'move'; // Chỉ cho phép di chuyển
            this.classList.add('dragging'); // Thêm lớp CSS cho phần tử đang kéo
        });

        wrapper.addEventListener('dragend', function() {
            this.classList.remove('dragging'); // Xóa lớp khi kết thúc kéo
            updateInputIndexes(); // Cập nhật lại số thứ tự sau khi kéo thả
        });

        // Xử lý dragover để cho phép thả
        wrapper.addEventListener('dragover', function(event) {
            event.preventDefault(); // Ngăn chặn hành động mặc định
            event.dataTransfer.dropEffect = 'move'; // Hiển thị hiệu ứng thả
        });

        // Xử lý drop
        wrapper.addEventListener('drop', function(event) {
            event.preventDefault();
            const draggingElement = document.querySelector('.dragging'); // Lấy phần tử đang kéo
            if (draggingElement && draggingElement !== this) {
                // Chèn phần tử đang kéo trước phần tử thả
                dynamicInputsContainer.insertBefore(draggingElement, this);
                updateInputIndexes(); // Cập nhật lại số thứ tự sau khi thả
            }
        });

        // Thêm input và nút Delete vào wrapper
        wrapper.appendChild(inputElement);
        wrapper.appendChild(deleteButton);

        // Thêm wrapper vào container
        dynamicInputsContainer.appendChild(wrapper);

        // Cập nhật lại số thứ tự sau khi thêm input mới
        updateInputIndexes();
    });
});

// Hàm cập nhật số thứ tự cho tất cả các input
function updateInputIndexes() {
    let textIndex = 0;
    let imageIndex = 0;
    const wrappers = dynamicInputsContainer.querySelectorAll('.input-wrapper');
    wrappers.forEach((wrapper, index) => {
        // Cập nhật thuộc tính data-index cho từng input
        const input = wrapper.querySelector('textarea, input[type="file"]');
        const hiddenInput = wrapper.querySelector('.input-index'); // Tìm input
        const idText = wrapper.querySelector('.idText');
        const idImage = wrapper.querySelector('.idImage');
        const pathImage = wrapper.querySelector('.path_image');
        if (input && input.tagName.toLowerCase() === 'textarea') {
            // Cập nhật cho textarea
            if (idText) idText.name = `newListText[${textIndex}].id`;
            hiddenInput.name = `newListText[${textIndex}].display_order`;
            input.name = `newListText[${textIndex}].content`;
            input.setAttribute('th:field', `*{newListText[${textIndex}].content}`);
            textIndex++;
        } else if (input && input.type === 'file') {
            // Cập nhật cho input file
            if (idImage) idImage.name = `newListImage[${imageIndex}].id`;
            if (pathImage) pathImage.name = `newListImage[${imageIndex}].pathImage`;
            hiddenInput.name = `newListImage[${imageIndex}].display_order`;
            input.name = `newListImage[${imageIndex}].file`;
            input.setAttribute('th:field', `*{newListImage[${imageIndex}].file}`);
            imageIndex++;
        }
        if (input) {
            hiddenInput.setAttribute('value', index + 1); // Lưu số thứ tự ẩn vào input hidden
        }
    });
}
document.addEventListener('DOMContentLoaded', function () {
    const dynamicInputsContainer = document.getElementById('dynamic-inputs');
// Function to auto-resize the textarea
    function autoResizeTextarea(textarea) {
        textarea.style.height = 'auto'; // Reset height to auto
        textarea.style.height = textarea.scrollHeight + 'px'; // Set height to scroll height
        textarea.style.overflow = 'hidden';
    }
    // Apply the auto-resize function to all textareas on input event
    dynamicInputsContainer.addEventListener('input', function (event) {
        if (event.target.tagName.toLowerCase() === 'textarea') {
            autoResizeTextarea(event.target); // Adjust height on user input
        }
    });

    // Initial auto-resize for all existing textareas on page load
    const textareas = dynamicInputsContainer.querySelectorAll('textarea');
    textareas.forEach(function (textarea) {
        autoResizeTextarea(textarea); // Adjust height initially based on content
    });
    // Sử dụng event delegation để gán sự kiện delete cho các phần tử ban đầu
    dynamicInputsContainer.addEventListener('click', function (event) {
        if (event.target.classList.contains('delete-btn')) {
            const wrapper = event.target.closest('.input-wrapper');
            if (wrapper) {
                dynamicInputsContainer.removeChild(wrapper); // Xóa phần tử cha
                updateInputIndexes(); // Cập nhật lại số thứ tự sau khi xóa
            }
        }
    });

    // Gán sự kiện drag and drop cho các div có sẵn
    const wrappers = document.querySelectorAll('.input-wrapper');

    wrappers.forEach(wrapper => {
        // Gán sự kiện kéo thả cho phần tử có sẵn
        wrapper.addEventListener('dragstart', function (event) {
            event.dataTransfer.setData('text/plain', null); // Thiết lập dữ liệu khi bắt đầu kéo
            event.dataTransfer.effectAllowed = 'move'; // Chỉ cho phép di chuyển
            this.classList.add('dragging'); // Thêm lớp CSS cho phần tử đang kéo
        });

        wrapper.addEventListener('dragend', function () {
            this.classList.remove('dragging'); // Xóa lớp khi kết thúc kéo
            updateInputIndexes(); // Cập nhật lại số thứ tự sau khi kéo thả
        });

        wrapper.addEventListener('dragover', function (event) {
            event.preventDefault(); // Ngăn chặn hành động mặc định
            event.dataTransfer.dropEffect = 'move'; // Hiển thị hiệu ứng thả
        });

        wrapper.addEventListener('drop', function (event) {
            event.preventDefault();
            const draggingElement = document.querySelector('.dragging'); // Lấy phần tử đang kéo
            if (draggingElement && draggingElement !== this) {
                dynamicInputsContainer.insertBefore(draggingElement, this); // Chèn phần tử đang kéo trước phần tử thả
                updateInputIndexes(); // Cập nhật lại số thứ tự sau khi thả
            }
        });
    });

    // Cập nhật lại số thứ tự cho các phần tử ban đầu
    updateInputIndexes();

    // Hàm cập nhật số thứ tự cho tất cả các input
});
window.onload = function() {
    let textareas = document.querySelectorAll('textarea');
    textareas.forEach(function(textarea) {
        // Thay thế tất cả <br> thành \n
        textarea.value = textarea.value.replace(/<br\s*\/?>/gi, '');
    });
}