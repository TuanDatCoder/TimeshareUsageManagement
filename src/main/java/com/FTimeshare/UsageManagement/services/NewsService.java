package com.FTimeshare.UsageManagement.services;
import com.FTimeshare.UsageManagement.dtos.NewsDto;
import com.FTimeshare.UsageManagement.entities.AccountEntity;
import com.FTimeshare.UsageManagement.entities.NewsEntity;
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
    private AccountRepository accountRepository;

    public String uploadImage(MultipartFile file,
                              String newsTitle,
                              LocalDateTime newsPost,
                              String newsContent,
                              int newsViewer,
                              String newsStatus,
                              int accID) throws IOException {

//        byte[] imgData = file.getBytes();

        AccountEntity accountEntity = accountRepository.findById(accID)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + accID));

        // Kiểm tra xem tên ảnh đã tồn tại trong cơ sở dữ liệu chưa
        String originalFilename = file.getOriginalFilename();
        String filename = originalFilename;
        int counter = 1;

        while (newsRepository.existsByImgName(filename)) {
            // If it does, append a counter to the filename and try again
            filename = originalFilename.substring(0, originalFilename.lastIndexOf('.'))
                    + "_" + counter
                    + originalFilename.substring(originalFilename.lastIndexOf('.'));
            counter++;
        }

        NewsEntity newsEntity = newsRepository.save(NewsEntity.builder()
                .newsTitle(newsTitle)
                .newsPost(newsPost)
                .newsContent(newsContent)
                .imgName(filename)
                .imgData(ImageService.compressImage(file.getBytes()))
                .newsViewer(newsViewer)
                .newsStatus(newsStatus)
                .accID(accountEntity)
                .build());

        return "File uploaded successfully: " + filename;
    }


    public byte[] downloadImage(String imgName) {
        Optional<NewsEntity> dbImageData = newsRepository.findByImgName(imgName);
        return ImageService.decompressImage(dbImageData.get().getImgData());
    }


    public NewsDto editNews(int newsID, NewsDto updatedNews, MultipartFile file) throws IOException {
        NewsEntity existingNews = newsRepository.findById(newsID)
                .orElseThrow(() -> new RuntimeException("News not found with id: " + newsID));

        String originalFilename = file.getOriginalFilename();
        String filename = originalFilename;
        int counter = 1;

        while (newsRepository.existsByImgName(filename)) {
            // If it does, append a counter to the filename and try again
            filename = originalFilename.substring(0, originalFilename.lastIndexOf('.'))
                    + "_" + counter
                    + originalFilename.substring(originalFilename.lastIndexOf('.'));
            counter++;
        }

        existingNews.setNewsTitle(updatedNews.getNewsTitle());
        existingNews.setNewsPost(updatedNews.getNewsPost());
        existingNews.setNewsContent(updatedNews.getNewsContent());
        existingNews.setImgName(filename);
        existingNews.setImgData(ImageService.compressImage(file.getBytes()));
        existingNews.setNewsViewer(updatedNews.getNewsViewer());
        existingNews.setNewsStatus(updatedNews.getNewsStatus());

        NewsEntity savedNews = newsRepository.save(existingNews);
        return convertToDto(savedNews);
    }

    private NewsDto convertToDto(NewsEntity newsEntity) {
        NewsDto newsDto = new NewsDto();
        newsDto.setNewsID(newsEntity.getNewsID());
        newsDto.setNewsTitle(newsEntity.getNewsTitle());
        newsDto.setNewsPost(newsEntity.getNewsPost());
        newsDto.setNewsContent(newsEntity.getNewsContent());
        newsDto.setImgName("https://bookinghomestayswp.azurewebsites.net/api/news/imgView/"+newsEntity.getImgName());
        newsDto.setImgData(new byte[0]);
        newsDto.setNewsViewer(newsEntity.getNewsViewer());
        newsDto.setNewsStatus(newsEntity.getNewsStatus());

        if (newsEntity.getAccID() != null) {
            newsDto.setAccID(newsEntity.getAccID().getAccID());
        }

        return newsDto;
    }
    

}
