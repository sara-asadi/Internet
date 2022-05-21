package ir.ac.ut.iemdb.controller;

import ir.ac.ut.iemdb.model.Actor;
import ir.ac.ut.iemdb.model.Movie;
import ir.ac.ut.iemdb.repository.ActorRepository;
import ir.ac.ut.iemdb.repository.CastRepository;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("actors")
public class ActorController {

    private final ActorRepository repository = ActorRepository.getInstance();

    @PostMapping("/insert/{id}/{name}/{birthDate}/{nationality}/{image}")
    public void insertActor(@PathVariable Integer id, @PathVariable String name, @PathVariable String birthDate, @PathVariable String nationality, @PathVariable String image) throws SQLException {
        repository.insert(new Actor(id, name, birthDate, nationality, image));
    }

    @GetMapping("/{id}")
    public Actor findById(@PathVariable Integer id) throws SQLException {
        return repository.findById(String.valueOf(id));
    }

    @GetMapping("/movies/{id}")
    public List<Movie> findMovies(@PathVariable Integer id) throws SQLException {
        return CastRepository.getInstance().getMovies(id);
    }
}
