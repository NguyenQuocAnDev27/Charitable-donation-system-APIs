package com.example.DonationInUniversity.controller.web.pm;

import com.example.DonationInUniversity.model.CustomUserDetails;
import com.example.DonationInUniversity.model.DonationProject;
import com.example.DonationInUniversity.model.TransferApplication;
import com.example.DonationInUniversity.model.User;
import com.example.DonationInUniversity.service.admin.ProjectDetailTextServiceAdmin;
import com.example.DonationInUniversity.service.admin.ProjectServiceAdmin;
import com.example.DonationInUniversity.service.admin.TransferApplicationService;
import com.example.DonationInUniversity.service.admin.UserAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private UserAdminService userAdminService;
    @Autowired
    private ProjectServiceAdmin projectServiceAdmin;
    @PostMapping("/saveTransferApplication")
    public String saveTransferApplication(
            @ModelAttribute("transfer") TransferApplication transferApplication) {
        try {
            DonationProject donationProject = this.projectServiceAdmin.getDonationProjectById(transferApplication.getProject());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userAdminService.adminGetUserByUsername(username);
            if (user == null) {
                return "redirect:/admin/login";
            }
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            MultipartFile documentFile = transferApplication.getDocumentFile();
            // Lưu các file PDF vào thư mục "images/transferapplication"
            String fileExtension = getFileExtension(documentFile.getOriginalFilename());
            String tempFileName = donationProject.getProjectId() +"."+ fileExtension;
            // Lấy đường dẫn tới thư mục gốc của project
            String projectRootPath = System.getProperty("user.dir");
            String uploadDir = projectRootPath + "/images/transfer_application/";
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs(); // Tạo thư mục nếu chưa tồn tại
            }

            // Lưu file tạm thời
            File tempFile = new File(uploadDir + tempFileName);
            documentFile.transferTo(tempFile);

            // Cập nhật đường dẫn tạm thời vào projectDetailImage
            transferApplication.setDocumentPath("images/project_detail/" + tempFileName);
            transferApplication.setProjectId(donationProject);
            // Thiết lập đường dẫn file trong đối tượng transferApplication
            transferApplication.setStatus("waiting");
            transferApplication.setUserId(userDetails.getUserModel());
            // Lưu transferApplication vào cơ sở dữ liệu
            transferApplicationService.save(transferApplication);
            return "redirect:/manager";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "pages/errorPage/404";
        }
    }
    public String getFileExtension(String fileName) {
        if (fileName == null) {
            return "";
        }
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex >= 0) {
            return fileName.substring(dotIndex + 1);
        } else {
            return "";
        }
    }
    public String replaceNewLinesWithBr(String content) {
        if (content != null) {
            return content.replace("\n", "<br>");
        }
        return content;
    }
}
