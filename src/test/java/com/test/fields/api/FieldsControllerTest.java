package com.test.fields.api;

import com.test.fields.model.Field;
import com.test.fields.persistence.FieldRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;
import java.util.UUID;

import static com.test.fields.TestUtil.getContents;
import static com.test.fields.TestUtil.getResource;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FieldsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // We do not have DB to test with so use mock at base layer!
    @MockBean
    private FieldRepository fieldRepository;

    @Test
    void testCreateNewField() throws Exception {
        mockMvc.perform(post(ApiEndpoint.FIELDS)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(getContents("sampledata/field.json")))
                .andExpect(status().isOk());
    }

    @Test
    void testGetFieldWhichDoesNotExist() throws Exception {
        UUID fieldId = UUID.fromString("558074c4-69ee-448c-8121-caecd2fa2a1e");
        when(fieldRepository.findById(fieldId)).thenReturn(Optional.empty());

        MvcResult mvcResult = mockMvc.perform(get(ApiEndpoint.FIELDS_WITH_FIELD_ID, fieldId.toString()))
                .andExpect(status().isNotFound())
                .andReturn();

        assertEquals(getContents("api/field-not-found.json"), mvcResult.getResponse().getContentAsString(), true);
    }

    @Test
    void testGetFieldValid() throws Exception {
        UUID fieldId = UUID.fromString("a0f63e74-d7ef-4924-acb3-0e770ae9ec98");
        Field field = getResource("sampledata/field.json", Field.class);
        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(field));

        mockMvc.perform(get(ApiEndpoint.FIELDS_WITH_FIELD_ID, fieldId.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteField() throws Exception {
        UUID fieldId = UUID.fromString("558074c4-69ee-448c-8121-caecd2fa2a1e");

        mockMvc.perform(delete(ApiEndpoint.FIELDS_WITH_FIELD_ID, fieldId.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateField() throws Exception {
        UUID fieldId = UUID.fromString("a0f63e74-d7ef-4924-acb3-0e770ae9ec98");

        mockMvc.perform(put(ApiEndpoint.FIELDS_WITH_FIELD_ID, fieldId.toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(getContents("sampledata/field.json")))
                .andExpect(status().isOk());
    }

}