package com.cdweb.backend.common;

import com.cdweb.backend.entities.Users;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class MailService{


    private final JavaMailSender mailSender;

    private final PasswordEncoder passwordEncoder;

    public Users generateOneTimePassword(Users user) throws MessagingException, UnsupportedEncodingException {
        String OTP = RandomString.make(8);
        user.setOtpCode(passwordEncoder.encode(OTP));
        user.setOtpRequestedTime(new Date());
        sendOTPEmail(user,OTP);
        return user;
    }

    public void sendOTPEmail(Users user, String OTP) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("accessories@gmail.com", "Accessories Shop");
        helper.setTo(user.getGmail());

        String subject = "Đây là mã xác nhận (OTP) của bạn - Nó có thời hạn 5 phút";

        String content = "<p>Xin chào " + user.getFullName() + "</p>"
                + "<p>Đây là mã xác nhận OTP của bạn: "
                + "<p><b>" + OTP + "</b></p>"
                + "<br>"
                + "<p>Note: Mã xác nhận này chỉ có thời hạn trong 5 phút!</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

    public Users clearOTP(Users user) {
        user.setOtpCode(null);
        user.setOtpRequestedTime(null);
        return user;
    }
}
