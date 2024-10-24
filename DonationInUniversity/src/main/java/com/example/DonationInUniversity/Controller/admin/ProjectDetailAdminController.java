package com.example.DonationInUniversity.controller.admin;

import com.example.DonationInUniversity.model.*;
import com.example.DonationInUniversity.service.admin.ProjectDetailImageServiceAdmin;
import com.example.DonationInUniversity.service.admin.ProjectDetailTextServiceAdmin;
import com.example.DonationInUniversity.service.admin.ProjectServiceAdmin;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/manager")
public class ProjectDetailAdminController{
    @Autowired
    private ProjectServiceAdmin projectServiceAdmin;
    @Autowired
    private ProjectDetailImageServiceAdmin projectDetailImageServiceAdmin;
    @Autowired
    private ProjectDetailTextServiceAdmin projectDetailTextServiceAdmin;
    @GetMapping("/{id}/ProjectDetail")
    public String getProjectDetail(Model model, @PathVariable int id) {
        try{
            List<ProjectDetailText> projectDetailTextList = projectDetailTextServiceAdmin.getProjectDetailTextAdmin(id);
            List<ProjectDetailImage> projectDetailImageList = projectDetailImageServiceAdmin.getProjectDetailImageAdmin(id);
            model.addAttribute("projectDetailTextList", projectDetailTextList);
            model.addAttribute("projectDetailImageList", projectDetailImageList);
        }catch (Exception e){
           throw new RuntimeException(e);
        }
        Optional<DonationProject> donationProject= projectServiceAdmin.getProjectById(id);
        List<ProjectDetailImage> newListImage = new ArrayList<ProjectDetailImage>();
        List<ProjectDetailText> newListText = new ArrayList<ProjectDetailText>();
        model.addAttribute("donationProject", donationProject);
        model.addAttribute("newListImage", newListImage);
        model.addAttribute("newListText", newListText);
        return "ProjectManager/ProjectDetail";
    }
    @PostMapping("/{id}/ProjectDetail")
    public String deleteProjectDetail(@PathVariable int id,
                                      @RequestParam(value = "idText", required = false) String idText,
                                      @RequestParam(value = "idImage", required = false) String idImage,
                                      RedirectAttributes redirectAttributes) {
        // Khai báo logger
        Logger logger = LoggerFactory.getLogger(this.getClass());

        // Kiểm tra và chuyển đổi idText từ chuỗi sang số nguyên (int)
        Integer textId = null;
        if (idText != null) {
            try {
                textId = Integer.parseInt(idText);
            } catch (NumberFormatException e) {
                logger.error("idText không phải là số hợp lệ: {}", idText);
                redirectAttributes.addFlashAttribute("errorMessage", "idText không phải là số hợp lệ.");
                return "redirect:/manager/" + id + "/ProjectDetail";
            }
        }

        // Kiểm tra và chuyển đổi idImage từ chuỗi sang số nguyên (int)
        Integer imageId = null;
        if (idImage != null) {
            try {
                imageId = Integer.parseInt(idImage);
            } catch (NumberFormatException e) {
                logger.error("idImage không phải là số hợp lệ: {}", idImage);
                redirectAttributes.addFlashAttribute("errorMessage", "idImage không phải là số hợp lệ.");
                return "redirect:/manager/" + id + "/ProjectDetail";
            }
        }

        // Nếu idText hợp lệ thì xóa ProjectDetailText
        if (textId != null) {
            try {
                ProjectDetailText projectDetailText = this.projectDetailTextServiceAdmin.findProjectDetailTextById(textId);
                this.projectDetailTextServiceAdmin.deleteProjectDetailText(projectDetailText);
            } catch (Exception e) {
                logger.error("Lỗi khi xóa ProjectDetailText với idText: {}", textId, e);
                redirectAttributes.addFlashAttribute("errorMessage", "Xóa ProjectDetailText thất bại.");
                return "redirect:/manager/" + id + "/ProjectDetail";
            }
        }

        // Nếu idImage hợp lệ thì xóa ProjectDetailImage
        if (imageId != null) {
            try {
                ProjectDetailImage projectDetailImage = this.projectDetailImageServiceAdmin.findProjectDetailImageById(imageId);
                this.projectDetailImageServiceAdmin.deleteProjectDetailImage(projectDetailImage);
            } catch (Exception e) {
                logger.error("Lỗi khi xóa ProjectDetailImage với idImage: {}", imageId, e);
                redirectAttributes.addFlashAttribute("errorMessage", "Xóa ProjectDetailImage thất bại.");
                return "redirect:/manager/" + id + "/ProjectDetail";
            }
        }
        return "redirect:/manager/" + id + "/ProjectDetail";
    }
    @Transactional
    @PostMapping("/{projectId}/saveOrUpdateProjectDetail")
    public String addOrUpdateProjectDetail(@ModelAttribute("projectDetailForm") ProjectDetailForm projectDetailForm,
                                           @PathVariable int projectId) {
        DonationProject donationProject = this.projectServiceAdmin.getDonationProjectById(projectId);


        if (projectDetailForm.getNewListImage() != null) {
            projectDetailForm.getNewListImage().forEach(projectDetailImage -> {
                projectDetailImage.setProject(donationProject);
                projectDetailImage.setIsDelete(1);
                projectDetailImage.setCreatedAt(LocalDateTime.now());

                MultipartFile file = projectDetailImage.getFile();
                if (file != null && !file.isEmpty()) {
                    try {
                        // Tạo tên file tạm thời (chưa có imageId)
                        String fileExtension = getFileExtension(file.getOriginalFilename());
                        String tempFileName = donationProject.getProjectId() + "_" + projectDetailImage.getDisplay_order() + "_temp." + fileExtension;

                        // Lấy đường dẫn tới thư mục gốc của project
                        String projectRootPath = System.getProperty("user.dir");
                        String uploadDir = projectRootPath + "/images/project_detail/";
                        File uploadFolder = new File(uploadDir);
                        if (!uploadFolder.exists()) {
                            uploadFolder.mkdirs(); // Tạo thư mục nếu chưa tồn tại
                        }

                        // Lưu file tạm thời
                        File tempFile = new File(uploadDir + tempFileName);
                        file.transferTo(tempFile);

                        // Cập nhật đường dẫn tạm thời vào projectDetailImage
                        projectDetailImage.setPathImage("images/project_detail/" + tempFileName);

                        // Lưu bản ghi vào database (với pathImage tạm thời)
                        ProjectDetailImage savedImage = this.projectDetailImageServiceAdmin.saveProjectDetailImage(projectDetailImage);
                        int imageId = savedImage.getId(); // Lấy imageId sau khi lưu

                        // Tạo tên file chính thức với imageId
                        String fileName = donationProject.getProjectId() + "_" + projectDetailImage.getDisplay_order() + "_" + imageId + "." + fileExtension;

                        // Đổi tên file tạm thời thành tên chính thức
                        File newFile = new File(uploadDir + fileName);
                        tempFile.renameTo(newFile);

                        // Cập nhật lại đường dẫn file chính thức vào database
                        savedImage.setPathImage("images/project_detail/" + fileName);
                        this.projectDetailImageServiceAdmin.saveProjectDetailImage(savedImage);

                    } catch (IOException e) {
                        e.printStackTrace();
                        // Xử lý ngoại lệ nếu cần
                    }
                }
                else {
                    this.projectDetailImageServiceAdmin.saveProjectDetailImage(projectDetailImage);
                }
            });
        }

        else {
            this.projectDetailImageServiceAdmin.deleteProjectDetailImageByProjectId(projectId);
        }
        if (projectDetailForm.getNewListText() != null) {
            List<ProjectDetailText> currentTexts = this.projectDetailTextServiceAdmin.getProjectDetailTextAdmin(projectId);

            // Lưu danh sách mới
            projectDetailForm.getNewListText().forEach(projectDetailText -> {
                String contentWithBr = replaceNewLinesWithBr(projectDetailText.getContent());
                projectDetailText.setContent(contentWithBr);
                projectDetailText.setProject(donationProject);
                projectDetailText.setIsDelete(1);
                projectDetailText.setCreatedAt(LocalDateTime.now());
                this.projectDetailTextServiceAdmin.saveProjectDetailText(projectDetailText);
            });

            // Xóa những bản ghi không tồn tại trong danh sách mới
            currentTexts.forEach(existingText -> {
                boolean existsInNewList = projectDetailForm.getNewListText().stream()
                        .anyMatch(newText -> newText.getId() == existingText.getId());

                if (!existsInNewList) {
                    this.projectDetailTextServiceAdmin.deleteProjectDetailText(existingText);
                }
            });
        }
        List<ProjectDetailImage> projectDetailImageList = projectDetailImageServiceAdmin.getProjectDetailImageAdmin(projectId);

        return "redirect:/manager/" + projectId + "/ProjectDetail";
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

