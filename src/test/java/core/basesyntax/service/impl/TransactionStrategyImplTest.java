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
    void getReturnHandler_OK() {
        TransactionHandler returnHandler =
                TransactionStrategyImpl.handlers.get(FruitShopOperation.RETURN);
        Assertions.assertEquals(returnHandler,
                transactionStrategy.get(FruitShopOperation.RETURN));
    }

    @Test
    void getSupplyHandler_OK() {
        TransactionHandler supplyHandler =
                TransactionStrategyImpl.handlers.get(FruitShopOperation.SUPPLY);
        Assertions.assertEquals(supplyHandler,
                transactionStrategy.get(FruitShopOperation.SUPPLY));
    }

    @Test
    void getPurchaseHandler_OK() {
        TransactionHandler purchaseHandler =
                TransactionStrategyImpl.handlers.get(FruitShopOperation.PURCHASE);
        Assertions.assertEquals(purchaseHandler,
                transactionStrategy.get(FruitShopOperation.PURCHASE));
    }

    @Test
    void getBalanceHandler_OK() {
        TransactionHandler balanceHandler =
                TransactionStrategyImpl.handlers.get(FruitShopOperation.BALANCE);
        Assertions.assertEquals(balanceHandler,
                transactionStrategy.get(FruitShopOperation.BALANCE));
    }
}

