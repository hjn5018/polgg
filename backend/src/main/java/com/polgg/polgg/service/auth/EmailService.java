package com.polgg.polgg.service.auth;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final StringRedisTemplate redisTemplate;

    @Value("${spring.mail.username}")
    private String fromEmail;

    private static final String REDIS_PREFIX = "EMAIL_VERIFY:";
    private static final long VERIFY_CODE_EXPIRATION_MINUTES = 5;

    public void sendVerificationEmail(String toEmail) {
        String verificationCode = generateRandomCode();

        // Save to Redis with 5-minute expiration
        redisTemplate.opsForValue().set(
                REDIS_PREFIX + toEmail,
                verificationCode,
                Duration.ofMinutes(VERIFY_CODE_EXPIRATION_MINUTES));

        sendEmail(toEmail, verificationCode);
    }

    private String generateRandomCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    private void sendEmail(String toEmail, String code) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("[POL.GG] 이메일 인증 코드가 도착했습니다.");

            String content = "<h3>안녕하세요, 폴리텍 AI융합소프트웨어과 포트폴리오 관리 플랫폼 [POL.GG]입니다.</h3>" +
                    "<p>회원가입 완료를 위해 아래의 인증 코드를 입력해주세요.</p>" +
                    "<h1>" + code + "</h1>" +
                    "<p>인증 코드는 발송 후 5분 동안만 유효합니다.</p>";

            helper.setText(content, true);
            mailSender.send(message);
            log.info("Verification email sent to: {}", toEmail);
        } catch (MessagingException e) {
            log.error("Failed to send email to {}", toEmail, e);
            throw new RuntimeException("이메일 발송에 실패했습니다.");
        }
    }

    public boolean verifyCode(String email, String code) {
        String storedCode = redisTemplate.opsForValue().get(REDIS_PREFIX + email);
        return code != null && code.equals(storedCode);
    }
}
