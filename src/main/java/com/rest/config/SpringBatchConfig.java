package com.rest.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.rest.model.User;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
		
	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory,StepBuilderFactory stepBuilderFactory,
			ItemReader<User> itemReader,ItemWriter<User> itemWriter,ItemProcessor<User, User> itemProcessor) {
		
		Step step1 = stepBuilderFactory.get("ETL-Database-Load-male")
				.<User,User> chunk(1)
				.reader(itemReader)
				.processor(itemProcessor)
				.writer(itemWriter)
				.build();
		
		Step step2 = stepBuilderFactory.get("ETL-Database-Load-female")
				.<User,User> chunk(1)
				.reader(itemReader)
				.processor(itemProcessor)
				.writer(itemWriter)
				.build();
				
				
		return jobBuilderFactory.get("ETL-Load")
				.incrementer(new RunIdIncrementer())
				.flow(step1)
				.next(step2)
				.end()
				.build();
		
	}
	
	private static final String QUERY_FIND_MALE = "SELECT * from user where gender = 'M'";
	
	private static final String QUERY_FIND_FEMALE = "SELECT * from user where gender = 'F'";
	
	@Bean
	ItemReader<User> databaseItemReader (DataSource datasource){
		JdbcCursorItemReader<User> databaseReader = new JdbcCursorItemReader<>();
		databaseReader.setDataSource(datasource);
		databaseReader.setSql(QUERY_FIND_MALE);
		databaseReader.setRowMapper(new BeanPropertyRowMapper<>(User.class));
		
		return databaseReader;
	}
	
	@Bean
	ItemReader<User> databaseItemReaderFemale (DataSource datasource){
		JdbcCursorItemReader<User> databaseReaderFemale = new JdbcCursorItemReader<>();
		databaseReaderFemale.setDataSource(datasource);
		databaseReaderFemale.setSql(QUERY_FIND_FEMALE);
		databaseReaderFemale.setRowMapper(new BeanPropertyRowMapper<>(User.class));
		
		return databaseReaderFemale;
	}
}
