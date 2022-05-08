package ir.ac.ut.iemdb;

import ir.ac.ut.iemdb.services.ActorService;
import ir.ac.ut.iemdb.services.MoviesService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IemdbApplication {

	public static void main(String[] args) {
		try {
			MoviesService.getInstance().importMoviesFromWeb();
			ActorService.getInstance().importActorFromWeb();
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpringApplication.run(IemdbApplication.class, args);
	}

}
