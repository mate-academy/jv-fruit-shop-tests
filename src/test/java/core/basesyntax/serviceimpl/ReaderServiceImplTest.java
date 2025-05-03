package core.basesyntax.serviceimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.List;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String FILE_PATH = "src/test/resources/input.csv";
    private static final ReaderServiceImpl fileReaderService = new ReaderServiceImpl();

    @Test
    public void readData_invalidPath_noOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReaderService.readFromFile("asd.asd");
        });
    }

    @Test
    public void readData_ok() {
        List<String> stringsFromFile = fileReaderService.readFromFile(FILE_PATH);
        String expectedFirstString = "b,banana,20";
        String actualFirstString = stringsFromFile.get(0);
        assertEquals(expectedFirstString, actualFirstString);
    }

}
