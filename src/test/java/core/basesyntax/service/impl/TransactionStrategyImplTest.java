package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitShopOperation;
import core.basesyntax.service.interfaces.TransactionStrategy;
import core.basesyntax.service.transaction.impl.BalanceTransactionHandler;
import core.basesyntax.service.transaction.impl.PurchaseTransactionHandler;
import core.basesyntax.service.transaction.impl.ReturnTransactionHandler;
import core.basesyntax.service.transaction.impl.SupplyTransactionHandler;
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
        assertEquals(ReturnTransactionHandler.class,
                transactionStrategy.get(FruitShopOperation.RETURN).getClass());
    }

    @Test
    void getSupplyHandler_OK() {
        assertEquals(SupplyTransactionHandler.class,
                transactionStrategy.get(FruitShopOperation.SUPPLY).getClass());
    }

    @Test
    void getPurchaseHandler_OK() {
        assertEquals(PurchaseTransactionHandler.class,
                transactionStrategy.get(FruitShopOperation.PURCHASE).getClass());
    }

    @Test
    void getBalanceHandler_OK() {
        assertEquals(BalanceTransactionHandler.class,
                transactionStrategy.get(FruitShopOperation.BALANCE).getClass());
    }
}

