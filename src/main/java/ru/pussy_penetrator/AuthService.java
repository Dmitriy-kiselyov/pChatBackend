package ru.pussy_penetrator;

import ru.pussy_penetrator.model.AuthResponse.ErrorCode;
import ru.pussy_penetrator.model.EncryptedUser;
import ru.pussy_penetrator.model.User;
import ru.pussy_penetrator.model.AuthResponse;
import ru.pussy_penetrator.model.UserDB;
import ru.pussy_penetrator.util.AuthUtils;
import ru.pussy_penetrator.util.UserValidator;
import ru.pussy_penetrator.util.UserValidator.LoginValidationError;
import ru.pussy_penetrator.util.UserValidator.PasswordValidationError;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@Path("auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthService {
    @POST
    public AuthResponse signIn(User user) {
        LoginValidationError loginError = UserValidator.validateLogin(user.getLogin());
        if (loginError != null) {
            return new AuthResponse(ErrorCode.LOGIN_VALIDATION, loginError.get());
        }

        PasswordValidationError passwordError = UserValidator.validatePassword(user.getPassword());
        if (passwordError != null) {
            return new AuthResponse(ErrorCode.PASSWORD_VALIDATION, passwordError.get());
        }

        String token = null;
        try {
            token = UserDB.getToken(user);
        }
        catch (SQLException e) {}

        if (token == null) {
            return new AuthResponse(ErrorCode.INCORRECT_CREDENTIALS, "Неверный логин или пароль");
        }

        AuthResponse response = new AuthResponse();
        response.setToken(token);

        return response;
    }
}
