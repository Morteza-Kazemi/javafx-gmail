package model.messaging;

import java.io.Serializable;

public enum MessageType implements Serializable {
    SIGN_UP,SIGN_IN,MAIL,SAVE_USER,ERROR_SIGNING_IN,ACCEPTED,REJECTED
}
