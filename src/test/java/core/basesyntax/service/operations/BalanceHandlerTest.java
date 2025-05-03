package core.basesyntax.service.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static FruitDao fruitDao;
    private static OperationHandler balanceHandler;

    @BeforeClass
    public static void beforeClass() {
        Storage.getFruits().clear();
        fruitDao = new FruitDaoImpl();
        balanceHandler = new BalanceHandler(fruitDao);
    }

    @Test
    public void handle_correctNumberOfFruits_ok() {
        FruitTransaction peachTransaction = new FruitTransaction(Operation.BALANCE, "peach", 20);
        FruitTransaction appleTransaction = new FruitTransaction(Operation.BALANCE, "apple", 10);
        FruitTransaction bananaTransaction = new FruitTransaction(Operation.BALANCE, "banana", 30);
        FruitTransaction orangeTransaction = new FruitTransaction(Operation.BALANCE, "orange", 0);
        balanceHandler.handle(peachTransaction);
        balanceHandler.handle(appleTransaction);
        balanceHandler.handle(bananaTransaction);
        balanceHandler.handle(orangeTransaction);
        Integer expectedStorageSize = 4;
        Integer actualStorageSize = Storage.getFruits().size();
        assertEquals(expectedStorageSize, actualStorageSize);
    }

    @Test
    public void handle_correctQuantityOfFruits_ok() {
        FruitTransaction peachTransaction = new FruitTransaction(Operation.BALANCE, "peach", 20);
        FruitTransaction orangeTransaction = new FruitTransaction(Operation.BALANCE, "orange", 0);

        balanceHandler.handle(peachTransaction);
        balanceHandler.handle(orangeTransaction);

        Integer expectedPeachQuantity = peachTransaction.getQuantity();
        Integer actualPeachQuantity = Storage.getFruits().get(peachTransaction.getFruitName());
        assertEquals(expectedPeachQuantity, actualPeachQuantity);

        Integer expectedOrangeQuantity = orangeTransaction.getQuantity();
        Integer actualOrangeQuantity = Storage.getFruits().get(orangeTransaction.getFruitName());
        assertEquals(expectedOrangeQuantity, actualOrangeQuantity);
    }
}
