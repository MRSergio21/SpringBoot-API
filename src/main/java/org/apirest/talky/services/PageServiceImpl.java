package org.apirest.talky.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apirest.talky.dto.PageRequest;
import org.apirest.talky.dto.PageResponse;
import org.apirest.talky.dto.PostRequest;
import org.apirest.talky.dto.PostResponse;
import org.apirest.talky.entities.PageEntity;
import org.apirest.talky.repositories.PageRepository;
import org.apirest.talky.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@Slf4j
@AllArgsConstructor

public class PageServiceImpl implements PageService {

    private final PageRepository pageRepository;
    private final UserRepository userRepository;

    @Override
    public PageResponse createPage(PageRequest pageCreate) {
        final var pageEntity = new PageEntity();
        BeanUtils.copyProperties(pageCreate, pageEntity);

        final var userEntity = this.userRepository.findById(pageCreate.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        pageEntity.setDateCreation(LocalDateTime.now());
        pageEntity.setUser(userEntity);
        pageEntity.setPosts(new ArrayList<>());

        var pageCreated = this.pageRepository.save(pageEntity);

        final var response = new PageResponse();
        BeanUtils.copyProperties(pageCreated, response);

        return response;
    }

    @Override
    public PageResponse readPageByTitle(String tittle) {

        var entityResult = this.pageRepository.findByTitle(tittle).orElseThrow(() -> new RuntimeException("Page not found"));

        final var response = new PageResponse();
        BeanUtils.copyProperties(entityResult, response);

        final List<PostResponse> postResponse = entityResult.getPosts().stream().map(postE ->
                PostResponse.builder()
                        .img(postE.getImg())
                        .content(postE.getContent())
                        .dateCreation(postE.getDateCreation())
                        .build()
        ).toList();

        response.setPostResponseList(postResponse);
        return response;
    }

    @Override
    public PageResponse updatePage(PageRequest pageUpdate, String tittle) {
        var entityFromDB = this.pageRepository.findByTitle(tittle).orElseThrow(() -> new RuntimeException("Page not found"));

        entityFromDB.setTitle(pageUpdate.getTitle());

        var pageUpdated = this.pageRepository.save(entityFromDB);
        final var response = new PageResponse();
        BeanUtils.copyProperties(pageUpdated, response);

        return response;
    }

    @Override
    public void deletePage(String tittle) {

        if(this.pageRepository.existsByTitle(tittle)){
            log.info("Deleting page");
            this.pageRepository.deleteByTitle(tittle);
        } else {
            throw new IllegalArgumentException("Error deleting page: Page not found");
        }

        /*var entityFromDB = this.pageRepository.findByTitle(tittle).orElseThrow(() -> new RuntimeException("Page not found"));
        this.pageRepository.delete(entityFromDB);*/

        /*this.pageRepository.deleteById(1L);*/
    }

    @Override
    public PageResponse createPost(PostRequest postCreate) {
        return null;
    }

    @Override
    public PageResponse deletePost(Long idPost) {
        return null;
    }
}
