package harisbrulicita2024.microservice1.consumer;

import harisbrulicita2024.microservice1.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import harisbrulicita2024.microservice1.model.Job;

@Component
public class JobConsumer {

    private static final Logger logger = LoggerFactory.getLogger(JobConsumer.class);

    @Autowired
    private JobService jobService;

    @JmsListener(destination = "job")
    public void processJob(Job job) {
        logger.info("Received job from ActiveMQ queue: {}", job);
        jobService.save(job);
    }
}