package com.application.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.application.repository.TransactionsRepository;

@ExtendWith(MockitoExtension.class)
public class TransactionsServiceTest {
	
	@Mock 
	TransactionsRepository transactionsRepository;
	
	@InjectMocks
	private TransactionsService transactionsService;
	
	
	
	
	

	

}
