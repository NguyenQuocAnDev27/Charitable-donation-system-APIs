package com.example.DonationInUniversity.controller.admin;

import com.example.DonationInUniversity.model.DonationProject;
import com.example.DonationInUniversity.model.ProjectDetailImage;
import com.example.DonationInUniversity.model.ProjectDetailText;
import com.example.DonationInUniversity.model.User;
import com.example.DonationInUniversity.service.admin.ProjectDetailImageServiceAdmin;
import com.example.DonationInUniversity.service.admin.ProjectDetailTextServiceAdmin;
import com.example.DonationInUniversity.service.admin.ProjectServiceAdmin;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/manager")
public class ProjectDetailAdminController {
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
    public String addOrUpdateProjectDetail(@ModelAttribute("newListImage") ArrayList<ProjectDetailImage> projectImageList,
                                           @ModelAttribute("newListText") ArrayList<ProjectDetailText> projectTextList,
                                     @PathVariable int projectId) {
        Optional<DonationProject> donationProject= projectServiceAdmin.getProjectById(projectId);
        if(donationProject.isPresent()){
            DonationProject donationProject1 = donationProject.get();
            if(!projectImageList.isEmpty()){
                for (ProjectDetailImage projectDetailImage : projectImageList) {
                    projectDetailImage.setDonationProject(donationProject1);
                    projectDetailImage.setIsDelete(1);
                    projectDetailImage.setCreatedAt(LocalDate.now());
                    this.projectDetailImageServiceAdmin.saveProjectDetailImageAdmin(projectDetailImage);
                }
            }
            if(!projectTextList.isEmpty()){
                for (ProjectDetailText projectDetailText : projectTextList) {
                    projectDetailText.setDonationProject(donationProject1);
                    projectDetailText.setIsDelete(1);
                    projectDetailText.setCreatedAt(LocalDate.now());
                    this.projectDetailTextServiceAdmin.saveProjectDetailText(projectDetailText);
                }
            }
            return "redirect:/manager/"+projectId+"/ProjectDetail";
        }
        else {
            return "redirect:/error";
        }
    }
}
