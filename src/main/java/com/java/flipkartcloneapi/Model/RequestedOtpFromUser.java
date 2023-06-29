package com.java.flipkartcloneapi.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class RequestedOtpFromUser {
    private String requestedOtp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String statusDesc;

}
