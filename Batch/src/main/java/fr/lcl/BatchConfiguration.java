package fr.lcl;

import java.util.Iterator;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fr.lcl.businessLayer.model.Certificat;
import fr.lcl.businessLayer.service.CertificatService;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	CertificatService certificatService;

	// tag::readerwriterprocessor[]
	@Bean
	public ItemReader<Certificat> reader() {
		IteratorItemReader<Certificat> iteratorItemReader;
		String chemin = "C:\\Users\\Dell\\Desktop\\certificat\\";

		Iterator<Certificat> iter = certificatService.listCertificates(chemin).iterator();
		iteratorItemReader = new IteratorItemReader<>(iter);
		return iteratorItemReader;
	}

	@Bean
	public CertificatItemProcessor processor() {
		return new CertificatItemProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<Certificat> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Certificat>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
			//.sql("INSERT INTO certificat (dn, cn, datedebut, datefin, type, cle_public, cle_prive, commentaire, passeword, destinataire) VALUES (:dn, :cn, :startdate, :enddate, :type, :cle_public, :cle_prive, :commentaire, :passeword, :destinataire, :publicPrive)")
				.sql("INSERT INTO certificat (dn, cn) VALUES (:dn, :cn, :)")

				.dataSource(dataSource).build();
	}
	// end::readerwriterprocessor[]

	// tag::jobstep[]
	@Bean
	public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
		return jobBuilderFactory.get("importUserJob").incrementer(new RunIdIncrementer()).listener(listener).flow(step1)
				.end().build();
	}

	@Bean
	public Step step1(JdbcBatchItemWriter<Certificat> writer) {
		return stepBuilderFactory.get("step1").<Certificat, Certificat>chunk(10).reader(reader()).processor(processor())
				.writer(writer).build();
	}
}
