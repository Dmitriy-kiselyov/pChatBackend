package ru.pussy_penetrator;

import ru.pussy_penetrator.model.UserMessagePreview;
import ru.pussy_penetrator.model.UserMessagePreviewDB;
import ru.pussy_penetrator.model.UserMessagePreviewListResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

import static ru.pussy_penetrator.model.UserMessagePreviewListResponse.*;

@Path("users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserMessagePreviewService {
    @GET
    public UserMessagePreviewListResponse getAllUsers() {
        List<UserMessagePreview> users;
        try {
             users = UserMessagePreviewDB.getAllUsers();
        }
        catch (SQLException e) {
            return new UserMessagePreviewListResponse(ErrorCode.DATABASE_ERROR, "Ошибка базы данных");
        }

        return new UserMessagePreviewListResponse(users);
    }
}
