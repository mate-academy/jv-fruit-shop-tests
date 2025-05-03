package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileService;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileServiceImplTest {
    private static final String INPUT_FILEPATH = "src/test/resources/input.csv";
    private static final String INVALID_FILEPATH = "src/test/resources/invalid.csv";
    private static final String OUTPUT_FILEPATH = "src/test/resources/output.csv";
    private static FileService fileService;

    @BeforeAll
    static void beforeAll() {
        fileService = new FileServiceImpl();
    }

    @Test
    void read_readFromFile_ok() {
        List<String> actual = fileService.read(INPUT_FILEPATH);
        List<String> expected = List.of(
                "operation,fruit,quantity",
                "b,banana,20",
                "b,apple,15"
        );
        assertEquals(expected, actual);
    }

    @Test
    void read_invalidPath_notOk() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            fileService.read(INVALID_FILEPATH);
        });
        String actualMessage = runtimeException.getMessage();
        String expectedMessage = "Can't read from file " + INVALID_FILEPATH;
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void write_writeToFile_ok() {
        File file = new File(OUTPUT_FILEPATH);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Can't create test file", e);
        }
        fileService.write(OUTPUT_FILEPATH, "some_data");
        List<String> actual = fileService.read(OUTPUT_FILEPATH);
        List<String> expected = List.of("some_data");
        assertEquals(expected, actual);
        file.delete();
    }

    @Test
    void write_invalidPath_notOk() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            fileService.write(INVALID_FILEPATH, "any_data");
        });
        String actualMessage = runtimeException.getMessage();
        String expectedMessage = "Can't write data to file " + INVALID_FILEPATH;
        assertEquals(expectedMessage, actualMessage);
    }
}
