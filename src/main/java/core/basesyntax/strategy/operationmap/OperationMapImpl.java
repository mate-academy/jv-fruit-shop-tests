package core.basesyntax.strategy.operationmap;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.transactionhandlers.BalanceTransactionHandlerImpl;
import core.basesyntax.strategy.transactionhandlers.PurchaseTransactionHandlerImpl;
import core.basesyntax.strategy.transactionhandlers.ReturnTransactionHandlerImpl;
import core.basesyntax.strategy.transactionhandlers.SupplyTransactionHandlerImpl;
import core.basesyntax.strategy.transactionhandlers.TransactionHandler;
import java.util.HashMap;
import java.util.Map;

public class OperationMapImpl implements OperationMap {
    private Map<FruitTransaction.Operation, TransactionHandler> map;

    public OperationMapImpl() {
        this.map = new HashMap<>();
    }

    @Override
    public Map<FruitTransaction.Operation, TransactionHandler> getOperationMap() {
        TransactionHandler balance = new BalanceTransactionHandlerImpl();
        addOperationToMap(FruitTransaction.Operation.BALANCE,balance);
        TransactionHandler supply = new SupplyTransactionHandlerImpl();
        addOperationToMap(FruitTransaction.Operation.SUPPLY,supply);
        TransactionHandler retur = new ReturnTransactionHandlerImpl();
        addOperationToMap(FruitTransaction.Operation.RETURN,retur);
        TransactionHandler purchase = new PurchaseTransactionHandlerImpl();
        addOperationToMap(FruitTransaction.Operation.PURCHASE,purchase);
        return map;
    }

    @Override
    public void addOperationToMap(FruitTransaction.Operation operation,
                                  TransactionHandler handler) {
        map.put(operation, handler);
    }
}
