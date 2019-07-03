package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.messaging.Message;
import model.messaging.MessageType;
import model.user.User;
import model.user.UserAccount;

import java.io.*;
import java.time.LocalDate;
import java.util.Calendar;

public class UserSignUp {
    @FXML
    public Button create_button;
    @FXML
    public TextField name_textField;
    @FXML
    public TextField lastName_textField;
    @FXML
    public TextField username_textField;
    @FXML
    private PasswordField password_passwordField;
    @FXML
    public DatePicker birthDay_datePicker;
    @FXML
    public Label invalid_label;

    private ObjectOutputStream outputStreamToServer = Connection.getOos();
    private ObjectInputStream inputStreamFromServer = Connection.getOis();

    //+++++++++ change this LOL
    private final int ACCEPTABE_AGE = 0;



        @FXML
        private void validate() throws IOException, ClassNotFoundException {
        String password = password_passwordField.getText();
        LocalDate birthday = birthDay_datePicker.getValue();
        UserAccount newAccount;
        if(!passwordIsStrong(password)){//password strength can be evaluated here so no need to send it to the server.
            invalid_label.setText("password not strong enough");
            invalid_label.setVisible(true);
        }
        else if(!ageIsAcceptable(birthday)){
            invalid_label.setText("your age is under "+ACCEPTABE_AGE);
            invalid_label.setVisible(true);
            validate();
        }
        else{
            User newUser = new User(new UserAccount
                    (name_textField.getText(),lastName_textField.getText(),username_textField.getText(),birthday,password_passwordField.getText())
            );
            Message signUpMsg = new Message(MessageType.SIGN_UP,newUser);
            outputStreamToServer.writeObject(signUpMsg);
            outputStreamToServer.flush();
            Message answer = (Message) inputStreamFromServer.readObject();
            if(answer.getMessageType().equals(MessageType.REJECTED)){
                invalid_label.setText("invalid username");
                invalid_label.setVisible(true);
            }
            else{
                Connection.setConnectedUser(newUser);
                new PageLoader().load("User_signUp_extras");
            }
        }
    }
    //+++++++++++ be careful there may be different classes in  server and client side! check it at the end.
    public static boolean passwordIsStrong(String password){
        if(password.length()<8){
            return false;
        }
        Boolean containsDigit = false,containsUpperCase = false,containsLowerCase = false;
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if('0'<=ch && ch<='9'){
                containsDigit = true;
            }
            else if('A'<=ch && ch<='Z'){
                containsUpperCase = true;
            }
            else if ('a'<=ch && ch<='z'){
                containsLowerCase = true;
            }
            else if(ch !='.'){
                return false;
            }
        }
        return containsDigit && containsLowerCase && containsUpperCase;
    }

    /**
     * method checks if the age is more than the acceptable minimum age
     * */
    private boolean ageIsAcceptable(LocalDate birthday){
        //++++++ check there shouldn't be any magic numbers...
        Calendar cal = Calendar.getInstance();
        int yearDiff = cal.get(Calendar.YEAR) - birthday.getYear();
        if(yearDiff >ACCEPTABE_AGE){
            return true;
        }
        if(yearDiff<ACCEPTABE_AGE-1){
            return false;
        }
        //yearDiff == ACCEPTABLE_AGE
        if(cal.get(Calendar.MONTH)>birthday.getMonth().getValue()){
            return false;
        }
        if(cal.get(Calendar.DAY_OF_MONTH)>birthday.getDayOfMonth()){
            return false;
        }
        return true;
    }
}
