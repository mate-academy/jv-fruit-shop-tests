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
    private static FileService fileService;

    @BeforeAll
    static void beforeAll() {
        fileService = new FileServiceImpl();
    }

    @Test
    void read_readFromFile_ok() {
        List<String> actual = fileService.read("src/test/resources/input.csv");
        List<String> expected = List.of(
                "operation,fruit,quantity",
                "b,banana,20",
                "b,apple,15"
        );
        assertEquals(expected, actual);
    }

    @Test
    void read_invalidPath_notOk() {
        String filePath = "src/test/resources/invalid.csv";
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            fileService.read(filePath);
        });
        String actualMessage = runtimeException.getMessage();
        String expectedMessage = "Can't read from file " + filePath;
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void write_writeToFile_ok() {
        String filePath = "src/test/resources/output.csv";
        File file = new File(filePath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Can't create test file", e);
        }
        fileService.write(filePath, "some_data");
        List<String> actual = fileService.read(filePath);
        List<String> expected = List.of("some_data");
        assertEquals(expected, actual);
        file.delete();
    }

    @Test
    void write_invalidPath_notOk() {
        String filePath = "src/test/resources/invalid.csv";
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            fileService.write(filePath, "any_data");
        });
        String actualMessage = runtimeException.getMessage();
        String expectedMessage = "Can't write data to file " + filePath;
        assertEquals(expectedMessage, actualMessage);
    }
}
