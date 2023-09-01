package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.IllegalQuantityException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private static BalanceHandler balanceHandler;
    private static ReturnHandler returnHandler;
    private static final Map<String, Integer> RESULT_MAP = Map.of("apple", 20);

    @BeforeEach
    void init() {
        balanceHandler = new BalanceHandler();
        returnHandler = new ReturnHandler();
        FruitTransaction fruitTransactionBalance
                = new FruitTransaction(Operation.BALANCE, "apple", 10);

        Storage.fruitStorage.clear();
        balanceHandler.handleOperation(fruitTransactionBalance);
    }

    @Test
    void handleOperation_normalValue_Ok() {
        FruitTransaction fruitTransactionReturn
                = new FruitTransaction(Operation.RETURN, "apple", 10);

        returnHandler.handleOperation(fruitTransactionReturn);

        Assertions.assertEquals(Storage.fruitStorage, RESULT_MAP);
    }

    @Test
    void handleOperation_negativeValue_Ok() {
        FruitTransaction fruitTransactionReturn
                = new FruitTransaction(Operation.RETURN, "apple", -10);

        Assertions.assertThrows(IllegalQuantityException.class,
                () -> returnHandler.handleOperation(fruitTransactionReturn));
    }
}
