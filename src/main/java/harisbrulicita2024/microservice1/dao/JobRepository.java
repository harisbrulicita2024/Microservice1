package harisbrulicita2024.microservice1.dao;

import harisbrulicita2024.microservice1.model.Job;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends MongoRepository<Job, Integer> {
    List<Job> findByCity(String city);
}
