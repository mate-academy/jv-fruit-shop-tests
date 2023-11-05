package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;

import core.basesyntax.service.Writer;
import java.io.File;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
    public static void setUp() {
        writer = new WriteCsvService();
        folder = new File(FOLDER_PATH);
    }

    @AfterEach
    public void afterEach() {
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
    public void writeCsvService_NullValue_NotOk() {
        assertThrows(RuntimeException.class, () -> writer.write(null,null));
    }

    @Test
    public void writeCsvService_EmptyContent_Ok() {
        Assertions.assertTrue(writer.write(EMPTY_CONTENT,EMPTY_FILE_PATH));
    }

    @Test
    public void writeCsvService_NormalContentName_Ok() {
        Assertions.assertTrue(writer.write(CONTENT_TO_WRITE, CORRECT_FILE_PATH));
    }

    @Test
    public void writeCsvService_NotCsvFile_NotOk() {
        assertThrows(RuntimeException.class,
                () -> writer.write(CONTENT_NOT_TO_WRITE,NOT_CSV_FILE_PATH));
    }
}
