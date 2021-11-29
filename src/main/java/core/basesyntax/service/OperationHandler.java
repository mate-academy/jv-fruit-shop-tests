package core.basesyntax.service;

import core.basesyntax.service.impl.TransactionDto;

public interface OperationHandler {
    int getFruitAmount(TransactionDto transactionDto);
}
