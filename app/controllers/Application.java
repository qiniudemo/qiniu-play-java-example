package controllers;

import play.*;
import play.mvc.*;
import java.util.Map;
import java.util.HashMap;
import views.html.*;
import com.qiniu.api.config.Config;

public class Application extends Controller {
    public static Result index() {
        return ok(index.render("Qiniu Demo"));
    }
}
