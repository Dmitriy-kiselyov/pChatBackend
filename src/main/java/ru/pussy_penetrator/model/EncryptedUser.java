package ru.pussy_penetrator.model;

import ru.pussy_penetrator.util.AuthUtils;

public class EncryptedUser {
    private String login;
    private String password;
    private String token;

    public EncryptedUser(User user) {
        login = user.getLogin();
        password = AuthUtils.encrypt(user.getPassword());
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void generateToken() {
        token = AuthUtils.generateToken(this);
    }

    public String getToken() {
        return token;
    }
}
