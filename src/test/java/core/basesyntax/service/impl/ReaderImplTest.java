package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.Reader;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderImplTest {
    private static String emptyFile;
    private static String nonExistentFile;
    private static String rightDataFile;
    private static Reader reader;

    @BeforeAll
    static void beforeAll() {
        emptyFile = "src/test/resources/empty.csv";
        nonExistentFile = "src/test/resources/file_exist.csv";
        rightDataFile = "src/test/resources/testFile.csv";
        reader = new ReaderImpl();
    }

    @Test
    void readFromFile_emptyFile_ok() {
        List<String> list = reader.readFromFile(emptyFile);
        assertTrue(list.isEmpty());
    }

    @Test
    void readFromFile_fileDoesNotExist_notOk() {
        assertThrows(RuntimeException.class, () -> reader.readFromFile(nonExistentFile));
    }

    @Test
    void readFromFile_fightData_ok() {
        List<String> list = new ArrayList<>();
        list.add("b,banana,20");
        list.add("b,apple,100");
        assertEquals(list, reader.readFromFile(rightDataFile));
    }
}
