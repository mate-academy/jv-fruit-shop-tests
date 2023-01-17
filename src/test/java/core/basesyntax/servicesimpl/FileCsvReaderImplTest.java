package core.basesyntax.servicesimpl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.services.FileCsvReader;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileCsvReaderImplTest {
    private final String filePath = "src/test/resources/FiletoRead1.csv";
    private List<String> expected;
    private FileCsvReader reader;

    @Before
    public void setUp() {
        reader = new FileCsvReaderImpl();
        expected = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "b,kivi,100", "s,banana,100",
                "p,kivi,100", "p,banana,13", "r,apple,10", "p,apple,20",
                "p,banana,5", "s,kivi,5", "s,kivi,20", "s,banana,50");
    }

    @Test
    public void readFromFile_ok() {
        List<String> actual = reader.readFromFile(filePath);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_wrongPath_notOk() {
        reader.readFromFile("src/test/resources/wrongName.csv");
    }
}
