package core.basesyntax.shopservice;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class CsvReadServiceImplTest {
    private static final String FILE_PATH = "src/test/resources/testFile.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/emptyFile.csv";
    private static final String NON_EXISTING_FILE_PATH = "src/test/resources/FFile.csv";

    @Test
    public void readFromFile_NotEmptyData_Ok() {
        assertEquals(List.of("type,fruit,quantity",
                "b,apple,60",
                "b,banana,60",
                "p,apple,20",
                "p,banana,20"), new CsvReadServiceImpl()
                .readFromFile(FILE_PATH));
    }

    @Test
    public void readFromFile_EmptyData_Ok() {
        assertEquals(new ArrayList<>(), new CsvReadServiceImpl()
                .readFromFile(EMPTY_FILE_PATH));
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_NotExistingFile_Bad() {
        new CsvReadServiceImpl().readFromFile(NON_EXISTING_FILE_PATH);
    }
}
