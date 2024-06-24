package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.FileReader;

class FileReaderImplTest {
    private static FileReader fileReader;
    private static final String INPUT_TEST_FILE_OK = "src/test/java/service/impl/fromTestFile.csv";
    private static final String INPUT_TEST_FILE_NOT_OK
            = "src/test/java/service/impl/fromWrongTestFile.csv";

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_nullFileName_notOk() {
        assertThrows(NullPointerException.class, () -> fileReader.read(null));
    }

    @Test
    void read_wrongFormatOfFirstLine_notOk() {
        assertThrows(RuntimeException.class, () -> fileReader.read(INPUT_TEST_FILE_NOT_OK));
    }

    @Test
    void read_file_Ok() {
        List<String> expectedLines = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50",
                "p,banana,12"
        );
        List<String> lines = fileReader.read(INPUT_TEST_FILE_OK);
        assertEquals(expectedLines.size(), lines.size());
        for (int i = 0; i < expectedLines.size(); i++) {
            assertEquals(expectedLines.get(i), lines.get(i));
        }
    }
}
