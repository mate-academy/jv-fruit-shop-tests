package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Test;

public class FileWriterImplTest {
    private static final FileWriter writer = new FileWriterImpl();
    private static final String TEST_REPORT_FILE_PATH = "src/test/resources/reportTest.csv";

    @Test
    public void writeToFile_ok() throws IOException {
        String testMessage = "Test message";
        writer.writeToFile(testMessage, TEST_REPORT_FILE_PATH);
        List<String> currentInFile = Files.readAllLines(Path.of(TEST_REPORT_FILE_PATH));
        assertTrue(currentInFile.size() == 1);
        assertEquals(currentInFile.get(0), testMessage);
    }
}
