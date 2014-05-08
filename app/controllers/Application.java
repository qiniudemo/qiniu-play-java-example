package controllers;

import play.*;
import play.mvc.*;
import java.util.Map;
import java.util.HashMap;
import views.html.*;
import com.qiniu.api.config.Config;
import java.io.File;

public class Application extends Controller {
    public static Result index() {
        return ok(index.render(Play.application().configuration().getString("qiniu.domain")));
    }
}
