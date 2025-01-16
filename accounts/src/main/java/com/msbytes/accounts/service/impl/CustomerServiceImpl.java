package com.msbytes.accounts.service.impl;

import com.msbytes.accounts.dto.AccountsDto;
import com.msbytes.accounts.dto.CardsDto;
import com.msbytes.accounts.dto.CustomerDetailsDto;
import com.msbytes.accounts.dto.LoansDto;
import com.msbytes.accounts.entity.Accounts;
import com.msbytes.accounts.entity.Customer;
import com.msbytes.accounts.exception.ResourceNotFoundException;
import com.msbytes.accounts.mapper.AccountsMapper;
import com.msbytes.accounts.mapper.CustomerMapper;
import com.msbytes.accounts.repository.AccountsRepository;
import com.msbytes.accounts.repository.CustomerRepository;
import com.msbytes.accounts.service.ICustomersService;
import com.msbytes.accounts.service.client.CardsFeignClient;
import com.msbytes.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomersService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        if(null != loansDtoResponseEntity){
            customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());
        }

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
        if(null != cardsDtoResponseEntity){
            customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
        }

        return customerDetailsDto;
    }
}
