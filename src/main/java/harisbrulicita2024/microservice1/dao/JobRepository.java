package harisbrulicita2024.microservice1.dao;

import harisbrulicita2024.microservice1.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    List<Job> findByCity(String city);
}