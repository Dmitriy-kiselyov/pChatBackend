package ru.pussy_penetrator;

import ru.pussy_penetrator.exception.DatabaseException;
import ru.pussy_penetrator.model.AuthDB;
import ru.pussy_penetrator.model.Message;
import ru.pussy_penetrator.model.MessageDB;
import ru.pussy_penetrator.response.MessageListResponse;
import ru.pussy_penetrator.response.MessageReceiveResponse;
import ru.pussy_penetrator.response.MessageResponse;
import ru.pussy_penetrator.response.StatusResponse;
import ru.pussy_penetrator.util.MessageValidator;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Path("messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageService {
    @POST
    public MessageReceiveResponse receiveMessage(Message message, @Context HttpHeaders headers) {
        String login = AuthDB.getLoginByAuth(headers);

        MessageReceiveResponse.ErrorCode error = MessageValidator.validate(message);
        if (error != null) {
            return new MessageReceiveResponse(error);
        }

        try {
            String target = message.getTarget();
            if (target == null || !AuthDB.isUserExists(target)) {
                return new MessageReceiveResponse(MessageReceiveResponse.ErrorCode.NO_SUCH_USER);
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

    @GET
    public MessageListResponse getAllMessages(@QueryParam("for") String target, @Context HttpHeaders headers) {
        String login = AuthDB.getLoginByAuth(headers);

        try {
            if (target == null || !AuthDB.isUserExists(target)) {
                return new MessageListResponse(MessageListResponse.ErrorCode.NO_SUCH_USER);
            }
        }
        catch (SQLException e) {
            throw new DatabaseException();
        }

        List<MessageResponse> messages;
        try {
            messages = MessageDB.getAllMessages(login, target);
        }
        catch (SQLException e) {
            throw new DatabaseException();
        }

        return new MessageListResponse(messages);
    }
}
