package core.basesyntax.service.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.FileWritingService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWritingServiceImplTest {
    private static final String DATA = "fruit,quantity"
            + System.lineSeparator()
            + "banana,100"
            + System.lineSeparator()
            + "apple,100";
    private static final String ACTUAL_REPORT_NAME = "src/test/resources/report.csv";
    private static final String EXPECTED_REPORT_NAME = "src/test/resources/report.csv";
    private static FileWritingService fileWriter;

    @BeforeClass
    public static void setFileHandler() {
        fileWriter = new FileWritingServiceImpl();
    }

    @Before
    public void deleteFile() throws IOException {
        Files.delete(Path.of(ACTUAL_REPORT_NAME));
    }

    @Test
    public void writeFile_ok() throws IOException {
        File actualFile = fileWriter.writeFile(ACTUAL_REPORT_NAME, DATA);
        byte[] bytesOfExpectedFile = Files.readAllBytes(Path.of(EXPECTED_REPORT_NAME));
        byte[] bytesOfActualFile = Files.readAllBytes(Path.of(ACTUAL_REPORT_NAME));
        assertTrue(actualFile.exists());
        assertArrayEquals(bytesOfExpectedFile, bytesOfActualFile);
    }
}
