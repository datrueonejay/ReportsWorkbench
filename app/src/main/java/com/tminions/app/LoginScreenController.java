package com.tminions.app;
import com.tminions.app.controllers.LoginController;
import com.tminions.app.models.LoginModel;
import org.apache.commons.lang3.StringUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginScreenController {

    @FXML private TextField usernameField;
    @FXML private TextField passField;
    @FXML private Label messageLabel;
    private LoginController loginController;

    public void LoginScreenController() {
        loginController = new LoginController();
    }

    public void login() {

        //TODO @Balaji, send the request to the login controller and update ui with response

        String firstName = usernameField.getText();
        String lastName = passField.getText();

        StringBuilder builder = new StringBuilder();

        if (!StringUtils.isEmpty(firstName)) {
            builder.append(firstName);
        }

        if (!StringUtils.isEmpty(lastName)) {
            if (builder.length() > 0) {
                builder.append(" ");
            }
            builder.append(lastName);
        }

        if (builder.length() > 0) {
            String name = builder.toString();
            messageLabel.setText("Hello " + name);
        } else {
            messageLabel.setText("Hello mysterious person");
        }
    }
}
