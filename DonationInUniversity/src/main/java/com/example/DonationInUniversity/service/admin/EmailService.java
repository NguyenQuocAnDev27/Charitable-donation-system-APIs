package com.example.DonationInUniversity.service.admin;

import com.example.DonationInUniversity.model.DonationProject;
import com.example.DonationInUniversity.model.Transaction;
import com.example.DonationInUniversity.model.TransferApplication;
import com.example.DonationInUniversity.model.User;
import com.example.DonationInUniversity.service.schedule.ScheduledTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.SpringVersion;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.mail.internet.MimeMessage;
import jakarta.mail.MessagingException;
@Service
public class EmailService {
    @Value("${server.url}")
    private String SERVER_URL;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private ProjectServiceAdmin projectServiceAdmin;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private UserAdminService userAdminService;
    @Autowired
    private TransferApplicationService transferApplicationService;
    @Autowired
    private ScheduledTransactionService scheduledTransactionService;

    public void sendEmailWithTemplate(int projectId) throws MessagingException {
        // Tạo Context của Thymeleaf
        Optional<DonationProject> donationProjectList = projectServiceAdmin.getProjectById(projectId);
        if(donationProjectList.isPresent()) {
            DonationProject donationProject = donationProjectList.get();
            Set<Integer> emailedUserIds = new HashSet<>();
            List<Transaction> listTransaction = scheduledTransactionService.getTransactionsByProjectId(projectId);
            for(Transaction transaction : listTransaction) {
                String des=transaction.getDescription();

                Pattern pattern = Pattern.compile("USR(\\d+)M");
                Matcher matcher = pattern.matcher(des);
                String number = "0";
                while (matcher.find()) {
                    number = matcher.group(1);
                }
                int userId= Integer.parseInt(number);
                if(userId != 0){
                    Optional<User> userList = userAdminService.getUserById(userId);
                    if(userList.isPresent() && !emailedUserIds.contains(userId)) {
                        User user = userList.get();
                        Map<String, Object> emailModel = new HashMap<>();
                        emailModel.put("name",user.getFullName());
                        emailModel.put("message", "Cảm ơn bạn đã ủng hộ quỹ từ thiện, chiến dịch đã hoàn thành.");
                        Context context = new Context();
                        context.setVariables(emailModel);
                        TransferApplication transferApplication = transferApplicationService.getTransferApplication(projectId);

                        // Tạo email
                        MimeMessage message = mailSender.createMimeMessage();
                        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

                        helper.setTo(user.getEmail());
                        helper.setSubject(donationProject.getProjectName());
                        helper.setText("<p>Cảm ơn bạn đã ủng hộ quỹ từ thiện, chiến dịch <strong>"
                                + donationProject.getProjectName() + "</strong> đã hoàn thành.</p>" +
                                "<p>Nếu bạn muốn kiểm tra thông tin và xem thêm các chiến dịch khác, vui lòng truy cập: " +
                                "<a href='" + SERVER_URL + transferApplication.getDocumentPath() + "'>tại đây</a>.</p>", true);

                        // Gửi email
                        mailSender.send(message);
                        emailedUserIds.add(userId);
                    }
                }
            }
        }
    }
}
