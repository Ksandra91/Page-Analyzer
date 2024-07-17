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
        try {
            response = Unirest.get(url.getName()).asString();
            statusCode = response.getStatus();
            Unirest.shutDown();
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Неверный URL");
            ctx.sessionAttribute("alertType", "danger");
            ctx.redirect(NamedRoutes.urlPath(String.valueOf(urlId)));
            return;
        }

        UrlCheck check = new UrlCheck(urlId, statusCode, "-", "-", "-", Timestamp.valueOf(LocalDateTime.now()));
        CheckRepository.save(check);
        // var cheks = CheckRepository.find(urlId);
        //  var url1 = new Url(url.getName(), url.getCreatedAt(),cheks);
        // String flash = "Страница успешно проверена";
        // String flashtype = "success";
        // var page = new UrlPage(url1, flash, flashtype);
//        ctx.render("urls/list.jte", model("page", page));
//
        ctx.sessionAttribute("flash", "Страница успешно добавлена");
        ctx.sessionAttribute("flash-type", "success");
        ctx.redirect(NamedRoutes.urlPath(urlId));
        //ctx.render("urls/show.jte", model("page", page));

    }
}


