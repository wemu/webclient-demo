package ch.brontofundus.spring.webclient;

import ch.brontofundus.spring.webclient.controller.Dto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ApplicationReadListTests {

	final static String STRING_ONE_LINE = "/string-list-one-line";
	final static String STRING_FORMATTED = "/string-list-formatted";
	final String DTO_ONE_LINE = "/dto-list-one-line";
	final String DTO_FORMATTED = "/dto-list-formatted";

	@LocalServerPort
	int PORT;

	WebClient client;

	@BeforeEach
	public void setupClient() {
		client = WebClient.builder()
				.baseUrl("http://localhost:" + PORT)
				.defaultHeader(ACCEPT, APPLICATION_JSON_VALUE)
				.build();
	}

	@Test
	public void contextLoads() {
		System.out.println("PORT: " + PORT);
	}

	/*

	List<String> Section below

	 */

	@DisplayName("read List<String> using bodyToFlux - does NOT work")
	@ParameterizedTest
	@ValueSource(strings = {STRING_ONE_LINE, STRING_FORMATTED})
	void readStringListUsingBodyToFlux(String path) {
		List<String> vals = client
				.get()
				.uri(path)
				.retrieve()
				.bodyToFlux(String.class)
				.collectList()
				.block();

		assertThat(vals).isNotNull();
		assertThat(vals).hasSize(3);
	}

	@DisplayName("read List<String> using toEntityList - does NOT work")
	@ParameterizedTest
	@ValueSource(strings = {STRING_ONE_LINE, STRING_FORMATTED})
	public void readStringListUsingToEntityList(String path) {
		// https://github.com/spring-projects/spring-framework/blob/master/spring-webflux/src/test/java/org/springframework/web/reactive/function/client/WebClientIntegrationTests.java#L519
		List<String> vals = client
				.get()
				.uri(path)
				.retrieve()
				.toEntityList(String.class)
				.block()
				.getBody();

		assertThat(vals).isNotNull();
		assertThat(vals).hasSize(3);
	}

	@DisplayName("read List<String> using bodyToMono - DOES work")
	@ParameterizedTest
	@ValueSource(strings = {STRING_ONE_LINE, STRING_FORMATTED})
	public void readStringListUsingBodyToMono(String path) {
		List<String> vals = client
				.get()
				.uri(path)
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<String>>() {})
				.block();

		assertThat(vals).isNotNull();
		assertThat(vals).hasSize(3);
	}

	/*

	List<Dto> Section below

	 */

	@DisplayName("read List<Dto> using toEntityList - does work")
	@ParameterizedTest
	@ValueSource(strings = {DTO_ONE_LINE, DTO_FORMATTED})
	public void readDtoListUsingBodyToFlux(String path) {
		List<Dto> vals = client
				.get()
				.uri(path)
				.retrieve()
				.bodyToFlux(Dto.class)
				.collectList()
				.block();

		assertThat(vals).isNotNull();
		assertThat(vals).hasSize(3);
	}

	@DisplayName("read List<Dto> using toEntityList - does work")
	@ParameterizedTest
	@ValueSource(strings = {DTO_ONE_LINE, DTO_FORMATTED})
	public void readDtoListUsingToEntityList(String path) {
		List<Dto> vals = client
				.get()
				.uri(path)
				.retrieve()
				.toEntityList(Dto.class)
				.block()
				.getBody();

		assertThat(vals).isNotNull();
		assertThat(vals).hasSize(3);
	}

	@DisplayName("read List<Dto> using bodyToMono - does work")
	@ParameterizedTest
	@ValueSource(strings = {DTO_ONE_LINE, DTO_FORMATTED})
	public void readDtoListUsingBodyToMono(String path) {
		List<Dto> vals = client
				.get()
				.uri(path)
				.retrieve()
				// .bodyToMono(List.class)
				.bodyToMono(new ParameterizedTypeReference<List<Dto>>() {})
				.block();

		assertThat(vals).isNotNull();
		assertThat(vals).hasSize(3);
	}

}
