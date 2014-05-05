package models;

import java.util.List;
import play.libs.F.Option;

import play.data.*;
import play.data.validation.Constraints.*;
import javax.validation.Valid;
import play.db.ebean.*;

import org.joda.time.LocalTime;

import javax.persistence.*;

//"key=$(key)&hash=$(etag)&width=$(imageInfo.width)&height=$(imageInfo.height)&user=$(x:user)&size=$(fsize)&mime=$(mimeType)&collection=$(x:collection)"

@Entity
public class File extends Model {
    @Required
    @Id
    public String key;

    @Required
    public String hash;

    // @Required, but form has not the property
    public LocalTime timestamp;

    @Required
    public String user; //user email or id

    @Required
    public Long size;

    @Required
    public String mime;

    //use index
    public String collection;

    public Long width;
    public Long height;

    public static Finder<String,File> find = new Finder<String,File>(
        String.class, File.class
    );
}
