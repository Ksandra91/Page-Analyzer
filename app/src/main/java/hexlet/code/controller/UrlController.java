package hexlet.code.controller;

import static io.javalin.rendering.template.TemplateUtil.model;

import hexlet.code.dto.UrlsPage;
import hexlet.code.dto.UrlPage;
import hexlet.code.util.NamedRoutes;

import hexlet.code.dto.IndexUrlPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;


import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;


public class UrlController {

    public static void root(Context ctx) {
        String flash = ctx.consumeSessionAttribute("flash");
        String flashtype = ctx.consumeSessionAttribute("flash-type");
        var page = new IndexUrlPage(flash, flashtype);
        ctx.render("index.jte", model("page", page));
    }


    public static void create(Context ctx) throws SQLException, URISyntaxException {

        var url = ctx.formParam("url");

        URI uri = new URI(url);

        URL param;

        try {
            param = uri.toURL();
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flash-type", "danger");
            ctx.redirect(NamedRoutes.rootPath());
            return;
        }

        String name = param.getProtocol() + "://" + param.getAuthority();
        var urlObj = new Url(name, Timestamp.valueOf(LocalDateTime.now()));
        if (UrlRepository.findName(name).isPresent()) {
            ctx.sessionAttribute("flash", "Страница уже существует");
            ctx.sessionAttribute("flash-type", "danger");
            ctx.redirect(NamedRoutes.urlsPath());
        } else {
            UrlRepository.save(urlObj);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.sessionAttribute("flash-type", "success");
            ctx.redirect(NamedRoutes.urlsPath());
        }
    }

    public static void showList(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        String flash = ctx.consumeSessionAttribute("flash");
        String flashtype = ctx.consumeSessionAttribute("flash-type");
        var page = new UrlsPage(urls, flash, flashtype);
        ctx.render("urls/list.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Url not found"));

        var page = new UrlPage(url);
        ctx.render("urls/show.jte", model("page", page));
    }
}
