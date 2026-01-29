package org.apirest.talky.controllers;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.apirest.talky.dto.PageRequest;
import org.apirest.talky.dto.PageResponse;
import org.apirest.talky.dto.PostRequest;
import org.apirest.talky.services.PageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/pages")
@AllArgsConstructor
public class PageController {

    private final PageService pageService;

    @GetMapping("/{title}")
    public ResponseEntity<PageResponse> getPage(@PathVariable String title){
        return ResponseEntity.ok(this.pageService.readPageByTitle(title));
    }

    @PostMapping
    public ResponseEntity<?> postPage(@RequestBody PageRequest request){

        request.setTitle(this.normalizeTitle(request.getTitle()));
        final String uri = this.pageService.createPage(request).getTitle();

        return ResponseEntity.created(URI.create(uri)).build();
    }

    @PutMapping("/{title}")
    public ResponseEntity<PageResponse> updatePage(@PathVariable String title, @RequestBody PageRequest request){


        return ResponseEntity.ok(this.pageService.updatePage(title, request));
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<Void> deletePage(@PathVariable String title){

        this.pageService.deletePage(title);
        return ResponseEntity.noContent().build();
    }

    private String normalizeTitle(String title) {
        if(title.contains(" ")){
            return title.trim().toLowerCase().replaceAll("\\s+", "-");
        } else {
            return title;
        }
    }

    @PostMapping("/{title}/post")
    public ResponseEntity<PageResponse> postPage(@RequestBody PostRequest request, @PathVariable String title){

        return ResponseEntity.ok(this.pageService.createPost(request, title));
    }

    @DeleteMapping("/{title}/post/{idPost}")
    public ResponseEntity<Void> deletePost(@PathVariable Long idPost, @PathVariable String title){
        this.pageService.deletePost(idPost, title);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/img/upload")
    public ResponseEntity<String> uploadImage(@RequestParam(value = "file") MultipartFile file) {

        try {

            final var imageUrl = "C:\\Users\\samayen\\IdeaProjects\\API-Rest\\src\\main\\resources\\static\\img";
            final var filePath = Paths.get(imageUrl);

            if(!Files.exists(filePath)){
                Files.createDirectory(filePath);
            }

            final var fullName = imageUrl + "/" + file.getOriginalFilename();
            final var destination = new File(imageUrl);
            file.transferTo(destination);

            return ResponseEntity.ok("Uploaded successfully: " + fullName);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Cant upload the image");
        }
    }

}
