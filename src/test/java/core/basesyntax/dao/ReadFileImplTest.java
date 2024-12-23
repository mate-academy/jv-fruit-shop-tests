package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReadFileImplTest {
    private static ReadFile readFile;

    @BeforeAll
    static void setUp() {
        readFile = new ReadFileImpl();
    }

    @Test
    void readFromFile_readPerfectFile_Ok() {
        String path = "src/main/java/resources/test1.txt";
        List<String> expectedList = List.of("b,apple,80",
                "s,apple,50",
                "p,apple,20",
                "r,apple,5");
        assertEquals(readFile.readFromFile(path), expectedList);
    }

    @Test
    void readFromFile_readEmptyFile_Ok() {
        String path = "src/main/java/resources/test2.txt";
        List<String> emptyList = new ArrayList<>();
        assertEquals(readFile.readFromFile(path), emptyList);
    }

    @Test
    void readFromFile_fileNotFount_notOk() {
        String path = "src/main/java/resources/notEnoughFile.txt";
        assertThrows(RuntimeException.class, () -> readFile.readFromFile(path));
    }

}
