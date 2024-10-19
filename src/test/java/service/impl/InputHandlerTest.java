package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.InputFileHandler;
import service.exceptions.EmptyFileException;

class InputHandlerTest {
    private static final String INPUT_FILE = "src/main/java/resources/input.csv";
    private static final String NONEXISTENT_FILE = "src/main/java/resources/grocery.csv";
    private static final String EMPTY_FILE = "src/main/java/resources/emptyFile.csv";
    private static final String EMPTY_LINES_FILE = "src/main/java/resources/fileWithEmptyLines.csv";
    private static InputFileHandler inputHandler;

    @BeforeAll
    static void beforeAll() {
        inputHandler = new InputHandler();
    }

    @Test
    void tryReadFile() {
        List<String> actual = inputHandler.readFile(INPUT_FILE);
        List<String> expected = readFromFile(INPUT_FILE);
        assertEquals(expected, actual);

    }

    @Test
    void tryReadNonexistentFile() {
        assertThrows(RuntimeException.class,
                () -> inputHandler.readFile(NONEXISTENT_FILE));
    }

    @Test
    void tryReadEmptyFile() {
        assertThrows(EmptyFileException.class,
                () -> inputHandler.readFile(EMPTY_FILE));
    }

    private List<String> readFromFile(String fileName) {
        try {
            return Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Cannot read data from file " + fileName + " correctly.", e);
        }
    }
}
