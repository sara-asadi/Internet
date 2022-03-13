package app.db;

import app.model.Actor;

import java.io.IOException;
import java.util.List;

public class ErrorDB {
    private static ErrorDB instance;
    String errorMessage;
    private ErrorDB() throws IOException {
        errorMessage = "";
    }
    public void setErrorMessage(String s) {
        errorMessage = s;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public static ErrorDB getInstance() throws IOException {
        if (instance == null)
            instance = new ErrorDB();
        return instance;
    }
}