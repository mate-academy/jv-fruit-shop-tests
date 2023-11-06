package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.Writer;
import java.io.File;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriteCsvServiceTest {
    private static final String FOLDER_PATH = "src/test/files/output/";
    private static final String EMPTY_FILE_PATH = "src/test/files/output/emptyCsvFile.csv";
    private static final String CORRECT_FILE_PATH = "src/test/files/output/mustBeCreated.csv";
    private static final String NOT_CSV_FILE_PATH = "src/test/files/output/helloMentor.txt";
    private static final String EMPTY_CONTENT = "";
    private static final String CONTENT_TO_WRITE =
            "some data\nanother date\nplus one more to be sure";
    private static final String CONTENT_NOT_TO_WRITE = "some data than must not be created";
    private static File folder;
    private static Writer writer;

    @BeforeAll
    static void setUp() {
        writer = new WriteCsvService();
        folder = new File(FOLDER_PATH);
    }

    @AfterEach
    void afterEach() {
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        }
    }

    @Test
    void nullValue_NotOk() {
        assertThrows(RuntimeException.class, () -> writer.write(null,null));
    }

    @Test
    void emptyContent_Ok() {
        assertTrue(writer.write(EMPTY_CONTENT,EMPTY_FILE_PATH));
    }

    @Test
    void normalContentName_Ok() {
        assertTrue(writer.write(CONTENT_TO_WRITE, CORRECT_FILE_PATH));
    }

    @Test
    void notCsvFile_NotOk() {
        assertThrows(RuntimeException.class,
                () -> writer.write(CONTENT_NOT_TO_WRITE,NOT_CSV_FILE_PATH));
    }
}
