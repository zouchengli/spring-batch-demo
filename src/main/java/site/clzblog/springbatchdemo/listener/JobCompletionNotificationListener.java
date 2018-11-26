package site.clzblog.springbatchdemo.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import site.clzblog.springbatchdemo.domain.Person;

import java.util.List;

@Slf4j
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");
            final String sql = "SELECT first_name, last_name FROM people";
            List<Person> results = jdbcTemplate.query(sql, (rs, i) -> new Person(rs.getString(1), rs.getString(2)));
            results.forEach(person -> log.info("Found {} in the database.", person));
        }
    }
}
