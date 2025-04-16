package service.impl;

import db.Storage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import model.FruitTransaction.OperationType;
import service.ShopService;
import strategy.BalanceImpl;
import strategy.Operation;
import strategy.PurchaseImpl;
import strategy.ReturnImpl;
import strategy.SupplyImpl;

public class ShopServiceImpl implements ShopService {
    private final Map<OperationType, Operation> operationMap;

    public ShopServiceImpl() {
        Storage storage = new Storage();
        operationMap = new HashMap<>();
        operationMap.put(OperationType.BALANCE, new BalanceImpl(storage));
        operationMap.put(OperationType.PURCHASE, new PurchaseImpl(storage));
        operationMap.put(OperationType.SUPPLY, new SupplyImpl(storage));
        operationMap.put(OperationType.RETURN, new ReturnImpl(storage));
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
