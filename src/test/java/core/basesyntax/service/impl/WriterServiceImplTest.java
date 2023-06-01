package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {

    private static final String PATH_TO_FILE = "src/test/resources/report.csv";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String INVALID_PATH = "invalid/path/to/file";
    private static WriterService writerService;

    @BeforeAll
    public static void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void write_To_File_Ok() throws IOException {
        String expectedReport = "fruit,quantity" + LINE_SEPARATOR
                + "apple,20" + LINE_SEPARATOR
                + "banana,50" + LINE_SEPARATOR
                + "orange,30" + LINE_SEPARATOR;
        writerService.writeToFile(PATH_TO_FILE, expectedReport);
        List<String> expectedLines = Arrays.asList(expectedReport.split(LINE_SEPARATOR));
        List<String> actualLines = Files.readAllLines(Path.of(PATH_TO_FILE));

        assertEquals(expectedLines.size(), actualLines.size());
        for (int i = 0; i < expectedLines.size(); i++) {
            assertEquals(expectedLines.get(i), actualLines.get(i));
        }
    }

    @Test
    public void write_To_File_Illegal_Path_not_ok() {
        String report = "fruit,quantity" + LINE_SEPARATOR
                + "apple,20" + LINE_SEPARATOR
                + "banana,50" + LINE_SEPARATOR
                + "orange,30" + LINE_SEPARATOR;

        assertThrows(RuntimeException.class, () -> writerService.writeToFile(INVALID_PATH, report));
    }

    @Test
    public void write_To_File_null_report_not_ok() {
        assertThrows(RuntimeException.class, () -> writerService.writeToFile(PATH_TO_FILE, null));
    }
}
