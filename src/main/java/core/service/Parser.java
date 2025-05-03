package core.service;

import core.model.TransactionDto;

public interface Parser {
    TransactionDto parseLine(String line);
}
