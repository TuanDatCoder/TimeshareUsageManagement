package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.NewsDto;
import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.entities.NewsEntity;
import com.FTimeshare.UsageManagement.entities.PictureEntity;
import com.FTimeshare.UsageManagement.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    // Get all news
    public List<NewsEntity> getAllNews() {
        return newsRepository.findAll();
    }

    // Add news
    public NewsEntity addNews(NewsEntity newsEntity) {
        return newsRepository.save(newsEntity);
    }

    // Delete news by ID
    public void deleteNewsById(int newsId) {
        newsRepository.deleteById(newsId);
    }

    public NewsEntity getNewsById(int newsId) {
        return newsRepository.findById(newsId).orElse(null);
    }




}
