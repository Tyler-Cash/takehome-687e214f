package dev.tylercash.takehome.interview687e214f.service;

import dev.tylercash.takehome.interview687e214f.exceptions.ApiException;
import dev.tylercash.takehome.interview687e214f.model.job.DistanceUnit;
import dev.tylercash.takehome.interview687e214f.model.job.Job;
import dev.tylercash.takehome.interview687e214f.model.worker.Worker;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class MatcherService {
    private final SwipeService swipeService;

    public List<Job> matchWorkerToJobs(String workerGuid){
        Optional<Worker> worker = swipeService.getWorker(workerGuid);
        if (worker.isEmpty()){
            throw new ApiException("Worker doesn't exist", HttpStatusCode.valueOf(404));
        }
        return swipeService.getJobs().stream()
                .filter(job -> isJobNearWorker(worker.get(), job)) // Filters jobs outside of worker's area
                .filter(job -> new HashSet<>(worker.get().getSkills()).contains(job.getJobTitle())) // Filters jobs that the worker is interested in
                .filter(job -> new HashSet<>(worker.get().getCertificates()).containsAll(job.getRequiredCertificates())) // Filters jobs that the worker is qualified for
                .sorted(Comparator.comparing(Job::getBillRate).reversed()) // Gets worker the highest paid job
                .limit(3)
                .toList();

    }

    // I'm assuming there'd be some pre-existing mechanism to do this so avoided an external dep.
    // Verified the function works by using https://www.calcmaps.com/map-radius/ and checking that a found job for a worker is in their job search radius
    // SO post for reference. https://stackoverflow.com/a/16794680
    // https://i.imgur.com/4u17JI5.png
    public boolean isJobNearWorker(Worker worker, Job job){
        final int R = 6371; // Radius of the earth
        double latDistance = Math.toRadians(job.getLocation().getLatitude() - worker.getJobSearchAddress().getLatitude());
        double lonDistance = Math.toRadians(job.getLocation().getLongitude() -  worker.getJobSearchAddress().getLongitude());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(job.getLocation().getLatitude())) * Math.cos(Math.toRadians(worker.getJobSearchAddress().getLatitude()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;
        if (worker.getJobSearchAddress().getUnit().equals(DistanceUnit.KM)){
            // Do nothing, already KMs
        } else {
            throw new ApiException("Unknown DistanceUnit specified");
        }
        distance = Math.sqrt(Math.pow(distance, 2));
        return distance <= worker.getJobSearchAddress().getMaxJobDistance();
    }


}
