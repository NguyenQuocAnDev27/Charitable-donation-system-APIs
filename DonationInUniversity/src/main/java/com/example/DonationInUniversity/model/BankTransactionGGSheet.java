package com.example.DonationInUniversity.model;

public class BankTransactionGGSheet {
    private String maGD;               // Mã GD (Transaction ID)
    private String moTa;               // Mô tả (Description)
    private String giaTri;             // Giá trị (Value)
    private String ngayDienRa;         // Ngày diễn ra (Date)
    private String soTaiKhoan;         // Số tài khoản (Account Number)
    private String maThamChieu;        // Mã tham chiếu (Reference Code)
    private String soDu;               // Số dư (Balance)
    private String soTaiKhoanAo;       // Số tài khoản ảo (Virtual Account Number)
    private String tenTaiKhoanAo;      // Tên tài khoản ảo (Virtual Account Name)
    private String soTaiKhoanDoiUng;   // Số tài khoản đối ứng (Counter Account Number)
    private String tenTaiKhoanDoiUng;  // Tên tài khoản đối ứng (Counter Account Name)
    private String maBinNganHangDoiUng; // Mã BIN ngân hàng đối ứng (Counter Bank BIN)
    private String tenNHTaiKhoanDoiUng; // Tên NH tài khoản đối ứng (Counter Bank Name)
    private String kenhThanhToan;      // Kênh thanh toán (Payment Channel)

    public String getMaGD() {
        return maGD;
    }

    public void setMaGD(String maGD) {
        this.maGD = maGD;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(String giaTri) {
        this.giaTri = giaTri;
    }

    public String getNgayDienRa() {
        return ngayDienRa;
    }

    public void setNgayDienRa(String ngayDienRa) {
        this.ngayDienRa = ngayDienRa;
    }

    public String getSoTaiKhoan() {
        return soTaiKhoan;
    }

    public void setSoTaiKhoan(String soTaiKhoan) {
        this.soTaiKhoan = soTaiKhoan;
    }

    public String getMaThamChieu() {
        return maThamChieu;
    }

    public void setMaThamChieu(String maThamChieu) {
        this.maThamChieu = maThamChieu;
    }

    public String getSoDu() {
        return soDu;
    }

    public void setSoDu(String soDu) {
        this.soDu = soDu;
    }

    public String getSoTaiKhoanAo() {
        return soTaiKhoanAo;
    }

    public void setSoTaiKhoanAo(String soTaiKhoanAo) {
        this.soTaiKhoanAo = soTaiKhoanAo;
    }

    public String getTenTaiKhoanAo() {
        return tenTaiKhoanAo;
    }

    public void setTenTaiKhoanAo(String tenTaiKhoanAo) {
        this.tenTaiKhoanAo = tenTaiKhoanAo;
    }

    public String getSoTaiKhoanDoiUng() {
        return soTaiKhoanDoiUng;
    }

    public void setSoTaiKhoanDoiUng(String soTaiKhoanDoiUng) {
        this.soTaiKhoanDoiUng = soTaiKhoanDoiUng;
    }

    public String getTenTaiKhoanDoiUng() {
        return tenTaiKhoanDoiUng;
    }

    public void setTenTaiKhoanDoiUng(String tenTaiKhoanDoiUng) {
        this.tenTaiKhoanDoiUng = tenTaiKhoanDoiUng;
    }

    public String getMaBinNganHangDoiUng() {
        return maBinNganHangDoiUng;
    }

    public void setMaBinNganHangDoiUng(String maBinNganHangDoiUng) {
        this.maBinNganHangDoiUng = maBinNganHangDoiUng;
    }

    public String getTenNHTaiKhoanDoiUng() {
        return tenNHTaiKhoanDoiUng;
    }

    public void setTenNHTaiKhoanDoiUng(String tenNHTaiKhoanDoiUng) {
        this.tenNHTaiKhoanDoiUng = tenNHTaiKhoanDoiUng;
    }

    public String getKenhThanhToan() {
        return kenhThanhToan;
    }

    public void setKenhThanhToan(String kenhThanhToan) {
        this.kenhThanhToan = kenhThanhToan;
    }
}
