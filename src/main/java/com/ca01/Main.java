package com.ca01;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args)
            throws IOException, ParseException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        while (true) {
            String command = reader.readLine();
            String commandOp = "", commandData = "";

            StringTokenizer tokenizer = new StringTokenizer(command, "{}");
            while (tokenizer.hasMoreTokens()) {
                commandOp = tokenizer.nextToken().trim();
                commandData = tokenizer.nextToken();
            }

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse("{" + commandData + "}");

            DataManager handler = new DataManager();

            int error;

            if (commandOp.equals("addActor")){
                error = handler.addActor(jsonObject);
                System.out.println("{\"success\": true, \"data\": \"actor added successfully\"}");
            }
            if (commandOp.equals("addMovie")) {
                error = handler.addMovie(jsonObject);
                if (error != 0)
                    System.out.println("{\"success\": false, \"data\": \"ActorNotFound\"}");
                else
                    System.out.println("{\"success\": true, \"data\": \"movie added successfully\"}");
            }
            if (commandOp.equals("addUser")) {
                error = handler.addUser(jsonObject);
                System.out.println("{\"success\": true, \"data\": \"user added successfully\"}");
            }
            if (commandOp.equals("addComment")) {
                error = handler.addComment(jsonObject);
                if (error == -1)
                    System.out.println("{\"success\": false, \"data\": \"UserNotFound\"}");
                else if (error == -2)
                    System.out.println("{\"success\": false, \"data\": \"MovieNotFound\"}");
                else
                    System.out.println("{\"success\": true, \"data\": \"comment with id " + error + " added successfully\"}");
            }
            if (commandOp.equals("rateMovie")) {
                error = handler.rateMovie(jsonObject);
                if (error == -1)
                    System.out.println("{\"success\": false, \"data\": \"UserNotFound\"}");
                else if (error == -2)
                    System.out.println("{\"success\": false, \"data\": \"MovieNotFound\"}");
                else if (error == -3)
                    System.out.println("{\"success\": false, \"data\": \"InvalidRateScore\"}");
                else
                    System.out.println("\"success\": true, \"data\": \"movie rated successfully\"");
            }
            if (commandOp.equals("voteComment")) {
                error = handler.voteComment(jsonObject);
                if (error == -1)
                    System.out.println("{\"success\": false, \"data\": \"UserNotFound\"}");
                else if (error == -2)
                    System.out.println("{\"success\": false, \"data\": \"CommentNotFound\"}");
                else if (error == -3)
                    System.out.println("{\"success\": false, \"data\": \"InvalidVoteValue\"}");
                else
                    System.out.println("\"success\": true, \"data\": \"comment voted successfully\"");
            }
            if (commandOp.equals("addToWatchList")) {
                error = handler.addToWatchList(jsonObject);
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
            if (commandOp.equals("removeFromWatchList")) {
                error = handler.removeFromWatchList(jsonObject);
                if (error == -1)
                    System.out.println("{\"success\": false, \"data\": \"UserNotFound\"}");
                else if (error == -2)
                    System.out.println("{\"success\": false, \"data\": \"CommentNotFound\"}");
                else
                    System.out.println("{\"success\": true, \"data\": \"movie removed from watchlist successfully\"}");
            }
        }
    }
}

