package com.example.DonationInUniversity.controller.api;

import com.example.DonationInUniversity.model.ProjectDetailImage;
import com.example.DonationInUniversity.repository.ProjectDetailImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.MalformedURLException;

@RestController
public class ResourceController {

    private final ProjectDetailImageRepository imageRepository;
    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    public ResourceController(ProjectDetailImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @GetMapping("/api/project_detail/image/{imageId}")
    public ResponseEntity<Resource> getProjectImage(@PathVariable Integer imageId) {
        logger.info("Request received to fetch image with ID: {}", imageId);

        ProjectDetailImage image = imageRepository.findById(imageId)
                .orElseThrow(() -> {
                    logger.error("Image not found with id: {}", imageId);
                    return new RuntimeException("Image not found with id: " + imageId);
                });

        // Directly use the path stored in the database
        Path path = Paths.get(image.getPathImage());
        logger.info("Accessing image file at path: {}", path.toString());

        try {
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() && resource.isReadable()) {
                logger.info("Successfully found and accessed image at path: {}", path.toString());
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG) // Adjust the MediaType if needed
                        .body(resource);
            } else {
                logger.warn("Image file not found or not readable at path: {}", path.toString());
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            logger.error("Failed to read image file at path: {}", path.toString(), e);
            return ResponseEntity.badRequest().build();
        }
    }
}
