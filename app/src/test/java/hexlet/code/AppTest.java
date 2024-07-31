package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.CheckRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import hexlet.code.util.NamedRoutes;


public class AppTest {

    static Javalin app;
    static MockWebServer mockServer;
    static String urlName;
    public static final String TEST_PAGE = "TestPage.html";

    private static String getFile() throws IOException {
        var path = Paths.get("src", "test", "resources", "fixtures", TEST_PAGE)
                .toAbsolutePath().normalize();
        return Files.readString(path);
    }

    @BeforeAll
    static void generalSetUp() throws Exception {
        mockServer = new MockWebServer();
        MockResponse mockResponse = new MockResponse()
                .setBody(getFile())
                .setResponseCode(200);
        mockServer.enqueue(mockResponse);
        mockServer.start();
        urlName = mockServer.url("/test").toString();

    }

    @AfterAll
    public static void afterAll() throws IOException {
        mockServer.shutdown();
    }

    @BeforeEach
    public final void setUp() throws SQLException {
        app = App.getApp();
    }

    @Test
    public void testIndexPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Анализатор страниц");
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testCreateUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://www.doodle.com";
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://www.doodle.com");
        });


    }

    @Test
    public void testUrlPage() throws SQLException {
        var url = new Url(urlName);
        url.setCreatedAt(LocalDateTime.now());
        UrlRepository.save(url);
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    void testUrlNotFound() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/999999");
            assertThat(response.code()).isEqualTo(404);
        });
    }

    @Test
    public void testCheckUrl() throws SQLException {
        var url = new Url(urlName);
        url.setCreatedAt(LocalDateTime.now());
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            var response = client.post(NamedRoutes.urlChecksPath(url.getId()));
            List<UrlCheck> checks = CheckRepository.findAllCheck(url.getId());
            var check = checks.getFirst();
            assertThat(response.code()).isEqualTo(200);
            assertThat(check.getStatusCode()).isEqualTo(200);
            assertThat(check.getH1()).isEqualTo("Test h1");
            assertThat(check.getDescription()).isEqualTo("Test description");
        });


    }
}
