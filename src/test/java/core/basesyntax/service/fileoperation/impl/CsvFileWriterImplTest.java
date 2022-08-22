package core.basesyntax.service.fileoperation.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitShopStorage;
import core.basesyntax.service.fileoperation.CsvFileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileWriterImplTest {
    private static final String TITLE = "fruit,quantity"
            + System.lineSeparator();
    private static final Path TEST_FILE_PATH = Path.of("src/test/resources/test-output.csv");
    private static CsvFileWriter writer;
    private static String report;

    @BeforeClass
    public static void setUp() {
        report = TITLE
                + "apple,40" + System.lineSeparator()
                + "peach,50" + System.lineSeparator()
                + "apricot,10" + System.lineSeparator();
    }

    @AfterClass
    public static void clear_storage() {
        FruitShopStorage.storageFruits.clear();
    }

    @Test
    public void write_report_Ok() {
        writer = new CsvFileWriterImpl();
        writer.writeFile(TEST_FILE_PATH.toString(), report);
        List<String> actual = readTestFile();
        List<String> expected = Arrays.asList(report.split(System.lineSeparator()));
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void write_invalidPath_notOk() {
        writer.writeFile("",report);
    }

    @Test(expected = RuntimeException.class)
    public void write_nullPath_notOk() {
        writer.writeFile(null,report);
    }

    @Test(expected = RuntimeException.class)
    public void write_nullReport_notOk() {
        writer.writeFile(TEST_FILE_PATH.toString(),null);
    }

    private List<String> readTestFile() {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(TEST_FILE_PATH.toString()))) {
            return reader.lines()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file "
                    + TEST_FILE_PATH, e);
        }
    }
}
