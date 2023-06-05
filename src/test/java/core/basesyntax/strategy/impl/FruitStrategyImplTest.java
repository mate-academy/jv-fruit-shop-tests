package core.basesyntax.strategy.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;

import core.basesyntax.strategy.FruitStrategy;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitStrategyImplTest {
    private static FruitStrategy fruitStrategy;

    @BeforeAll
    static void beforeAll() {
        fruitStrategy = new FruitStrategyImpl(Map.of(
                BALANCE, new BalanceOperation(),
                RETURN, new ReturnOperation(),
                PURCHASE, new PurchaseOperation(),
                SUPPLY, new SupplyOperation()
        ));
    }

    @Test
    void get_balanceOperation_ok() {
        String expected = String.valueOf(BalanceOperation.class);
        String actual = String.valueOf(fruitStrategy
                .get(BALANCE)
                .getClass());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void get_purchaseOperation_ok() {
        String expected = String.valueOf(PurchaseOperation.class);
        String actual = String.valueOf(fruitStrategy
                .get(PURCHASE)
                .getClass());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void get_returnOperation_ok() {
        String expected = String.valueOf(ReturnOperation.class);
        String actual = String.valueOf(fruitStrategy
                .get(RETURN)
                .getClass());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void get_supplyOperation_ok() {
        String expected = String.valueOf(SupplyOperation.class);
        String actual = String.valueOf(fruitStrategy
                .get(SUPPLY)
                .getClass());
        Assertions.assertEquals(expected, actual);
    }
}
