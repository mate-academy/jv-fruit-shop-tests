package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.fruitshop.db.Storage;
import core.basesyntax.fruitshop.model.FruitTransaction;
import core.basesyntax.fruitshop.strategy.impl.BalanceHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private final BalanceHandler balanceHandler = new BalanceHandler();
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = Storage.getInstance();
        storage.getFruitStorage().clear();
    }

    @Test
    void handleBalanceOperation() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 20);
        balanceHandler.handle(transaction);
        assertEquals(20, storage.getFruitStorage().get("apple").intValue(),
                "Balance operation should update storage");
    }
}
