package core.basesyntax.handler.impl;

import core.basesyntax.dto.Transaction;

public interface OperationHandler {
    int apply(Transaction transactionDto);
}
