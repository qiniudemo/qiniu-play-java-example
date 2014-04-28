import play.*;
import com.qiniu.api.config.Config;
import com.qiniu.api.auth.digest.Mac;

public class Global extends GlobalSettings{

    @Override
    public void onStart(Application app) {
        Config.ACCESS_KEY = Play.application().configuration().getString("qiniu.access_key");
        Config.SECRET_KEY = Play.application().configuration().getString("qiniu.secret_key");
        Logger.info("Qiniu has started" + Config.ACCESS_KEY);
    }

    @Override
    public void onStop(Application app) {
        Logger.info("Qiniu shutdown...");
    }
}
