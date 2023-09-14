package dev.tylercash.takehome.interview687e214f.controller;

import dev.tylercash.takehome.interview687e214f.service.MatcherService;
import dev.tylercash.takehome.interview687e214f.model.job.Job;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class MatcherController {
    private final MatcherService matcherService;

    @GetMapping(value = "/api/v1/matcher/{workerGuid}")
    public List<Job> getMatches(@PathVariable String workerGuid){
        return matcherService.matchWorkerToJobs(workerGuid);
    }
}
