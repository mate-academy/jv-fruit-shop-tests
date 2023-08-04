package core.basesyntax.service.impl;

import core.basesyntax.model.FruitShopOperation;
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
        TransactionHandler returnHandler =
                TransactionStrategyImpl.handlers.get(FruitShopOperation.RETURN);
        TransactionHandler supplyHandler =
                TransactionStrategyImpl.handlers.get(FruitShopOperation.SUPPLY);
        TransactionHandler purchaseHandler =
                TransactionStrategyImpl.handlers.get(FruitShopOperation.PURCHASE);
        Assertions.assertEquals(
                returnHandler, transactionStrategy.get(FruitShopOperation.RETURN));
        Assertions.assertEquals(
                supplyHandler, transactionStrategy.get(FruitShopOperation.SUPPLY));
        Assertions.assertEquals(
                purchaseHandler, transactionStrategy.get(FruitShopOperation.PURCHASE));
        TransactionHandler balanceHandler =
                TransactionStrategyImpl.handlers.get(FruitShopOperation.BALANCE);
        Assertions.assertEquals(
                balanceHandler, transactionStrategy.get(FruitShopOperation.BALANCE));
    }
}
