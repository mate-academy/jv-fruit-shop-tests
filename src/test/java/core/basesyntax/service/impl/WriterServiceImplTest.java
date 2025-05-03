package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.WriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String REPORT_TITLE_ROW = "fruit,quantity";
    private static final String NULL_PATH = null;
    private static final File WRITE_TO = new File("src/test/resources/output/write-to.csv");
    private static final File EXPECTED_CONTENT = new File(
            "src/test/resources/output/valid-output-for-writer.csv");
    private static final File WRONG_PATH = new File("src/test/resources/wrong-path/new-file.csv");
    private static ReportService report;
    private static WriterService writer;

    @BeforeClass
    public static void beforeClass() {
        report = new ReportServiceImpl();
        writer = new WriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void write_nullReport_notOk() {
        writer.write(WRONG_PATH, report.newReport());
    }

    @Test(expected = NullPointerException.class)
    public void write_toNullPath_notOk() {
        writer.write(new File(NULL_PATH), "banana,152");
    }

    @Test
    public void write_dataMustBeSame_ok() {
        Storage.storage.put("banana", 152);
        writer.write(WRITE_TO, REPORT_TITLE_ROW
                + System.lineSeparator() + "banana,152");
        try {
            List<String> actual = Files.readAllLines(WRITE_TO.toPath());
            List<String> expected = Files.readAllLines(EXPECTED_CONTENT.toPath());
            assertEquals(expected, actual);
        } catch (IOException e) {
            throw new RuntimeException("File can`t be read!", e);
        }
    }
}
