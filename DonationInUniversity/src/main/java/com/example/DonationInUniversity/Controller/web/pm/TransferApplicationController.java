package com.example.DonationInUniversity.controller.web.pm;

import com.example.DonationInUniversity.model.TransferApplication;
import com.example.DonationInUniversity.service.admin.ProjectDetailTextServiceAdmin;
import com.example.DonationInUniversity.service.admin.TransferApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/manager")
public class TransferApplicationController {
    private static final Logger logger = LoggerFactory.getLogger(TransferApplicationController.class);
    @Autowired
    TransferApplicationService transferApplicationService;
    @PostMapping("/saveTransferApplication")
    public String saveTransferApplication(
            @ModelAttribute("transfer") TransferApplication transferApplication) {
        try {
            MultipartFile documentFile = transferApplication.getDocumentFile();
            // Lưu các file PDF vào thư mục "images/transferapplication"
            String documentPath = saveFile(documentFile,transferApplication);

            // Thiết lập đường dẫn file trong đối tượng transferApplication
            transferApplication.setDocumentPath(documentPath);

            // Lưu transferApplication vào cơ sở dữ liệu
            transferApplicationService.save(transferApplication);
            return "redirect:/manager";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "pages/errorPage/404";
        }
    }
    private String saveFile(MultipartFile file, TransferApplication transferApplication) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        // Đường dẫn lưu file (cập nhật đường dẫn mong muốn)
        String uploadDir = "images/transferapplication/";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs(); // Tạo thư mục nếu chưa tồn tại
        }

        // Tạo đường dẫn đầy đủ của file
        String filePath = uploadDir + transferApplication.getProjectId();
        file.transferTo(new File(filePath)); // Lưu file vào thư mục

        return filePath; // Trả về đường dẫn để lưu trong database
    }

}
