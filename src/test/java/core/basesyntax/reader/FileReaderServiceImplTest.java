package core.basesyntax.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Fruit;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private static FileReaderService fileReaderService;

    @BeforeAll
    static void beforeAll() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    void readFile_invalidPath_notOk() {
        String pathFalse = "src/test/resource/textInput.csv";

        assertThrows(RuntimeException.class, () -> {
            fileReaderService.readFile(pathFalse);
        }, "An exception should be thrown");
    }

    @Test
    void readFile_emptyFile_notOk() {
        String pathEmptyFile = "src/test/resources/emptyFile.csv";

        assertThrows(IllegalArgumentException.class, () -> {
            fileReaderService.readFile(pathEmptyFile);
        }, "An exception should be thrown");
    }

    @Test
    void readFile_validPath_ok() {
        String pathTestFile = "src/test/resources/textInputTest.csv";
        List<Fruit> fruitList = fileReaderService.readFile(pathTestFile);

        assertFalse(fruitList.isEmpty());
        assertEquals(2, fruitList.size());
        assertEquals(Fruit.Operation.BALANCE, fruitList.get(0).getTypeOperation());
        assertEquals("banana", fruitList.get(0).getTypeFruit());
        assertEquals(20, fruitList.get(0).getQuantity());
        assertEquals(Fruit.Operation.SUPPLY, fruitList.get(1).getTypeOperation());
        assertEquals("apple", fruitList.get(1).getTypeFruit());
        assertEquals(10, fruitList.get(1).getQuantity());
    }
}
