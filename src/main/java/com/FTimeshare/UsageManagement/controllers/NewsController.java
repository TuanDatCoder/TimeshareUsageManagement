package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.NewsDto;
import com.FTimeshare.UsageManagement.entities.NewsEntity;
import com.FTimeshare.UsageManagement.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    @Autowired
    private NewsService newsService;


    @GetMapping("/customerview")
    public ResponseEntity<List<NewsDto>> getAllNews() {
        List<NewsEntity> news = newsService.getAllNews();
        List<NewsDto> newsDtos = news.stream()
                .map(newsEntity -> new NewsDto(
                        newsEntity.getNewsID(),
                        newsEntity.getNewsTitle(),
                        newsEntity.getNewsPost(),
                        newsEntity.getNewsContent(),
                        newsEntity.getNewsPicture(),
                        newsEntity.getNewsViewer(),
                        newsEntity.getNewsStatus(),
                        newsEntity.getAccID().getAccID()))
                .collect(Collectors.toList());


        return ResponseEntity.ok().body(newsDtos);
    }


}