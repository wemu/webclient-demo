package ch.brontofundus.spring.webclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.util.StreamUtils.copyToString;

@RestController
public class Controller {

	@Value("classpath:json/string-list-one-line.json")
	Resource stringListOneLine;
	@Value("classpath:json/string-list-formatted.json")
	Resource stringListFormatted;

	@Value("classpath:json/dto-list-one-line.json")
	Resource dtoListOneLine;
	@Value("classpath:json/dto-list-formatted.json")
	Resource dtoListFormatted;


	@GetMapping(value = "/string-list-one-line", produces = APPLICATION_JSON_VALUE)
	public String stringListOneLine() throws IOException {
		return copyToString(stringListOneLine.getInputStream(), UTF_8);
	}
	@GetMapping(value = "/string-list-formatted", produces = APPLICATION_JSON_VALUE)
	public String stringListFormatted() throws IOException {
		return copyToString(stringListFormatted.getInputStream(), UTF_8);
	}

	@GetMapping(value = "/dto-list-one-line", produces = APPLICATION_JSON_VALUE)
	public String dtoListOneLine() throws IOException {
		return copyToString(dtoListOneLine.getInputStream(), UTF_8);
	}
	@GetMapping(value = "/dto-list-formatted", produces = APPLICATION_JSON_VALUE)
	public String dtoListFormatted() throws IOException {
		return copyToString(dtoListFormatted.getInputStream(), UTF_8);
	}

}
