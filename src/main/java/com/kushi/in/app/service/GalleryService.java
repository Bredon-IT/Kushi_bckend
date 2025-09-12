package com.kushi.in.app.service;

import com.kushi.in.app.model.GalleryDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface GalleryService {
    void saveFile(MultipartFile file, String description) throws IOException;
    List<GalleryDTO> getAllFiles();
    void deleteFile(Long id);
    boolean updateDescription(Long id, String description);
}
