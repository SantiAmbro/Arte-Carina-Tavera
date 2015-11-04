package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.List;

;

/**
 * Created with IntelliJ IDEA.
 * User: Santiago Ambrosetti
 * Date: 9/23/15
 */
@Entity @Table
public class Category extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public String name;

    @ManyToMany(cascade = CascadeType.ALL)
    public List<Image> images;

    public static Model.Finder<Long, Category> find = new Model.Finder<>(Category.class);

}
