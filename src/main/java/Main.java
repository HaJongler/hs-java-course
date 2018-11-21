import static spark.Spark.*;

import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

public class Main {

    static class Message {
        public String message;

        public Message(String message) {
            this.message = message;
        }
    }

    public static void main(String[] args) {
        staticFileLocation("public");

        get("/hello", (q, a) -> {
            final Map<String, Object> map = new HashMap<>();
            map.put("name", "Peter");
            map.put("age", 21);
            return render(map, "hello.ftl");
        });

        get("/private", (q, a) -> halt(403));

        notFound("Custom Not Found Page");
    }

    public static String render(Map<String, Object> model, String templatePath) {
        return new FreeMarkerEngine().render(new ModelAndView(model, templatePath));
    }
}
