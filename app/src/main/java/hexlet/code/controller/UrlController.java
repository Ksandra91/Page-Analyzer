package hexlet.code.controller;

import static io.javalin.rendering.template.TemplateUtil.model;

import hexlet.code.dto.UrlsPage;
import hexlet.code.dto.UrlPage;
import hexlet.code.util.NamedRoutes;

import hexlet.code.dto.BuildUrlPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import io.javalin.http.NotFoundResponse;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UrlController {

    public static void root(Context ctx) {
        var page = new BuildUrlPage();
        ctx.render("index.jte", model("page", page));
    }

//    public static void build(Context ctx) {
//        var page = new BuildUrlPage();
//        ctx.render("urls/build.jte", model("page", page));
//    }

//    public static void create(Context ctx) throws SQLException, URISyntaxException, MalformedURLException {
//
//        try {
//
//            var url = ctx.formParamAsClass("url", URL.class)
//                    .check(value -> value::toURL, "Цена не должна быть отрицательной")
//                    .get();
//          // var url = ctx.formParam("url");
//
//            var urlObj = new Url(name);
//            UrlRepository.save(urlObj);
//            ctx.sessionAttribute("flash", "Страница успешно добавлена");
//            ctx.sessionAttribute("flash-type", "success");
//            ctx.redirect(NamedRoutes.urlsPath());
//
//        } catch (ValidationException e) {
//            var title = ctx.formParam("url");
//            var page = new BuildUrlPage(title,e.getErrors());
//            ctx.render("urls/build.jte", model("page", page)).status(422);
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static void create(Context ctx) throws SQLException, URISyntaxException {


        var url = ctx.formParam("url");

        URI uri = new URI(url);

        try {
            URL param = uri.toURL();
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
        } catch (MalformedURLException e) {
            var page = new BuildUrlPage(url,"Некорректный URL","warning");
            //ctx.sessionAttribute("flash", "Некорректный URL");
           // ctx.sessionAttribute("flash-type", "warning");
           // ctx.redirect(NamedRoutes.rootPath());
            ctx.render("index.jte", model("page", page));
        }


    }

    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        String flash = ctx.consumeSessionAttribute("flash");
        String flashtype = ctx.consumeSessionAttribute("flash-type");
        // Добавляем flash в определение CoursesPage
        var page = new UrlsPage(urls, flash, flashtype);
        //  page.setFlash(ctx.consumeSessionAttribute("flash"));
        //  page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("urls/index.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Url not found"));

        var page = new UrlPage(url);
        ctx.render("urls/show.jte", model("page", page));
    }
}
