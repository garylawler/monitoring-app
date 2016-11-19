package monitoring.app.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import monitoring.app.service.DatabaseService;
import monitoring.app.service.QueueService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MonitoringControllerTest {

    private MonitoringController monitoringController;
    private MockHttpServletRequestBuilder requestBuilder;
    private MockMvc mockMvc;

    @Mock
    private DatabaseService databaseService;

    @Mock
    private QueueService queueService;

    @Before
    public void onSetUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        monitoringController = new MonitoringController();
        mockMvc = MockMvcBuilders.standaloneSetup(monitoringController).build();

        ReflectionTestUtils.setField(monitoringController, "databaseService", databaseService);
        ReflectionTestUtils.setField(monitoringController, "queueService", queueService);

        when(databaseService.ping()).thenReturn(true);
        when(queueService.ping()).thenReturn(true);

        requestBuilder = MockMvcRequestBuilders.get("/heartbeat");
    }

    @Test
    public void happyPath() throws Exception {
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        assertNotNull(jsonResponse);

        JsonNode jsonNode = new ObjectMapper().readTree(jsonResponse);
        assertTrue(jsonNode.findValue("dbConnectionSuccessful").asBoolean());
        assertTrue(jsonNode.findValue("queueConnectionSuccessful").asBoolean());

    }
}
