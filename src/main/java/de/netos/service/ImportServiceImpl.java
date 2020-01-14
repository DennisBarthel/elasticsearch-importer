package de.netos.service;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.xml.sax.SAXException;

import de.netos.index.IndexHandler;
import de.netos.model.Instrument;
import de.netos.parser.Parser;
import de.netos.repository.ImportRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImportServiceImpl implements ImportService {

	@Autowired
	private ImportRepository importRepository;
	
	@Autowired
	private IndexHandler indexHandler;
	
	public void importInstruments(String filename) throws ParserConfigurationException, SAXException, IOException {
		StopWatch watch = new StopWatch("Import Instrument data");
		
		watch.start("SetupIndex");
		indexHandler.setupIndex();
		watch.stop();

		Set<Instrument> set = new HashSet<>();
		
		watch.start("Parse file");
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser parserFactory = saxParserFactory.newSAXParser();
		Parser parser = new Parser(i -> {
			log.info(i.toString());
			set.add(i);
		});
		parserFactory.parse(new File(filename), parser);
		watch.stop();
		
		watch.start("Insert");
		importRepository.saveAll(set);
		watch.stop();
		
		log.info(watch.toString());
	}
}
