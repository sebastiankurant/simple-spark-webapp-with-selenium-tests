package controller;

import dao.CategoryDaoSqlite;
import dao.EventDaoSqlite;
import model.Category;
import model.Event;
import spark.ModelAndView;
import spark.Request;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventController {

    private CategoryDaoSqlite categoryDaoSqlite = new CategoryDaoSqlite();
    private EventDaoSqlite eventDaoSqlite = new EventDaoSqlite();

    public ModelAndView renderEvents() {
        List<Event> eventList = new ArrayList<>();
        List<Category> categoryList = new ArrayList<>();
        Map<String, Object> parameters = new HashMap<>();
        try {
            eventList = eventDaoSqlite.getAll();
            categoryList = categoryDaoSqlite.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
            parameters.put("dangerContainer", "Cant display events, try again later.");
        }
        parameters.put("eventsContainer", eventList);
        parameters.put("categoriesContainer", categoryList);
        return new ModelAndView(parameters, "product/index");
    }

    public ModelAndView render404() {
        Map<String, Object> parameters = new HashMap<>();
        return new ModelAndView(parameters, "product/404");
    }

    public void createEvent(Request request) throws SQLException {
        Integer nameLength;
        try {
            nameLength = request.queryParams("formName").length();
        } catch (NullPointerException e) {
            nameLength = null;
        }
        if (nameLength != null && nameLength > 0 && nameLength < 200) {
            Event event = this.createEventFromForm(request);
            try {
                eventDaoSqlite.add(event);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ModelAndView renderEventsBy(Request request) {
        Category category;
        List<Event> eventList = new ArrayList<>();
        Map<String, Object> parameters = new HashMap<>();
        try {
            category = categoryDaoSqlite.find(request.queryParams("category"));
            eventList = eventDaoSqlite.getBy(category);
        } catch (SQLException e) {
            e.printStackTrace();
            parameters.put("dangerContainer", "Cant display events, try again later.");
        }
        parameters.put("eventsContainer", eventList);
        return new ModelAndView(parameters, "product/category");
    }

    public ModelAndView renderEvent(Request request) {
        Event event = new Event(null);
        Map<String, Object> parameters = new HashMap<>();
        Integer id;
        try {
            id = Integer.parseInt(request.params(":id"));
        } catch (NumberFormatException e) {
            return new ModelAndView(parameters, "product/404");
        }
        try {
            event = eventDaoSqlite.find(id);
        } catch (SQLException e) {
            e.printStackTrace();
            parameters.put("dangerContainer", "Cant display event, try again later.");
        }
        parameters.put("eventContainer", event);
        return new ModelAndView(parameters, "product/event");
    }

    public ModelAndView displayEventCreator() {
        Map<String, Object> parameters = new HashMap<>();
        List<Category> categoryList = new ArrayList<>();
        try {
            categoryList = categoryDaoSqlite.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
            parameters.put("dangerContainer", "Cant display event, try again later.");
        }
        parameters.put("categoriesContainer", categoryList);
        return new ModelAndView(parameters, "product/new");
    }

    public ModelAndView displayCategoryCreator() {
        Map<String, Object> parameters = new HashMap<>();
        return new ModelAndView(parameters, "product/newCategory");
    }

    public void createCategory(Request request) {
        String name = request.queryParams("formName");
        Category category = new Category(name);
        try {
            categoryDaoSqlite.add(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ModelAndView displayManager() {
        List<Event> eventList = new ArrayList<>();
        Map<String, Object> parameters = new HashMap<>();
        try {
            eventList = eventDaoSqlite.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
            parameters.put("dangerContainer", "Cant display event, try again later.");
        }
        parameters.put("eventsContainer", eventList);
        return new ModelAndView(parameters, "product/manager");
    }

    public void editProduct(Request request) {
        Integer id = Integer.parseInt(request.params(":id"));
        Event event = new Event(null);
        try {
            event = eventDaoSqlite.find(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (request.queryParams("formName").length() > 0) {
            event = this.editEventFromForm(event, request);
            try {
                eventDaoSqlite.edit(event);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ModelAndView displayEventEditor(Request request) {
        Integer id = Integer.parseInt(request.params(":id"));
        Map<String, Object> parameters = new HashMap<>();
        Event event = new Event(null);
        List<Category> categoryList = new ArrayList<>();
        try {
            event = eventDaoSqlite.find(id);
            categoryList = categoryDaoSqlite.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        parameters.put("categoriesContainer", categoryList);
        parameters.put("eventContainer", event);
        return new ModelAndView(parameters, "product/edit");
    }

    public void removeEvent(Request request) {
        Integer id = Integer.parseInt(request.params(":id"));
        try {
            eventDaoSqlite.remove(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private Event createEventFromForm(Request request) {
        Event event = new Event(null);
        event.setName(request.queryParams("formName"));
        event.setDescription(request.queryParams("formDescription"));
        this.addDateToEvent(request, event);
        this.addCategoryToEvent(request, event);
        return event;
    }

    private Event editEventFromForm(Event event, Request request) {
        event.setName(request.queryParams("formName"));
        event.setDescription(request.queryParams("formDescription"));
        this.addDateToEvent(request, event);
        this.addCategoryToEvent(request, event);

        return event;
    }

    private void addCategoryToEvent(Request request, Event event) {
        try {
            Category category = categoryDaoSqlite.find(request.queryParams("category"));
            event.setCategory(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addDateToEvent(Request request, Event event) {
        if (Event.isDateValid(request.queryParams("date"))) {
            event.setDate(request.queryParams("date"));
        }
    }

}
