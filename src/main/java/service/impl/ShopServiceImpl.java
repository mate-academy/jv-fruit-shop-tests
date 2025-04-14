package service.impl;

import db.Storage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import service.ShopService;
import strategy.BalanceImpl;
import strategy.Operation;
import strategy.PurchaseImpl;
import strategy.ReturnImpl;
import strategy.SupplyImpl;

public class ShopServiceImpl implements ShopService {
    private final Map<FruitTransaction.Operation, Operation> operationMap;

    public ShopServiceImpl(Map<FruitTransaction.Operation, Operation> operationMap) {
        this.operationMap = operationMap;
    }

    public ShopServiceImpl() {
        operationMap = new HashMap<>();
        operationMap.put(FruitTransaction.Operation.BALANCE, new BalanceImpl());
        operationMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseImpl());
        operationMap.put(FruitTransaction.Operation.SUPPLY, new SupplyImpl());
        operationMap.put(FruitTransaction.Operation.RETURN, new ReturnImpl());
    }

    @Override
    public void processTransactions(List<FruitTransaction> transactions) {
        Storage.storage.clear();
        for (FruitTransaction transaction : transactions) {
            Operation handler = operationMap.get(transaction.getOperationType());
            if (handler != null) {
                handler.execute(transaction);
            } else {
                throw new RuntimeException("No handler for operation: " + transaction
                        .getOperationType());
            }
        }
    }
}
