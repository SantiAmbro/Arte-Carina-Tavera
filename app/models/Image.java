package models;


import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Santiago Ambrosetti
 * Date: 8/24/15
 */

@Entity @Table
public class Image extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public String name;

    public String description;

    @ManyToMany(mappedBy = "images")
    public List<Category> category;

    @Lob
    public byte[] data;

    public static Finder<Long, Image> find = new Finder<>(Image.class);

    public Image(String name, String description, List<Category> category, byte[] data) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.data = data;
    }

}
