package ru.pussy_penetrator.model;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class DatabaseException extends WebApplicationException {
    public DatabaseException() {
        super(Response.status(500).entity("Ошибка базы данных").type("text/plain").build());
    }
}
