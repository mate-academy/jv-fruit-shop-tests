package core.basesyntax.service;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceTest {
    private static final StringBuilder REPORT_DATA = new StringBuilder();
    private static final String REPORT_STRUCTURE = "fruit,quantity";
    private static final String REPORT_FILE_NAME = "src/test/resources/reportTest.csv";
    private static final List<String> EXPECTED_DATA = List.of("fruit,quantity",
            "kiwi,10", "apple,20", "orange,30", "banana,40");

    @BeforeClass
    public static void beforeClass() {
        REPORT_DATA.append(REPORT_STRUCTURE)
                .append(System.lineSeparator())
                .append("kiwi,10").append(System.lineSeparator())
                .append("apple,20").append(System.lineSeparator())
                .append("orange,30").append(System.lineSeparator())
                .append("banana,40");
    }

    @Test
    public void write_stringDataToCsvFile_Ok() {
        WriterService writer = new WriterServiceImpl();
        writer.writeToCsvFile(REPORT_FILE_NAME, REPORT_DATA.toString());

        List<String> actualReport;
        try {
            actualReport = Files.readAllLines(Path.of(REPORT_FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file "
                    + REPORT_FILE_NAME + " !", e);
        }

        Assert.assertEquals("Wrong data written to file: " + REPORT_FILE_NAME,
                EXPECTED_DATA, actualReport);
    }

    @Test
    public void write_stringDataToCsvFileTwice_Ok() {
        WriterService writer = new WriterServiceImpl();
        writer.writeToCsvFile(REPORT_FILE_NAME, REPORT_DATA.toString());
        writer.writeToCsvFile(REPORT_FILE_NAME, REPORT_DATA.toString());

        List<String> actualReport;
        try {
            actualReport = Files.readAllLines(Path.of(REPORT_FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file "
                    + REPORT_FILE_NAME + " !", e);
        }

        Assert.assertEquals("Wrong data written to file: " + REPORT_FILE_NAME,
                EXPECTED_DATA, actualReport);
    }
}
