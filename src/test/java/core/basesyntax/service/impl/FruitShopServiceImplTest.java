package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.impl.BalanceOperationHandler;
import core.basesyntax.service.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.service.strategy.impl.ReturnOperationHandler;
import core.basesyntax.service.strategy.impl.SupplyOperationHandler;
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
    private static final String INVALID_PATH = "NO|SUCH|PATH";
    private static final Map<FruitTransaction.Operation, OperationHandler> map = new HashMap<>();
    private final FruitShopService fruitShopService = new FruitShopServiceImpl(map);

    @BeforeClass
    public static void beforeClass() {
        map.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        map.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        map.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        map.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
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
