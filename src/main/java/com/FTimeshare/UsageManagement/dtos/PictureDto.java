package com.FTimeshare.UsageManagement.dtos;


import com.FTimeshare.UsageManagement.entities.ProductEntity;
import com.FTimeshare.UsageManagement.entities.RoleEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


public class PictureDto {

    private int img_id;
    private String img_name;
    private String img_url;


}
