package com.payment.web;

import com.payment.security.JwtAuthenticationFilter;
import com.payment.service.payment.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
@AutoConfigureMockMvc(addFilters = false)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private PaymentService paymentService;

    @Test
    void shouldReturnPaymentPage_whenGetRequestIsMade_callShowPaymentPage() throws Exception {
        when(paymentService.findAllByUsername(anyString())).thenReturn(List.of());

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                new User("test", "test", List.of()),
                null,
                List.of()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment-page"));

        SecurityContextHolder.clearContext();
    }
}