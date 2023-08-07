package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class CsvFileReaderTest {

    private static final FileReader csvFileReader = new CsvFileReader();
    private static final String FILE_NAME = "src/test/resources/activities.csv";
    private static final String UNEXISTED_FILE_NAME = "src/test/resources/activities2.csv";

    @Test
    void getLinesFromFile_validName_Ok() {
        List<String> actual = csvFileReader.getLinesFromFile(FILE_NAME);
        List<String> expected = new ArrayList<>() {{
                add("type,fruit,quantity");
                add("b,banana,20");
                add("b,apple,100");
                add("s,banana,100");
                add("p,banana,13");
            }};
        assertEquals(expected, actual, "CsvFileReader doesnt work properly");
    }

    @Test
    void getLinesFromFile_invalidName_NotOk() {
        assertThrows(RuntimeException.class,
                () -> {
                    csvFileReader.getLinesFromFile(UNEXISTED_FILE_NAME);
                    csvFileReader.getLinesFromFile(null);
                },
                "Reading unexisted file should throw exception");
    }
}
