package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import core.basesyntax.service.WriteToFileService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteToFileServiceImplTest {
    private static final String FILE_REPORT_NAME = "src/main/java/resources/FruitShopReport.csv";
    private static WriteToFileService writeToFileService;

    @BeforeClass
    public static void beforeClass() {
        writeToFileService = new WriteToFileServiceImpl();
    }

    @After
    public void clearResults() {
        try {
            Files.deleteIfExists(Path.of(FILE_REPORT_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly clear result files after test ", e);
        }
    }

    @Test
    public void writeToFile_dataNotNull_Ok() {
        String report = "banana,65" + System.lineSeparator() + "apple,45" + System.lineSeparator();
        writeToFileService.writeToFile(report, FILE_REPORT_NAME);
        assertTrue(Files.exists(Path.of(FILE_REPORT_NAME)));
    }

    @Test
    public void readFromFile_NotExistsFile_NotOk() {
        try {
            writeToFileService.writeToFile("null", "java");
        } catch (RuntimeException e) {
            fail("Cant trow RuntimeException");
        }
    }
}
