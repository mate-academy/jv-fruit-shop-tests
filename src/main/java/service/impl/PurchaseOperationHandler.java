package service.impl;

import db.Storage;
import model.FruitTransaction;
import service.OperationHandler;

public class PurchaseOperationHandler implements OperationHandler {
    private final FruitBalanceCheckService fruitBalanceCheckService
            = new FruitBalanceCheckService();

    @Override
    public void handleTransaction(FruitTransaction transaction) {
        final String fruit = transaction.getFruit();
        final int newBalance = Storage.STORAGE.get(fruit) - transaction.getQuantity();
        fruitBalanceCheckService.checkNegativeBalance(newBalance, fruit);
        Storage.STORAGE.put(fruit, newBalance);
    }
}
