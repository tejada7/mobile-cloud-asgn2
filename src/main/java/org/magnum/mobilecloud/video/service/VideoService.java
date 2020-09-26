package org.magnum.mobilecloud.video.service;

import org.magnum.mobilecloud.video.repository.Video;
import org.magnum.mobilecloud.video.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    public Video registerVideo(Video video) {
        return videoRepository.save(video);
    }

    public Collection<Video> getAllVideos() {
        return (Collection<Video>) videoRepository.findAll();
    }
}
