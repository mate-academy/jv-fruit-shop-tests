package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.FruitShopException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.validator.QuantityValidator;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationHandlerTest {
    private static final String FRUIT_NAME = "apple";
    private static final int FRUIT_QUANTITY = 100;
    private static final int INCORRECT_FRUIT_QUANTITY = -1;

    private static FruitTransaction fruitTransaction;
    private static OperationHandler balanceOperationHandler;

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit(FRUIT_NAME);
        fruitTransaction.setQuantity(FRUIT_QUANTITY);
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @Test
    void getTransaction_nullData_notOk() {
        Assert.assertThrows(NullPointerException.class, () ->
                balanceOperationHandler.getTransaction(null));
    }

    @Test
    void getTransaction_incorrectFruitQuantity_notOk() {
        QuantityValidator quantityValidator = new QuantityValidator();
        Assert.assertThrows(FruitShopException.class, () ->
                quantityValidator.getQuantityValidation(INCORRECT_FRUIT_QUANTITY));
    }

    @Test
    void getTransaction_balanceOperation_ok() {
        balanceOperationHandler.getTransaction(fruitTransaction);
        int expected = 100;
        int actual = Storage.remainsOfFruits.get(fruitTransaction.getFruit());
        Assertions.assertEquals(expected, actual, "Expected quantity of fruit \""
                + fruitTransaction.getFruit() + "\" is " + expected
                + ", but actual is " + actual + ".");
    }

    @Test
    void getTransaction_purchaseOperation_ok() {
        balanceOperationHandler.getTransaction(fruitTransaction);
        fruitTransaction.setQuantity(20);
        OperationHandler purchaseOperationHandler = new PurchaseOperationHandler();
        purchaseOperationHandler.getTransaction(fruitTransaction);
        int expected = 80;
        int actual = Storage.remainsOfFruits.get(fruitTransaction.getFruit());
        Assertions.assertEquals(expected, actual, "Expected quantity of fruit \""
                + fruitTransaction.getFruit() + "\" is " + expected
                + ", but actual is " + actual + ".");
    }

    @Test
    void getTransaction_returnOperation_ok() {
        balanceOperationHandler.getTransaction(fruitTransaction);
        fruitTransaction.setQuantity(10);
        OperationHandler returnOperationHandler = new ReturnOperationHandler();
        returnOperationHandler.getTransaction(fruitTransaction);
        int expected = 110;
        int actual = Storage.remainsOfFruits.get(fruitTransaction.getFruit());
        Assertions.assertEquals(expected, actual, "Expected quantity of fruit \""
                + fruitTransaction.getFruit() + "\" is " + expected
                + ", but actual is " + actual + ".");
    }

    @Test
    void getTransaction_supplyOperation_ok() {
        balanceOperationHandler.getTransaction(fruitTransaction);
        fruitTransaction.setQuantity(40);
        OperationHandler supplyOperationHandler = new SupplyOperationHandler();
        supplyOperationHandler.getTransaction(fruitTransaction);
        int expected = 140;
        int actual = Storage.remainsOfFruits.get(fruitTransaction.getFruit());
        Assertions.assertEquals(expected, actual, "Expected quantity of fruit \""
                + fruitTransaction.getFruit() + "\" is " + expected
                + ", but actual is " + actual + ".");
    }

    @AfterEach
    void tearDown() {
        Storage.remainsOfFruits.clear();
    }
}
