package com.java.flipkartcloneapi.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class RequestedOtpFromUser {
    private String requestedOtp;
    @JsonIgnore
    private String statusDesc;
    private String sameEmail;

}
