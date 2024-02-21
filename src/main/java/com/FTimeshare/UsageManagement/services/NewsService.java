package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.entities.NewsEntity;
import com.FTimeshare.UsageManagement.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    //List<NewsDto> getAllNews();

    @Autowired
    private NewsRepository newsRepository;
    public List<NewsEntity> getAllNews() {
        return newsRepository.findAll();
    }
}
