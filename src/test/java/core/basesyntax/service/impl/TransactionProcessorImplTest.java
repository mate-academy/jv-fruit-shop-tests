package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceHandlerImpl;
import core.basesyntax.strategy.impl.PurchaseHandlerImpl;
import core.basesyntax.strategy.impl.ReturnHandlerImpl;
import core.basesyntax.strategy.impl.SupplyHandlerImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionProcessorImplTest {
    private static String BANANA = "banana";
    private static String ORANGE = "orange";
    private static String PINEAPPLE = "pineapple";
    private static int BANANA_QUANTITY_ADDED = 50;
    private static int ORANGE_QUANTITY_ADDED = 100;
    private static int PINEAPPLE_QUANTITY_ADDED = 200;
    private static int BANANA_PURCHASE_QUANTITY = 20;
    private static int ORANGE_SUPPLY_QUANTITY = 40;
    private static int PINEAPPLE_RETURN_QUANTITY = 50;
    private static int BANANA_QUANTITY_EXPECTED = 30;
    private static int ORANGE_QUANTITY_EXPECTED = 140;
    private static int PINEAPPLE_QUANTITY_EXPECTED = 250;

    private static OperationStrategy operationStrategy = new OperationStrategy(Map.of(
            Operation.BALANCE, new BalanceHandlerImpl(),
            Operation.SUPPLY, new SupplyHandlerImpl(),
            Operation.PURCHASE, new PurchaseHandlerImpl(),
            Operation.RETURN, new ReturnHandlerImpl()));
    private static TransactionProcessorImpl transactionProcessor;
    private static List<FruitTransaction> fruitTransactions;
    private static FruitDao fruitDao;

    @BeforeAll
    public static void storageSetUp() {
        transactionProcessor = new TransactionProcessorImpl(operationStrategy);
        fruitDao = new FruitDaoImpl();
        fruitDao.add(BANANA,BANANA_QUANTITY_ADDED);
        fruitDao.add(ORANGE, ORANGE_QUANTITY_ADDED);
        fruitDao.add(PINEAPPLE, PINEAPPLE_QUANTITY_ADDED);

        FruitTransaction fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setOperation(Operation.PURCHASE);
        fruitTransaction1.setFruit(BANANA);
        fruitTransaction1.setQuantity(BANANA_PURCHASE_QUANTITY);
        FruitTransaction fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setOperation(Operation.SUPPLY);
        fruitTransaction2.setFruit(ORANGE);
        fruitTransaction2.setQuantity(ORANGE_SUPPLY_QUANTITY);
        FruitTransaction fruitTransaction3 = new FruitTransaction();
        fruitTransaction3.setOperation(Operation.RETURN);
        fruitTransaction3.setFruit(PINEAPPLE);
        fruitTransaction3.setQuantity(PINEAPPLE_RETURN_QUANTITY);
        fruitTransactions = List.of(
                fruitTransaction1,
                fruitTransaction2,
                fruitTransaction3
        );
    }

    @Test
    void process_ok() {
        transactionProcessor.process(fruitTransactions);
        int expectedBanana = BANANA_QUANTITY_EXPECTED;
        int expectedOrange = ORANGE_QUANTITY_EXPECTED;
        int expectedPineapple = PINEAPPLE_QUANTITY_EXPECTED;
        assertEquals(expectedBanana, Storage.fruits.get("banana"),
                "Method process wrong execute operation");
        assertEquals(expectedOrange, Storage.fruits.get("orange"),
                "Method process wrong execute operation");
        assertEquals(expectedPineapple, Storage.fruits.get("pineapple"),
                "Method process wrong execute operation");
    }

    @Test
    void process_nullList_notOk() {
        List<FruitTransaction> modifiedList = null;
        assertThrows(RuntimeException.class, () -> transactionProcessor.process(modifiedList));
    }
}
