package ru.pussy_penetrator;

import ru.pussy_penetrator.model.*;
import ru.pussy_penetrator.model.AuthResponse.ErrorCode;
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
    public AuthResponse signIn(AuthUser user) {
        AuthResponse validationErrorResponse = validate(user);
        if (validationErrorResponse != null) {
            return validationErrorResponse;
        }

        String token = null;
        try {
            token = AuthUserDB.getToken(user);
        }
        catch (SQLException e) {
            return new AuthResponse(ErrorCode.DATABASE_ERROR, "Ошибка базы данных");
        }

        if (token == null) {
            return new AuthResponse(ErrorCode.INCORRECT_CREDENTIALS, "Неверный логин или пароль");
        }

        AuthResponse response = new AuthResponse(StatusResponse.SUCCESS);
        response.setToken(token);

        return response;
    }

    @PUT
    public AuthResponse signUp(AuthUser user) {
        AuthResponse validationErrorResponse = validate(user);
        if (validationErrorResponse != null) {
            return validationErrorResponse;
        }

        try {
            if (AuthUserDB.isUserExists(user)) {
                return new AuthResponse(ErrorCode.USER_EXISTS, "Пользователь с таким логином уже существует");
            }
        }
        catch (SQLException e) {
            return new AuthResponse(ErrorCode.DATABASE_ERROR, "Ошибка базы данных");
        }

        String token;
        try {
            token = AuthUserDB.insertUser(user);
        }
        catch (SQLException e) {
            return new AuthResponse(ErrorCode.DATABASE_ERROR, "Ошибка базы данных");
        }

        AuthResponse response = new AuthResponse(StatusResponse.SUCCESS);
        response.setToken(token);

        return response;
    }

    private AuthResponse validate(AuthUser user) {
        LoginValidationError loginError = UserValidator.validateLogin(user.getLogin());
        if (loginError != null) {
            return new AuthResponse(ErrorCode.LOGIN_VALIDATION, loginError.get());
        }

        PasswordValidationError passwordError = UserValidator.validatePassword(user.getPassword());
        if (passwordError != null) {
            return new AuthResponse(ErrorCode.PASSWORD_VALIDATION, passwordError.get());
        }

        return null;
    }
}
