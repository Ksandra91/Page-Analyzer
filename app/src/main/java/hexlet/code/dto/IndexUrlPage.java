package hexlet.code.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter

public class IndexUrlPage extends BasePage {
    private String name;


    public IndexUrlPage(String name, String flash, String flashType) {
        super();

        this.name = name;
        super.setFlash(flash);
        super.setFlashType(flashType);
    }

}
