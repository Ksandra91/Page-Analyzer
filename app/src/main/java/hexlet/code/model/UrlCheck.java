package hexlet.code.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public final class UrlCheck {

    private Long id;
    private LocalDateTime createdAt;
    private int statusCode;
    private String title;
    private String h1;
    private String description;
    private Long urlId;

    public UrlCheck(Long urlId, int statusCode, String h1, String title, String description, LocalDateTime created) {
        this.urlId = urlId;
        this.statusCode = statusCode;
        this.h1 = h1;
        this.title = title;
        this.description = description;
        this.createdAt = created;
    }

    public UrlCheck(Long urlId, int statusCode, LocalDateTime created) {
        this.urlId = urlId;
        this.statusCode = statusCode;
        this.createdAt = created;
    }
}
