package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class DataToCsvFileImplTest {
    private final DataToCsvFile dataToCsvFile = new DataToCsvFileImpl();
    private final List<String> listToWrite = Arrays.asList("apple,50", "banana,2");

    @Test
    public void returnListWithTitle_Ok() {
        List<String> exepted = Arrays.asList("fruit,quantity","apple,50", "banana,2");
        List<String> actual = dataToCsvFile.generateListToWriteFile(listToWrite,
                "src/main/resources/testfiledatatocsv.csv");
        assertEquals(exepted, actual);
    }

    @Test (expected = RuntimeException.class)
    public void nullFileName_Ok() {
        dataToCsvFile.generateListToWriteFile(listToWrite,
                null);
    }
}
