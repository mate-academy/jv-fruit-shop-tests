package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.handlers.BalanceHandler;
import core.basesyntax.service.strategy.handlers.ReturnHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class HandleTransactionStrategyImplTest {

    private static HandleTransactionStrategyImpl handleTransactionStrategy;

    @BeforeAll
    static void beforeAll() {
        handleTransactionStrategy = new HandleTransactionStrategyImpl();
        Storage.fruitsMap.clear();
    }

    @Test
    void get_correctOperation_ok() {
        FruitTransaction.Operation balanceOperation = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation returnOperation = FruitTransaction.Operation.RETURN;
        Class expectedBalance = new BalanceHandler().getClass();
        Class expectedReturn = new ReturnHandler().getClass();
        Class actualBalance = handleTransactionStrategy.get(balanceOperation).getClass();
        Class actualReturn = handleTransactionStrategy.get(returnOperation).getClass();
        assertEquals(expectedBalance, actualBalance);
        assertEquals(expectedReturn, actualReturn);
    }
}

