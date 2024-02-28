package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.entities.NewsEntity;
import com.FTimeshare.UsageManagement.entities.PictureEntity;
import com.FTimeshare.UsageManagement.repositories.AccountRepository;
import com.FTimeshare.UsageManagement.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
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

    @Autowired
    private NewsService newsService;
    @Autowired
    private AccountRepository accountRepository;

    public String uploadImage(MultipartFile file,
                              String newsTitle,
                              LocalDateTime newsPost,
                              String newsContent,
                              int newsViewer,
                              String newsStatus,
                              int accID) throws IOException {

        byte[] imgData = file.getBytes();

        AccountEntity accountEntity = accountRepository.findById(accID)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + accID));


        NewsEntity newsEntity = newsRepository.save(NewsEntity.builder()
                .newsTitle(newsTitle)
                .newsPost(newsPost)
                .newsContent(newsContent)
                .imgName(file.getOriginalFilename())
                .imgData(imgData)
                .newsViewer(newsViewer)
                .newsStatus(newsStatus)
                .accID(accountEntity)
                .build());

        return "File uploaded successfully: " + file.getOriginalFilename();
    }


}
