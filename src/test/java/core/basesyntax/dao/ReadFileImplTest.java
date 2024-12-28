package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReadFileImplTest {

    @Test
    void readFromFile_readValidDataFile_Ok() {
        ReadFile readFile = new ReadFileImpl();
        String path = "src/test/java/resources/test1.txt";
        List<String> expectedList = List.of("b,apple,80",
                "s,apple,50",
                "p,apple,20",
                "r,apple,5");
        assertEquals(readFile.readFromFile(path), expectedList);
    }

    @Test
    void readFromFile_readEmptyFile_Ok() {
        ReadFile readFile = new ReadFileImpl();
        String path = "src/test/java/resources/test2.txt";
        List<String> emptyList = new ArrayList<>();
        assertEquals(readFile.readFromFile(path), emptyList);
    }

    @Test
    void readFromFile_fileNotFount_notOk() {
        ReadFile readFile = new ReadFileImpl();
        String path = "src/test/java/resources/notEnoughFile.txt";
        assertThrows(RuntimeException.class, () -> readFile.readFromFile(path));
    }
}
