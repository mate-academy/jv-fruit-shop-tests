package core.basesyntax.report;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.enums.Operation;
import core.basesyntax.exceptions.FileWasNotWrittenException;
import core.basesyntax.handler.TransactionHandler;
import core.basesyntax.handler.impl.BalanceTransactionHandler;
import core.basesyntax.handler.impl.PurchaseTransactionHandler;
import core.basesyntax.handler.impl.ReturnTransactionHandler;
import core.basesyntax.handler.impl.SupplyTransactionHandler;
import core.basesyntax.processing.RawDataProcessor;
import core.basesyntax.reader.CsvFileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CsvFileWriterTest {
    public static final String FRUIT_QUANTITY = "fruit,quantity";
    public static final String FRUIT_BANANA = "banana";
    public static final String FRUIT_APPLE = "apple";
    private static final String FILE_TO_READ = "src/test/resources/db/ReadTransactionTest";
    private static final String FILE_TO_WRITE = "src/test/resources/db/FileToWrite";
    private static final String NOT_EXISTENT_FILE = "";

    @Test
    void fileReader_read_ok() throws IOException {
        Map<String, Integer> mapForWritingReport = new HashMap<>();
        mapForWritingReport.put(FRUIT_APPLE, 64);
        mapForWritingReport.put(FRUIT_BANANA, 45);

        CsvFileWriter actualFileWriter = new CsvFileWriter(FILE_TO_WRITE);
        actualFileWriter.writeReport(mapForWritingReport);

        String actual = Files.readString(Paths.get(FILE_TO_WRITE));

        String expectedResult =
                FRUIT_QUANTITY
                        + System.lineSeparator()
                        + "banana,45"
                        + System.lineSeparator()
                        + "apple,64"
                        + System.lineSeparator();

        Assertions.assertEquals(expectedResult, actual);
    }

    @Test
    void testWithEmptyPath_notOk() {
        CsvFileReader fileReader = new CsvFileReader(FILE_TO_READ);
        List<List<String>> actualLists = fileReader.readTransactions();

        Map<String, TransactionHandler> map = Map.of(
                Operation.BALANCE.getCode(), new BalanceTransactionHandler(),
                Operation.SUPPLY.getCode(), new SupplyTransactionHandler(),
                Operation.PURCHASE.getCode(), new PurchaseTransactionHandler(),
                Operation.RETURN.getCode(), new ReturnTransactionHandler());

        RawDataProcessor rawDataProcessor = new RawDataProcessor(map);
        Map<String, Integer> actualProcessMap = rawDataProcessor.process(actualLists);

        CsvFileWriter actualResult = new CsvFileWriter(NOT_EXISTENT_FILE);

        assertThrows(FileWasNotWrittenException.class,
                () -> actualResult.writeReport(actualProcessMap));
    }

    @Test
    void write_null_notOk() {
        CsvFileWriter actualResult = new CsvFileWriter(NOT_EXISTENT_FILE);

        Assertions.assertThrows(RuntimeException.class,
                () -> actualResult.writeReport(null));
    }
}
