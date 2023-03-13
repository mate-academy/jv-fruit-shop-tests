package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceTest {
    private static final String FILE_NAME = "src/test/resources/input.csv";
    private static final String REPORT_TEXT = "Some report";
    private static File testFile;
    private final WriterServiceImpl writerService = new WriterServiceImpl();

    @BeforeClass
    public static void setUpBeforeClass() {
        testFile = new File(FILE_NAME);
        try {
            testFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void cleanStorageAfterEach() {
        Storage.STORAGE.clear();
    }

    @Test
    public void writeReportToFile_ok() {
        File reportFile = writerService.writeReportToFile(REPORT_TEXT);
        assertTrue(reportFile.exists());
        try {
            assertEquals(REPORT_TEXT, new String(Files.readAllBytes(reportFile.toPath())));
            reportFile.delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
