package core.basesyntax.service.filewriter;

import static org.junit.Assert.assertTrue;

import core.basesyntax.Main;
import core.basesyntax.dao.TransactionDao;
import core.basesyntax.dao.TransactionDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.interfaces.strategy.TransactionStrategy;
import core.basesyntax.service.transactions.TransactionStrategyImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String REPORT_TEST_FILE = "src/test/resources/reportTest.csv";
    private static final String WRONG_FILE_PATH = "srcff/wrong/resources/reportTest.csv";
    private static FileWriter fileWriter;
    private static final Fruit banana = new Fruit("banana");
    private static final Fruit apple = new Fruit("apple");

    @BeforeClass
    public static void beforeClass() {
        TransactionStrategy transactionStrategy =
                new TransactionStrategyImpl(Main.createOperationsMap());
        TransactionDao transactionDao = new TransactionDaoImpl(transactionStrategy);
        fileWriter = new FileWriterImpl(transactionDao);
        Storage.fruitStorage.put(banana, 10);
        Storage.fruitStorage.put(apple, 20);
    }

    @Test
    public void writeToFile_Ok() {
        boolean expected = fileWriter.writeToFile(REPORT_TEST_FILE);
        assertTrue(expected);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_Null_NotOk() {
        fileWriter.writeToFile(WRONG_FILE_PATH);
    }
}
