package harisbrulicita2024.microservice1.service;

import harisbrulicita2024.microservice1.dao.JobRepository;
import harisbrulicita2024.microservice1.model.Job;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    private final JobRepository jobRepository;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public Job saveJob(Job job) {
        logger.info("Saving job: {}", job.toString());
        try {
            return jobRepository.save(job);
        } catch (Exception e) {
            logger.error("Error saving job", e);
            throw e;
        }
    }

    @Override
    public Optional<Job> findById(Integer id) {
        return jobRepository.findById(id);
    }

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public Job updateJob(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public void deleteById(Integer id) {
        jobRepository.deleteById(id);
    }

    @Override
    public List<Job> findByCity(String city) {
        return jobRepository.findByCity(city);
    }
}