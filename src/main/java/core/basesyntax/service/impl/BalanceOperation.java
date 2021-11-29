package core.basesyntax.service.impl;

import core.basesyntax.service.OperationHandler;

public class BalanceOperation implements OperationHandler {
    @Override
    public int getFruitAmount(core.basesyntax.service.impl.TransactionDto transactionDto) {
        Storage.reportMap.put(transactionDto.getFruit(), transactionDto.getAmount());
        return transactionDto.getAmount();
    }
}
