package com.siesth.mothus.dataManagementService;

import com.siesth.mothus.model.ValidationCode;
import com.siesth.mothus.repository.UserRepository;
import com.siesth.mothus.repository.ValidationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class EmailService implements IEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    UserRepository userRepository;

    @Autowired
    IUserManagement userManagement;

    @Autowired
    ValidationCodeRepository validationCodeRepository;

    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
    }

    private void createValidationCode(String username) {
        ValidationCode validationCodeObject = new ValidationCode();
        validationCodeRepository.save(validationCodeObject);

        userManagement.getUserByUsernameAndUpdateValidationCode(username, validationCodeObject);
    }

    @Override
    public int getValidationCodeTime(String username) {
        ValidationCode vc = userRepository.findUserByUsername(username).getValidationCode();
        if (vc == null) { // if there is no validation code we create one
            createValidationCode(username);
            vc = userRepository.findUserByUsername(username).getValidationCode();
        }
        assert vc != null;
        LocalDateTime lt = vc.getCreationDate();
        int duration = vc.getDuration();
        LocalDateTime currentTime = LocalDateTime.now();

        Duration d = Duration.between(lt.plusSeconds(duration), currentTime);
        return (int) d.getSeconds();
    }

    @Override
    public boolean checkValidationCode(String username, String code) {
        ValidationCode vc = userRepository.findUserByUsername(username).getValidationCode();
        if (vc == null) {
            return false;
        }
        return vc.getCode().equals(code);
    }

    @Override
    public String getValidationCode(String username) {
        ValidationCode vc = userRepository.findUserByUsername(username).getValidationCode();
        if (vc == null) { // if there is no validation code we create one
            createValidationCode(username);
            vc = userRepository.findUserByUsername(username).getValidationCode();
        }
        assert vc != null;
        return vc.getCode();
    }

    @Override
    public void createNewValidationCode(String username) {
        createValidationCode(username);
    }

    @Override
    public int getDurationMinutes(String username) {
        ValidationCode vc = userRepository.findUserByUsername(username).getValidationCode();
        if (vc == null) {
            return -1;
        }

        // Because the duration is a int, dividing it with another int already returns as an int
        return vc.getDuration() / 60;
    }

    @Override
    public void removeValidationCode(String username) {
        ValidationCode vc = userRepository.findUserByUsername(username).getValidationCode();
        if (vc != null) {
            validationCodeRepository.deleteByIdValidationCode(vc.getIdValidationCode());
        }
    }
}

