package hexlet.code.dto;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import hexlet.code.model.Url;
import io.javalin.validation.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter

public class BuildUrlPage extends BasePage{
    private String name;
    //private Timestamp createdAt;
    private Map<String, List<ValidationError<Object>>> errors;
    public BuildUrlPage(String name, String flash, String flashType) {
        super();

        this.name = name;
        super.setFlash(flash);
        super.setFlashType(flashType);
    }

}
