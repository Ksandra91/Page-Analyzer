package hexlet.code.controller;

import static io.javalin.rendering.template.TemplateUtil.model;

import hexlet.code.dto.UrlsPage;
import hexlet.code.dto.UrlPage;
import hexlet.code.util.NamedRoutes;

import hexlet.code.dto.MainPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import hexlet.code.repository.CheckRepository;


import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;


public class UrlController {

    public static void root(Context ctx) {
        var page = new MainPage();
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("index.jte", Collections.singletonMap("page", page));

    }

    public static void create(Context ctx) throws SQLException {
        var inputUrl = ctx.formParam("url");
        URL parsedUrl;
        String name;
        try {
            var uri = new URI(inputUrl);
            parsedUrl = uri.toURL();
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flash-type", "danger");
            ctx.redirect(NamedRoutes.rootPath());
            return;
        }
        name = parsedUrl.getProtocol() + "://" + parsedUrl.getAuthority();
        var urlObj = new Url(name);
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
        String flash = ctx.consumeSessionAttribute("flash");
        String flashtype = ctx.consumeSessionAttribute("flash-type");
        var cheks = CheckRepository.findAllCheck(id);
        var page = new UrlPage(url, cheks, flash, flashtype);

        ctx.render("urls/show.jte", model("page", page));
    }


}
