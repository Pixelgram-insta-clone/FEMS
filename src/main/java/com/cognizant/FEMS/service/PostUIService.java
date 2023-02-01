package com.cognizant.FEMS.service;

import com.cognizant.FEMS.model.PageOfItems;
import com.cognizant.FEMS.model.PostUI;

public interface PostUIService {
    PageOfItems<PostUI> getPublicPosts(int pageNumber, int pageSize);
}
