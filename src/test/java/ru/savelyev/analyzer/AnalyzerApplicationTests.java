package ru.savelyev.analyzer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.savelyev.analyzer.exceptions.StringLengthViolenceException;
import ru.savelyev.analyzer.service.CharacterFrequencyService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = AnalyzerApplication.class)
@AutoConfigureMockMvc
class AnalyzerApplicationTests {
    @Value("${string.length}")
    private int length;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private CharacterFrequencyService characterFrequencyService;

    @Test
    void calculateCharacterFrequency_successful() throws Exception {
        mvc.perform(get("/api/calculate-character-frequency?source=ababacbb")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("{\"b\":4,\"a\":3,\"c\":1}"));
    }

    @Test
    void calculateCharacterFrequency_withoutParameter() throws Exception {
        mvc.perform(get("/api/calculate-character-frequency").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void calculateCharacterFrequency_emptyParameter() throws Exception {
        mvc.perform(get("/api/calculate-character-frequency?source=").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void calculateCharacterFrequency_blankParameter() throws Exception {
        mvc.perform(get("/api/calculate-character-frequency?source=    ")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void calculateCharacterFrequency_StringLengthViolation() throws Exception {
        mvc.perform(get("/api/calculate-character-frequency?source").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void assertThrowStringLengthViolenceException_blankString() {
        assertThatThrownBy(() -> characterFrequencyService.validRequestString(""))
                .isInstanceOf(StringLengthViolenceException.class)
                .hasMessageContaining("String  length shouldn't  be blank or more than");
        assertThatThrownBy(() -> characterFrequencyService.validRequestString("  "))
                .isInstanceOf(StringLengthViolenceException.class)
                .hasMessageContaining("String  length shouldn't  be blank or more than");
    }

    @Test
    void assertThrowStringLengthViolenceException_StringSize() {
        String str = new String(new char[length + 1]);
        assertThatThrownBy(() -> characterFrequencyService.validRequestString(str))
                .isInstanceOf(StringLengthViolenceException.class)
                .hasMessageContaining("String  length shouldn't  be blank or more than");
    }

}
