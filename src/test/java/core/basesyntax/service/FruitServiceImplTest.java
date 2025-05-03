package core.basesyntax.service;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaocsv;
import core.basesyntax.service.operation.BalanceHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseHandler;
import core.basesyntax.service.operation.ReturnHandler;
import core.basesyntax.service.operation.SupplyHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FruitServiceImplTest {
    private static final String TEST_INPUT_FILE = "test.csv";
    private static final String TEST_OUTPUT_CONTENT = "type,fruit,quantity"
            + System.lineSeparator()
            + "b,banana,20" + System.lineSeparator()
            + "b,apple,100" + System.lineSeparator()
            + "s,banana,100" + System.lineSeparator()
            + "p,banana,13" + System.lineSeparator()
            + "r,apple,10" + System.lineSeparator()
            + "p,apple,20" + System.lineSeparator()
            + "p,banana,5" + System.lineSeparator()
            + "s,banana,50";
    private static final String TEST_OUTPUT_FILE = "out.csv";
    private FruitService fruitService;

    @Before
    public void setUp() {
        Map<String, OperationHandler> fruitOperationMap = new HashMap<>();
        fruitOperationMap.put("b", new BalanceHandler());
        fruitOperationMap.put("s", new SupplyHandler());
        fruitOperationMap.put("p", new PurchaseHandler());
        fruitOperationMap.put("r", new ReturnHandler());
        FruitDao fruitDao = new FruitDaocsv();
        FruitStrategy fruitStrategy = new FruitStrategyImpl(fruitOperationMap);
        FruitParser fruitParser = new FruitParserImpl(fruitStrategy);
        DataValidator dataValidator = new FileValidator();
        ReportCreator reportCreator = new ReportCreatorImpl();
        ReportWriter reportWriter = new ReportWriterImpl();
        fruitService = new FruitServiceImpl(fruitDao,
                fruitParser,
                dataValidator,
                reportCreator,
                reportWriter);
        createTestFile();
    }

    @After
    public void tearDown() {
        try {
            Files.deleteIfExists(Path.of(TEST_INPUT_FILE));
            Files.deleteIfExists(Path.of(TEST_OUTPUT_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't remove test files");
        }
    }

    @Test(expected = RuntimeException.class)
    public void nullInputFile_NotOk() {
        fruitService.createReport(null, TEST_OUTPUT_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void nullOutputFile_NotOk() {
        fruitService.createReport(TEST_INPUT_FILE, null);
    }

    @Test(expected = RuntimeException.class)
    public void nullFiles_NotOk() {
        fruitService.createReport(null, null);
    }

    @Test
    public void reportExist_Ok() {
        fruitService.createReport(TEST_INPUT_FILE, TEST_OUTPUT_FILE);
    }

    private void createTestFile() {
        Path path = Path.of(TEST_INPUT_FILE);
        try {
            Files.createFile(path);
            Files.write(path, TEST_OUTPUT_CONTENT.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can't create test file", e);
        }
    }

}
