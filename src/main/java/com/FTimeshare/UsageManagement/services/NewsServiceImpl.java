package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.NewsDto;
import com.FTimeshare.UsageManagement.entities.NewsEntity;
import com.FTimeshare.UsageManagement.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {


    @Autowired
    private NewsRepository newsRepository;

    @Override
    public List<NewsDto> getAllNews() {
        List<NewsEntity> newsList = newsRepository.findAll();
        return newsList.stream()
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
    }

}

