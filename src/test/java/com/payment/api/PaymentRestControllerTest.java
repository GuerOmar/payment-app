package com.payment.api;

import com.payment.api.dto.PaymentRequest;
import com.payment.service.payment.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentRestController.class)
class PaymentRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private RuntimeService runtimeService;

    @BeforeEach
    void init() {
        ProcessInstance processInstance = mock(ProcessInstance.class);
        when(processInstance.getId()).thenReturn("1");
        when(runtimeService.startProcessInstanceByKey(anyString(), anyMap())).thenReturn(processInstance);
    }

    @Test
    void shouldReturn200_whenPaymentIsValid_callPay() throws Exception {
        PaymentRequest request = new PaymentRequest(1L, "CREDIT_CARD", BigDecimal.valueOf(100));


        mockMvc.perform(post("/api/pay")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(runtimeService, times(1)).startProcessInstanceByKey(
                anyString(),
                anyMap()
        );
    }

    @Test
    void shouldReturn400_whenRequestBodyIsMissing_callPay() throws Exception {
        mockMvc.perform(post("/api/pay")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verifyNoInteractions(runtimeService);
    }

    @Test
    void shouldReturn500_whenServiceThrowsException_callPay() throws Exception {
        PaymentRequest request = new PaymentRequest(1L, "CREDIT_CARD", BigDecimal.valueOf(100.0));

        doThrow(new RuntimeException("Payment processing failed"))
                .when(runtimeService)
                .startProcessInstanceByKey(
                        anyString(),
                        anyMap()
                );

        mockMvc.perform(post("/api/pay")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }
}