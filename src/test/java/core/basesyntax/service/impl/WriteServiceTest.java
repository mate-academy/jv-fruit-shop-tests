package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriteService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class WriteServiceTest {

    private static final String EMPTY_FILE_NAME = "";
    private static final String OUTPUT_FILE_TEST = "src/test/resources/reportTest.csv";

    @Test
    void writeFile_reportList_ok() throws IOException {
        List<String> expected = List.of("fruit,quantity", "orange,140", "lemon,67");
        WriteService writer = new WriteServiceImpl();
        writer.writeFile(OUTPUT_FILE_TEST, List.of("fruit,quantity", "orange,140", "lemon,67"));
        List<String> actual = Files.readAllLines(Path.of(OUTPUT_FILE_TEST));
        assertLinesMatch(expected, actual, "Test failed! You should returned next list "
                + expected + " but you returned "
                + actual);
    }

    @Test
    void writeFile_fileNotFound_notOk() {
        WriteService writer = new WriteServiceImpl();
        assertThrows(RuntimeException.class, () ->
                writer.writeFile(EMPTY_FILE_NAME,
                        List.of("fruit,quantity", "orange,140", "lemon,67")));
    }
}
