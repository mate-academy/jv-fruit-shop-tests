package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.fruitshop.db.Storage;
import core.basesyntax.fruitshop.model.FruitTransaction;
import core.basesyntax.fruitshop.strategy.impl.ReturnHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private final ReturnHandler returnHandler = new ReturnHandler();
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = Storage.getInstance();
        storage.getFruitStorage().clear();
    }

    @Test
    void handleReturnOperation() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "apple", 15);
        returnHandler.handle(transaction);
        assertEquals(15, storage.getFruitStorage().get("apple").intValue(),
                "Return operation should increase stock");
    }
}
