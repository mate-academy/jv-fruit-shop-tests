package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReaderImplTest {
    private final ReaderImpl reader = new ReaderImpl();

    @Test
    void readFromFile_nullPath_notOk() {
        assertThrows(RuntimeException.class, () -> reader.readFromFile(null));
    }

    @Test
    void readFromFile_emptyPath_notOk() {
        assertThrows(RuntimeException.class, () -> reader.readFromFile(""));
    }

    @Test
    void readFromFile_fileNotExist_notOk() {
        assertThrows(RuntimeException.class,
                () -> reader.readFromFile("src/main/resources/notExists.txt"));
    }

    @Test
    void readFromFile_emptyFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> reader.readFromFile("src/main/resources/empty.txt"));
    }

    @Test
    void readFromFile_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        List<String> actual =
                reader.readFromFile("src/main/resources/input.txt");
        assertEquals(expected, actual);
    }
}
