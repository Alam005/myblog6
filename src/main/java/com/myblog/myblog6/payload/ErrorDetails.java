package com.myblog.myblog6.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorDetails {
    private String message;
    private String uri;
    private Date date;


}
