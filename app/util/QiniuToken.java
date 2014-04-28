package util;
import com.qiniu.api.rs.*;
import com.qiniu.api.config.Config;
import com.qiniu.api.auth.digest.Mac;

public class QiniuToken {
    public static String uploadToken(String bucket, String user){
        PutPolicy upPolicy = new PutPolicy(bucket);
        upPolicy.endUser = user;
        upPolicy.callbackUrl = "http://60.166.120.19:9000/callback";
        upPolicy.callbackBody = "key=$(key)&hash=$(etag)&width=$(imageInfo.width)&height=$(imageInfo.height)&user=$(x:user)&size=$(fsize)&mime=$(mimeType)";
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
}
