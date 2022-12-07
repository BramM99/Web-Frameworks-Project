package app.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender getJavaMailSender;

    @Async
    public void sendEmail(SimpleMailMessage email) {
        getJavaMailSender.send(email);
    }
}
