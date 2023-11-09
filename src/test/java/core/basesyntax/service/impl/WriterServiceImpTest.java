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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceImpTest {
    private static final String FILE_PATH = "src/test/resources/output.csv";
    private WriterService writerService;
    private File testFile;

    @BeforeEach
    void setUp() throws IOException {
        writerService = new WriterServiceImp();
        testFile = new File(FILE_PATH);
    }

    @AfterEach
    void tearDown() {
        testFile.delete();
    }

    @Test
    void writeFile_fileExist_ok() {
        String report = "fruit,quantity";
        writerService.writeFile(report, FILE_PATH);
        assertTrue(testFile.exists());
    }

    @Test
    public void writeFile_writeContent_ok() {
        String report = "fruit,quantity";
        writerService.writeFile(report, FILE_PATH);
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String content = reader.readLine();
            assertEquals(report, content);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from the output.csv file: " + FILE_PATH);
        }
    }

    @Test
    public void writeFile_NullReport_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> writerService.writeFile(null, FILE_PATH));
    }

    @Test
    public void writeFile_NullFilePath_notOk() {
        String report = "fruit,quantity";
        assertThrows(IllegalArgumentException.class,
                () -> writerService.writeFile(report, null));
    }

    @Test
    public void writeFile_EmptyReport_notOk() {
        String report = "";
        assertThrows(IllegalArgumentException.class,
                () -> writerService.writeFile(report, FILE_PATH));
    }
}
