package com.ca01;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataManagerVoteCommentTest {

    protected DataManager dataManager;
    protected JSONParser parser;
    Comment comment;

    @BeforeEach
    void setUp(){
        dataManager = new DataManager();
        parser = new JSONParser();

        dataManager.users.add(new User("sara@ut.ac.ir"," "," "," ","2000-01-12"));
        dataManager.movies.add(new Movie(1,"","","","",new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),0,0,0));

        comment = new Comment(" ",1," ",1);
        dataManager.movies.get(0).comments.add(comment);
        dataManager.comments.add(comment);
    }

    @Test
    void newVoteCommentLikeTest() throws ParseException {

        String voteString = "{\"userEmail\": \"sara@ut.ac.ir\", \"commentId\": 1, \"vote\": 1}";
        JSONObject voteObject = (JSONObject) parser.parse(voteString);
        dataManager.voteComment(voteObject);

        List<Long> vote = new ArrayList<>();
        vote.add(1L);
        vote.add(1L);

        assertEquals(vote, dataManager.users.get(0).ratedComments.get(0));
        assertEquals(1, dataManager.movies.get(0).comments.get(0).likes);
    }

    @Test
    void updateVoteCommentLikeTest() throws ParseException {

        comment.likes = 1;
        List<Long> vote = new ArrayList<>();
        vote.add(1L);
        vote.add(1L);

        dataManager.users.get(0).ratedComments.add(vote);

        String voteString = "{\"userEmail\": \"sara@ut.ac.ir\", \"commentId\": 1, \"vote\": -1}";
        JSONObject voteObject = (JSONObject) parser.parse(voteString);
        dataManager.voteComment(voteObject);

        List<Long> updateVote = new ArrayList<>();
        updateVote.add(1L);
        updateVote.add(-1L);

        assertEquals(updateVote, dataManager.users.get(0).ratedComments.get(0));
        assertEquals(0, dataManager.movies.get(0).comments.get(0).likes);
        assertEquals(1, dataManager.movies.get(0).comments.get(0).disLikes);
    }

    @Test
    void undoVoteCommentLikeTest() throws ParseException {
        comment.likes = 1;
        List<Long> vote = new ArrayList<>();
        vote.add(1L);
        vote.add(1L);

        dataManager.users.get(0).ratedComments.add(vote);

        String voteString = "{\"userEmail\": \"sara@ut.ac.ir\", \"commentId\": 1, \"vote\": 0}";
        JSONObject voteObject = (JSONObject) parser.parse(voteString);
        dataManager.voteComment(voteObject);

        List<Long> updateVote = new ArrayList<>();
        updateVote.add(-1L);
        updateVote.add(1L);

        assertEquals(updateVote, dataManager.users.get(0).ratedComments.get(0));
        assertEquals(0, dataManager.movies.get(0).comments.get(0).likes);
        assertEquals(0, dataManager.movies.get(0).comments.get(0).disLikes);
    }

    @Test
    void voteCommentUserNotFountTest() throws ParseException {
        String voteString = "{\"userEmail\": \"sara1@ut.ac.ir\", \"commentId\": 1, \"vote\": -1}";
        JSONObject voteObject = (JSONObject) parser.parse(voteString);
        int error = dataManager.voteComment(voteObject);

        assertEquals(-1, error);
        assertEquals(0, dataManager.movies.get(0).comments.get(0).likes);
        assertEquals(0, dataManager.movies.get(0).comments.get(0).disLikes);
    }

    @Test
    void voteCommentCommentNotFoundTest() throws ParseException {
        String voteString = "{\"userEmail\": \"sara@ut.ac.ir\", \"commentId\": 2, \"vote\": 0}";
        JSONObject voteObject = (JSONObject) parser.parse(voteString);
        int error = dataManager.voteComment(voteObject);

        assertEquals(-2, error);
        assertEquals(0, dataManager.movies.get(0).comments.get(0).likes);
        assertEquals(0, dataManager.movies.get(0).comments.get(0).disLikes);
    }

    @Test
    void voteCommentInvalidValueTest() throws ParseException {
        String voteString = "{\"userEmail\": \"sara@ut.ac.ir\", \"commentId\": 1, \"vote\": 2}";
        JSONObject voteObject = (JSONObject) parser.parse(voteString);
        int error = dataManager.voteComment(voteObject);

        assertEquals(-3, error);
        assertEquals(0, dataManager.movies.get(0).comments.get(0).likes);
        assertEquals(0, dataManager.movies.get(0).comments.get(0).disLikes);
    }
}
