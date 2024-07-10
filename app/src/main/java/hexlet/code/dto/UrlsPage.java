package hexlet.code.dto;
import java.util.List;
import hexlet.code.model.Url;
import lombok.AllArgsConstructor;
import lombok.Getter;



@Getter

public class UrlsPage extends BasePage{
    private List<Url> urls;

    public UrlsPage(List<Url> urls, String flash, String flashType) {
        super();

        this.urls = urls;
        super.setFlash(flash);
        super.setFlashType(flashType);
    }
}
