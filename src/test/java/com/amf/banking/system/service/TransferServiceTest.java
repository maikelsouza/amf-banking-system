package com.amf.banking.system.service;

import com.amf.banking.system.dto.TransactionRequestDto;
import com.amf.banking.system.exception.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

    @InjectMocks
    private TransferService service;

    @Mock
    private  TransactionService transactionService;

    @Mock
    private AccountService accountService;

    @Test
    public void shouldMakeTransfer(){

        TransactionRequestDto transactionRequestDto = TransactionRequestDto
                .builder()
                .originAccountId("123")
                .destinationAccountId("456")
                .amount(new BigDecimal("10.00"))
                .build();


        when(accountService.getAccountBalance("123")).thenReturn(new BigDecimal("10.00"));
        doNothing().when(accountService).withdraw("123", new BigDecimal("10.00"));
        doNothing().when(accountService).deposit("456", new BigDecimal("10.00"));

        service.transfer(transactionRequestDto);

        verify(accountService).getAccountBalance("123");
        verify(accountService).withdraw("123", new BigDecimal("10.00"));
        verify(accountService).deposit("456", new BigDecimal("10.00"));
    }

    @Test
    public void whenTryMakeTransferWithOriginAndDestinationAccountEqualsShouldBusinessException(){

        TransactionRequestDto transactionRequestDto = TransactionRequestDto
                .builder()
                .originAccountId("123")
                .destinationAccountId("123")
                .amount(new BigDecimal("10.00"))
                .build();



        Assertions.assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> service.transfer(transactionRequestDto))
                .withMessage("A conta de origem e destino devem ser distintas");
    }

    @Test
    public void whenTryMakeTransferWithOriginAccountNotBalanceShouldBusinessException(){

        TransactionRequestDto transactionRequestDto = TransactionRequestDto
                .builder()
                .originAccountId("123")
                .destinationAccountId("456")
                .amount(new BigDecimal("10.00"))
                .build();

        when(accountService.getAccountBalance("123")).thenReturn(BigDecimal.ZERO);

        Assertions.assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> service.transfer(transactionRequestDto))
                .withMessage("A conta de origem não tem saldo");

        verify(accountService).getAccountBalance("123");
    }

}