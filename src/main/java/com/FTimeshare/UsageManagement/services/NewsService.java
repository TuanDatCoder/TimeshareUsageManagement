package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.NewsDto;
import java.util.List;

public interface NewsService {
    List<NewsDto> getAllNews();
}
