package com.example.application.views.about;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.textfield.TextField;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

@PageTitle("About us")
@Route(value = "about", layout = MainLayout.class)
public class AboutView extends Div {

    private TextField subject = new TextField("Subject");
    private TextArea body = new TextArea("Message");
    private Button send = new Button("Send");
    private String emailBaseUrl = "https://pcwqo24tsh.execute-api.us-east-1.amazonaws.com/Prod/mail";


    public AboutView() {
        addClassName("about-view");
        Span description = new Span("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.\nLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        description.addClassName("text");

        add(description, createTitle(), createFormLayout(), createButtonLayout());


        send.addClickListener(e -> {
            if (subject.isEmpty() || body.isEmpty()) {
                openWarning("Fields cannot be blank");
            } else {
                try {
                    sendEmail(subject.getValue(), body.getValue());
                } catch (IOException ex) {
//                    ex.printStackTrace();
                }
                clearFields();

            }
        });
    }

    private void clearFields(){
        subject.setValue("");
        body.setValue("");
    }

    public void sendEmail(String subject, String message) throws IOException {
        Notification success = new Notification(new Html(String.format(
                "<div class='email-sent-success'><h3>Successfully Sent Message</h3><h4>%s</h4><p>%s</p><div/>", subject,
                message)));

        success.setDuration(3000);
        success.setPosition(Notification.Position.BOTTOM_CENTER);

        success.open();

        OkHttpClient clientPost = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        String bodyPkg = String.format("{\r\n    \"emailFrom\": \"%s\",\r\n    \"subject\": \"%s\",\r\n    \"body\": \"%s\"\r\n}", "gtian@gmail.com", subject, message);
        RequestBody reqBody = RequestBody.create(mediaType, bodyPkg);
        Request requestPost = new Request.Builder()
                .url(emailBaseUrl)
                .method("POST", reqBody)
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response responsePost = clientPost.newCall(requestPost).execute();
            if (responsePost.isSuccessful()) {
                openWarning("Email sent!");
            } else {
                openWarning("Failed to send email!");
            }
        } catch (IOException ex) {
//            ex.printStackTrace();
        }

    }

    public void openWarning(String errorMsg) {
        Notification notification = new Notification(
                errorMsg, 3000, Notification.Position.BOTTOM_CENTER);
        notification.open();
    }



    private Component createTitle() {
        return new H3("Contact Us");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep(StringUtils.EMPTY, 1));
        formLayout.add(subject, body);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        send.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(send);
        return buttonLayout;
    }


}
