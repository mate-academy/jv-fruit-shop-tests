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
    private static WriterService writerService;

    @BeforeAll
    public static void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writeToFile_Ok() throws IOException {
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "apple,20" + System.lineSeparator()
                + "banana,50" + System.lineSeparator()
                + "orange,30" + System.lineSeparator();
        writerService.writeToFile("src/test/resources/report.csv", expectedReport);
        List<String> expectedLines = Arrays.asList(expectedReport.split(System.lineSeparator()));
        List<String> actualLines = Files.readAllLines(Path.of("src/test/resources/report.csv"));

        assertEquals(expectedLines.size(), actualLines.size());
        for (int i = 0; i < expectedLines.size(); i++) {
            assertEquals(expectedLines.get(i), actualLines.get(i));
        }
    }

    @Test
    public void writeToFile_IllegalPath_notOk() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "apple,20" + System.lineSeparator()
                + "banana,50" + System.lineSeparator()
                + "orange,30" + System.lineSeparator();

        assertThrows(RuntimeException.class, ()
                -> writerService.writeToFile("invalid/path/to/file", report));
    }

    @Test
    public void writeToFile_nullReport_notOk() {
        assertThrows(RuntimeException.class, ()
                -> writerService.writeToFile("src/test/resources/report.csv", null));
    }
}
