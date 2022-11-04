package core.basesyntax.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.impl.DataWriterCsvImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.Test;

public class DataWriterTest {
    private static final String EXPECTED_REPORT_PATH = "src/test/resources/Report.csv";
    private static final String ACTUAL_REPORT_PATH = "src/test/resources/ReportActual.csv";
    private static final String REPORT_EXAMPLE_OK = "fruit,quantity" + System.lineSeparator()
            + "banana,107" + System.lineSeparator()
            + "orange,170" + System.lineSeparator()
            + "apple,100";
    private DataWriter dataWriter = new DataWriterCsvImpl();

    @Test
    public void writeOk() {
        dataWriter.write(REPORT_EXAMPLE_OK, ACTUAL_REPORT_PATH);
        byte[] reportExpected = null;
        byte[] reportActual = null;
        try {
            reportExpected = Files.readAllBytes(Path.of(EXPECTED_REPORT_PATH));
        } catch (IOException e) {
            fail("Can't read file: " + EXPECTED_REPORT_PATH);
        }
        try {
            reportActual = Files.readAllBytes(Path.of(ACTUAL_REPORT_PATH));
        } catch (IOException e) {
            fail("Can't read file: " + ACTUAL_REPORT_PATH);
        }
        assertArrayEquals("Reports not equals", reportExpected, reportActual);
    }

    @Test
    public void nullPathTest_NotOk() {
        try {
            dataWriter.write(REPORT_EXAMPLE_OK, null);
        } catch (RuntimeException e) {
            return;
        }
        fail("DataReader should be thrown: \"Can't read data to file: filePath\"");
    }

    @After
    public void before() {
        try {
            Files.deleteIfExists(Path.of(ACTUAL_REPORT_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
