package com.java.amazoncloneapi.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class RequestedOtpFromUser {
    private String requestedOtp;
    @JsonIgnore
    private String statusDesc;
    private String sameEmail;

}
