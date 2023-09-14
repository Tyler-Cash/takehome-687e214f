### Requirements

GET /matcher/${userId}

Match worker with jobs on the following fields in this order

- in location of job - as if a worker is outside the region of the job, they'd be excluded
- Job title matches worker skills
- has required certificates
- sort by pay rate - Assuming worker most interested in highest paid positions (I suspect this is greedy optimization rather than an optimal one)

