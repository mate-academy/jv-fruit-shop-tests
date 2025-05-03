package core.basesyntax.services.writes;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class CsvFileWriterOrderImplTest {
    private static final String CONTENT = "Test,Content";
    private static final String NON_EXISTENT_FILE_PATH = "non_existent_directory/test.csv";
    private static final String NULL_FILE_PATH = null;
    private CsvFileWriterOrder writer;

    @TempDir
    private Path tempDir;

    @BeforeEach
    void setUp() {
        writer = new CsvFileWriterOrderImpl();
    }

    @Test
    void testWriteOrder_toExistingFile_OK() {
        String filePath = tempDir.resolve("test.csv").toString();

        assertTrue(writer.writerOrder(CONTENT, filePath));
    }

    @Test
    void testWriteOrder_toNonExistingFile_NotOK() {
        assertThrows(RuntimeException.class, () ->
                writer.writerOrder(CONTENT, NON_EXISTENT_FILE_PATH));
    }

    @Test
    void testWriteOrder_withNullFilePath_NotOK() {
        assertThrows(IllegalArgumentException.class, () ->
                writer.writerOrder(CONTENT, NULL_FILE_PATH));
    }

    @Test
    void testWriteOrder_withEmptyContent_NotOK() {
        String filePath = tempDir.resolve("test.csv").toString();
        assertThrows(IllegalArgumentException.class, () ->
                writer.writerOrder("", filePath));
    }

    @AfterEach
    void tearDown() {
        deleteDirectory(tempDir.toFile());
    }

    private void deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            directory.delete();
        }
    }
}
