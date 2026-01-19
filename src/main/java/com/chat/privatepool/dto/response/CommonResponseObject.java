package com.chat.privatepool.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponseObject {

    private boolean status;
    private String message;
    private Collection<?> details;
    private String data;
    private String traceId;

    public static CommonResponseObject setErrorMessage(CommonResponseObject responseObject,String message){
        if(responseObject == null) responseObject = new CommonResponseObject();
        responseObject.setStatus(false);
        responseObject.setMessage(message);
        return responseObject;
    }

    public static CommonResponseObject setData(CommonResponseObject responseObject, String message) {
        if(responseObject == null) responseObject = new CommonResponseObject();
        responseObject.setStatus(true);
        responseObject.setMessage(message);
        return responseObject;
    }

    public static CommonResponseObject setData(CommonResponseObject responseObject, String message, Collection<?> details) {
        if(responseObject == null) responseObject = new CommonResponseObject();
        responseObject = setData(responseObject,message);
        responseObject.setDetails(details);
        return responseObject;
    }

    public static CommonResponseObject setData(CommonResponseObject responseObject, String message, Object object) {
        if(responseObject == null) responseObject = new CommonResponseObject();
        responseObject = setData(responseObject,message);
        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(object);
        responseObject.setData(data);
        return responseObject;
    }
}
