package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.NewsDto;
import com.FTimeshare.UsageManagement.entities.NewsEntity;
import com.FTimeshare.UsageManagement.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    // Get all news
    @GetMapping("/view")
    public ResponseEntity<List<NewsDto>> getAllNews() {
        List<NewsEntity> newsEntities = newsService.getAllNews();
        List<NewsDto> newsDto = newsEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(newsDto);
    }

    @GetMapping("viewDetail/{newsId}")
    public ResponseEntity<NewsEntity> viewNewsById(@PathVariable int newsId) {
        NewsEntity news = newsService.getNewsById(newsId);
        if (news != null) {
            return ResponseEntity.ok(news);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Add news
    @PostMapping("/add")
    public ResponseEntity<NewsDto> addNews(@RequestBody NewsDto newsDto) {
        NewsEntity newsEntity = convertToEntity(newsDto);
        newsEntity = newsService.addNews(newsEntity);
        NewsDto responseDto = convertToDto(newsEntity);
        return ResponseEntity.ok(responseDto);
    }

    // Delete news
    @DeleteMapping("/delete/{newsId}")
    public ResponseEntity<String> deleteNews(@PathVariable int newsId) {
        newsService.deleteNewsById(newsId);
        return ResponseEntity.ok("News with ID " + newsId + " has been deleted successfully.");
    }




    // Helper method to convert Entity to DTO
    private NewsDto convertToDto(NewsEntity newsEntity) {
        NewsDto newsDto = new NewsDto();
        newsDto.setNewsID(newsEntity.getNewsID());
        newsDto.setNewsTitle(newsEntity.getNewsTitle());
        newsDto.setNewsPost(newsEntity.getNewsPost());
        newsDto.setNewsContent(newsEntity.getNewsContent());
        newsDto.setNewsPicture(newsEntity.getNewsPicture());
        newsDto.setNewsViewer(newsEntity.getNewsViewer());
        newsDto.setNewsStatus(newsEntity.getNewsStatus());
        // You can map other fields here if needed
        return newsDto;
    }

    // Helper method to convert DTO to Entity
    private NewsEntity convertToEntity(NewsDto newsDto) {
        NewsEntity newsEntity = new NewsEntity();
        newsEntity.setNewsTitle(newsDto.getNewsTitle());
        newsEntity.setNewsPost(newsDto.getNewsPost());
        newsEntity.setNewsContent(newsDto.getNewsContent());
        newsEntity.setNewsPicture(newsDto.getNewsPicture());
        newsEntity.setNewsViewer(newsDto.getNewsViewer());
        newsEntity.setNewsStatus(newsDto.getNewsStatus());
        // You can map other fields here if needed
        return newsEntity;
    }
}
