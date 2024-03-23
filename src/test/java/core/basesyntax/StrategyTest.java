package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.NotEnoughFruitsException;
import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.models.Operation;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.operations.BalanceStrategy;
import core.basesyntax.service.strategy.operations.PurchaseStrategy;
import core.basesyntax.service.strategy.operations.ReturnStrategy;
import core.basesyntax.service.strategy.operations.SupplyStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrategyTest {
    private static final String FRUIT_NAME = "apple";
    private static final int QUANTITY = 10;
    private static final int NEGATIVE_QUANTITY = -10;
    private static OperationStrategy operationStrategy;
    private static Map<String, OperationHandler> services;
    private FruitTransaction fruitTransaction;

    @BeforeAll
    static void initAll() {
        services = new HashMap<>();
        services.put("b", new BalanceStrategy());
        services.put("s", new SupplyStrategy());
        services.put("r", new ReturnStrategy());
        services.put("p", new PurchaseStrategy());

        operationStrategy = new OperationStrategy(services);
    }

    @BeforeEach
    void setUp() {
        Storage.fruitStorage.clear();
        fruitTransaction = new FruitTransaction();

        fruitTransaction.setFruit(FRUIT_NAME);
        fruitTransaction.setOperation(null);
        fruitTransaction.setQuantity(QUANTITY);
    }

    @Test
    void getOperation_balanceOperation_Ok() {
        OperationHandler result = operationStrategy.getOperation(Operation.BALANCE);
        assertEquals(BalanceStrategy.class, result.getClass(),
                "We need to have Balance operation.");
    }

    @Test
    void getOperation_purchaseOperation_Ok() {
        OperationHandler result = operationStrategy.getOperation(Operation.PURCHASE);
        assertEquals(PurchaseStrategy.class, result.getClass(),
                "We need to have Purchase operation.");
    }

    @Test
    void getOperation_returnOperation_Ok() {
        OperationHandler result = operationStrategy.getOperation(Operation.RETURN);
        assertEquals(ReturnStrategy.class, result.getClass(),
                "We need to have Return operation.");
    }

    @Test
    void getOperation_supplyOperation_Ok() {
        OperationHandler result = operationStrategy.getOperation(Operation.SUPPLY);
        assertEquals(SupplyStrategy.class, result.getClass(),
                "We need to have Supply operation.");
    }

    @Test
    void getOperation_nullOperation_notOk() {
        assertThrows(RuntimeException.class,
                () -> operationStrategy.getOperation(null),
                "We can't get null operation");
    }

    @Test
    void handleBalance_updateDB_Ok() {
        OperationHandler operationHandler = new BalanceStrategy();
        int result = operationHandler.handle(fruitTransaction);
        assertEquals(Storage.fruitStorage.get(fruitTransaction.getFruit()), result,
                "Added fruit count, and result in DB must be the same");
    }

    @Test
    void handleBalance_nullData_notOk() {
        OperationHandler operationHandler = new BalanceStrategy();
        assertThrows(NullDataException.class,
                () -> operationHandler.handle(null),
                "Fruit transaction can't be null");
    }

    @Test
    void handleBalance_negativeData_notOk() {
        OperationHandler operationHandler = new BalanceStrategy();
        fruitTransaction.setQuantity(NEGATIVE_QUANTITY);

        assertThrows(NotEnoughFruitsException.class,
                () -> operationHandler.handle(fruitTransaction),
                "Can't process negative amount of fruits");
    }

    @Test
    void handleReturn_updateDB_Ok() {
        OperationHandler operationHandler = new ReturnStrategy();
        int result = operationHandler.handle(fruitTransaction);
        assertEquals(Storage.fruitStorage.get(fruitTransaction.getFruit()), result,
                "Added fruit count, and result in DB must be the same");
    }

    @Test
    void handleReturn_nullData_notOk() {
        OperationHandler operationHandler = new ReturnStrategy();
        assertThrows(NullDataException.class,
                () -> operationHandler.handle(null),
                "Fruit transaction can't be null");
    }

    @Test
    void handleReturn_negativeData_notOk() {
        OperationHandler operationHandler = new ReturnStrategy();
        fruitTransaction.setQuantity(NEGATIVE_QUANTITY);

        assertThrows(NotEnoughFruitsException.class,
                () -> operationHandler.handle(fruitTransaction),
                "Can't process negative amount of fruits");
    }

    @Test
    void handlePurchase_updateDB_Ok() {
        OperationHandler operationHandler = new PurchaseStrategy();
        Storage.fruitStorage.put(fruitTransaction.getFruit(), QUANTITY);
        int result = operationHandler.handle(fruitTransaction);
        assertEquals(Storage.fruitStorage.get(fruitTransaction.getFruit()), result,
                "Updated fruit count, and result in DB must be the same");
    }

    @Test
    void handlePurchase_purchaseMoreFruitsThanInDB_notOk() {
        OperationHandler operationHandler = new PurchaseStrategy();
        assertThrows(NotEnoughFruitsException.class,
                () -> operationHandler.handle(fruitTransaction),
                "Can't process negative amount of fruits");
    }

    @Test
    void handlePurchase_negativeData_notOk() {
        OperationHandler operationHandler = new PurchaseStrategy();
        fruitTransaction.setQuantity(NEGATIVE_QUANTITY);

        assertThrows(NotEnoughFruitsException.class,
                () -> operationHandler.handle(fruitTransaction),
                "Can't process negative amount of fruits");
    }

    @Test
    void handlePurchase_nullData_notOk() {
        OperationHandler operationHandler = new PurchaseStrategy();
        assertThrows(NullDataException.class,
                () -> operationHandler.handle(null),
                "Fruit transaction can't be null");
    }

    @Test
    void handleSupply_nullData_notOk() {
        OperationHandler operationHandler = new SupplyStrategy();
        assertThrows(NullDataException.class,
                () -> operationHandler.handle(null),
                "Fruit transaction can't be null");
    }

    @Test
    void handleSupply_updateDB_Ok() {
        OperationHandler operationHandler = new SupplyStrategy();
        int result = operationHandler.handle(fruitTransaction);
        assertEquals(Storage.fruitStorage.get(fruitTransaction.getFruit()), result,
                "Updated fruit count, and result in DB must be the same");
    }

    @Test
    void handleSupply_negativeData_notOk() {
        OperationHandler operationHandler = new SupplyStrategy();
        fruitTransaction.setQuantity(NEGATIVE_QUANTITY);

        assertThrows(NotEnoughFruitsException.class,
                () -> operationHandler.handle(fruitTransaction),
                "Can't process negative amount of fruits");
    }
}
