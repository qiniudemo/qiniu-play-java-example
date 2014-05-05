package models;

import java.util.List;
import play.libs.F.Option;

import play.data.*;
import play.data.validation.Constraints.*;
import javax.validation.Valid;
import play.db.ebean.*;

import javax.persistence.*;

@Entity
public class User extends Model {
  @Required
  public String name;

  @Required
  @Email
  @Id
  public String email;

  @Valid
  public List<User> friends;

  public static Finder<String,User> find = new Finder<String,User>(
    String.class, User.class
  );
}
