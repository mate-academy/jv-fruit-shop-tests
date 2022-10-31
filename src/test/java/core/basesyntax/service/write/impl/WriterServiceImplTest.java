package core.basesyntax.service.write.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.service.write.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String DIRECTORY_PATH = "src/test/resources/";
    private static final String INVALID_DIRECTORY_PATH = "src/test/resources/reports/";
    private static final String FIRST_LINE = "fruit,quantity";
    private static final WriterService writerService = new WriterServiceImpl();

    @Test
    public void writeToFile_validData_ok() throws IOException {
        String report = FIRST_LINE + LINE_SEPARATOR
                + "apple,999" + LINE_SEPARATOR
                + "banana,200";
        String reportFileName = "report.csv";
        writerService.writeToFile(DIRECTORY_PATH, reportFileName, report);
        String actual = Files.readString(Path.of(DIRECTORY_PATH, reportFileName));
        assertEquals(report, actual);
    }

    @Test
    public void writeToFile_invalidData_notOk() {
        String report = FIRST_LINE + LINE_SEPARATOR
                + "apple,999" + LINE_SEPARATOR
                + "banana,200";
        String reportFileName = "report.csv";
        assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(INVALID_DIRECTORY_PATH, reportFileName, report));
    }
}
