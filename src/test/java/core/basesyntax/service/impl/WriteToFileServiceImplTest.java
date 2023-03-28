package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

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
        String report = "banana,65\napple,45\n";
        writeToFileService.writeToFile(report, FILE_REPORT_NAME);
        assertTrue(Files.exists(Path.of(FILE_REPORT_NAME)));
    }
}
