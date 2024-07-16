package hexlet.code.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class UrlCheck {

    private Long id;
    private Timestamp createdAt;
    private int statusCode;
    private String title;
    private String h1;
    private String description;
    private Long urlId;

    public UrlCheck(int statusCode, String h1, String title, String description, Timestamp created) {
        this.statusCode = statusCode;
        this.h1 = h1;
        this.title = title;
        this.description = description;
        this.createdAt = created;
    }
}
