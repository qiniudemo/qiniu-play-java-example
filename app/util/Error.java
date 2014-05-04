package util;

import play.libs.Json;
import java.util.Map;
import java.util.HashMap;

public enum Error {
    Forbiden(1, "forbiden", "you have not the permission"),
    Invalid(2, "invalid args","the args are invalid"),
    NetworkError(3, "network error", "network error");

    public final int error_code;
    public final String error;
    public final String error_description;
    private Error(int code, String error, String desc){
        this.error_code = code;
        this.error = error;
        this.error_description = desc;
    }

    public com.fasterxml.jackson.databind.JsonNode toJson(){
        Map<String, String> data = new HashMap<String, String>();
        data.put("error_code", "" + this.error_code);
        data.put("error", this.error);
        data.put("error_description", this.error_description);
        return Json.toJson(data);
    }
}
