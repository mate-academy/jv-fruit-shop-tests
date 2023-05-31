package core.basesyntax.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.impl.DataWriterCsvImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataWriterTest {
    private static final String EXPECTED_REPORT_PATH = "src/test/resources/Report.csv";
    private static final String ACTUAL_REPORT_PATH = "src/test/resources/ReportActual.csv";
    private static final String REPORT_EXAMPLE_OK = "fruit,quantity" + System.lineSeparator()
            + "banana,107" + System.lineSeparator()
            + "orange,170" + System.lineSeparator()
            + "apple,100";
    private static DataWriter dataWriter;

    @BeforeClass
    public static void setUp() {
        dataWriter = new DataWriterCsvImpl();
    }

    @Test
    public void write_correctDataTest_Ok() {
        dataWriter.write(REPORT_EXAMPLE_OK, ACTUAL_REPORT_PATH);
        byte[] expected = null;
        byte[] actual = null;
        try {
            expected = Files.readAllBytes(Path.of(EXPECTED_REPORT_PATH));
        } catch (IOException e) {
            fail("Can't read file: " + EXPECTED_REPORT_PATH);
        }
        try {
            actual = Files.readAllBytes(Path.of(ACTUAL_REPORT_PATH));
        } catch (IOException e) {
            fail("Can't read file: " + ACTUAL_REPORT_PATH);
        }
        assertArrayEquals("Reports not equals", expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void write_nullPathTest_notOk() {
        dataWriter.write(REPORT_EXAMPLE_OK, null);
    }

    @After
    public void before() {
        try {
            Files.deleteIfExists(Path.of(ACTUAL_REPORT_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't delete file:" + ACTUAL_REPORT_PATH);
        }
    }
}
