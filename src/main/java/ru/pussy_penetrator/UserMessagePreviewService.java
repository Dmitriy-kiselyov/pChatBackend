package ru.pussy_penetrator;

import ru.pussy_penetrator.exception.DatabaseException;
import ru.pussy_penetrator.exception.UnauthorizedException;
import ru.pussy_penetrator.model.*;
import ru.pussy_penetrator.response.UserListResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

@Path("users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserMessagePreviewService {
    @GET
    public UserListResponse getAllUsers(@Context HttpHeaders headers) {
        String token = headers.getHeaderString("Authorization");

        String login;
        try {
            login = AuthUserDB.getUserLogin(token);
        }
        catch (SQLException e) {
            throw new DatabaseException();
        }
        if (login == null) {
            throw new UnauthorizedException();
        }

        List<UserPreview> users;
        try {
             users = UserMessagePreviewDB.getAllUsersExcept(login);
        }
        catch (SQLException e) {
            throw new DatabaseException();
        }

        return new UserListResponse(users);
    }
}
