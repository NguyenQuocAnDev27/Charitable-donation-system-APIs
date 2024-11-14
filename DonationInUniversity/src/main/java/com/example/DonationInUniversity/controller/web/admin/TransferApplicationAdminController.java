package com.example.DonationInUniversity.controller.web.admin;

import com.example.DonationInUniversity.model.TransferApplication;
import com.example.DonationInUniversity.service.admin.TransferApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static com.google.common.io.Files.getFileExtension;

@Controller
@RequestMapping("/admin/")
public class TransferApplicationAdminController {

    @Autowired
    private TransferApplicationService transferApplicationService;

    @GetMapping("TransferApplication")
    public String transfer(Model model) {
        List<TransferApplication> listTransfer = transferApplicationService.getAllTransfer();
        model.addAttribute("listTransfer", listTransfer);
        model.addAttribute("transfer", new TransferApplication());
        model.addAttribute("currentUrl", "TransferApplication");
        model.addAttribute("role", "admin");
        return "pages/TransferApplication/transferapplication_management";
    }
    @PostMapping("updateTransferApplication")
    public String updateTransferApplication(@ModelAttribute("transfer") TransferApplication transferApplication) {
        try {
            // Tìm đối tượng TransferApplication từ cơ sở dữ liệu theo ID
            Optional<TransferApplication> existingTransferOpt = transferApplicationService.getTransferById(transferApplication.getId());

            if (existingTransferOpt.isEmpty()) {
                return "pages/errorPage/404"; // Nếu không tìm thấy, trả về trang lỗi 404
            }

            TransferApplication existingTransfer = existingTransferOpt.get();

            // Nếu người dùng tải lên hóa đơn mới
            MultipartFile billFile = transferApplication.getBillFile();
            if (billFile != null && !billFile.isEmpty()) {
                String projectRootPath = System.getProperty("user.dir");
                String uploadDir = projectRootPath + "/images/transfer_application/";

                File uploadFolder = new File(uploadDir);
                if (!uploadFolder.exists()) {
                    uploadFolder.mkdirs(); // Tạo thư mục nếu chưa tồn tại
                }

                // Lưu file hóa đơn mới
                String fileExtension = getFileExtension(billFile.getOriginalFilename());
                String tempFileName = existingTransfer.getId() + "." + fileExtension;
                Path filePath = Paths.get(uploadDir + tempFileName);
                Files.write(filePath, billFile.getBytes());

                // Cập nhật đường dẫn file hóa đơn mới
                existingTransfer.setBillPath("/images/transfer_application/" + tempFileName);
            }

            // Cập nhật trạng thái mới từ form
            existingTransfer.setStatus(transferApplication.getStatus());

            // Lưu thông tin đã cập nhật vào cơ sở dữ liệu
            transferApplicationService.save(existingTransfer);

            return "redirect:/admin/TransferApplication";
        } catch (Exception e) {
            e.printStackTrace();
            return "pages/errorPage/500";
        }
    }



}
