package core.basesyntax.infrastructure.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderTest {
    private static final String OPERATION_LIST_FILE_PATH
            = "src/main/resources/operationslist.csv";

    @Test
    void readInnitDataOk() {
        String[] expectedStr = new String[] {
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        };
        List<String> expectedList = Arrays.asList(expectedStr);

        FileReader fileReader = new FileReaderImpl();
        List<String> actualList = fileReader.read(OPERATION_LIST_FILE_PATH);
        assertEquals(expectedList, actualList);
    }

    @Test
    void readInnitDataNotOk() {
        String[] expectedStr = new String[] {
                "b,banana,20",
                "b,apple,100",
                "s,banana,10",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        };
        List<String> expectedList = Arrays.asList(expectedStr);

        FileReader fileReader = new FileReaderImpl();
        List<String> actualList = fileReader.read(OPERATION_LIST_FILE_PATH);
        assertNotEquals(expectedList, actualList);

    }

    @Test
    void incorrectPathNotToReadOk() {
        FileReader fileReader = new FileReaderImpl();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileReader.read("/invalid/path/notPath"));

        assertEquals("Can't read from file", exception.getMessage());
    }

}
