package mmi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mmi.domain.UnitValue;
import mmi.service.ConverterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.closeTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ConverterController.class)
public class ConverterControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ConverterService converterService;


    @Test
    public void convert() throws Exception {
        final UnitValue foot = new UnitValue("foot", 1.);
        final UnitValue meter = new UnitValue("meter", 0.3048);
        given(converterService.toMetric(foot)).willReturn(meter);

        mvc.perform(post("/convert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(foot)))
            .andExpect(status().isOk())
                .andExpect(jsonPath("$.unit").value("meter"))
                .andExpect(jsonPath("$.value").value(closeTo(0.3049, 0.0005)));
    }
}