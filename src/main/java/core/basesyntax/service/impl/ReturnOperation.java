package core.basesyntax.service.impl;

import core.basesyntax.service.OperationHandler;

public class ReturnOperation implements OperationHandler {
    @Override
    public int getFruitAmount(core.basesyntax.service.impl.TransactionDto transactionDto) {
        int fruitAmount = Storage.reportMap.get(transactionDto.getFruit())
                + transactionDto.getAmount();
        core.basesyntax.service.impl.Storage.reportMap.put(transactionDto.getFruit(), fruitAmount);
        return fruitAmount;
    }
}
