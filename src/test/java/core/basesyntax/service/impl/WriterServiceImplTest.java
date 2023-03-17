package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WriterServiceImplTest {
    private static final String HEADER = "type,fruit,quantity";
    private static final String LINE_OK = "b,banana,100";
    private static final String PATH_TO_TEST = "src/test/resources/test.txt";
    private static WriterService writerService;

    @BeforeAll
    static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void write_toFile_isOk() {
        writerService.write(HEADER + LINE_OK, PATH_TO_TEST);
        try {
            assertEquals(Files.readAllLines(Path.of(PATH_TO_TEST)),
                    List.of(HEADER + LINE_OK));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
