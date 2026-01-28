package org.apirest.talky.services;

import org.apirest.talky.dto.PageRequest;
import org.apirest.talky.dto.PageResponse;
import org.apirest.talky.dto.PostRequest;
import org.apirest.talky.dto.PostResponse;


public interface PageService {

    PageResponse createPage (PageRequest pageCreate);
    PageResponse readPageByTitle (String title);
    PageResponse updatePage (PageRequest pageUpdate, String tittle);
    void deletePage (String tittle);

    PageResponse createPost (PostRequest postCreate);
    PageResponse deletePost (Long idPost);

}
