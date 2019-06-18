package com.abdulrohman.flickerapp;

import java.io.Serializable;

public class Photo implements Serializable {
    public   static  final long SEREAL_VERSION_UID =1;
    private String title;
    private String link;
    private String media;
    private String tag;
    private String author;
    private String authorId;


    public Photo(String title, String link, String media, String tag, String author, String authorId) {
        this.title = title;
        this.link = link;
        this.media = media;
        this.tag = tag;
        this.author = author;
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getMedia() {
        return media;
    }

    public String getTag() {
        return tag;
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthorId() {
        return authorId;
    }

    @Override
    public String toString() {
        return "Title "+title+"link "+link+"media "+"tag "+tag+"author "+authorId;
    }
}
