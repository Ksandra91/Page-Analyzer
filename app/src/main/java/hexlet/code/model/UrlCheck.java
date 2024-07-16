package hexlet.code.model;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

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

    public UrlCheck(Long urlId, int statusCode, String h1, String title, String description, Timestamp created) {
        this.urlId = urlId;
        this.statusCode = statusCode;
        this.h1 = h1;
        this.title = title;
        this.description = description;
        this.createdAt = created;
    }

//    public UrlCheck(Long urlId, int statusCode, Timestamp created) {
//        this.urlId = urlId;
//        this.statusCode = statusCode;
//        this.createdAt = created;
//    }

    public String dateToString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss");
        return createdAt.toLocalDateTime().format(formatter);
    }
}
