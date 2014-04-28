package controllers;

import play.*;
import play.mvc.*;
import play.libs.Json;
import play.data.*;
import play.libs.*;
import java.util.Map;
import java.util.HashMap;
import views.html.*;

import static play.mvc.Results.*;

import play.data.validation.*;
import static play.data.validation.Constraints.*;
import javax.validation.*;

import util.*;

import org.joda.time.LocalTime;

import models.*;

// server must authorize client before return token
public class Qiniu extends Controller {
    private static String user= "test";
    public static Result uploadToken() {
        String bucket= Play.application().configuration().getString("qiniu.bucket");
        String token = QiniuToken.uploadToken(bucket, user);
        Logger.info(token);
        Map<String, String> resp = new HashMap<String, String>();
        resp.put("token", token);
        return ok(
            Json.toJson(resp)
        );
    }

    //bucket, key
    public static Result downloadToken() {
        Map<String,String[]> queryString = request().queryString();
        String key = queryString.get("key")[0];
        // to do find, and check owner
        // File = File.Find()
        // check collection permission
        //
        String domain= Play.application().configuration().getString("qiniu.domain");
        String downloadUrl = "http://"+ domain + "/" + key;
        String downTokenUrl = QiniuToken.downloadToken(downloadUrl);
        Map<String, String> resp = new HashMap<String, String>();
        resp.put("url", downTokenUrl);
        return ok(
            Json.toJson(resp)
        );
    }

    private static Form<File> upCallbackForm = Form.form(File.class);
    //bucket, key
    public static Result callback() {
        Form<File> boundCallback = upCallbackForm.bindFromRequest();
        if (boundCallback.hasErrors()) {
            return badRequest();
        }

        File data = boundCallback.get();

        Logger.info("callback done"+ data.user);
        data.timestamp = LocalTime.now();
        data.save();
        Map<String, String> resp = new HashMap<String, String>();
        resp.put("success", "true");
        resp.put("user", user);
        return ok(
            Json.toJson(resp)
        );
    }
}
