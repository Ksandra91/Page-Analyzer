package hexlet.code;

import hexlet.code.repository.BaseRepository;
import io.javalin.Javalin;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.Map;

public class App {
    public static Javalin getApp() {


        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(getDatabaseUrl());

        var dataSource = new HikariDataSource(hikariConfig);
        BaseRepository.dataSource = dataSource;

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            //config.fileRenderer(new JavalinJte());
        });
        app.get("/", ctx -> {
            ctx.result("Hello World");
        });
        return app;
    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(7070);
        getDatabaseUrl();
    }

    public static String getDatabaseUrl() {
        return System.getenv().getOrDefault("JDBC_DATABASE_URL", "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");
    }

}
