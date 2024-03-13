package harisbrulicita2024.microservice1;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import harisbrulicita2024.microservice1.controller.JobController;
import harisbrulicita2024.microservice1.model.Job;
import harisbrulicita2024.microservice1.service.GeoLocationService;
import harisbrulicita2024.microservice1.service.JobService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@WebMvcTest(JobController.class)
public class Microservice1ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobService jobService;

    @MockBean
    private GeoLocationService geoLocationService;

    @Test
    public void getAllJobs_ReturnsJobsList() throws Exception {
        Job job = new Job();
        job.setId(1);
        job.setName("Software Developer");
        List<Job> allJobs = Arrays.asList(job);

        given(jobService.findAll()).willReturn(allJobs);

        mockMvc.perform(get("/api/jobs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(job.getName())));
    }

    @Test
    public void getJobById_WithExistingId_ReturnsJob() throws Exception {
        int jobId = 1;
        Job job = new Job();
        job.setId(jobId);
        job.setName("Software Developer");

        given(jobService.findById(jobId)).willReturn(Optional.of(job));

        mockMvc.perform(get("/api/jobs/{id}", jobId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(job.getName())));
    }

    @Test
    public void getJobById_WithNonExistingId_ReturnsNotFound() throws Exception {
        int jobId = 1;
        given(jobService.findById(jobId)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/jobs/{id}", jobId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createJob_ReturnsSavedJob() throws Exception {
        Job job = new Job();
        job.setName("Software Developer");

        given(jobService.saveJob(any(Job.class))).willReturn(job);

        mockMvc.perform(post("/api/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(job)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(job.getName())));
    }

    @Test
    public void updateJob_WithExistingId_ReturnsUpdatedJob() throws Exception {
        int jobId = 1;
        Job existingJob = new Job();
        existingJob.setId(jobId);
        existingJob.setName("Software Developer");

        Job updatedJob = new Job();
        updatedJob.setId(jobId);
        updatedJob.setName("Senior Developer");

        given(jobService.findById(jobId)).willReturn(Optional.of(existingJob));
        given(jobService.updateJob(any(Job.class))).willReturn(updatedJob);

        mockMvc.perform(put("/api/jobs/{id}", jobId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedJob)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(updatedJob.getName())));
    }

    @Test
    public void updateJob_WithNonExistingId_ReturnsNotFound() throws Exception {
        int jobId = 1;
        Job updatedJob = new Job();
        updatedJob.setId(jobId);
        updatedJob.setName("Senior Developer");

        given(jobService.findById(jobId)).willReturn(Optional.empty());

        mockMvc.perform(put("/api/jobs/{id}", jobId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedJob)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteJob_WithExistingId_ReturnsOk() throws Exception {
        int jobId = 1;
        given(jobService.findById(jobId)).willReturn(Optional.of(new Job()));

        mockMvc.perform(delete("/api/jobs/{id}", jobId))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteJob_WithNonExistingId_ReturnsNotFound() throws Exception {
        int jobId = 1;
        given(jobService.findById(jobId)).willReturn(Optional.empty());

        mockMvc.perform(delete("/api/jobs/{id}", jobId))
                .andExpect(status().isNotFound());
    }
}
