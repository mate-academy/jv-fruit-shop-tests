package core.basesyntax.strategy.handler;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;

public class BalanceOperation implements OperationHandler {
    @Override
    public void execute(FruitTransaction transaction, Map<String, Integer> fruits) {
        if (transaction == null) {
            throw new RuntimeException("Illegal transaction");
        }
        if (!transaction.getOperation().equals(FruitTransaction.Operation.BALANCE)) {
            throw new RuntimeException("Wrong operation type! Expected ");
        }
        fruits.put(transaction.getName(), transaction.getQuantity());
    }
}
