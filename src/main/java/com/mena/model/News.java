/**
 *
 */
package com.mena.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The type News.
 */
@Entity
@Table(name = "news")
public class News implements Serializable {
    private static final long serialVersionUID = 5124000706092599751L;

    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /**
     * The Date.
     */
    @Column(name = "date")
    String date;

    /**
     * The Title.
     */
    @Column(name = "title")
    String title;

    /**
     * The Description.
     */
    @Size(min = 0, max = 32768)
    @Column(name = "description")
    String description;

    /**
     * The Content.
     */
    @Column(name = "content")
    String content;

    /**
     * Pre persist.
     */
    @PrePersist
    public void prePersist() {
        if (this.getDate() == null) {
            this.setDate(LocalDateTime.now().toString());
        }
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets content.
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets content.
     *
     * @param content the content
     */
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News)) return false;

        News news = (News) o;

        if (!getId().equals(news.getId())) return false;
        return getTitle().equals(news.getTitle());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getTitle().hashCode();
        return result;
    }
}
