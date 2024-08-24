package core.basesyntax.fileservise;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class CsvFileReaderImplTest {
    private static final CsvFileReader csvFileReader = new CsvFileReaderImpl();
    private static String fileName = "src/main/java/core/basesyntax/csvFiles/data.csv";
    private static final List<String> expectedResult = new ArrayList<>();

    @Test
    void wrongFileName() {
        fileName = "wrong name/path";
        assertThrows(RuntimeException.class, () -> {
            csvFileReader.getTransactionsFromFile(fileName);
        });
    }

    @Test
    void readValidFileNotNull() {
        assertNotNull(csvFileReader.getTransactionsFromFile(fileName));
    }

    @Test
    void readValidInputFileNamePath() {
        expectedResult.add("type,fruit,quantity");
        expectedResult.add("b,banana,20");
        expectedResult.add("b,apple,100");
        expectedResult.add("s,banana,100");
        expectedResult.add("p,banana,13");
        expectedResult.add("r,apple,10");
        expectedResult.add("p,apple,20");
        expectedResult.add("p,banana,5");
        expectedResult.add("s,banana,50");

        assertEquals(expectedResult, csvFileReader.getTransactionsFromFile(fileName));
    }
}
