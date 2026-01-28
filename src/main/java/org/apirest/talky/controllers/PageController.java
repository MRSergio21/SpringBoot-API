package org.apirest.talky.controllers;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.apirest.talky.dto.PageResponse;
import org.apirest.talky.services.PageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pages")
@AllArgsConstructor
public class PageController {

    private final PageService pageService;

    @GetMapping
    public ResponseEntity<PageResponse> getPage(){
        return null;
    }

    @PostConstruct
    public ResponseEntity<?> postPage(){
        return null;
    }

    @PutMapping
    public ResponseEntity<?> putPage(){
        return null;
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePage(){
        return null;
    }
}
