package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.InputFileHandler;
import service.exceptions.EmptyFileException;

class InputHandlerTest {
    private static final String INPUT_FILE = "src/test/resources/input.csv";
    private static final String NONEXISTENT_FILE = "src/test/resources/grocery.csv";
    private static final String EMPTY_FILE = "src/test/resources/emptyFile.csv";
    private static InputFileHandler inputHandler;

    @BeforeAll
    static void beforeAll() {
        inputHandler = new InputHandler();
    }

    @Test
    void readFile_ok() {
        List<String> actual = inputHandler.readFile(INPUT_FILE);
        List<String> expected = List.of("type,fruit,quantity",
                "    b,banana,20",
                "    b,apple,100",
                "    s,banana,100",
                "    p,banana,13",
                "    r,apple,10",
                "    p,apple,20",
                "    p,banana,5",
                "    s,banana,50");
        assertEquals(expected, actual);
    }

    @Test
    void readFile_nonexistent_notOk() {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> inputHandler.readFile(NONEXISTENT_FILE));
        assertEquals("Can't find src/test/resources/grocery.csv file",
                exception.getMessage());
    }

    @Test
    void readFile_empty_notOk() {
        Throwable exception = assertThrows(EmptyFileException.class,
                () -> inputHandler.readFile(EMPTY_FILE));
        assertEquals("The file src/test/resources/emptyFile.csv is empty.",
                exception.getMessage());
    }
}
