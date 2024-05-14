package harisbrulicita2024.microservice1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import harisbrulicita2024.microservice1.dao.JobRepository;
import harisbrulicita2024.microservice1.model.Job;
import harisbrulicita2024.microservice1.service.GeoLocationService;
import harisbrulicita2024.microservice1.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private static final Logger logger = LoggerFactory.getLogger(JobController.class);
    private final JobService jobService;
    private final GeoLocationService geoLocationService;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public JobController(JobService jobService, GeoLocationService geoLocationService) {
        this.jobService = jobService;
        this.geoLocationService = geoLocationService;
        logger.info("Ready");

    }
    @Autowired
    private JmsTemplate jmsTemplate;
    public JobRepository jobRepository;

    private void sendJobToQueue(Job job) {
        try {
            String jobJson = objectMapper.writeValueAsString(job);
            jmsTemplate.convertAndSend("jobQueue", jobJson);
            logger.info("Sent job ActiveMQ {}", jobJson);
        } catch (JsonProcessingException e) {
            logger.error("Error to JSON", e);
            throw new RuntimeException("Error serializing", e);
        }
    }


    @JmsListener(destination = "jobQueue")
    public void processJob(String jobJson) {
        try {
            Job job = objectMapper.readValue(jobJson, Job.class);
            jobService.save(job);
            logger.info("ActiveMQ queue: {}", job);
        } catch (Exception e) {
            logger.error("Error from queue", e);
            throw new RuntimeException("Error from queue", e);
        }
    }

    @GetMapping
    public List<Job> getAllJobs() {
        logger.info("Getting all jobs");
        return jobService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Integer id) {
        logger.info("Getting job by id: {}", id);
        return jobService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Job createJob(@RequestBody Job job) {
        logger.info("Creating job: {}", job.toString());
        jobService.saveJob(job);
        sendJobToQueue(job);

        try {
            return jobService.saveJob(job);
        } catch(Exception ex) {
            logger.error("Error saving job to database: {}", ex.getMessage());
            throw ex;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Integer id, @RequestBody Job job) {
        logger.info("Updating job with id: {}", id);
        return jobService.findById(id)
                .map(existingJob -> {
                    job.setId(id);
                    return ResponseEntity.ok(jobService.updateJob(job));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Integer id) {
        logger.info("Deleting job with id: {}", id);
        if (jobService.findById(id).isPresent()) {
            jobService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/jobsByIp")
    public CompletableFuture<ResponseEntity<List<Job>>> getJobsByIp(@RequestParam String ip) {
        logger.info("Getting jobs by ip: {}", ip);
        return geoLocationService.getCityFromIp(ip)
                .thenApply(city -> {
                    if (city != null) {
                        List<Job> jobs = jobService.findByCity(city);
                        return new ResponseEntity<>(jobs, HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                });
    } }
