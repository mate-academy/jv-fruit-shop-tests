package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WriterServiceTest {
    private static WriterService writerService;

    private static final String CORRECT_FILE_NAME = "src/main/resources/input.csv";
    private static final String OUTPUT_FILE_NAME = "src/main/resources/output.csv";
    private static final String WRONG_FILE_NAME = "wrong_output.csv";
    private static final String EMPTY_TEXT = "";

    private static final String EXPECTED_DATA = "type,fruit,quantity" + System.lineSeparator()
            + "b,banana,20" + System.lineSeparator()
            + "b,apple,100" + System.lineSeparator()
            + "s,banana,100" + System.lineSeparator()
            + "p,banana,13" + System.lineSeparator()
            + "r,apple,10" + System.lineSeparator()
            + "p,apple,20" + System.lineSeparator()
            + "p,banana,5" + System.lineSeparator()
            + "s,banana,50";

    @BeforeAll
    static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void write_emptyText_notOk() {
        assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(EMPTY_TEXT, CORRECT_FILE_NAME));
    }

    @Test
    void write_nullText_notOk() {
        assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(null, CORRECT_FILE_NAME));
    }

    @Test
    void write_Ok() {
        writerService.writeToFile(EXPECTED_DATA, OUTPUT_FILE_NAME);
        List<String> lines;
        String actual;
        try {
            lines = Files.readAllLines(Path.of(OUTPUT_FILE_NAME));
            actual = lines.stream()
                    .collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(EXPECTED_DATA, actual);
    }
}
