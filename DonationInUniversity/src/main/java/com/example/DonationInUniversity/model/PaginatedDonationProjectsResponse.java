package com.example.DonationInUniversity.model;

import java.util.List;

public class PaginatedDonationProjectsResponse<T> {
    private int totalPages;
    private int currentPage;
    private List<T> data;

    public PaginatedDonationProjectsResponse(int totalPages, int currentPage, List<T> data) {
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.data = data;
    }

    // Getters and Setters
    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
