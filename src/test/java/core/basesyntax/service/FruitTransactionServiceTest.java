package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataValidatorImpl;
import core.basesyntax.service.impl.FruitTransactionServiceImpl;
import core.basesyntax.service.impl.SplitServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FruitTransactionServiceTest {
    public static final FruitTransaction.Operation BALANCE = FruitTransaction.Operation.BALANCE;
    private static FruitTransactionDao fruitTransactionDao;
    private static SplitService splitService;
    private static FruitTransactionService fruitTransactionService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitTransactionDao = new FruitTransactionDaoImpl();
        splitService = new SplitServiceImpl(new DataValidatorImpl());
        fruitTransactionService =
                new FruitTransactionServiceImpl(fruitTransactionDao, splitService);
    }

    @Test
    public void addTransaction_ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(BALANCE, "banana", 100));
        expected.add(new FruitTransaction(BALANCE, "apple", 50));
        List<String> dataFromCsv = new ArrayList<>();
        dataFromCsv.add("type,fruit,quantity");
        dataFromCsv.add("b,banana,100");
        dataFromCsv.add("b,apple,50");
        fruitTransactionService.addTransaction(dataFromCsv);
        List<FruitTransaction> actual = Storage.fruitTransactions;
        assertEquals(expected, actual);
    }

    @Test
    public void addTransaction_empty_ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        List<String> dataFromCsv = new ArrayList<>();
        dataFromCsv.add("type,fruit,quantity");
        fruitTransactionService.addTransaction(dataFromCsv);
        List<FruitTransaction> actual = Storage.fruitTransactions;
        assertEquals(expected, actual);
    }

    @Test
    public void addTransaction_invalidData_notOk() {
        List<String> dataFromCsv = new ArrayList<>();
        dataFromCsv.add("######");
        dataFromCsv.add("######");
        exception.expect(RuntimeException.class);
        fruitTransactionService.addTransaction(dataFromCsv);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruitTransactions.clear();
    }
}
