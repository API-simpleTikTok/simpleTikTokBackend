package com.simpletiktok.simpletiktok.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author ricetea
 * @since 2024-06-06
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Love implements Serializable {

    private static final long serialVersionUID = 1L;

    private String author;

    private String awemeId;

    private String isloved;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public String getAwemeId() {
        return awemeId;
    }

    public void setAwemeId(String awemeId) {
        this.awemeId = awemeId;
    }
    public String getIsloved() {
        return isloved;
    }

    public void setIsloved(String isloved) {
        this.isloved = isloved;
    }

    @Override
    public String toString() {
        return "Love{" +
                "author=" + author +
                ", awemeId=" + awemeId +
                ", isloved=" + isloved +
                "}";
    }
}
