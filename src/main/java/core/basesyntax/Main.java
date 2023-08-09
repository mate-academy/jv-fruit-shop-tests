package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.BalanceOperationHandlerImpl;
import core.basesyntax.service.operation.OperationHandler;

public class Main {
    public static void main(String[] args) {
        FruitTransaction fruitTransaction = null;
        OperationHandler handler = new BalanceOperationHandlerImpl();
        handler.handle(fruitTransaction);
    }
}
