package models;

import java.util.List;
import play.libs.F.Option;

import play.data.*;
import play.data.validation.Constraints.*;
import javax.validation.Valid;
import play.db.ebean.*;

import javax.persistence.*;

// 文件集合，集合属性，集合中的文件列表由此处管理，便于修改快速集合属性，获取列表，进行授权
@Entity
public class FileCollection {
    @Required
    @Id
    public Long id;

    @Required
    @ManyToOne
    public User user;

    @Required
    public String name;
}
