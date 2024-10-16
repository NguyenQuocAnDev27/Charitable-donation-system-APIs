package com.example.DonationInUniversity.service.admin;

import com.example.DonationInUniversity.model.ProjectDetailTextAdmin;
import com.example.DonationInUniversity.repository.ProjectDetailTextAdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectDetailTextServiceAdmin {
    @Autowired
    private ProjectDetailTextAdminRepository projectDetailTextRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProjectDetailTextServiceAdmin.class);

    public List<ProjectDetailTextAdmin> getProjectDetailTextAdmin(int projectId) {
        return projectDetailTextRepository.adminGetProjectDetailTextByProjectId(projectId);
    }
    public List<ProjectDetailTextAdmin> getProjectDetailTexts() {
        return projectDetailTextRepository.findAll();
    }
    public ProjectDetailTextAdmin saveProjectDetailText(ProjectDetailTextAdmin projectDetailText) {
        try {
            return projectDetailTextRepository.save(projectDetailText);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Vi phạm ràng buộc dữ liệu khi lưu ProjectDetailText", e);
        } catch (JpaSystemException e) {
            throw new RuntimeException("Lỗi hệ thống khi tương tác với cơ sở dữ liệu", e);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi không xác định khi lưu ProjectDetailText", e);
        }
    }
    public ProjectDetailTextAdmin findProjectDetailTextById(Integer imageId) {
        return projectDetailTextRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy ProjectDetailText với ID: " + imageId));
    }
    public void deleteProjectDetailText(ProjectDetailTextAdmin projectDetailText) {
        try {
            projectDetailTextRepository.delete(projectDetailText);
            logger.info("Đã xóa thành công ProjectDetailText với ID: {}", projectDetailText.getId());
        } catch (DataAccessException e) {
            logger.error("Lỗi khi xóa ProjectDetailText với ID: {}", projectDetailText.getId(), e);
            throw new RuntimeException("Lỗi cơ sở dữ liệu khi xóa ProjectDetailText.");
        } catch (IllegalArgumentException e) {
            logger.error("Dữ liệu ProjectDetailText không hợp lệ: {}", projectDetailText, e);
            throw new RuntimeException("ProjectDetailText không hợp lệ.");
        } catch (Exception e) {
            logger.error("Lỗi không xác định khi xóa ProjectDetailText: {}", projectDetailText, e);
            throw new RuntimeException("Đã xảy ra lỗi không xác định khi xóa ProjectDetailText.");
        }
    }
    public void deleteProjectDetailTextByProjectId(int id) {
        this.projectDetailTextRepository.deleteProjectDetailTextByProjectId(id);
    }
}
