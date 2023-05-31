package com.mate.fruitshop.service.impl;

import com.mate.fruitshop.model.Transaction;
import com.mate.fruitshop.service.TransactionsProcessingService;
import com.mate.fruitshop.strategy.BalanceHandler;
import com.mate.fruitshop.strategy.OperationHandler;
import com.mate.fruitshop.strategy.PurchaseHandler;
import com.mate.fruitshop.strategy.ReturnHandler;
import com.mate.fruitshop.strategy.SupplyHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionsProcessingServiceImpl implements TransactionsProcessingService {
    private final Map<Transaction.Operation, OperationHandler> strategyMap;

    public TransactionsProcessingServiceImpl() {
        this.strategyMap = new HashMap<>();
        this.strategyMap.put(Transaction.Operation.BALANCE, new BalanceHandler());
        this.strategyMap.put(Transaction.Operation.PURCHASE, new PurchaseHandler());
        this.strategyMap.put(Transaction.Operation.SUPPLY, new SupplyHandler());
        this.strategyMap.put(Transaction.Operation.RETURN, new ReturnHandler());
    }

    @Override
    public boolean process(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            OperationHandler strategy = strategyMap.get(transaction.getOperation());
            strategy.process(transaction);
        }
        return true;
    }
}
