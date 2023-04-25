package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseOperationHandler;
    private static FruitTransaction bananaPurchaseOperation;

    @BeforeAll
    static void beforeAll() {
        purchaseOperationHandler = new PurchaseOperationHandler();
        bananaPurchaseOperation = new FruitTransaction();
    }

    @BeforeEach
    void setUp() {
        bananaPurchaseOperation.setOperation(FruitTransaction.Operation.getCode("p"));
        bananaPurchaseOperation.setFruit("banana");
        bananaPurchaseOperation.setAmount(70);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitData.clear();
    }

    @Test
    void execute_firstOperationOfFruitTransaction_NotOk() {
        assertThrows(RuntimeException.class, () ->
                purchaseOperationHandler.process(bananaPurchaseOperation));
    }

    @Test
    void execute_PurchaseValueMoreThanBalanceValue_NotOk() {
        bananaPurchaseOperation.setAmount(150);
        Storage.fruitData.put("banana",100);

        assertThrows(RuntimeException.class, () ->
                purchaseOperationHandler.process(bananaPurchaseOperation));
    }

    @Test
    void execute_correctPurchaseOperation_Ok() {
        Storage.fruitData.put("banana",100);
        purchaseOperationHandler.process(bananaPurchaseOperation);
        assertEquals(30,Storage.fruitData
                .get(bananaPurchaseOperation.getFruit()));
    }
}
