package core.basesyntax.service.impl;

import core.basesyntax.service.OperationHandler;

public class PurchaseOperation implements OperationHandler {
    @Override
    public int getFruitAmount(TransactionDto transactionDto) {
        int fruitAmount = Storage.reportMap
                .get(transactionDto.getFruit()) - transactionDto.getAmount();
        if (fruitAmount < 0) {
            throw new RuntimeException("Not enough fruits");
        }
        Storage.reportMap.put(transactionDto.getFruit(), fruitAmount);
        return fruitAmount;
    }
}
