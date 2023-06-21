package io.springbatch.springbatch.complete;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;

import javax.sql.DataSource;

//@Configuration
public class CustomBatchConfigurer extends BasicBatchConfigurer {

	private final DataSource dataSource;

	protected CustomBatchConfigurer(BatchProperties properties, DataSource dataSource, TransactionManagerCustomizers transactionManagerCustomizers) {
		super(properties, dataSource, transactionManagerCustomizers);
		this.dataSource = dataSource;
	}

	@Override
	protected JobRepository createJobRepository() throws Exception {

		JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
		factory.setDataSource(dataSource);
		factory.setTransactionManager(getTransactionManager());
		factory.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
		factory.setTablePrefix("SYSTEM_");

		return factory.getObject();
	}
}
