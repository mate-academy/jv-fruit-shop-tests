package core.basesyntax.service.impl;

import core.basesyntax.service.OperationHandler;

public class SupplyOperation implements OperationHandler {
    @Override
    public int getFruitAmount(TransactionDto transactionDto) {
        int fruitAmount = Storage.reportMap.get(transactionDto.getFruit())
                + transactionDto.getAmount();
        core.basesyntax.service.impl.Storage.reportMap.put(transactionDto.getFruit(), fruitAmount);
        return fruitAmount;
    }
}
