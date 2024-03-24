package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.NotEnoughFruitsException;
import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.strategy.operations.BalanceStrategy;
import core.basesyntax.service.strategy.operations.PurchaseStrategy;
import core.basesyntax.service.strategy.operations.ReturnStrategy;
import core.basesyntax.service.strategy.operations.SupplyStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrategyOperationsTest {
    private static final String FRUIT_NAME = "apple";
    private static final int QUANTITY = 10;
    private static final int NEGATIVE_QUANTITY = -10;
    private static FruitTransaction fruitTransaction;
    private OperationHandler operationHandler;

    @BeforeAll
    static void initAll() {
        fruitTransaction = new FruitTransaction();
    }

    @BeforeEach
    void setUp() {
        Storage.fruitStorage.clear();

        fruitTransaction.setFruit(FRUIT_NAME);
        fruitTransaction.setOperation(null);
        fruitTransaction.setQuantity(QUANTITY);
    }

    @Test
    void handleBalance_updateDB_Ok() {
        operationHandler = new BalanceStrategy();
        int result = operationHandler.handle(fruitTransaction);
        assertEquals(Storage.fruitStorage.get(fruitTransaction.getFruit()), result,
                "Added fruit count, and result in DB must be the same");
    }

    @Test
    void handleBalance_nullData_notOk() {
        operationHandler = new BalanceStrategy();
        assertThrows(NullDataException.class,
                () -> operationHandler.handle(null),
                "Fruit transaction can't be null");
    }

    @Test
    void handleBalance_negativeData_notOk() {
        operationHandler = new BalanceStrategy();
        fruitTransaction.setQuantity(NEGATIVE_QUANTITY);

        assertThrows(NotEnoughFruitsException.class,
                () -> operationHandler.handle(fruitTransaction),
                "Can't process negative amount of fruits");
    }

    @Test
    void handleReturn_updateDB_Ok() {
        operationHandler = new ReturnStrategy();
        int result = operationHandler.handle(fruitTransaction);
        assertEquals(Storage.fruitStorage.get(fruitTransaction.getFruit()), result,
                "Added fruit count, and result in DB must be the same");
    }

    @Test
    void handleReturn_nullData_notOk() {
        operationHandler = new ReturnStrategy();
        assertThrows(NullDataException.class,
                () -> operationHandler.handle(null),
                "Fruit transaction can't be null");
    }

    @Test
    void handleReturn_negativeData_notOk() {
        operationHandler = new ReturnStrategy();
        fruitTransaction.setQuantity(NEGATIVE_QUANTITY);

        assertThrows(NotEnoughFruitsException.class,
                () -> operationHandler.handle(fruitTransaction),
                "Can't process negative amount of fruits");
    }

    @Test
    void handlePurchase_updateDB_Ok() {
        operationHandler = new PurchaseStrategy();
        Storage.fruitStorage.put(fruitTransaction.getFruit(), QUANTITY);
        int result = operationHandler.handle(fruitTransaction);
        assertEquals(Storage.fruitStorage.get(fruitTransaction.getFruit()), result,
                "Updated fruit count, and result in DB must be the same");
    }

    @Test
    void handlePurchase_purchaseMoreFruitsThanInDB_notOk() {
        operationHandler = new PurchaseStrategy();
        assertThrows(NotEnoughFruitsException.class,
                () -> operationHandler.handle(fruitTransaction),
                "Can't process negative amount of fruits");
    }

    @Test
    void handlePurchase_negativeData_notOk() {
        operationHandler = new PurchaseStrategy();
        fruitTransaction.setQuantity(NEGATIVE_QUANTITY);

        assertThrows(NotEnoughFruitsException.class,
                () -> operationHandler.handle(fruitTransaction),
                "Can't process negative amount of fruits");
    }

    @Test
    void handlePurchase_nullData_notOk() {
        operationHandler = new PurchaseStrategy();
        assertThrows(NullDataException.class,
                () -> operationHandler.handle(null),
                "Fruit transaction can't be null");
    }

    @Test
    void handleSupply_nullData_notOk() {
        operationHandler = new SupplyStrategy();
        assertThrows(NullDataException.class,
                () -> operationHandler.handle(null),
                "Fruit transaction can't be null");
    }

    @Test
    void handleSupply_updateDB_Ok() {
        operationHandler = new SupplyStrategy();
        int result = operationHandler.handle(fruitTransaction);
        assertEquals(Storage.fruitStorage.get(fruitTransaction.getFruit()), result,
                "Updated fruit count, and result in DB must be the same");
    }

    @Test
    void handleSupply_negativeData_notOk() {
        operationHandler = new SupplyStrategy();
        fruitTransaction.setQuantity(NEGATIVE_QUANTITY);

        assertThrows(NotEnoughFruitsException.class,
                () -> operationHandler.handle(fruitTransaction),
                "Can't process negative amount of fruits");
    }
}
