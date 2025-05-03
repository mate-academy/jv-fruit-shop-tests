package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileReaderTest {
    private static final String CORRECT_FILE_PATH = "src/main/resources/file.csv";
    private static final String INCORRECT_FILE_PATH = "src/main/resources/incData.csv";
    private static final String FILE_DATA = """
            b,banana,20
            b,apple,100
            s,banana,100
            p,banana,13
            r,apple,10
            p,apple, 20
            p,banana,5
            s,banana,50""";
    private static final List<String> EXPECTED_LIST = List.of("b,banana,20",
            "b,apple,100", "s,banana,100", "p,banana,13", "r,apple,10",
            "p,apple, 20", "p,banana,5", "s,banana,50");
    private static FileReader reader;

    @BeforeAll
    static void beforeAll() throws IOException {
        reader = new CsvFileReader();
        Files.write(Path.of(CORRECT_FILE_PATH), FILE_DATA.getBytes());
    }

    @Test
    void readFile_isOk() {
        List<String> actualList = reader.readFile(CORRECT_FILE_PATH);
        assertEquals(EXPECTED_LIST, actualList);
    }

    @Test
    void readFile_isNotOk() {
        assertThrows(RuntimeException.class,
                () -> reader.readFile(INCORRECT_FILE_PATH));
    }
}
