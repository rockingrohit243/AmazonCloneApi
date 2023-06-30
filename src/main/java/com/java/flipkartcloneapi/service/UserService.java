package com.java.flipkartcloneapi.service;
import com.java.flipkartcloneapi.Entity.SignUpUser;
import com.java.flipkartcloneapi.Model.RequestedOtpFromUser;
import com.java.flipkartcloneapi.Model.ResponseSignUp;
import com.java.flipkartcloneapi.Repository.UserSignupRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;
@Service
public class UserService {
    @Autowired
    UserSignupRepository userSignupRepository;
    ResponseSignUp responseSignUp = new ResponseSignUp();
    @Autowired
    private JavaMailSender emailSender;

    public ResponseSignUp createUserService(SignUpUser signUpUser) throws MessagingException {
        Optional<SignUpUser> signUpUser1 = Optional.ofNullable(userSignupRepository.findByEmail(signUpUser.getEmail()));
        if (signUpUser1.isPresent()) {
            responseSignUp.setStatus("-1");
            responseSignUp.setSuccess(false);
            responseSignUp.setStatusDesc("User already exists with this email!! \n  please signIn with your credentials");
            return responseSignUp;
        } else {
            validateUser(signUpUser);
            if (responseSignUp.getCheck() == "0") {
                sendOtpToUser(signUpUser);
                responseSignUp.setSuccess(true);
                responseSignUp.setStatus("0");
                responseSignUp.setStatusDesc("otp send successfully");
                return responseSignUp;
            }
        }
        return responseSignUp;
    }



    private ResponseSignUp validateUser(SignUpUser signUpUser) {
        //todo for validating first name
        String firstNameRegex = "^[A-Z][A-Za-z]{1,15}$";
        String passwordRegExp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,18}$";
        String lastNameRegExp = "^[A-Z][A-Za-z]{1,15}$";
        String emailRegExp = "^[a-z-0-9+_.-]+@[a-z-0-9.-]+\\.[a-z-0-9]+$";
        if (!signUpUser.getFirstName().matches(firstNameRegex)) {
            responseSignUp.setStatus("-1");
            responseSignUp.setSuccess(false);
            responseSignUp.setStatusDesc("kindly enter a valid first name with first character as uppercase");
            responseSignUp.setCheck("-1");
            return responseSignUp;
        }
        //todo for validating last name
        if (!signUpUser.getLastName().matches(lastNameRegExp)) {
            responseSignUp.setStatus("-1");
            responseSignUp.setSuccess(false);
            responseSignUp.setStatusDesc("kindly enter a valid last name with first character as uppercase");
            responseSignUp.setCheck("-1");
            return responseSignUp;
        }
        //todo for validating email
        if (!signUpUser.getEmail().matches(emailRegExp)) {
            responseSignUp.setStatus("-1");
            responseSignUp.setSuccess(false);
            responseSignUp.setStatusDesc("kindly enter a valid email");
            responseSignUp.setCheck("-1");
            responseSignUp.setCheck("-1");
            return responseSignUp;
        }
        // todo for validating password
        else if (!signUpUser.getPassword().matches(passwordRegExp)) {
            responseSignUp.setStatus("-1");
            responseSignUp.setSuccess(false);
            responseSignUp.setStatusDesc("password must  contains at least 8 characters and at most 20 characters.\n" + "It contains at least one digit.\n" + "It contains at least one upper case alphabet.\n" + "It contains at least one lower case alphabet.\n" + "It contains at least one special character which includes !@#$%&*()-+=^.\n" + "It doesn’t contain any white space.");
            responseSignUp.setCheck("-1");
            return responseSignUp;
        } else if (!(signUpUser.getPassword().equals(signUpUser.getPasswordAgain()))) {
            responseSignUp.setStatus("-1");
            responseSignUp.setSuccess(false);
            responseSignUp.setStatusDesc("both password must be same");
            responseSignUp.setCheck("-1");
            return responseSignUp;
        } else {
            responseSignUp.setCheck("0");
            return responseSignUp;
        }
    }

    // todo verify user
    public String verifyNewUser(RequestedOtpFromUser requestedOtpFromUser) {
        Optional<SignUpUser> signUpUser;
        signUpUser = Optional.ofNullable(userSignupRepository.findByEmail(requestedOtpFromUser.getSameEmail()));
        if (signUpUser.isPresent()) {
            if (Objects.equals(signUpUser.get().getOtp(), requestedOtpFromUser.getRequestedOtp())) {
                return "amazonHomePage";
            }

            else
                return "Invalid otp";

        } else
            return "email not found";
    }


       // todo for sending otp to the user
    private void sendOtpToUser(SignUpUser signUpUser) throws MessagingException {
        //todo generating random 6 digit otp
        int otpLength = 6;
        StringBuilder otp = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < otpLength; i++) {
            int digit = random.nextInt(10);  // Generate a random digit from 0 to 9
            otp.append(digit);
        }
        String generatedOtp = otp.toString();
        System.out.println("otp is" + generatedOtp);
        MimeMessage message11 = emailSender.createMimeMessage();
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(message11, true);
        helper.setTo(signUpUser.getEmail());
        helper.setFrom("amazon.servicebackend@gmail.com");
        helper.setSubject("Amazon password assistance");
        helper.setText("To authenticate, please use the following One Time Password (OTP):\n" + "\n" + generatedOtp + "\n" + "Don't share this OTP with anyone. Our customer service team will never ask you for your password, OTP, credit card, or banking info.\n" + "\n" + "We hope to see you again soon.");
        emailSender.send(message11);
        System.out.println("otp send");
        signUpUser.setOtp(generatedOtp);
        userSignupRepository.save(signUpUser);

    }
}
