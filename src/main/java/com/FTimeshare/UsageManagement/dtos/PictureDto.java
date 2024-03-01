package com.FTimeshare.UsageManagement.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PictureDto {

    private int imgID;
    private String imgName;
    private byte[] imgData;
    private int productID;


}
