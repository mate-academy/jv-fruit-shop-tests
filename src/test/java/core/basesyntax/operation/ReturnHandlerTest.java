package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.storage.FruitStorageImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.transaction.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private static final String APPLE = "apple";
    private static final int INITIAL_QUANTITY = 100;
    private FruitStorage fruitStorage;
    private ShopService shopService;
    private ReturnHandler returnHandler;
    private FruitTransaction fruitTransaction;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        fruitStorage = new FruitStorageImpl();
        shopService = new ShopServiceImpl(operationStrategy);
        returnHandler = new ReturnHandler(fruitStorage);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                APPLE, INITIAL_QUANTITY);
    }

    @Test
    void apply_returnTransaction_success() {
        returnHandler.apply(fruitTransaction);
        assertEquals(INITIAL_QUANTITY, fruitStorage.getQuantity(APPLE));
    }

}
