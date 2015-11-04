package controllers;

import models.Category;
import models.Image;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin;
import views.html.index;

import static play.data.Form.form;

public class Application extends Controller {

    public Result index() {
        return ok(index.render(form(ImageUtil.UploadImageForm.class), Image.find.all()));
    }

    public Result admin() {
        return ok(admin.render(form(ImageUtil.UploadImageForm.class), Image.find.all(),Category.find.all()));
    }

}
