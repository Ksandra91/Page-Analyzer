package hexlet.code.controller;

import hexlet.code.dto.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.CheckRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.sql.SQLException;

import static io.javalin.rendering.template.TemplateUtil.model;

public class CheckController {
    public static void check(Context ctx) throws SQLException {

        Long urlId = ctx.pathParamAsClass("id", Long.class).get();
        Url url = UrlRepository.find(urlId)
                .orElseThrow(() -> new NotFoundResponse("Url not found"));
    }
}

