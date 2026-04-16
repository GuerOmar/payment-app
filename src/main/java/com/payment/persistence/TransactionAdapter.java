package com.payment.persistence;

import com.payment.model.Transaction;
import com.payment.persistence.mapper.TransactionMapper;
import com.payment.persistence.repository.TransactionRepository;
import org.springframework.stereotype.Component;

@Component
public class TransactionAdapter {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionAdapter(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    public void save(Transaction tx) {
        transactionRepository.save(transactionMapper.toEntity(tx));
    }
}
