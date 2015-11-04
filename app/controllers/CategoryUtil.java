package controllers;

import models.Category;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created with IntelliJ IDEA.
 * User: Santiago Ambrosetti
 * Date: 9/28/15
 */
public class CategoryUtil extends Controller {

    public Result addCategory() {
        Category category = Form.form(Category.class).bindFromRequest().get();
        category.save();
        return redirect(routes.Application.admin());
    }

    public Result getCategory(long id) {
        Category category = Category.find.byId(id);
        return ok(category.name).as("category");
    }

    public Result deleteCategory(long id){
        Category category = Category.find.byId(id);
        category.delete();
        return redirect(routes.Application.admin());
    }

}
