package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.storage.FruitStorage;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class FileServiceImplTest {
    private static final String INPUT_FILE_NAME = "src/main/resources/inputReportTest";
    private static final String RESULT_FILE_NAME = "src/main/resources/resultReportTest";
    private final FileService fileService = new FileServiceImpl();

    @BeforeEach
    public void cleanStorage() {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    public void readFileTest_OK() {
        String expected = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,20" + System.lineSeparator()
                + "b,apple,100" + System.lineSeparator()
                + "s,banana,100" + System.lineSeparator()
                + "p,banana,13" + System.lineSeparator()
                + "r,apple,10" + System.lineSeparator()
                + "p,apple,20" + System.lineSeparator()
                + "p,banana,5" + System.lineSeparator()
                + "s,banana,50";
        String actual = fileService.readFile(INPUT_FILE_NAME);
        assertEquals(expected,actual);
    }

    @Test
    public void readFileTest_IncorrectContent_notOK() {
        String expected = fileService.readFile(INPUT_FILE_NAME);
        String actual = "Incorrect content";
        assertNotEquals(expected, actual);
    }

    @Test
    public void readFileTest_NullPath_notOK() {
        assertThrows(RuntimeException.class, () -> {
            fileService.readFile(null);
        });
    }

    @Test
    public void readFileTest_IncorrectPath_notOK() {
        assertThrows(RuntimeException.class, () -> {
            fileService.readFile("IncorrectPath");
        });
    }

    @Test
    public void writeFileTest_OK() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        fileService.writeToFile(expected, RESULT_FILE_NAME);
        String actual = fileService.readFile(RESULT_FILE_NAME);
        assertEquals(expected,actual);
    }

    @Test
    public void writeFileTest_NullContent_notOK() {
        assertThrows(RuntimeException.class, () -> {
            fileService.writeToFile(null, RESULT_FILE_NAME);
        });
    }

    @Test
    public void writeFileTest_NullPath_notOK() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        assertThrows(RuntimeException.class, () -> {
            fileService.writeToFile(expected, null);
        });
    }
}
