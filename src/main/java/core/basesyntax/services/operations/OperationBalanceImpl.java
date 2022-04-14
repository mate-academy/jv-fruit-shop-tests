package core.basesyntax.services.operations;

import core.basesyntax.model.TransactionDto;

public class OperationBalanceImpl implements Operation {

    @Override
    public Integer getNewAmount(TransactionDto transactionDto, int oldAmount) {
        return transactionDto.getAmount();
    }
}
