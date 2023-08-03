package core.basesyntax.service.impl;

import core.basesyntax.model.Operation;
import core.basesyntax.service.interfaces.TransactionStrategy;
import core.basesyntax.service.transaction.TransactionHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionStrategyImplTest {
    private TransactionStrategy transactionStrategy;

    @BeforeEach
    void setUp() {
        transactionStrategy = new TransactionStrategyImpl();
    }

    @Test
    void getHandler_OK() {
        TransactionHandler returnHandler = TransactionStrategyImpl.handlers.get(Operation.RETURN);
        TransactionHandler supplyHandler = TransactionStrategyImpl.handlers.get(Operation.SUPPLY);
        TransactionHandler purchaseHandler = TransactionStrategyImpl.handlers
                .get(Operation.PURCHASE);
        Assertions.assertEquals(returnHandler, transactionStrategy.get(Operation.RETURN));
        Assertions.assertEquals(supplyHandler, transactionStrategy.get(Operation.SUPPLY));
        Assertions.assertEquals(purchaseHandler, transactionStrategy.get(Operation.PURCHASE));
        TransactionHandler balanceHandler = TransactionStrategyImpl.handlers.get(Operation.BALANCE);
        Assertions.assertEquals(balanceHandler, transactionStrategy.get(Operation.BALANCE));
    }
}
