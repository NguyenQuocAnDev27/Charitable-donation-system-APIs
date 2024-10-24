package com.example.DonationInUniversity.service.admin;

import com.example.DonationInUniversity.model.ProjectDetailImage;
import com.example.DonationInUniversity.repository.ProjectDetailImageAdminRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectDetailImageServiceAdmin {
    private static final Logger logger = LoggerFactory.getLogger(ProjectDetailImageServiceAdmin.class);
    @Autowired
    private ProjectDetailImageAdminRepository projectDetailImageRepository;

    public List<ProjectDetailImage> getProjectDetailImages() {
        return projectDetailImageRepository.findAll();
    }

    public ProjectDetailImage saveProjectDetailImage(ProjectDetailImage projectDetailImage) {
        try {
            return projectDetailImageRepository.save(projectDetailImage);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Vi phạm ràng buộc dữ liệu", e);
        } catch (JpaSystemException e) {
            throw new RuntimeException("Lỗi hệ thống khi tương tác với cơ sở dữ liệu", e);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi không xác định khi lưu ProjectDetailImage", e);
        }
    }

    public List<ProjectDetailImage> getProjectDetailImageAdmin(int projectId) {
        return projectDetailImageRepository.adminGetProjectDetailImageByProjectId(projectId);
    }

    public ProjectDetailImage findProjectDetailImageById(Integer imageId) {
        return projectDetailImageRepository.findById(imageId)
                .orElseThrow(
                        () -> new IllegalArgumentException("Không tìm thấy ProjectDetailImage với ID: " + imageId));
    }

    public void deleteProjectDetailImage(ProjectDetailImage projectDetailImage) {
        try {
            projectDetailImageRepository.delete(projectDetailImage);
        } catch (DataAccessException e) {
            logger.error("Lỗi khi xóa chi tiết hình ảnh dự án với ID: " + projectDetailImage.getId(), e);
            throw new RuntimeException("Lỗi cơ sở dữ liệu khi xóa chi tiết hình ảnh dự án.");
        } catch (IllegalArgumentException e) {
            logger.error("ProjectDetailImage không hợp lệ: " + projectDetailImage, e);
            throw new RuntimeException("ProjectDetailImage không hợp lệ.");
        } catch (Exception e) {
            logger.error("Lỗi không xác định khi xóa projectDetailImage: " + projectDetailImage, e);
            throw new RuntimeException("Đã xảy ra lỗi không xác định.");
        }
    }

    public void deleteProjectDetailImageByProjectId(int id) {
        this.projectDetailImageRepository.deleteProjectDetailImageByProjectId(id);
    }

    public ProjectDetailImage updateProjectDetailImage(ProjectDetailImage projectDetailImage) {
        return projectDetailImageRepository.save(projectDetailImage);
    }
}
