package com.kushi.in.app.controller;

import com.kushi.in.app.model.GalleryDTO;
import com.kushi.in.app.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gallery")
@CrossOrigin(origins ={ "http://localhost:5174", "http://localhost:5173"})
public class GalleryController {

    @Autowired
    private GalleryService galleryService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("description") String description) {
        try {
            galleryService.saveFile(file, description);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        }
    }  // ✅ properly closed uploadFile method

    @GetMapping
    public ResponseEntity<List<GalleryDTO>> getAllFiles() {
        return ResponseEntity.ok(galleryService.getAllFiles());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        galleryService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/description")
    public ResponseEntity<String> updateDescription(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload) {
        String description = payload.get("description");
        boolean updated = galleryService.updateDescription(id, description);
        if (updated) {
            return ResponseEntity.ok("Description updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
