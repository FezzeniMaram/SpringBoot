package com.example.testapp.services;

import com.example.testapp.entities.Video;

import java.util.List;

public interface VideoInterface {
    public String addVideo(Video video);
    public void deleteVideo(Long id);
    public Video getVideoById(Long id);
    public List<Video> getAllVideo();
    public Video updateVideo(Long id, Video video);

}
