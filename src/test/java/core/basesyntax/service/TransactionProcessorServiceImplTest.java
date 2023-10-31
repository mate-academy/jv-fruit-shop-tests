package core.basesyntax.service;

import core.basesyntax.Constants;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.TransactionProcessorServiceImpl;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TransactionProcessorServiceImplTest {
    private static final int PURCHASE_BALANCE = 100;
    private static final int ORANGE_QUANTITY = 40;
    private static final int ORANGE_NOT_ENOUGH_QUANTITY = 60;
    private static final int ORANGE_BOUGHT_QUANTITY = 30;
    private static final int ORANGE_LEFT_QUANTITY = 10;
    private ProcessorService processorService;
    private FruitStorage fruitStorage;

    @Before
    public void setUp() {
        processorService = new TransactionProcessorServiceImpl();
        fruitStorage = new FruitStorage();
    }

    @Test
    public void processBalanceTransaction_ValidState_QuantityUpdated() {
        // Arrange
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                Constants.APPLE, PURCHASE_BALANCE);

        // Act
        processorService.processTransactions(Collections.singletonList(transaction), fruitStorage);

        // Assert
        Assert.assertEquals(PURCHASE_BALANCE,
                fruitStorage
                        .getFruitQuantities()
                        .get(Constants.APPLE)
                        .intValue());
    }

    @Test
    public void processSupplyTransaction_ValidState_QuantityUpdated() {
        // Arrange
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                Constants.BANANA,
                PURCHASE_BALANCE);

        // Act
        processorService.processTransactions(Collections.singletonList(transaction), fruitStorage);

        // Assert
        Assert.assertEquals(PURCHASE_BALANCE,
                fruitStorage
                        .getFruitQuantities()
                        .get(Constants.BANANA)
                        .intValue());
    }

    @Test
    public void processPurchaseTransaction_EnoughQuantity_QuantityUpdated() {
        // Arrange
        fruitStorage.updateQuantity(Constants.ORANGE, ORANGE_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                Constants.ORANGE,
                ORANGE_BOUGHT_QUANTITY);

        // Act
        processorService.processTransactions(Collections.singletonList(transaction), fruitStorage);

        // Assert
        Assert.assertEquals(ORANGE_LEFT_QUANTITY,
                fruitStorage
                        .getFruitQuantities()
                        .get(Constants.ORANGE)
                        .intValue());
    }

    @Test
    public void processPurchaseTransaction_NotEnoughQuantity_RuntimeExceptionThrown() {
        // Arrange
        fruitStorage.updateQuantity(Constants.ORANGE, ORANGE_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                Constants.ORANGE,
                ORANGE_NOT_ENOUGH_QUANTITY);

        // Act & Assert
        Assert.assertThrows(RuntimeException.class, () -> {
            processorService
                    .processTransactions(Collections.singletonList(transaction),
                            fruitStorage);
        });
    }

    @Test
    public void processReturnTransaction_ValidState_QuantityUpdated() {
        // Arrange
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                Constants.GRAPES,
                PURCHASE_BALANCE);

        // Act
        processorService.processTransactions(Collections.singletonList(transaction), fruitStorage);

        // Assert
        Assert.assertEquals(PURCHASE_BALANCE,
                fruitStorage
                        .getFruitQuantities()
                        .get(Constants.GRAPES)
                        .intValue());
    }
}
