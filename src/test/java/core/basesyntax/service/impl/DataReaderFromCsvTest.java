package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.DataReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataReaderFromCsvTest {
    private static final String FILE_PATH = "src/test/resources/data.csv";
    private static final String BAD_FILE_PATH = "src/test/resources/badData.csv";
    private static final String DATA = """
            type,fruit,quantity
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
    private static DataReader reader;
    private static List<String> expected;

    @BeforeAll
    static void beforeAll() throws IOException {
        reader = new DataReaderFromCsv();
        Files.write(Path.of(FILE_PATH), DATA.getBytes());
    }

    @Test
    void readData_isOk() {
        List<String> actualList = reader.readData(FILE_PATH);

        assertEquals(EXPECTED_LIST, actualList);
    }

    @Test
    void readData_fileNotExistOrBadPath_notOk() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reader.readData(BAD_FILE_PATH);
        }, "If file does not exist or path is incorrect it should throw Exception!!!");

        String expectedMessage = "Can't read data from file: ";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
