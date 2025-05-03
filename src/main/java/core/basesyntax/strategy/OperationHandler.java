package core.basesyntax.strategy;

import core.basesyntax.dto.Transaction;

public interface OperationHandler {
    int perform(Transaction transferObject);
}
