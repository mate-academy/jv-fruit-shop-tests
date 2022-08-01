package core.basesyntax;

import core.basesyntax.service.CsvReaderService;
import core.basesyntax.service.impl.CsvReaderServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvReaderServiceTest {
    private static final Path TEST_FRUIT_CSV_FILE_PATH =
            Path.of("src/test/resources/fruits_test.csv");
    private static List<String> testFruitsCsvLines;
    private static CsvReaderService csvReaderService;

    @BeforeClass
    public static void initCsvReaderService() {
        csvReaderService = new CsvReaderServiceImpl();
    }

    @BeforeClass
    public static void initFruitsTestCsvLines() {
        try {
            testFruitsCsvLines = Files.readAllLines(TEST_FRUIT_CSV_FILE_PATH);
        } catch (IOException e) {
            throw new RuntimeException("Could not read file : "
                    + TEST_FRUIT_CSV_FILE_PATH + ".", e);
        }
    }

    @Test
    public void read_dataMatch_Ok() {
        Assert.assertEquals(testFruitsCsvLines,
                csvReaderService.read(TEST_FRUIT_CSV_FILE_PATH));
    }

    @Test(expected = RuntimeException.class)
    public void read_fileNotExist_notOk() {
        csvReaderService.read(Path.of("/src/test/resources/test.dat"));
    }
}
