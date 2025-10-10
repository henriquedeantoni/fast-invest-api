package com.invest.fastinvestment.service;

import com.invest.fastinvestment.controller.dtos.CreateInvestorDto;
import com.invest.fastinvestment.controller.dtos.UpdateInvestorDto;
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
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvestorServiceTest {

    @Mock
    private InvestorRepository investorRepository;

    @InjectMocks
    private InvestorService investorService;

    @Captor
    private ArgumentCaptor<Investor> investorArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

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

    @Nested
    class getInvestorById{

        @Test
        @DisplayName("Should get investor by Id with success. Optional is present case. ")
        void shouldGetInvestorByIdWithSuccessOptionalPresent() {

            //Arrange
            var investor = new Investor(
                    UUID.randomUUID(),
                    "username",
                    "myemail@email.com",
                    "pass",
                    "Investor Name",
                    Instant.now(),
                    null
            );
            doReturn(Optional.of(investor))
                    .when(investorRepository)
                    .findById(uuidArgumentCaptor
                            .capture());

            //Act
            var output =  investorService.getInvestorById(investor.getInvestorId().toString());

            //Assert
            assertTrue(output.isPresent());
            assertEquals(investor.getInvestorId(), uuidArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should get investor by Id with success. Optional is not present case. ")
        void shouldGetInvestorByIdWithSuccessOptionalNotPresent() {

            //Arrange
            var investorId = UUID.randomUUID();
            doReturn(Optional.empty())
                    .when(investorRepository)
                    .findById(uuidArgumentCaptor.capture());

            //Act
            var output =  investorService.getInvestorById(investorId.toString());

            //Assert
            assertTrue(output.isEmpty());
            assertEquals(investorId, uuidArgumentCaptor.getValue());
        }
    }

    @Nested
    class listAllInvestors{

        @Test
        @DisplayName("Should list all investor list with success")
        void shouldListAllInvestorsWithSuccess() {

            //Arrange
            List<Investor> investorList = new ArrayList<>();

            investorList.add(new Investor(
                    UUID.randomUUID(),
                    "username",
                    "myemail@email.com",
                    "pass",
                    "Investor Name",
                    Instant.now(),
                    null
            ));

            investorList.add(new Investor(
                    UUID.randomUUID(),
                    "username2",
                    "myemail2@email.com",
                    "pass2",
                    "Investor Name 2",
                    Instant.now(),
                    null
            ));

            doReturn(investorList)
                    .when(investorRepository)
                    .findAll();

            //Act
            var output = investorService.getAllInvestors();

            //Assert
            assertNotNull(output);
            assertEquals(investorList.size(), output.size());
        }
    }

    @Nested
    class deleteInvestorById{

        @Test
        @DisplayName("Should delete investor by id with success, when investor user exists")
        void shouldDeleteInvestorByIdWithSuccessWhenInvestorExists() {
            //Arrange
            doReturn(true)
                    .when(investorRepository)
                    .existsById(uuidArgumentCaptor.capture());

            doNothing()
                    .when(investorRepository)
                    .deleteById(uuidArgumentCaptor.capture());

            var investorId = UUID.randomUUID();

            //Act
            investorService.deleteInvestorById(investorId.toString());

            //Assert
            var idList = uuidArgumentCaptor.getAllValues();
            assertEquals(investorId, idList.get(0));
            assertEquals(investorId, idList.get(1));

            verify(investorRepository, times(1)).existsById(idList.get(0));
            verify(investorRepository, times(1)).deleteById(idList.get(1));
        }

        @Test
        @DisplayName("Should Not delete investor when it doesnt exist")
        void shouldNotDeleteInvestorWhenNotExists() {
            //Arrange
            doReturn(false)
                    .when(investorRepository)
                    .existsById(uuidArgumentCaptor.capture());

            var investorId = UUID.randomUUID();

            //Act
            investorService.deleteInvestorById(investorId.toString());

            //Assert
            assertEquals(investorId, uuidArgumentCaptor.getValue());

            verify(investorRepository, times(1)).existsById(uuidArgumentCaptor.getValue());
            verify(investorRepository, times(0)).deleteById(any());
        }
    }

    @Nested
    class updateInvestorById{

        @Test
        @DisplayName("Should update investor user by Id when user exists, username and password is filled. ")
        void shouldUpdateInvestorByIdWhenUserExistsAndUsernameAndPasswordIsFilled() {

            //Arrange
            var updateInvestorDto = new UpdateInvestorDto(
                    "newUsername",
                        "newpass",
                    "newemail@email.com",
                    "Investor Name"
            );

            var investor = new Investor(
                    UUID.randomUUID(),
                    "username",
                    "myemail@email.com",
                    "pass",
                    "Investor Name",
                    Instant.now(),
                    null
            );
            doReturn(Optional.of(investor))
                    .when(investorRepository)
                    .findById(uuidArgumentCaptor
                            .capture());
            doReturn(investor)
                    .when(investorRepository)
                    .save(investorArgumentCaptor.capture());

            //Act
            investorService.updateInvestorById(investor.getInvestorId().toString(), updateInvestorDto);

            //Assert
            assertEquals(investor.getInvestorId(), uuidArgumentCaptor.getValue());

            var investorCaptured = investorArgumentCaptor.getValue();

            assertEquals(investor.getUsername(), investorCaptured.getUsername());
            assertEquals(investor.getPassword(), investorCaptured.getPassword());
            assertEquals(investor.getEmail(), investorCaptured.getEmail());
            assertEquals(investor.getName(), investorCaptured.getName());

            verify(investorRepository, times(1)).findById(uuidArgumentCaptor.getValue());
            verify(investorRepository, times(1)).save(investor);
        }

        @Test
        @DisplayName("Should not update investor user by Id when user not exists. ")
        void shouldNotUpdateInvestorByIdWhenUserNotExist() {

            //Arrange
            var updateInvestorDto = new UpdateInvestorDto(
                    "newUsername",
                    "newpass",
                    "newemail@email.com",
                    "Investor Name"
            );

            var investor = new Investor(
                    UUID.randomUUID(),
                    "username",
                    "myemail@email.com",
                    "pass",
                    "Investor Name",
                    Instant.now(),
                    null
            );
            var investorId = UUID.randomUUID();
            doReturn(Optional.empty())
                    .when(investorRepository)
                    .findById(uuidArgumentCaptor
                            .capture());

            //Act
            investorService.updateInvestorById(investorId.toString(), updateInvestorDto);

            //Assert
            assertEquals(investorId, uuidArgumentCaptor.getValue());

            verify(investorRepository, times(1)).findById(uuidArgumentCaptor.getValue());
            verify(investorRepository, times(0)).save(any());
        }
    }
}