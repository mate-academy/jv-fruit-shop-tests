package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileReaderTest {
    private static final String FILE_NAME = "src/test/resources/activities.csv";
    private static final String UNEXISTED_FILE_NAME = "src/test/resources/activities2.csv";
    private static FileReader csvFileReader;

    @BeforeAll
    static void beforeAll() {
        csvFileReader = new CsvFileReader();
    }

    @Test
    void getLinesFromFile_validName_Ok() {
        List<String> actual = csvFileReader.getLinesFromFile(FILE_NAME);
        List<String> expected = List.of(
                "type,fruit,quantity", "b,banana,20", "b,apple,100", "s,banana,100", "p,banana,13");
        assertEquals(expected, actual, "CsvFileReader doesnt work properly");
    }

    @Test
    void getLinesFromFile_invalidName_notOk() {
        assertThrows(RuntimeException.class,
                () -> {
                    csvFileReader.getLinesFromFile(UNEXISTED_FILE_NAME);
                    csvFileReader.getLinesFromFile(null);
                },
                "Reading unexisted file should throw exception");
    }
}
