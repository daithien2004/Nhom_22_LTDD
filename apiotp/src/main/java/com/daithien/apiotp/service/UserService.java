package com.daithien.apiotp.service;

import com.daithien.apiotp.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserService {
    private Map<String, User> users = new HashMap<>();
    private Map<String, String> otps = new HashMap<>();

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public UserService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public boolean register(String email, String password) {
        if (users.containsKey(email)) {
            return false;
        }
        users.put(email, new User(email, password));
        String otp = generateOTP();
        otps.put(email, otp);
        sendOTPEmail(email, otp, "Kích hoạt tài khoản của bạn");
        return true;
    }

    public boolean activateAccount(String email, String otp) {
        if (otps.containsKey(email) && otps.get(email).equals(otp)) {
            User user = users.get(email);
            user.setActive(true);
            otps.remove(email);
            return true;
        }
        return false;
    }

    public boolean login(String email, String password) {
        User user = users.get(email);
        return user != null && user.isActive() && user.getPassword().equals(password);
    }

    public boolean requestPasswordReset(String email) {
        if (users.containsKey(email)) {
            String otp = generateOTP();
            otps.put(email, otp);
            sendOTPEmail(email, otp, "Đặt lại mật khẩu của bạn");
            return true;
        }
        return false;
    }

    public boolean resetPassword(String email, String otp, String newPassword) {
        if (otps.containsKey(email) && otps.get(email).equals(otp)) {
            User user = users.get(email);
            user.setPassword(newPassword);
            otps.remove(email);
            return true;
        }
        return false;
    }

    private String generateOTP() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    private void sendOTPEmail(String email, String otp, String subject) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText("OTP của bạn là: " + otp);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}