package com.thelastcodebenders.social_commerce_be.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PinataApiResponse {
    private String IpfsHash;
    private String PinSize;
    private String Timestamp;
    private String ID;
    private String Name;
    private int NumberOfFiles;
    private String MimeType;
    private String GroupId;
    private String Keyvalues;
    private boolean isDuplicate;
}
