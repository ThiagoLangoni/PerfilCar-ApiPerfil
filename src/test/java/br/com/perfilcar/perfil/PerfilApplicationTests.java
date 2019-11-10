
package br.com.perfilcar.perfil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.perfilcar.perfil.application.PerfilService;
import br.com.perfilcar.perfil.domain.Perfil;
import br.com.perfilcar.perfil.interfaces.PerfilResource;


@RunWith(SpringRunner.class)
@WebMvcTest(value = PerfilResource.class, secure = false)
public class PerfilApplicationTests {
	Perfil mockPerfil = new Perfil(1l, "Mustang", "Ford", "Mustang", "1000", "2020", "2020", "Eduardo", "Bunito dmais",
			"timestamp 2019-11-03-19.41.55", "eduardo@fiap.com.br");

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PerfilService service;

	@Before
	public void setup() throws Exception {
		when(service.listaPerfis()).thenReturn(Arrays.asList(mockPerfil));
		when(service.salvaPerfil(any(Perfil.class))).thenReturn(mockPerfil);
		when(service.listaPerfilUnico(anyLong())).thenReturn(mockPerfil);
		when(service.atualizaPerfil(any(Perfil.class))).thenReturn(mockPerfil);
		doAnswer((i) -> {
			assertNotNull(i.getArgument(0));
			assertTrue(((Long) i.getArgument(0)) > 0);
			return null;
		}).when(service).deletaPerfil(anyLong());
	}

	@Test
	public void testsalvaPerfil() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/perfil/perfil")
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(mockPerfil))
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		assertEquals("http://localhost/perfil/perfil/1", response.getHeader(HttpHeaders.LOCATION));
	}

	@Test
	public void testatualizaPerfil() throws Exception {
			Perfil perfil = mockPerfil;
			perfil.setAnoFabricacao("2021");
			RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/perfil/perfil")
							.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(perfil))
							.contentType(MediaType.APPLICATION_JSON);

			MvcResult result = mockMvc.perform(requestBuilder).andReturn();

			MockHttpServletResponse response = result.getResponse();

			assertEquals(HttpStatus.OK.value(), response.getStatus());

			String expected = objectMapper.writeValueAsString(perfil);

			JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testdeletaPerfil() throws Exception {
			RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/perfil/perfil/1")
							.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(mockPerfil))
							.contentType(MediaType.APPLICATION_JSON);

			MvcResult result = mockMvc.perform(requestBuilder).andReturn();

			MockHttpServletResponse response = result.getResponse();

			assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

}