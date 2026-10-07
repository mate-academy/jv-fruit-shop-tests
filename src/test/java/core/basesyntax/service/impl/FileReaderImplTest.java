package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static FileReaderImpl fileReaderImpl;
    private static String fileName;
    private static List<String> expected;

    @BeforeAll
    static void beforeAll() {
        fileReaderImpl = new FileReaderImpl();
        expected = new ArrayList<>();
    }

    @BeforeAll
    static void setUp() {
        fileName = "src/test/resources/";
        expected.clear();
    }

    @Test
    void read_ValidFile_Ok() {
        expected.add("type,fruit,quantity");
        expected.add("test,test,test");
        assertEquals(expected, fileReaderImpl.read(fileName + "valid.csv"),
                "Test read_ValidFile_Ok failed");
    }

    @Test
    void read_EmptyFile_Ok() {
        assertTrue(fileReaderImpl.read(fileName + "empty.csv").isEmpty(),
                "Test read_EmptyFile_Ok failed");
    }

    @Test
    void read_NullFile_NotOk() {
        assertThrows(NullPointerException.class,
                () -> fileReaderImpl.read(null),
                "Test read_NullFile_NotOk failed, expected Exception");
    }

    @Test
    void read_NotExistFile_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileReaderImpl.read(fileName + "notExist.csv"),
                "Test read_NotExistFile_NotOk failed, expected Exception");
    }

    @Test
    void read_InvalidPathToFile_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileReaderImpl.read("valid.csv"),
                "Test read_InvalidPathToFile_NotOk failed, expected Exception");
    }
}
