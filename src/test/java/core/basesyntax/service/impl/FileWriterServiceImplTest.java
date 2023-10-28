package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private static final String OUTPUT = "src/test/resources/test_output.csv";
    private static FileWriterService writerService;

    @BeforeAll
    static void beforeAll() {
        writerService = new FileWriterServiceImpl();
    }

    @Test
    void write_checkLists_Ok() {
        List<String> expected = List.of("p,apple,20", "b,banana,5");
        String data = "p,apple,20\nb,banana,5\n";
        writerService.write(OUTPUT, data);
        try {
            Assertions.assertEquals(expected, Files.readAllLines(Path.of(OUTPUT)));
        } catch (IOException e) {
            throw new RuntimeException("Can't right to the file", e);
        }
    }

    @Test
    void write_nullInput_NotOk() {
        assertThrows(RuntimeException.class,
                () -> writerService.write(OUTPUT, null));
    }
}
