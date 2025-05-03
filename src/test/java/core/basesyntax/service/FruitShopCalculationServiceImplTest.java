package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitShopStorage;
import core.basesyntax.model.AbstractTransaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.impl.FruitShopCalculationServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitShopCalculationServiceImplTest {
    private static FruitShopCalculationServiceImpl service;

    @BeforeAll
    static void setUp() {
        service = new FruitShopCalculationServiceImpl(new FruitShopStorage());
    }

    @Test
    void calculateTransactionWithValidInput() {
        List<AbstractTransaction<Fruit>> abstractTransactions = List.of(
                new FruitTransaction(Fruit.BANANA, 10, OperationType.BALANCE),
                new FruitTransaction(Fruit.APPLE, 5, OperationType.BALANCE));

        FruitShopStorage storageOutput = service.calculate(abstractTransactions);

        assertEquals(10, storageOutput.getAmount(Fruit.BANANA),
                "Banana quantity must be 10 in the storage");
        assertEquals(5, storageOutput.getAmount(Fruit.APPLE),
                "Apple quantity must be 5 less in the storage");
    }

    @Test
    void calculateWithInvalidOperationTypeExpectException() {
        List<AbstractTransaction<Fruit>> abstractTransactions = List.of(
                new FruitTransaction(Fruit.BANANA, 10, OperationType.PURCHASE),
                new FruitTransaction(Fruit.APPLE, 5, null));

        assertThrows(RuntimeException.class, () -> {
            service.calculate(abstractTransactions);
        });
    }

    @Test
    void calculateTransactionWithNullListExpectException() {
        assertThrows(NullPointerException.class, () -> {
            service.calculate(null);
        }, "Transaction list with null should throw NullPointerException");
    }
}
