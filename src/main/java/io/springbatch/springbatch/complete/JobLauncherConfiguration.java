package io.springbatch.springbatch.complete;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;

//@Configuration
@RequiredArgsConstructor
public class JobLauncherConfiguration {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final JobRepositoryListener jobRepositoryListener;

	@Bean
	public Job batchJob() {
		return jobBuilderFactory.get("Job")
				.start(step1())
				.next(step2())
				.listener(jobRepositoryListener)
				.build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.tasklet(new Tasklet() {
					@Override
					public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
						Thread.sleep(3000);
						return RepeatStatus.FINISHED;
					}
				})
				.build();
	}

	@Bean
	public Step step2() {
		return stepBuilderFactory.get("step2")
				.tasklet((stepContribution, chunkContext) -> null)
				.build();
	}
}
