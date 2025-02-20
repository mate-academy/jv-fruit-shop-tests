package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static FileReader fileReader;

    @BeforeAll
    public static void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void completedList_Ok() {
        String fileName = "fruits.csv";

        List<String> expected = fileReader.readFile(fileName);
        List<String> actual = fileReader.readFile(fileName);

        assertEquals(expected, actual);
    }

    @Test
    public void completedList_NotOk() {
        String fileName = "fruits1.csv";

        List<String> expected = new ArrayList<>();
        List<String> actual = fileReader.readFile(fileName);

        assertEquals(expected, actual);
    }

    @Test
    public void fileFound_Ok() {
        String fileName = "fruits.csv";

        List<String> list = fileReader.readFile(fileName);

        assertTrue(list.size() != 0);
    }

    @Test
    public void fileFound_NotOk() {
        String fileName = "f";

        assertThrows(RuntimeException.class, () -> {
            fileReader.readFile(fileName);
        });
    }
}
