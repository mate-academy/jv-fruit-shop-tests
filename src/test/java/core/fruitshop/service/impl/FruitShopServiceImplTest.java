package core.fruitshop.service.impl;

import static org.junit.Assert.assertEquals;
import core.fruitshop.dao.FruitDao;
import core.fruitshop.dao.FruitDaoImpl;
import core.fruitshop.db.Storage;
import core.fruitshop.model.FruitTransaction.Operation;
import core.fruitshop.service.FruitShopService;
import core.fruitshop.service.strategy.OperationHandler;
import core.fruitshop.service.strategy.impl.BalanceOperationHandler;
import core.fruitshop.service.strategy.impl.OperationStrategyImpl;
import core.fruitshop.service.strategy.impl.PurchaseOperationHandler;
import core.fruitshop.service.strategy.impl.ReturnOperationHandler;
import core.fruitshop.service.strategy.impl.SupplyOperationHandler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static final String PATH_TO_SOURCE_FILE =
        "src/test/java/resources/fruitShopServiceFiles/FruitShopServiceTestSourceFile";
    private static final String PATH_TO_DEST_FILE =
        "src/test/java/resources/fruitShopServiceFiles/FruitShopServiceTestFileTo";
    private static final String PATH_TO_EXP_RESULT =
        "src/test/java/resources/fruitShopServiceFiles/ExpectedResult";
    private static final String EMPTY_FILE_PATH =
        "src/test/java/resources/EmptyFile";
    private static final String INVALID_PATH = "*/[][[313*&7652387/523|532";
    private static final Map<Operation, OperationHandler> map = new HashMap<>();
    private final FruitShopService fruitShopService = new FruitShopServiceImpl(new FileReaderImpl(),
        new DataHandlerImpl(new OperationStrategyImpl(map)), new ReportCreatorImpl(),
        new FileWriterImpl());

    @BeforeClass
    public static void beforeClass() {
        FruitDao dao = new FruitDaoImpl();
        map.put(Operation.BALANCE, new BalanceOperationHandler(dao));
        map.put(Operation.PURCHASE, new PurchaseOperationHandler(dao));
        map.put(Operation.RETURN, new ReturnOperationHandler(dao));
        map.put(Operation.SUPPLY, new SupplyOperationHandler(dao));
    }

    @Test
    public void createDayReport_allValid_ok() throws IOException {
        fruitShopService.createDayReport(PATH_TO_SOURCE_FILE, PATH_TO_DEST_FILE);
        List<String> expected = Files.readAllLines(Path.of(PATH_TO_EXP_RESULT));
        List<String> actual = Files.readAllLines(Path.of(PATH_TO_DEST_FILE));
        assertEquals(expected, actual);
    }

    @Test
    public void createDayReport_emptySourceFile_ok() throws IOException {
        fruitShopService.createDayReport(EMPTY_FILE_PATH, EMPTY_FILE_PATH);
        List<String> expected = Files.readAllLines(Path.of(EMPTY_FILE_PATH));
        List<String> actual = Files.readAllLines(Path.of(EMPTY_FILE_PATH));
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void createDayReport_invalidSourceFile_notOk() {
        fruitShopService.createDayReport(INVALID_PATH, PATH_TO_DEST_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void createDayReport_invalidDestinationFile_notOk() {
        fruitShopService.createDayReport(PATH_TO_SOURCE_FILE, INVALID_PATH);
    }

    @After
    public void tearDown() throws IOException {
        Storage.fruitsStorage.clear();
        BufferedWriter bufferedWriter =
            new BufferedWriter(new java.io.FileWriter(PATH_TO_DEST_FILE));
        bufferedWriter.write("");
        bufferedWriter.close();
        BufferedWriter bufferedWriterToEmpty =
            new BufferedWriter(new java.io.FileWriter(EMPTY_FILE_PATH));
        bufferedWriterToEmpty.write("");
        bufferedWriterToEmpty.close();
    }
}
