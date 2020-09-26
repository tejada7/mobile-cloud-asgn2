/*
 * 
 * Copyright 2014 Jules White
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package org.magnum.mobilecloud.video;

import org.eclipse.jetty.http.HttpException;
import org.eclipse.jetty.server.Response;
import org.magnum.mobilecloud.video.repository.Video;
import org.magnum.mobilecloud.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

@Controller
public class VideoController {

	@Autowired
	private VideoService videoService;

	@RequestMapping(value = "/video", method = RequestMethod.POST)
	public @ResponseBody Video addVideoMetadata(@RequestBody Video video) {
		return videoService.registerVideo(video);
	}

	@RequestMapping(value = "/video", method = RequestMethod.GET)
	public @ResponseBody Collection<Video> getAllVideos() {
		return videoService.getAllVideos();
	}

	@RequestMapping(value = "/video/{id}", method = RequestMethod.GET)
	public @ResponseBody Video getVideo(@PathVariable("id") long id) {
		return videoService.getVideo(id);
	}

	@RequestMapping(value = "/video/{id}/like", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Video> likeVideo(@PathVariable("id") long id, Principal principal) {
		try {
			return new ResponseEntity(videoService.likeVideo(id, principal.getName()), HttpStatus.OK);
		} catch (HttpException e) {
			if (e.getStatus() == 400) {
				return new ResponseEntity(HttpStatus.BAD_REQUEST);
			}
			if (e.getStatus() == 404) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity(HttpStatus.OK);
	}

	@RequestMapping(value = "/video/{id}/unlike", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Video> unlikeVideo(@PathVariable("id") long id, Principal principal) {
		try {
			return new ResponseEntity(videoService.unlikeVideo(id, principal.getName()), HttpStatus.OK);
		} catch (HttpException e) {
			if (e.getStatus() == 400) {
				return new ResponseEntity(HttpStatus.BAD_REQUEST);
			}
			if (e.getStatus() == 404) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * GET /video/search/findByName?title={title}
	 * - Returns a list of videos whose titles match the given parameter or an empty
	 * list if none are found.
	 * /search/findByName
	 */
	@RequestMapping(value = "/video/search/findByName", method = RequestMethod.GET)
	public @ResponseBody Collection<Video> getVideosByName(@RequestParam("title") String title) {
		return videoService.getVideoByName(title);
	}

}
