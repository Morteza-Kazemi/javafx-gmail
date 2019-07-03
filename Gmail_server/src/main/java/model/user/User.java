package model.user;

import model.messaging.Conversation;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {
    public User(String address) {
        this.address = address;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }

    private static final long SerialVersionUID = 10L;

    //++++++++ hmmm how to use polymorphism???
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return address.equals(user.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

    private String address;
    private UserAccount account;
    private List<Conversation> conversations;

    public User(UserAccount account) {
        this.address = account.getUserName() + "@gmail.com";
        this.account = account;
    }

    public String getAddress() {
        return address;
    }

    public UserAccount getAccount() {
        return account;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }
}
