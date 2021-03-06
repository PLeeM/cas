package org.apereo.cas.authentication;

import lombok.val;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.Before;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Test for JSON Serialization
 * @author David Rodriguez
 * @since 5.0.0
 */
@Slf4j
public class DefaultAuthenticationTests {

    private static final File JSON_FILE = new File(FileUtils.getTempDirectoryPath(), "defaultAuthentication.json");

    private ObjectMapper mapper;

    @Before
    public void initialize() {
        mapper = Jackson2ObjectMapperBuilder.json()
                .featuresToDisable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
        mapper.findAndRegisterModules();
    }
    
    @Test
    public void verifySerializeADefaultAuthenticationToJson() throws IOException {
        val serviceWritten = CoreAuthenticationTestUtils.getAuthentication();
        mapper.writeValue(JSON_FILE, serviceWritten);
        val serviceRead = mapper.readValue(JSON_FILE, Authentication.class);
        assertEquals(serviceWritten, serviceRead);
    }
}
