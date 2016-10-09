package main.Repositories;

import main.model.Lines;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;


@Component
public interface LinesRepository extends CrudRepository<Lines,Integer> {

}
