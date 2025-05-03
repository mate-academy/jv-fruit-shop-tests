package core.basesyntax.services.operations;

import core.basesyntax.model.TransactionDto;

public interface Operation {
    Integer getNewAmount(TransactionDto transactionDto, int oldAmount);
}
