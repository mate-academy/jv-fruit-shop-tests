package core.basesyntax.model.dto.impl;

import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {

    private static final String FILE_NAME = "Operations.csv";
    private static final String WRONG_FILE_NAME = "Invalid_fileName.csv";
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

        Assert.assertEquals(expected, actual);
    }
}
