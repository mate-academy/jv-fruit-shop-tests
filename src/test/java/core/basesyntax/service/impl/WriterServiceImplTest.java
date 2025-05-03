package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String PATH_TO_FILE = "src/test/resources/report.csv";
    private static final String INVALID_PATH = "invalid/path/to/file";
    private static WriterService writerService;

    @BeforeClass
    public static void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void write_ToFile_Ok() throws IOException {
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "apple,20" + System.lineSeparator()
                + "banana,50" + System.lineSeparator()
                + "orange,30" + System.lineSeparator();
        writerService.writeToFile(PATH_TO_FILE, expectedReport);
        List<String> expectedLines = Arrays.asList(expectedReport.split(System.lineSeparator()));
        List<String> actualLines = Files.readAllLines(Path.of(PATH_TO_FILE));

        assertEquals("Number of lines in file is incorrect",
                expectedLines.size(), actualLines.size());
        for (int i = 0; i < expectedLines.size(); i++) {
            assertEquals("Line #" + i + " is incorrect",
                    expectedLines.get(i), actualLines.get(i));
        }
    }

    @Test(expected = java.io.IOException.class)
    public void write_ToFileIllegalPath_NotOk() throws IOException {
        String report = "fruit,quantity" + System.lineSeparator()
                + "apple,20" + System.lineSeparator()
                + "banana,50" + System.lineSeparator()
                + "orange,30" + System.lineSeparator();

        writerService.writeToFile(INVALID_PATH, report);
    }

    @Test(expected = RuntimeException.class)
    public void write_ToFileNullReportNot_Ok() throws IOException {
        writerService.writeToFile(PATH_TO_FILE, null);
    }
}

