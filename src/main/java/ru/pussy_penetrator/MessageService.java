package ru.pussy_penetrator;

import ru.pussy_penetrator.exception.DatabaseException;
import ru.pussy_penetrator.model.AuthDB;
import ru.pussy_penetrator.model.Message;
import ru.pussy_penetrator.model.MessageDB;
import ru.pussy_penetrator.response.MessageReceiveResponse;
import ru.pussy_penetrator.response.MessageReceiveResponse.ErrorCode;
import ru.pussy_penetrator.response.StatusResponse;
import ru.pussy_penetrator.util.MessageValidator;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;

@Path("messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageService {
    @POST
    public MessageReceiveResponse receiveMessage(Message message, @Context HttpHeaders headers) {
        String login = AuthDB.getLoginByAuth(headers);

        ErrorCode error = MessageValidator.validate(message);
        if (error != null) {
            return new MessageReceiveResponse(error);
        }

        try {
            String target = message.getTarget();
            if (target == null || !AuthDB.isUserExists(target)) {
                return new MessageReceiveResponse(ErrorCode.NO_SUCH_USER);
            }
        }
        catch (SQLException e) {
            throw new DatabaseException();
        }

        try {
            MessageDB.insertMessage(login, message);
        }
        catch (IOException | SQLException e) {
            throw new DatabaseException();
        }

        return new MessageReceiveResponse(StatusResponse.SUCCESS);
    }
}
