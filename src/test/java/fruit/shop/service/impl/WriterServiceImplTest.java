package fruit.shop.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fruit.shop.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String OUTPUT1 = "src/test/resources/output1.csv";
    private static WriterService writerService;

    @BeforeAll
    static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeToFile_checkLists_Ok() {
        List<String> expected = List.of("test", "second line", "", "empty line above");
        String data = "test\nsecond line\n\nempty line above";
        writerService.writeToFile(OUTPUT1, data);
        try {
            assertEquals(expected, Files.readAllLines(Path.of(OUTPUT1)));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file.");
        }
    }

    @Test
    void writeToFile_nullInput_NotOk() {
        assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(OUTPUT1, null));
    }
}
