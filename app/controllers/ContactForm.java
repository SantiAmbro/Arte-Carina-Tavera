package controllers;

import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by: yankee
 * Created on: 28/09/15
 */
public class ContactForm extends Controller {

    public Result sendForm() {
        DynamicForm requestData = DynamicForm.form().bindFromRequest();
        String name, email, message;
        name = requestData.get("name");
        email = requestData.get("email");
        message = requestData.get("message");
        String[] lines = message.split("\\n");
        StringBuilder messageToSend = new StringBuilder();
        messageToSend.append("New message from: ").append(name).append("<br><br>At: ").append(email).append("<br>");
        for (String line : lines) {
            messageToSend.append("<br>").append(line);
        }

        try {
            MailSenderUtil.send(new String[]{"cmoscon@hotmail.com"}, messageToSend.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return badRequest();
        }
        return redirect(routes.Application.index());
    }
}
