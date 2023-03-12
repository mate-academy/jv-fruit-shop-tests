package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.FruitCalculatorImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.ReportMakerServiceImpl;
import core.basesyntax.service.impl.TransactionParserServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.File;
import java.io.IOException;
import org.junit.After;
import org.junit.BeforeClass;

public class FruitShopTest {
    protected static final String FULL_NAME_FILE = "input.csv";
    protected static File testFile;
    protected FruitCalculatorImpl fruitCalculator = new FruitCalculatorImpl();
    protected ReaderServiceImpl readerService = new ReaderServiceImpl();
    protected ReportMakerServiceImpl reportMakerService = new ReportMakerServiceImpl();
    protected TransactionParserServiceImpl transactionParser = new TransactionParserServiceImpl();
    protected WriterServiceImpl writerService = new WriterServiceImpl();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        testFile = new File(FULL_NAME_FILE);
        try {
            testFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void cleanStorageAfterEach() {
        Storage.STORAGE.clear();
    }
}
