package ir.ac.ut.iemdb;

import ir.ac.ut.iemdb.services.ActorService;
import ir.ac.ut.iemdb.services.CommentsService;
import ir.ac.ut.iemdb.services.MoviesService;
import ir.ac.ut.iemdb.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IemdbApplication {

	public static void main(String[] args) {
		try {
			ActorService.getInstance().importActorFromWeb();
			UserService.getInstance().importUserFromWeb();
			MoviesService.getInstance().importMoviesFromWeb();
			CommentsService.getInstance().importCommentsFromWeb();
		} catch (Exception e) {
			//e.printStackTrace();
		}
		SpringApplication.run(IemdbApplication.class, args);
	}

}
