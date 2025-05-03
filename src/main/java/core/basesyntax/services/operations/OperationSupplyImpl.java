package core.basesyntax.services.operations;

import core.basesyntax.model.TransactionDto;

public class OperationSupplyImpl implements Operation {
    @Override
    public Integer getNewAmount(TransactionDto transactionDto, int oldAmount) {
        return oldAmount + transactionDto.getAmount();
    }
}
