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

import com.qiniu.api.auth.digest.Mac;

public class Storage extends Controller {
    public static Result uploadToken() {
        Map<String,String[]> queryString = request().queryString();
        // this is demo, user get from query
        if (queryString.get("user") == null) {
            return badRequest(
                util.Error.Invalid.toJson()
            );
        }
        String user = queryString.get("user")[0];

        String bucket= Play.application().configuration().getString("qiniu.bucket");
        String callbackUrl = Play.application().configuration().getString("qiniu.callback_url");

        String token = util.Qiniu.uploadToken(bucket, user, callbackUrl);
        if (token == null) {
            return badRequest(
                util.Error.Invalid.toJson()
            );
        }

        Map<String, String> resp = new HashMap<String, String>();
        resp.put("token", token);
        return ok(
            Json.toJson(resp)
        );
    }

    public static Result downloadToken() {
        Map<String,String[]> queryString = request().queryString();
        if (queryString.get("key") == null || queryString.get("user") == null) {
            return badRequest(
                util.Error.Invalid.toJson()
            );
        }
        String key = queryString.get("key")[0];
        // this is demo, user get from query
        String user = queryString.get("user")[0];

        File f = File.find.byId(key);
        if (!f.user.equals(user)) {
            return forbidden(
                util.Error.Forbidden.toJson()
            );
        }

        String downTokenUrl = downloadUrl(key);
        Map<String, String> resp = new HashMap<String, String>();
        resp.put("url", downTokenUrl);
        return ok(
            Json.toJson(resp)
        );
    }

    private static String downloadUrl(String key){
        String domain= Play.application().configuration().getString("qiniu.domain");
        String downloadUrl = "http://"+ domain + "/" + key;
        return util.Qiniu.downloadToken(downloadUrl);
    }

    public static Result downloadTokens(FileCollection col){
        // get files from collection
        // iterate files to get tokens
        // return download token url array
        return null;
    }

    public static Result delete() {
        Map<String,String[]> queryString = request().queryString();
        String bucket= Play.application().configuration().getString("qiniu.bucket");
        if (queryString.get("key") == null || queryString.get("user") == null) {
            return badRequest(util.Error.Invalid.toJson());
        }
        String key = queryString.get("key")[0];
        String user = queryString.get("user")[0];

        File f = File.find.byId(key);
        if (!f.user.equals(user)) {
            return forbidden(
                util.Error.Forbidden.toJson()
            );
        }

        try {
            util.Qiniu.delete(bucket, key);
        } catch(Exception e){
            e.printStackTrace();
            return badRequest(util.Error.Invalid.toJson());
        }
        return ok(
            Json.toJson("ok")
        );
    }


    private static Form<File> upCallbackForm = Form.form(File.class);
    //bucket, key
    public static Result callback() {
        Logger.info("callbacking");
        Form<File> boundCallback = upCallbackForm.bindFromRequest();
        if (boundCallback.hasErrors()) {
            return badRequest();
        }

        File data = boundCallback.get();

        Logger.info("callback done "+ data.user);
        data.timestamp = LocalTime.now();
        data.save();
        Map<String, String> resp = new HashMap<String, String>();
        resp.put("success", "true");
        resp.put("key", data.key);
        resp.put("hash", data.hash);
        return ok(
            Json.toJson(resp)
        );
    }
}
