package util;
import com.qiniu.api.rs.*;
import com.qiniu.api.config.Config;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.net.CallRet;

public class Qiniu {
    public static String uploadToken(String bucket, String user, String callbackUrl){
        PutPolicy upPolicy = new PutPolicy(bucket);
        upPolicy.endUser = user;
        upPolicy.callbackUrl = callbackUrl;
        upPolicy.callbackBody = "key=$(key)&hash=$(etag)&width=$(imageInfo.width)&height=$(imageInfo.height)&user=$(endUser)&size=$(fsize)&mime=$(mimeType)";
        String token = null;
        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
        try{
            token = upPolicy.token(mac);
        }catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }

    public static String downloadToken(String url){
        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
        GetPolicy downPolicy = new GetPolicy();
        String tokenUrl = null;
        try{
            tokenUrl = downPolicy.makeRequest(url, mac);
        }catch(Exception e){
            e.printStackTrace();
        }
        return tokenUrl;
    }

    public static void delete(String bucket, String key) throws Exception{
        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
        RSClient rs = new RSClient(mac);
        CallRet cr = rs.delete(bucket, key);
        //612 means no such file
        if (cr.ok() || cr.statusCode == 612) {
            return;
        }
        throw cr.getException();
    }
}
