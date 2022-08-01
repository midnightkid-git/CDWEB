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

        helper.setFrom("peakyblinders@gmail.com", "By the order of Peaky blinders");
        helper.setTo(user.getGmail());

        String subject = "Your OTP will be expired in 5 minute";

        String content = "<p>Hello " + user.getFullName() + "</p>"
                + "<p>Here is your OTP "
                + "<p><b>" + OTP + "</b></p>"
                + "<br>"
                + "<p>Note: Your OTP will be expired in 5 minute!</p>";

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
