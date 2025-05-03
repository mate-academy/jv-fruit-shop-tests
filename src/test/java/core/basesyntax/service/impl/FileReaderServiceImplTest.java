package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderServiceImplTest {
    private static FileReader reader;
    private static final String CORRECT_FILE_PATH = "src/test/resources/inputFileTest.csv";
    private static final String NON_EXISTENT_FILE_PATH = "src/test/resources/nonExistent.csv";

    @BeforeAll
    static void beforeAll() {
        reader = new FileReaderImpl();
    }

    @Test
    void read_correctFile_Ok() {
        List<String> actualData = reader.read(CORRECT_FILE_PATH);
        List<String> expectedData = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        assertEquals(expectedData, actualData);
    }

    @Test
    void read_NonExistentFile_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            reader.read(NON_EXISTENT_FILE_PATH);
        });
    }
}
