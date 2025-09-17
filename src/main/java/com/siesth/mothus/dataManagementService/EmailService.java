package com.siesth.mothus.dataManagementService;

import com.siesth.mothus.model.User;
import com.siesth.mothus.model.ValidationCode;
import com.siesth.mothus.repository.UserRepository;
import com.siesth.mothus.repository.ValidationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * This class is used to manage the email service.
 */
@Service
public class EmailService implements IEmailService {
    /**
     * This field is used to get the java mail sender.
     */
    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * This field is used to get the user repository.
     */
    @Autowired
    UserRepository userRepository;

    /**
     * This field is used to get the user management.
     */
    @Autowired
    IUserManagement userManagement;

    /**
     * This field is used to get the validation code repository.
     */
    @Autowired
    ValidationCodeRepository validationCodeRepository;

    /**
     * This method is used to send an email.
     * @param to      the receiver
     * @param subject the subject
     * @param body    the body
     */
    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
    }

    /**
     * This method is used to create a validation code and send it by email.
     * @param username the username to send the validation code
     */
    private void createValidationCode(String username) {
        ValidationCode validationCodeObject = new ValidationCode();
        validationCodeRepository.save(validationCodeObject);

        userManagement.getUserByUsernameAndUpdateValidationCode(username, validationCodeObject);
    }

    /**
     * This method is used to get the validation code time.
     * @param username the username to get the validation code time
     * @return the validation code time
     */
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

        Duration d = Duration.between(currentTime, lt.plusSeconds(duration));
        return (int) d.getSeconds();
    }

    /**
     * This method is used to check if the validation code is valid.
     * @param username the username to check the validation code
     * @param code     the code to check
     * @return true if the validation code is valid, false otherwise
     */
    @Override
    public boolean checkValidationCode(String username, String code) {
        ValidationCode vc = userRepository.findUserByUsername(username).getValidationCode();
        if (vc == null) {
            return false;
        }
        return vc.getCode().equals(code);
    }

    /**
     * This method is used to get the validation code.
     * @param username the username to get the validation code
     * @return the validation code
     */
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

    /**
     * This method is used to create a new validation code.
     * @param username the username to create a new validation code
     */
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

        // Because the duration is an int, dividing it with another int already returns as an int
        return vc.getDuration() / 60;
    }

    @Override
    public void removeValidationCode(String username) {
        User user = userRepository.findUserByUsername(username);
        ValidationCode vc = user.getValidationCode();
        if (vc != null) {
            user.setValidationCode(null);
            validationCodeRepository.delete(vc);
        }
    }
}

