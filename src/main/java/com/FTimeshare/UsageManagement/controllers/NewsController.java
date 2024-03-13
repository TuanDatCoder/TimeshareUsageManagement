package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.AccountDto;
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
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:5173")
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
    @GetMapping("imgView/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
        byte[] imageData=newsService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }
    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("news") MultipartFile file,
                                         @RequestParam String newsTitle,
                                         @RequestParam String newsContent,
                                         @RequestParam int newsViewer,
                                         @RequestParam String newsStatus,
                                         @RequestParam int accID) throws IOException {

        LocalDateTime newsPost = LocalDateTime.now();
        String uploadImage = newsService.uploadImage(file, newsTitle, newsPost, newsContent, newsViewer, newsStatus, accID);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @PutMapping("/edit/{NewsID}")
    public ResponseEntity<?> editNews(
            @PathVariable int NewsID,
            @RequestParam("news") MultipartFile file,
            @RequestParam String newsTitle,
            @RequestParam String newsContent,
            @RequestParam int newsViewer,
            @RequestParam String newsStatus,
            @RequestParam int accID) {

        LocalDateTime newsPost = LocalDateTime.now();

        NewsDto updateNews =  NewsDto.builder()
                .newsTitle(newsTitle)
                .newsPost(newsPost)
                .newsContent(newsContent)
                .newsViewer(newsViewer)
                .newsStatus(newsStatus)
                .accID(accID)
                .build();
        try {
            NewsDto editedNews = newsService.editNews(NewsID, updateNews, file);
            return ResponseEntity.ok(editedNews);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating news: " + e.getMessage());
        }
    }


    // Helper method to convert Entity to DTO
    private NewsDto convertToDto(NewsEntity newsEntity) {
        NewsDto newsDto = new NewsDto();
        newsDto.setNewsID(newsEntity.getNewsID());
        newsDto.setNewsTitle(newsEntity.getNewsTitle());
        newsDto.setNewsPost(newsEntity.getNewsPost());
        newsDto.setNewsContent(newsEntity.getNewsContent());
        newsDto.setImgName("http://localhost:8080/api/news/imgView/"+newsEntity.getImgName());
        newsDto.setImgData(new byte[0]);
        newsDto.setNewsViewer(newsEntity.getNewsViewer());
        newsDto.setNewsStatus(newsEntity.getNewsStatus());

        if (newsEntity.getAccID() != null) {
            newsDto.setAccID(newsEntity.getAccID().getAccID());
        }

        return newsDto;
    }



}
