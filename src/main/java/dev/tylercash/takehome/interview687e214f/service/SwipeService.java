package dev.tylercash.takehome.interview687e214f.service;

import dev.tylercash.takehome.interview687e214f.configuration.SwipeConfiguration;
import dev.tylercash.takehome.interview687e214f.model.job.Job;
import dev.tylercash.takehome.interview687e214f.model.worker.Worker;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@AllArgsConstructor
public class SwipeService {
    private SwipeConfiguration swipeConfiguration;
    private RestTemplate restTemplate;

    public List<Job> getJobs() {
        ResponseEntity<Job[]> response = restTemplate.getForEntity(swipeConfiguration.getUrl() + "/api/jobs", Job[].class);
        return List.of(Objects.requireNonNull(response.getBody()));
    }

    public List<Worker> getWorkers() {
        ResponseEntity<Worker[]> response = restTemplate.getForEntity(swipeConfiguration.getUrl() + "/api/workers", Worker[].class);
        return List.of(Objects.requireNonNull(response.getBody()));
    }

    public Optional<Worker> getWorker(String workerGuid) {
        return getWorkers().stream()
                .filter(x -> Objects.equals(x.getGuid(), workerGuid))
                .findFirst();
    }
}
