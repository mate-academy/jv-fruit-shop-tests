package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.WriterService;
import java.util.List;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String REPORT_FILE_PATH = "src/test/java/"
            + "resources/reporttest.csv";
    private static final String forReport = "fruit,quantity" + System.lineSeparator()
            + "banana,10" + System.lineSeparator() + "apple,15";
    private WriterService writerService = new WriterServiceImpl();
    private ReaderService readerService = new ReaderServiceImpl();

    @Test
    public void writeToFile_Ok() {
        writerService.writeToFile(forReport, REPORT_FILE_PATH);
        List<String> expected = List.of("fruit,quantity",
                "banana,10",
                "apple,15");
        List<String> actual = readerService.readFromFile(REPORT_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void writeToFile_WritingNull_NotOk() {
        try {
            writerService.writeToFile(null, REPORT_FILE_PATH);
        } catch (NullPointerException e) {
            return;
        }
        fail("NullPointerException should be thrown");
    }
}
