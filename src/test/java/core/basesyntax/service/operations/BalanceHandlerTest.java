package core.basesyntax.service.operations;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class BalanceHandlerTest {

    @Before
    public void before() {
        Storage.getFruits().clear();

    }

    @Test
    public void handle_correctNumberOfFruits_ok() {
        FruitDao fruitDao = new FruitDaoImpl();
        OperationHandler balanceHandler = new BalanceHandler(fruitDao);
        FruitTransaction peachTransaction = new FruitTransaction(Operation.BALANCE, "peach",20);
        FruitTransaction appleTransaction = new FruitTransaction(Operation.BALANCE, "apple",10);
        FruitTransaction bananaTransaction = new FruitTransaction(Operation.BALANCE, "banana",20);
        FruitTransaction orangeTransaction = new FruitTransaction(Operation.BALANCE, "orange",20);
        balanceHandler.handle(peachTransaction);
        balanceHandler.handle(appleTransaction);
        balanceHandler.handle(bananaTransaction);
        balanceHandler.handle(orangeTransaction);

        assertEquals(4, Storage.getFruits().size());
    }

    @Test
    public void handle_correctQuantityOfFruits_ok() {
        FruitDao fruitDao = new FruitDaoImpl();
        OperationHandler balanceHandler = new BalanceHandler(fruitDao);
        FruitTransaction peachTransaction = new FruitTransaction(Operation.BALANCE, "peach",20);
        FruitTransaction appleTransaction = new FruitTransaction(Operation.BALANCE, "apple",10);
        FruitTransaction bananaTransaction = new FruitTransaction(Operation.BALANCE, "banana",20);
        FruitTransaction orangeTransaction = new FruitTransaction(Operation.BALANCE, "orange",20);

        balanceHandler.handle(peachTransaction);
        balanceHandler.handle(appleTransaction);
        balanceHandler.handle(bananaTransaction);
        balanceHandler.handle(orangeTransaction);

        assertEquals(peachTransaction.getQuantity(), Storage.getFruits().get(peachTransaction.getFruitName()));
        assertEquals(appleTransaction.getQuantity(),Storage.getFruits().get(appleTransaction.getFruitName()));
        assertEquals(bananaTransaction.getQuantity(),Storage.getFruits().get(bananaTransaction.getFruitName()));
        assertEquals(orangeTransaction.getQuantity(),Storage.getFruits().get(orangeTransaction.getFruitName()));
    }
}