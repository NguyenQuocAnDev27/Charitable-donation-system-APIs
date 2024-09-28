package com.example.DonationInUniversity.service;

import com.example.DonationInUniversity.model.DonationProject;
import com.example.DonationInUniversity.repository.DonationProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonationProjectService {
    @Autowired
    private DonationProjectRepository donationProjectRepository;
    public List<DonationProject> getAllDonationProjects() {
        return donationProjectRepository.findAll();
    }
    public DonationProject getDonationProjectById(int id) {
        return donationProjectRepository.findById(id).orElse(null);
    }
    public void deleteDonationProject(int id) {
        donationProjectRepository.deleteById(id);
    }
    public void addDonationProject(DonationProject donationProject) {
        donationProjectRepository.save(donationProject);
    }
    public DonationProject updateDonationProject(DonationProject updateDonationProject, int id) {
        Optional<DonationProject> optionalDonationProject = donationProjectRepository.findById(id);
        if (optionalDonationProject.isPresent()) {
            DonationProject oldDonationProject = optionalDonationProject.get();
            oldDonationProject.setProjectName(updateDonationProject.getProjectName());
            oldDonationProject.setProjectManagerId(updateDonationProject.getProjectManagerId());
            oldDonationProject.setCurrentAmount(updateDonationProject.getCurrentAmount());
            oldDonationProject.setEndDate(updateDonationProject.getEndDate());
            oldDonationProject.setStartDate(updateDonationProject.getStartDate());
            oldDonationProject.setStatus(updateDonationProject.getStatus());
            return donationProjectRepository.save(oldDonationProject);
        }
        else {
            return null;
        }
    }
}
