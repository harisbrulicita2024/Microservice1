package harisbrulicita2024.microservice1.controller;

import harisbrulicita2024.microservice1.model.Job;
import harisbrulicita2024.microservice1.service.GeoLocationService;
import harisbrulicita2024.microservice1.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final JobService jobService;
    private final GeoLocationService geoLocationService;

    @Autowired
    public JobController(JobService jobService, GeoLocationService geoLocationService) {
        this.jobService = jobService;
        this.geoLocationService = geoLocationService;
    }

    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Integer id) {
        return jobService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Job createJob(@RequestBody Job job) {
        return jobService.saveJob(job);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Integer id, @RequestBody Job job) {
        return jobService.findById(id)
                .map(existingJob -> {
                    job.setId(id);
                    return ResponseEntity.ok(jobService.updateJob(job));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Integer id) {
        if (jobService.findById(id).isPresent()) {
            jobService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/jobsByIp")
    public CompletableFuture<ResponseEntity<List<Job>>> getJobsByIp(@RequestParam String ip) {
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
