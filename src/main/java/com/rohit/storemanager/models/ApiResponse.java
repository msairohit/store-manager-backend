package com.rohit.storemanager.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {

    public Object result;
    public String message;
    public ResponseStatus responseStatus;
}
