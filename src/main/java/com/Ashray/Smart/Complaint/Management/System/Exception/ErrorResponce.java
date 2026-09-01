package com.Ashray.Smart.Complaint.Management.System.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponce {

    private LocalDateTime timeStamp;
    private int status;
    private String message;
}
