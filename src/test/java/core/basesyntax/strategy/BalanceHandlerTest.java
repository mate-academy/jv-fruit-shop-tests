package core.basesyntax.strategy;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dbtest.StorageTest;
import core.basesyntax.dto.FruitDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.CsvFileReader;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.FruitReportService;
import core.basesyntax.service.Parser;
import core.basesyntax.service.ParserImpl;
import core.basesyntax.service.Validator;
import core.basesyntax.service.ValidatorCsv;
import core.basesyntax.service.Writer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Test;

public class BalanceHandlerTest {
    public static final String PATH_INPUT_FILE =
            "src/test/resources/balancetest/storageBalanceTest.csv";
    public static final String PATH_OUTPUT_FILE =
            "src/test/resources/balancetest/reportBalanceTest.csv";
    public static final String PATH_FILE_RESULT =
            "src/test/resources/balancetest/resultBalanceTest.csv";

    @Test
    public void balanceHandler_checkGettingObject_notOk() {
        FruitDto fruitDto = new FruitDto("s", "banana", 100);
        OperationHandler expected = new BalanceHandler(StorageTest.storage);
        OperationHandler actual = StorageTest.operationHandlerMap.get(fruitDto.getOperation());
        assertNotEquals(expected.getClass(), actual.getClass());

        fruitDto = new FruitDto("p", "banana", 100);
        actual = StorageTest.operationHandlerMap.get(fruitDto.getOperation());
        assertNotEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void balanceHandler_checkGettingObject_ok() {
        FruitDto fruitDto = new FruitDto("b", "banana", 100);
        OperationHandler expected = new BalanceHandler(StorageTest.storage);
        OperationHandler actual = StorageTest.operationHandlerMap.get(fruitDto.getOperation());
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void balanceHandler_checkOperation_ok() {
        FileReader fileReader = new CsvFileReader();
        List<String> infoFromFile = fileReader.readFromFile(PATH_INPUT_FILE);

        Validator validator = new ValidatorCsv();
        Parser parser = new ParserImpl(validator);

        infoFromFile.stream()
                .map(parser::parseToFruitDto)
                .forEach(
                        fruitDto ->
                                StorageTest.operationHandlerMap.get(fruitDto.getOperation())
                                        .apply(fruitDto));
        FruitReportService report = new FruitReportService(StorageTest.storage);
        Writer fileWriter = new FileWriter();
        fileWriter.writeToFile(report.getReport(), PATH_OUTPUT_FILE);

        try {
            assertArrayEquals(
                    Files.readAllLines(Path.of(PATH_FILE_RESULT)).toArray(),
                    Files.readAllLines(Path.of(PATH_OUTPUT_FILE)).toArray());
        } catch (IOException e) {
            throw new RuntimeException(
                    "Files are not able to be read: " + PATH_FILE_RESULT + " " + PATH_OUTPUT_FILE,
                    e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void balanceHandler_apply_NullCheck_ok() {
        OperationHandler operationHandler = new BalanceHandler(StorageTest.storage);
        operationHandler.apply(null);
    }

    @Test
    public void balanceHandler_apply_changeStorage_ok() {
        StorageTest.storage.put(new Fruit("banana"), 100);
        OperationHandler purchaseHandler = new BalanceHandler(StorageTest.storage);
        purchaseHandler.apply(new FruitDto("b", "grape", 100));
        assertTrue(StorageTest.storage.containsKey(new Fruit("grape")));
        assertEquals(100,
                StorageTest.storage.get(new Fruit("grape")).intValue());
    }

    @Test(expected = RuntimeException.class)
    public void purchaseHandler_constructor_notOk() {
        OperationHandler purchaseHandler = new BalanceHandler(null);
    }
}
