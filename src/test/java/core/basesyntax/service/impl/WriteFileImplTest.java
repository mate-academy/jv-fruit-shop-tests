package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriteFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class WriteFileImplTest {
    private static final String TITLE = "fruit,quantity";
    private static final String REPORT_FILENAME = "src/test/resources/report.csv";
    private static final WriteFile WRITE_FILE = new WriteFileImpl();

    @Test
    public void writeToFile_Ok() {
        List<String> expected = new ArrayList<>(List.of(TITLE, "banana,60", "apple,100"));
        WRITE_FILE.writeToFile(expected, REPORT_FILENAME);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(REPORT_FILENAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
        assertEquals(expected, actual);
    }
}
