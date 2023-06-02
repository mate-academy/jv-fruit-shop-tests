package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.EmptyFileException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DailyReportFileReader;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DailyReportFileReaderImplTest {
    private static final Path TEST_FILE_ABSENT =
            Path.of("src/test/resources/dailyOperationTest_AbsentFile.csv");
    private static final Path TEST_FILE_EMPTY =
            Path.of("src/test/resources/dailyOperationTest_EmptyFile.csv");
    private static final Path TEST_FILE_PROPER_DATA_ONE_OPERATION =
            Path.of("src/test/resources/dailyOperationTest_ProperDataOneOperation.csv");
    private static final Path TEST_FILE_PROPER_DATA_THREE_OPERATION =
            Path.of("src/test/resources/dailyOperationTest_ProperDataThreeOperation.csv");
    private static final Path TEST_FILE_PROPER_DATA_SEVEN_OPERATION =
            Path.of("src/test/resources/dailyOperationTest_ProperDataSevenOperation.csv");
    private static final Path TEST_FILE_PROPER_DATA_TWELVE_OPERATION =
            Path.of("src/test/resources/dailyOperationTest_ProperDataTwelveOperation.csv");
    private static DailyReportFileReader dailyReportFileReader;

    @BeforeAll
    static void beforeAll() {
        dailyReportFileReader = new DailyReportFileReaderImpl();
    }

    @Test
    void readDailyReport_oneOperation_ok() {
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 444));
        List<FruitTransaction> actual =
                dailyReportFileReader.readDailyReport(TEST_FILE_PROPER_DATA_ONE_OPERATION);

        assertEquals(expected, actual);
    }

    @Test
    void readDailyReport_threeOperation_ok() {
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 444),
                        new FruitTransaction(FruitTransaction.Operation.BALANCE, "peach", 201),
                        new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 121));
        List<FruitTransaction> actual =
                dailyReportFileReader.readDailyReport(TEST_FILE_PROPER_DATA_THREE_OPERATION);

        assertEquals(expected, actual);
    }

    @Test
    void readDailyReport_sevenOperation_ok() {
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 444),
                        new FruitTransaction(FruitTransaction.Operation.BALANCE, "peach", 201),
                        new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 121),
                        new FruitTransaction(FruitTransaction.Operation.PURCHASE, "peach", 141),
                        new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 40),
                        new FruitTransaction(FruitTransaction.Operation.SUPPLY, "peach", 224),
                        new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 8));
        List<FruitTransaction> actual =
                dailyReportFileReader.readDailyReport(TEST_FILE_PROPER_DATA_SEVEN_OPERATION);

        assertEquals(expected, actual);
    }

    @Test
    void readDailyReport_twelveOperation_ok() {
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 444),
                        new FruitTransaction(FruitTransaction.Operation.BALANCE, "peach", 201),
                        new FruitTransaction(FruitTransaction.Operation.BALANCE, "grape", 1423),
                        new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 121),
                        new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 40),
                        new FruitTransaction(FruitTransaction.Operation.SUPPLY, "peach", 224),
                        new FruitTransaction(FruitTransaction.Operation.RETURN, "peach", 10),
                        new FruitTransaction(FruitTransaction.Operation.PURCHASE, "grape", 1245),
                        new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 307),
                        new FruitTransaction(FruitTransaction.Operation.SUPPLY, "grape", 500),
                        new FruitTransaction(FruitTransaction.Operation.PURCHASE, "peach", 34),
                        new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 700));
        List<FruitTransaction> actual =
                dailyReportFileReader.readDailyReport(TEST_FILE_PROPER_DATA_TWELVE_OPERATION);

        assertEquals(expected, actual);
    }

    @Test
    void readDailyReport_emptyFile_notOk() {
        assertThrows(EmptyFileException.class, () ->
                        dailyReportFileReader.readDailyReport(TEST_FILE_EMPTY),
                "\"EmptyFileException\" should be thrown when the input file is empty");
    }

    @Test
    void readDailyReport_nonExistingFile_notOk() {
        assertThrows(RuntimeException.class, () ->
                dailyReportFileReader.readDailyReport(TEST_FILE_ABSENT),
                "\"RuntimeException\" should be thrown when the file does not exist");
    }
}
