package de.netos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document(indexName = "instruments", type = "instrument")
public class Instrument {

	@Id
	private String isin;
	private String displayName;
	private String country;
	private String symbolType;
	private String ticker;
}
