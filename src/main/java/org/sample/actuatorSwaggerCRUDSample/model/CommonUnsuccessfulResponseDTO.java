package org.sample.actuatorSwaggerCRUDSample.model;

import java.util.LinkedList;
import java.util.List;

public class CommonUnsuccessfulResponseDTO<Data> extends CommonResponseDTO<Data> {
    private List<ErrorDesriptor> errorDesriptorList;

    public CommonUnsuccessfulResponseDTO(){
        this.errorDesriptorList = new LinkedList<>();
    }

    public CommonUnsuccessfulResponseDTO(int status,ErrorDesriptor errorDesriptor) {
        super(status);
        this.errorDesriptorList = new LinkedList<>();
        errorDesriptorList.add(errorDesriptor);
    }

    public CommonUnsuccessfulResponseDTO(int status, CommonMessageDTO commonMessageDTO,ErrorDesriptor errorDesriptor) {
        super(status,commonMessageDTO);
        this.errorDesriptorList = new LinkedList<>();
        errorDesriptorList.add(errorDesriptor);
    }

    public CommonUnsuccessfulResponseDTO(int status, String type, String messageKey, String message,ErrorDesriptor errorDesriptor) {
        this(status,new CommonMessageDTO(type,messageKey,message),errorDesriptor);
    }

    public List<ErrorDesriptor> getErrorDesriptorList() {
        return errorDesriptorList;
    }

    public void setErrorDesriptorList(List<ErrorDesriptor> errorDesriptorList) {
        this.errorDesriptorList = errorDesriptorList;
    }
}
