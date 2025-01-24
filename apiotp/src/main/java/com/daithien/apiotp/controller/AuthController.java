package com.daithien.apiotp.controller;

import com.daithien.apiotp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        boolean success = userService.register(email, password);
        if (success) {
            return ResponseEntity.ok("Đăng ký thành công. Kiếm tra OTP được gửi qua mail của bạn.");
        } else {
            return ResponseEntity.badRequest().body("Email đã tồn tại.");
        }
    }

    @PostMapping("/activate")
    public ResponseEntity<String> activateAccount(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");
        boolean success = userService.activateAccount(email, otp);
        if (success) {
            return ResponseEntity.ok("Kích hoạt tài khoản thành công.");
        } else {
            return ResponseEntity.badRequest().body("OTP hoặc email không hợp lệ.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        boolean success = userService.login(email, password);
        if (success) {
            return ResponseEntity.ok("Đăng nhập thành công.");
        } else {
            return ResponseEntity.badRequest().body("Sai mật khẩu hoặc tài khoản chưa được kích hoạt.");
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        boolean success = userService.requestPasswordReset(email);
        if (success) {
            return ResponseEntity.ok("OTP đặt lại mật khẩu đã được gửi qua mail.");
        } else {
            return ResponseEntity.badRequest().body("Email không tồn tại.");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");
        String newPassword = request.get("newPassword");
        boolean success = userService.resetPassword(email, otp, newPassword);
        if (success) {
            return ResponseEntity.ok("Đặt lại mật khẩu thành công");
        } else {
            return ResponseEntity.badRequest().body("OTP hoặc email không hợp lệ.");
        }
    }
}

