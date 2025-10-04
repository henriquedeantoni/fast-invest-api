package com.invest.fastinvestment.service;

import com.invest.fastinvestment.controller.CreateInvestorDto;
import com.invest.fastinvestment.entity.Investor;
import com.invest.fastinvestment.exceptions.InvestorCreationException;
import com.invest.fastinvestment.repository.InvestorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class InvestorServiceTest {

    @Mock
    private InvestorRepository investorRepository;

    @InjectMocks
    private InvestorService investorService;

    @Captor
    private ArgumentCaptor<Investor> investorArgumentCaptor;

    @Nested
    class createInvestor{

        @Test
        @DisplayName("Should create a investor user with success.")
        void shouldCreateInvestorWithSuccess() {

            //Arrange
            var userInvestor = new Investor(
                    UUID.randomUUID(),
                    "felipezan",
                    "felipezanatta@gmail.com",
                    "82sq8wF@",
                    "Felipe Zanatta",
                    Instant.now(),
                    null
            );

            doReturn(userInvestor).when(investorRepository).save(investorArgumentCaptor.capture());
            var input = new CreateInvestorDto(
                    "felipezan",
                    "felipezanatta@gmail.com",
                    "82sq8wF@",
                    "Felipe Zanatta");
            //Act
            var output = investorService.createInvestor(input);

            //Assert
            assertNotNull(output);
            var investorCaptured = investorArgumentCaptor.getValue();
            assertEquals(input.username(), investorCaptured.getUsername());
            assertEquals(input.email(), investorCaptured.getEmail());
            assertEquals(input.password(), investorCaptured.getPassword());
            assertEquals(input.name(), investorCaptured.getName());
        }

        @Test
        @DisplayName("Should throw an exception when error occurs")
        void shouldThrownAnExceptionWhenErrorOccurs() {

            //Arrange
            var input = new CreateInvestorDto(
                    "joanadarc",
                    "joanadarc@yahoo.com",
                    "2882wsh28",
                    "Joan Darc"
            );

            doThrow(new RuntimeException("Database error"))
            .when(investorRepository).save(argThat(investor ->
                                investor.getEmail().equals(input.email()) &&
                                investor.getUsername().equals(input.username())
                            ));

            // Act & Assert
            var exception = assertThrows(
                    InvestorCreationException.class,
                    () -> investorService.createInvestor(input)
            );
            assertEquals("Failed to create investor user", exception.getMessage());
            assertTrue(exception.getCause() instanceof RuntimeException);
            assertEquals("Database error", exception.getCause().getMessage());


            verify(investorRepository, times(1)).save(argThat(investor ->
                    investor.getEmail().equals(input.email()) &&
                            investor.getUsername().equals(input.username())
            ));
        }
    }
}