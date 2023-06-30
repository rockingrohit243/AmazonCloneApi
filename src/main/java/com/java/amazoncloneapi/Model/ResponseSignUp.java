package com.java.amazoncloneapi.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ResponseSignUp {
    private String status;
    private boolean success;
    private String statusDesc;
    @JsonIgnore
    private String check;
}
