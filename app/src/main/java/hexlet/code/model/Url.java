package hexlet.code.model;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class Url {

    private long id;

    private String name;
    private Timestamp createdAt;
    private List<UrlCheck> checkList;

    public Url(String name, Timestamp createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss");
        return "name='"
                + name
                + '\''
                + ", createdAt=" + createdAt.toLocalDateTime().format(formatter);
    }
}
