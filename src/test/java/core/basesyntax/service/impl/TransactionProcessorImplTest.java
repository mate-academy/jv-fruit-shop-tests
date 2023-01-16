package core.basesyntax.service.impl;

import core.basesyntax.db.FruitDao;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionProcessorImplTest {
    private static List<FruitTransaction> transactions;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void setUp() {
        transactions = new ArrayList<>();
        fruitTransaction = new FruitTransaction();
    }

    @Test
    public void process_validData_ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("marakuja");
        fruitTransaction.setQuantity(100);
        transactions.add(fruitTransaction);

    }

    @AfterClass
    public static void tearDown() {
        FruitDao.storage.clear();
    }
}
