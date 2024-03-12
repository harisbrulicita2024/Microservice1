package harisbrulicita2024.microservice1.service;

import harisbrulicita2024.microservice1.model.Job;
import java.util.List;
import java.util.Optional;

public interface JobService {
    Job saveJob(Job job);
    Optional<Job> findById(Integer id);
    List<Job> findAll();
    Job updateJob(Job job);
    void deleteById(Integer id);
    List<Job> findByCity(String city);
}
