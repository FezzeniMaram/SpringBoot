package com.example.testapp.serviceimplement;

import com.example.testapp.entities.Video;
import com.example.testapp.repository.VideoRepository;
import com.example.testapp.services.VideoInterface;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class VideoService implements VideoInterface {
    @Autowired
    VideoRepository videoRepository;
    @Override
    public Video addVideo(Video video) {
        return videoRepository.save(video);
    }

    @Override
    public void deleteVideo(Long id) {
        videoRepository.deleteById(id);
    }

    @Override
    public Video getVideoById(Long id) {
        return videoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Video> getAllVideo() {
        return videoRepository.findAll();
    }

    @Override
    public Video updateVideo(Long id, Video video) {
        Video video1 = videoRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Video avec l'ID " + id + " non trouvé"));
            video1.setTitre(video.getTitre());
        return videoRepository.save(video1);
    }
}
