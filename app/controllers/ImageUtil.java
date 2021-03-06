package controllers;

import models.Category;
import models.Image;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.admin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static play.data.Form.form;

/**
 * Created with IntelliJ IDEA.
 * User: Santiago Ambrosetti
 * Date: 9/28/15
 */
public class ImageUtil extends Controller {

    public Result getImage(long id) {
        Image image = Image.find.byId(id);
        if (image != null) {
            return ok(image.data).as("image");
        } else {
            flash("error", "Picture not found.");
            return redirect(routes.Application.admin());
        }
    }

    public Result editImage(long id) {
        Form<Image> imageForm = form(Image.class).bindFromRequest();
        Image image = Image.find.byId(id);
        image.name = imageForm.data().get("name");
        image.description = imageForm.data().get("description");
        image.category = categoriesFromRequest();
        image.save();
        return redirect(routes.Application.admin());
    }

    public Result deleteImage(long id) {
        Image image = Image.find.byId(id);
        image.delete();
        return redirect(routes.Application.admin());
    }


    public Result uploadImage() {
        Form<UploadImageForm> imageform = form(UploadImageForm.class).bindFromRequest();

        if (imageform.hasErrors()) {
            return badRequest(admin.render(imageform, Image.find.all(), Category.find.all()));

        } else {
            Image image = new Image(
                    imageform.get().name,
                    imageform.get().description,
                    categoriesFromRequest(),
                    castByteArray(imageform.get().image.getFile())
            );
            image.save();

            flash("success", "File uploaded.");
            return redirect(routes.Application.admin());
        }
    }

    public static byte[] castByteArray(File image) {
                /* write the image data into the byte array */
        byte[] data = new byte[(int) image.length()];
        InputStream inStream = null;
        try {
            inStream = new BufferedInputStream(new FileInputStream(image));
            inStream.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data;
    }

    public List<Category> categoriesFromRequest() {
        String[] jsonData = request().body().asMultipartFormData().asFormUrlEncoded().get("category");
        Form<Category> recordDummyForm = Form.form(Category.class);
        List<Category> categories = new ArrayList<>();
        for (String aJsonData : jsonData) {
            categories.add(recordDummyForm.fill(Category.find.all().get(findCategoryWS(aJsonData))).get());
        }
        return categories;
    }

    public int findCategoryWS(String jsonData) {
        List<Category> list = Category.find.all();
        for (int i = 0; i < list.size(); i++) {
            String name = list.get(i).name;
            if (name.equals(jsonData)) {
                return i;
            }
        }
        return -1;
    }


    public static class UploadImageForm {
        public Http.MultipartFormData.FilePart image;
        public String name;
        public String description;

        public String validate() {
            Http.MultipartFormData data = request().body().asMultipartFormData();
            image = data.getFile("image");

            if (image == null) {
                return "File is missing.";
            }

            return null;
        }
    }
}
