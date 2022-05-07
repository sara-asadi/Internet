package ir.ac.ut.iemdb.controller;

import ir.ac.ut.iemdb.model.IEMDB;
import ir.ac.ut.iemdb.repository.IEMDBRepository;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("iemdb")
public class IEMDBController {

    private final IEMDBRepository repository = IEMDBRepository.getInstance();

    @PostMapping("/insert/{id}/{name}/{actor}")
    public void insertIEMDB(@PathVariable String id, @PathVariable String name, @PathVariable String actor) throws SQLException {
        repository.insert(new IEMDB(id, name, actor));
    }

    @GetMapping("/find/{id}")
    public IEMDB findById(@PathVariable String id) throws SQLException {
        return repository.findById(id);
    }

    @GetMapping("/find")
    public List<IEMDB> findAll() throws SQLException {
        return repository.findAll();
    }
}
