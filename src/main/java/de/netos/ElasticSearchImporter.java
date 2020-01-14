package de.netos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.netos.service.ImportServiceImpl;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class ElasticSearchImporter implements CommandLineRunner {
	
	@Autowired
	private ImportServiceImpl importService;
	
	public static void main(String[] args) {
		SpringApplication.run(ElasticSearchImporter.class, args);
	}
	
	@Override
	public void run(String... args) {
		log.info("Starting Instrument import...");
		try {
			importService.importInstruments(args[0]);
		} catch (Exception e) {
			log.error("Import finished with exception", e);
			System.exit(1);
		}
		log.info("Import finished successful");
		System.exit(0);
	}
}
