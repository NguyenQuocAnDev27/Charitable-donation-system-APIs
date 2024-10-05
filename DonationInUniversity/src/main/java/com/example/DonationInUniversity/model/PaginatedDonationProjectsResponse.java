package com.example.DonationInUniversity.model;

import java.util.List;

public class PaginatedDonationProjectsResponse<T> {
    private int totalPages;
    private int currentPage;
    private List<T> list;

    public PaginatedDonationProjectsResponse(int totalPages, int currentPage, List<T> list) {
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.list = list;
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

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
