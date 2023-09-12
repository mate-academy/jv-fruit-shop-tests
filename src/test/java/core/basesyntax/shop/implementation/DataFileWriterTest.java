package core.basesyntax.shop.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.shop.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataFileWriterTest {
    private static FileWriter fileWriter;
    private static final String INPUT_VALUE = "s,banana,140";
    private static final String TEST_FILE_REPORT = "src/test/java/core/"
            + "basesyntax/resources/testFileReport.csv";
    private static final String INCORRECT_TEST_FILE_REPORT =
            "dsdd?sdcds/dsd/main.txt";

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new DataFileWriter();
    }

    @Test
    public void writeToReport_Ok() {
        fileWriter.writeToReport(INPUT_VALUE,TEST_FILE_REPORT);
        List<String> expectedFile;
        try {
            expectedFile = Files.readAllLines(Path.of(TEST_FILE_REPORT));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: " + TEST_FILE_REPORT, e);
        }

        List<String> actualFile;

        try {
            actualFile = Files.readAllLines(Path.of(TEST_FILE_REPORT));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: " + TEST_FILE_REPORT, e);
        }

        assertEquals(expectedFile, actualFile);
    }

    @Test(expected = RuntimeException.class)
    public void writeReport_NotOk() {
        fileWriter.writeToReport(INPUT_VALUE,
                        INCORRECT_TEST_FILE_REPORT);
    }
}
