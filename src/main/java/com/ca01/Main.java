package com.ca01;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static JSONObject getJsonObject(StringTokenizer tokenizer) throws ParseException {
        String jsonData = "";
        jsonData = tokenizer.nextToken();
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse("{" + jsonData + "}");
    }

    public static void main(String[] args)
            throws IOException, ParseException, java.text.ParseException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        DataManager handler = new DataManager();
        String line = null;
        while (!(line = reader.readLine()).trim().equals("")) {
            String command = "";

            StringTokenizer tokenizer = new StringTokenizer(line, "{}");
            command = tokenizer.nextToken().trim();

            int error;
            if (command.equals("addActor")){
                error = handler.addActor(getJsonObject(tokenizer));
                System.out.println("{\"success\": true, \"data\": \"actor added successfully\"}");
            }
            if (command.equals("addMovie")) {
                error = handler.addMovie(getJsonObject(tokenizer));
                if (error != 0)
                    System.out.println("{\"success\": false, \"data\": \"ActorNotFound\"}");
                else
                    System.out.println("{\"success\": true, \"data\": \"movie added successfully\"}");
            }
            if (command.equals("addUser")) {
                error = handler.addUser(getJsonObject(tokenizer));
                System.out.println("{\"success\": true, \"data\": \"user added successfully\"}");
            }
            if (command.equals("addComment")) {
                error = handler.addComment(getJsonObject(tokenizer));
                if (error == -1)
                    System.out.println("{\"success\": false, \"data\": \"UserNotFound\"}");
                else if (error == -2)
                    System.out.println("{\"success\": false, \"data\": \"MovieNotFound\"}");
                else
                    System.out.println("{\"success\": true, \"data\": \"comment with id " + error + " added successfully\"}");
            }
            if (command.equals("rateMovie")) {
                error = handler.rateMovie(getJsonObject(tokenizer));
                if (error == -1)
                    System.out.println("{\"success\": false, \"data\": \"UserNotFound\"}");
                else if (error == -2)
                    System.out.println("{\"success\": false, \"data\": \"MovieNotFound\"}");
                else if (error == -3)
                    System.out.println("{\"success\": false, \"data\": \"InvalidRateScore\"}");
                else
                    System.out.println("{\"success\": true, \"data\": \"movie rated successfully\"}");
            }
            if (command.equals("voteComment")) {
                error = handler.voteComment(getJsonObject(tokenizer));
                if (error == -1)
                    System.out.println("{\"success\": false, \"data\": \"UserNotFound\"}");
                else if (error == -2)
                    System.out.println("{\"success\": false, \"data\": \"CommentNotFound\"}");
                else if (error == -3)
                    System.out.println("{\"success\": false, \"data\": \"InvalidVoteValue\"}");
                else
                    System.out.println("{\"success\": true, \"data\": \"comment voted successfully\"}");
            }
            if (command.equals("addToWatchList")) {
                error = handler.addToWatchList(getJsonObject(tokenizer));
                if (error == -1)
                    System.out.println("{\"success\": false, \"data\": \"UserNotFound\"}");
                else if (error == -2)
                    System.out.println("{\"success\": false, \"data\": \"CommentNotFound\"}");
                else if (error == -3)
                    System.out.println("{\"success\": false, \"data\": \"AgeLimitError\"}");
                else if (error == -4)
                    System.out.println("\"success\": false, \"data\": \"MovieAlreadyExists\"");
                else
                    System.out.println("{\"success\": true, \"data\": \"movie added to watchlist successfully\"}");
            }
            if (command.equals("removeFromWatchList")) {
                error = handler.removeFromWatchList(getJsonObject(tokenizer));
                if (error == -1)
                    System.out.println("{\"success\": false, \"data\": \"UserNotFound\"}");
                else if (error == -2)
                    System.out.println("{\"success\": false, \"data\": \"CommentNotFound\"}");
                else
                    System.out.println("{\"success\": true, \"data\": \"movie removed from watchlist successfully\"}");
            }
            if (command.equals("getMoviesList")) {
                JSONObject data = new JSONObject();
                data = handler.getMoviesList();
                System.out.println("{\"success\": true, \"data\": "+data.toJSONString()+"}");
            }
            if (command.equals("getMovieById")) {
                JSONObject data = new JSONObject();
                data = handler.getMovieById(getJsonObject(tokenizer));
                if (data == null)
                    System.out.println("{\"success\": false, \"data\": \"MovieNotFound\"}");
                else
                    System.out.println("{\"success\": true, \"data\": "+data.toJSONString()+"}");
            }

        }
    }
}

