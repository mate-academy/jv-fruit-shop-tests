package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.WriterService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImpTest {
    private static final String FILE_PATH = "src/test/resources/output.csv";
    private static final String REPORT = "fruit,quantity";
    private static final String FAIL_READ_MESSAGE = "Failed to read from the output.csv file: ";
    private static final String EMPTY_REPORT = "";
    private static WriterService writerService;
    private static File testFile;

    @BeforeAll
    static void beforeAll() {
        writerService = new WriterServiceImp();
        testFile = new File(FILE_PATH);
    }

    @AfterEach
    void tearDown() {
        testFile.delete();
    }

    @Test
    void writeFile_fileExist_ok() {
        writerService.writeFile(REPORT, FILE_PATH);
        assertTrue(testFile.exists());
    }

    @Test
    void writeFile_writeContent_ok() {
        writerService.writeFile(REPORT, FILE_PATH);
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String content = reader.readLine();
            assertEquals(REPORT, content);
        } catch (IOException e) {
            throw new RuntimeException(FAIL_READ_MESSAGE + FILE_PATH);
        }
    }

    @Test
    void writeFile_NullReport_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> writerService.writeFile(null, FILE_PATH));
    }

    @Test
    void writeFile_NullFilePath_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> writerService.writeFile(REPORT, null));
    }

    @Test
    void writeFile_EmptyReport_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> writerService.writeFile(EMPTY_REPORT, FILE_PATH));
    }
}
