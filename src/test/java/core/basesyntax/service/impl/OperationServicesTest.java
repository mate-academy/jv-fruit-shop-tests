package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitDB;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationService;
import core.basesyntax.service.impl.strategy.BalanceOperationServiceImpl;
import core.basesyntax.service.impl.strategy.PurchaseOperationServiceImpl;
import core.basesyntax.service.impl.strategy.ReturnOperationServiceImpl;
import core.basesyntax.service.impl.strategy.SupplyOperationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationServicesTest {
    private static OperationService balanceService;
    private static OperationService purchaseService;
    private static OperationService returnService;
    private static OperationService supplyService;
    private static FruitTransaction transaction;
    private static final String FRUIT_TYPE = "banana";
    private static final int FRUIT_QUANTITY = 40;

    @BeforeAll
    static void beforeAll() {
        balanceService = new BalanceOperationServiceImpl();
        purchaseService = new PurchaseOperationServiceImpl();
        returnService = new ReturnOperationServiceImpl();
        supplyService = new SupplyOperationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        FruitDB.getFruitDataBase().clear();
    }

    @Test
    void processOperation_negativeQuantity_NotOk() {
        transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE.getCode(),
                FRUIT_TYPE, -FRUIT_QUANTITY);
        assertThrows(RuntimeException.class, () -> balanceService.processOperation(transaction));
    }

    @Test
    void processOperation_nullType_NotOk() {
        transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE.getCode(),
                null, FRUIT_QUANTITY);
        assertThrows(RuntimeException.class, () -> balanceService.processOperation(transaction));
    }

    @Test
    void processOperation_balance_Ok() {
        transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE.getCode(),
                FRUIT_TYPE, FRUIT_QUANTITY);
        balanceService.processOperation(transaction);
        assertEquals(FRUIT_QUANTITY, FruitDB.getFruitDataBase().get(FRUIT_TYPE));
    }

    @Test
    void processOperation_purchase_Ok() {
        int quantityPurchased = 12;
        FruitDB.getFruitDataBase().put(FRUIT_TYPE, FRUIT_QUANTITY);
        transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE.getCode(),
                FRUIT_TYPE, quantityPurchased);

        purchaseService.processOperation(transaction);

        assertEquals(FRUIT_QUANTITY - quantityPurchased,
                FruitDB.getFruitDataBase().get(FRUIT_TYPE));
    }

    @Test
    void processOperation_purchaseMoreThanStored_NotOk() {
        int quantityPurchased = 100;
        FruitDB.getFruitDataBase().put(FRUIT_TYPE, FRUIT_QUANTITY);
        transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE.getCode(),
                FRUIT_TYPE, quantityPurchased);

        assertThrows(RuntimeException.class, () -> purchaseService.processOperation(transaction));
    }

    @Test
    void processOperation_return_Ok() {
        int quantityReturned = 20;
        FruitDB.getFruitDataBase().put(FRUIT_TYPE, FRUIT_QUANTITY);
        transaction = new FruitTransaction(FruitTransaction.Operation.RETURN.getCode(),
                FRUIT_TYPE, quantityReturned);

        returnService.processOperation(transaction);

        assertEquals(FRUIT_QUANTITY + quantityReturned,
                FruitDB.getFruitDataBase().get(FRUIT_TYPE));
    }

    @Test
    void processOperation_returnUnknownFruit_notOk() {
        String unknownFruitType = "potato";
        FruitDB.getFruitDataBase().put(FRUIT_TYPE, FRUIT_QUANTITY);
        transaction = new FruitTransaction(FruitTransaction.Operation.RETURN.getCode(),
                unknownFruitType, FRUIT_QUANTITY);

        assertThrows(RuntimeException.class, () -> returnService.processOperation(transaction));
    }

    @Test
    void processOperation_supply_Ok() {
        int supplyFruit = 120;
        transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY.getCode(),
                FRUIT_TYPE, supplyFruit);

        supplyService.processOperation(transaction);

        assertEquals(supplyFruit, FruitDB.getFruitDataBase().get(FRUIT_TYPE));
    }
}
