package service;

import model.TransactionDto;

public interface Parser {
    TransactionDto parseLineToTransaction(String line);
}
