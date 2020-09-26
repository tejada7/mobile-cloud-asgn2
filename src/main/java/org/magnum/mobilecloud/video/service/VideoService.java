package org.magnum.mobilecloud.video.service;

import org.eclipse.jetty.http.HttpException;
import org.magnum.mobilecloud.video.repository.Video;
import org.magnum.mobilecloud.video.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

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

    public Video getVideo(long id) {
        return videoRepository.findOne(id);
    }

    public Video likeVideo(long id, String username) throws HttpException {
        final Video video = videoRepository.findOne(id);

        if (null == video) {
            throw new HttpException(404, "Video not found");
        }
        if (video.getLikedBy().contains(username)) {
            throw new HttpException(400, "You have already liked the video.");
        }

        video.setLikes(video.getLikes() + 1);
        video.getLikedBy().add(username);
        videoRepository.save(video);

        return video;
    }

    public Video unlikeVideo(long id, String username) throws HttpException {
        final Video video = videoRepository.findOne(id);

        if (null == video) {
            throw new HttpException(404, "Video not found");
        }
        if (!video.getLikedBy().contains(username)) {
            throw new HttpException(400, "You gotta first like the video.");
        }

        video.setLikes(video.getLikes() - 1);
        video.getLikedBy().remove(username);
        videoRepository.save(video);

        return video;
    }

    public Collection<Video> getVideoByName(String videoName) {
        final Collection<Video> videosByName = videoRepository.findByName(videoName);
        if (null == videosByName) {
            return Collections.emptyList();
        }

        return videosByName;
    }
}
