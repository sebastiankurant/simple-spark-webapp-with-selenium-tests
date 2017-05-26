import com.event.site.controller.EventController;
import com.event.site.dao.SqliteJDBCConnector;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.sql.SQLException;

import static spark.Spark.*;


public class Main {

    public static void main(String[] args) {
        EventController eventController = new EventController();
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);
        try {
            SqliteJDBCConnector.createTables();
        } catch (SQLException e) {
            System.out.println("Cant load database");
            e.printStackTrace();
            System.exit(1);
        }
        // Equivalent with above
        path("/index", () -> {
            get("", (Request request, Response response) ->
                    new ThymeleafTemplateEngine().render(eventController.renderEvents()));

            get("/:id", (Request request, Response response) ->
                    new ThymeleafTemplateEngine().render(eventController.renderEvent(request)));

            get("/category/", (Request request, Response response) ->
                    new ThymeleafTemplateEngine().render(eventController.renderEventsBy(request)));

            path("/manager/", () -> {
                get("", (Request request, Response response) ->
                        new ThymeleafTemplateEngine().render(eventController.displayManager()));

                get("new/", (Request request, Response response) ->
                        new ThymeleafTemplateEngine().render(eventController.displayEventCreator()));

                get("edit/:id", (Request request, Response response) ->
                        new ThymeleafTemplateEngine().render(eventController.displayEventEditor(request)));

                get("remove/:id", (Request request, Response response) -> {
                    eventController.removeEvent(request);
                    response.redirect("/index/manager/");
                    return null;
                });

                get("newCategory/", (Request request, Response response) ->
                        new ThymeleafTemplateEngine().render(eventController.displayCategoryCreator()));

                post("new/", (Request request, Response response) -> {
                    eventController.createEvent(request);
                    response.redirect("/index/manager/");
                    return null;
                });

                post("newCategory/", (Request request, Response response) -> {
                    eventController.createCategory(request);
                    response.redirect("/index/manager/");
                    return null;
                });

                post("edit/:id", (Request request, Response response) -> {
                    eventController.editProduct(request);
                    response.redirect("/index/manager/");
                    return null;
                });
            });
        });

        get("*", (request, response) -> {
            return new ThymeleafTemplateEngine().render(eventController.render404());
        });
    }
}