package com.example.testapp.controller;

import com.example.testapp.entities.Chapitre;
import com.example.testapp.entities.Video;
import com.example.testapp.services.ChapitreIntreface;
import com.example.testapp.services.VideoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("video")
public class VideoController {
    @Autowired
    VideoInterface videoInterface;
    @Autowired
    ChapitreIntreface chapitreIntreface;

    @PostMapping("/add")
    public Video addVideo(@RequestBody Map<String, Object> requestData){
try {
    Long chapitreId = requestData.get("chapitre_id") != null? Long.valueOf(requestData.get("chapitre_id").toString()):null;
    Chapitre chapitre = chapitreIntreface.getChapirteById(chapitreId);

    Video video;
    video = new Video(null, requestData.get("titre").toString(),chapitre);
    return videoInterface.addVideo(video);


}catch (Exception e){
    System.out.println(e);
    throw new RuntimeException(e);
}
    }

    @DeleteMapping("/delete/{id}")
    public void deleteVideo(@PathVariable Long id){
        videoInterface.deleteVideo(id);
    }

    @PatchMapping("/update/{id}")
    public Video updatevideo(@PathVariable Long id , @RequestBody Video video){
        return videoInterface.updateVideo(id,video);
    }
    @GetMapping("/getAllVideo")
        public List<Video> getAllVideo(){
            return videoInterface.getAllVideo();

    }
    @GetMapping("/getById/{id}")
    public Video getById(@PathVariable Long id ){
        return videoInterface.getVideoById(id);
    }









}
