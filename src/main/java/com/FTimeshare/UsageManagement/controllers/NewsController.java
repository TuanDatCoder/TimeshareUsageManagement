package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.NewsDto;
import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.entities.NewsEntity;
import com.FTimeshare.UsageManagement.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
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

    // Delete news
    @DeleteMapping("/delete/{newsId}")
    public ResponseEntity<String> deleteNews(@PathVariable int newsId) {
        newsService.deleteNewsById(newsId);
        return ResponseEntity.ok("News with ID " + newsId + " has been deleted successfully.");
    }
    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
        byte[] imageData=newsService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }
    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("news") MultipartFile file,
                                         @RequestParam String newsTitle,
                                         @RequestParam String newsPost,
                                         @RequestParam String newsContent,
                                         @RequestParam int newsViewer,
                                         @RequestParam String newsStatus,
                                         @RequestParam int accID) throws IOException {
        LocalDateTime parsedNewsPost = LocalDateTime.parse(newsPost);
        String uploadImage = newsService.uploadImage(file, newsTitle, parsedNewsPost, newsContent, newsViewer, newsStatus, accID);

        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }



    // Helper method to convert Entity to DTO
    private NewsDto convertToDto(NewsEntity newsEntity) {
        NewsDto newsDto = new NewsDto();
        newsDto.setNewsID(newsEntity.getNewsID());
        newsDto.setNewsTitle(newsEntity.getNewsTitle());
        newsDto.setNewsPost(newsEntity.getNewsPost());
        newsDto.setNewsContent(newsEntity.getNewsContent());
        newsDto.setImgName(newsEntity.getImgName());
        newsDto.setImgData(newsEntity.getImgData());
        newsDto.setNewsViewer(newsEntity.getNewsViewer());
        newsDto.setNewsStatus(newsEntity.getNewsStatus());

        if (newsEntity.getAccID() != null) {
            newsDto.setAccID(newsEntity.getAccID().getAccID());
        }

        return newsDto;
    }

    // Helper method to convert DTO to Entity
    private NewsEntity convertToEntity(NewsDto newsDto) {
        NewsEntity newsEntity = new NewsEntity();
        newsEntity.setNewsID(newsDto.getNewsID());
        newsEntity.setNewsTitle(newsDto.getNewsTitle());
        newsEntity.setNewsPost(newsDto.getNewsPost());
        newsEntity.setNewsContent(newsDto.getNewsContent());
        newsEntity.setImgName(newsDto.getImgName());
        newsEntity.setImgData(newsDto.getImgData());
        newsEntity.setNewsViewer(newsDto.getNewsViewer());
        newsEntity.setNewsStatus(newsDto.getNewsStatus());

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccID(newsDto.getAccID());

        newsEntity.setAccID(accountEntity);
        // You can map other fields here if needed
        return newsEntity;
    }



}
