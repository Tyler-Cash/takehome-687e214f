package dev.tylercash.takehome.interview687e214f;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.tylercash.takehome.interview687e214f.exceptions.ApiException;
import dev.tylercash.takehome.interview687e214f.model.job.Job;
import dev.tylercash.takehome.interview687e214f.model.worker.Worker;
import dev.tylercash.takehome.interview687e214f.service.MatcherService;
import dev.tylercash.takehome.interview687e214f.service.SwipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatusCode;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static dev.tylercash.takehome.interview687e214f.utils.FileUtils.getResourcesFile;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MatcherServiceTest {
    @Mock
    private SwipeService swipeService;
    private MatcherService matcherService;

    @BeforeEach
    void setup() throws IOException, URISyntaxException {
        swipeService = mock(SwipeService.class);
        matcherService = new MatcherService(swipeService);
        ObjectMapper mapper = JsonMapper.builder()   .addModule(new JavaTimeModule()).build();

        File jobsFile = getResourcesFile("jobs.json");
        Job[] jobs = mapper.readValue(jobsFile, Job[].class);

        File workersFile = getResourcesFile("workers.json");
        Worker[] workers = mapper.readValue(workersFile, Worker[].class);

        when(swipeService.getJobs()).thenReturn(Arrays.stream(jobs).toList());
        when(swipeService.getWorkers()).thenReturn(Arrays.stream(workers).toList());
    }

    @Test
    void findJobsForWorker() {
        String workerGUID = "562f66475ee4418d7ac560ab";
        Optional<Worker> foundWorker = swipeService.getWorkers().stream().filter(worker -> worker.getGuid().equals(workerGUID)).findAny();
        when(swipeService.getWorker(workerGUID)).thenReturn(foundWorker);

        List<Job> actualJobs = matcherService.matchWorkerToJobs(workerGUID);
        List<String> expectedJobGUIDs = List.of("562f66aaf75e928a5df43d76", "562f66aa1ceec2fb3e8bb3a0", "562f66aa89c9c662fa538fb7");
        List<Job> expectedJobs = swipeService.getJobs().stream()
            .filter(job -> expectedJobGUIDs.contains(job.getGuid()))
            .toList();
        assertTrue(expectedJobs.containsAll(actualJobs), "Expected jobs don't match. " +
                "actual: " + Arrays.toString(actualJobs.stream().map(Job::getGuid).toArray()) + ", " +
                "expected: " + Arrays.toString(expectedJobGUIDs.toArray()));
    }

    @Test
    void findJobsForWorkerWhereWorkerDoesntExist() {
        String workerGUID = "doesntexist";

        ApiException e = assertThrowsExactly(ApiException.class, () -> matcherService.matchWorkerToJobs(workerGUID));
        assertEquals(HttpStatusCode.valueOf(404), e.getHttpStatusCode());
        assertEquals("Worker doesn't exist", e.getMessage());
    }

}
