package com.cognizant.FEMS.model;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PostUI {
    private int id;
    private User user;
    private String img;
    private String description;
    private LocalDate createdOn;
    private PageOfItems<Comment> comments;
}