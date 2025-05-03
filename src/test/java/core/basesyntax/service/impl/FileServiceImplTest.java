package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileService;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.strategy.StoreOperationsStrategy;
import core.basesyntax.strategy.impl.StoreOperationsStrategyImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileServiceImplTest {
    public static final String PATH_TO_REPORT_FILE = "src/test/resources/daily_report.csv";
    private static final String PATH_TO_GIVEN_DATA_FILE = "src/test/resources/daily_activities.csv";
    private static final String WRONG_PATH_TO_FILE = "1src/WRONG_PATH_TO_FILE";
    private static final FruitDao storageDao = new FruitDaoImpl(Storage.getStorageOfFruits());
    private static final FileReader reader = new FileReaderImpl();
    private static final FileWriter writer = new DataWriterToFile();
    private static final Map<Operation, OperationHandler> operationDataHandlerMap = new HashMap<>();
    private static FileService fileService;
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        operationDataHandlerMap.put(Operation.BALANCE, new InitAmountHandler());
        operationDataHandlerMap.put(Operation.SUPPLY, new IncreaseAmountHandler());
        operationDataHandlerMap.put(Operation.RETURN, new IncreaseAmountHandler());
        operationDataHandlerMap.put(Operation.PURCHASE, new DecreaseAmountHandler());
        StoreOperationsStrategy activitiesStrategy
                = new StoreOperationsStrategyImpl(operationDataHandlerMap);
        fileService = new FileServiceImpl(reader, writer,
                activitiesStrategy, storageDao);
    }

    @Before
    public void setUp() {
        try {
            Files.write(Path.of(PATH_TO_REPORT_FILE), new byte[]{});
        } catch (IOException e) {
            throw new RuntimeException("Can't read file from path:" + PATH_TO_REPORT_FILE);
        }
    }

    @Test
    public void processFiles_validData_ok() throws IOException {
        List<String> actualEmptyFile = Files.readAllLines(Path.of(PATH_TO_REPORT_FILE));
        List<String> expectedEmptyFile = new ArrayList<>();
        assertNotNull(actualEmptyFile);
        assertEquals(expectedEmptyFile, actualEmptyFile);
        fileService.processFiles(PATH_TO_GIVEN_DATA_FILE, PATH_TO_REPORT_FILE);
        List<String> actualFileAfterProcessing = Files.readAllLines(Path.of(PATH_TO_REPORT_FILE));
        List<String> expectedFileAfterProcessing = List.of("fruit,quantity", "cherry,60", "banana,"
                + "152", "apple,90");
        assertNotNull(actualFileAfterProcessing);
        assertEquals(expectedFileAfterProcessing, actualFileAfterProcessing);
    }

    @Test
    public void processFiles_wrongPathToGivenFile_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Cannot read file by path: 1src/WRONG_PATH_TO_FILE");
        fileService.processFiles(WRONG_PATH_TO_FILE, PATH_TO_REPORT_FILE);
    }

    @Test
    public void processFiles_wrongPathToReportFile_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Cannot write to file by path: 1src/WRONG_PATH_TO_FILE");
        fileService.processFiles(PATH_TO_GIVEN_DATA_FILE, WRONG_PATH_TO_FILE);
    }
}
