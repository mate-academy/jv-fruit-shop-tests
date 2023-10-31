package core.basesyntax.strategy;

import core.basesyntax.Constants;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyTest {
    private static final int INITIAL_QUANTITY = 10;
    private static final int ADDED_QUANTITY = 20;
    private static final int PURCHASE_QUANTITY = 5;
    private static final int RETURN_QUANTITY = 3;
    private static final int SUPPLY_QUANTITY = 7;

    private FruitStorage fruitStorage;

    @BeforeEach
    void setUp() {
        fruitStorage = new FruitStorage();
        fruitStorage.updateQuantity(Constants.APPLE, INITIAL_QUANTITY);
    }

    @Test
    void balanceOperation_validTransaction_successful() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                Constants.APPLE,
                INITIAL_QUANTITY);
        new BalanceOperationStrategy().process(transaction, fruitStorage);
        Assertions.assertEquals(
                ADDED_QUANTITY,
                fruitStorage.getFruitQuantities().get(Constants.APPLE));
    }

    @Test
    void supplyOperation_validTransaction_successful() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                Constants.APPLE,
                SUPPLY_QUANTITY);
        new SupplyOperationStrategy().process(transaction, fruitStorage);
        Assertions.assertEquals(
                INITIAL_QUANTITY + SUPPLY_QUANTITY,
                fruitStorage.getFruitQuantities().get(Constants.APPLE));
    }

    @Test
    void purchaseOperation_enoughQuantity_successful() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                Constants.APPLE,
                PURCHASE_QUANTITY);
        new PurchaseOperationStrategy().process(transaction, fruitStorage);
        Assertions.assertEquals(
                INITIAL_QUANTITY - PURCHASE_QUANTITY,
                fruitStorage.getFruitQuantities().get(Constants.APPLE));
    }

    @Test
    void purchaseOperation_notEnoughQuantity_exceptionThrown() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                Constants.APPLE,
                INITIAL_QUANTITY + 1);
        Assertions.assertThrows(RuntimeException.class, () ->
                new PurchaseOperationStrategy()
                        .process(transaction, fruitStorage));
    }

    @Test
    void returnOperation_validTransaction_successful() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                Constants.APPLE,
                RETURN_QUANTITY);
        new ReturnOperationStrategy().process(transaction, fruitStorage);
        Assertions.assertEquals(INITIAL_QUANTITY + RETURN_QUANTITY,
                fruitStorage.getFruitQuantities().get(Constants.APPLE));
    }

    @Test
    void operationParser_validCode_correctOperation() {
        OperationParser operationParser = new OperationParser();
        Assertions.assertEquals(FruitTransaction.Operation.BALANCE,
                operationParser.parseOperationFromCode(Constants.BALANCE_SHORTHAND));
        Assertions.assertEquals(FruitTransaction.Operation.SUPPLY,
                operationParser.parseOperationFromCode(Constants.SUPPLY_SHORTHAND));
        Assertions.assertEquals(FruitTransaction.Operation.PURCHASE,
                operationParser.parseOperationFromCode(Constants.PURCHASE_SHORTHAND));
        Assertions.assertEquals(FruitTransaction.Operation.RETURN,
                operationParser.parseOperationFromCode(Constants.RETURN_SHORTHAND));
    }

    @Test
    void operationParser_invalidCode_exceptionThrown() {
        OperationParser operationParser = new OperationParser();
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                operationParser.parseOperationFromCode(Constants.INVALID_CODE));
    }
}
