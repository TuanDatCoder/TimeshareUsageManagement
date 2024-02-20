package com.FTimeshare.UsageManagement.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PictureDto {

    private int img_id;
    private String img_name;
    private String img_url;


}