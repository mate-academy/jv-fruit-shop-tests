package core.basesyntax.model.dto.impl;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private static final String FILE_NAME = "Operations.csv";
    private static final String INVALID_FILE_NAME = "Invalid_fileName.csv";
    private static FileReaderImpl fileReader;

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void fileReadTest_Ok() {
        List<String> expected = List.of("type,  fruit,   quantity",
                " b,    banana,     20",
                " b,    apple,      100",
                " s,    banana,     100",
                " p,    banana,     13",
                " r,    apple,      10",
                " p,    apple,      20",
                " p,    banana,     5",
                " p,    banana,     50",
                " s,    banana,     50");
        List<String> actual = fileReader.readFile(FILE_NAME);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void writeFileWithWrongInput_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                fileReader.readFile(INVALID_FILE_NAME));
    }

}
