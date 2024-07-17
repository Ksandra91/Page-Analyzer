package hexlet.code.controller;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.CheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;


public class CheckController {
    public static void check(Context ctx) throws SQLException {

        Long urlId = ctx.pathParamAsClass("id", Long.class).get();
        Url url = UrlRepository.find(urlId)
                .orElseThrow(() -> new NotFoundResponse("Url not found"));

        HttpResponse<String> response;
        int statusCode;
        String title;
        String h1;
        String description;
        try {
            response = Unirest.get(url.getName()).asString();
            statusCode = response.getStatus();
            Document document = Jsoup.parse(response.getBody());
            title = document.title();
            var h1temp = document.selectFirst("h1");
            h1 = h1temp == null ? "" : h1temp.text();
            var descriptionTemp = document.selectFirst("meta[name=description]");
            description = descriptionTemp == null ? "" : descriptionTemp.attr("content");

            Unirest.shutDown();
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Неверный URL");
            ctx.sessionAttribute("alertType", "danger");
            ctx.redirect(NamedRoutes.urlPath(String.valueOf(urlId)));
            return;
        }
        UrlCheck check = new UrlCheck(urlId, statusCode, h1, title,
                description, Timestamp.valueOf(LocalDateTime.now()));
        CheckRepository.save(check);
        ctx.sessionAttribute("flash", "Страница успешно добавлена");
        ctx.sessionAttribute("flash-type", "success");
        ctx.redirect(NamedRoutes.urlPath(urlId));
    }
}


