package com.java.amazoncloneapi.controller;

import com.java.amazoncloneapi.Entity.SignUpUser;
import com.java.amazoncloneapi.Model.RequestedOtpFromUser;
import com.java.amazoncloneapi.Model.ResponseSignUp;
import com.java.amazoncloneapi.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SignUpController {
    @Autowired
    UserService userService;
    @RequestMapping(value = "/signup",method = RequestMethod.POST)
    public ResponseSignUp createUser(@RequestBody SignUpUser signUpUser) throws MessagingException {
return userService.createUserService(signUpUser);
    }
    @PostMapping("verifysignupotp")
public String verifyOtp(@RequestBody RequestedOtpFromUser requestedOtpFromUser)
    {
        return userService.verifyNewUser(requestedOtpFromUser);
    }
}
