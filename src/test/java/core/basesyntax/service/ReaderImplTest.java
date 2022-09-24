package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ReaderImplTest {
    private Reader reader;

    @Before
    public void setUp() {
        reader = new ReaderImpl();
    }

    @Test(expected = RuntimeException.class)
    public void read_nullValue_notOk() {
        List<String> data = reader.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void read_nonExistFile_notOk() {
        List<String> data =
                reader.readFromFile("src/main/java/core/basesyntax/db/nonExistSource.csv");
    }

    @Test
    public void read_existFile_ok() {
        List<String> data = reader.readFromFile("src/main/java/core/basesyntax/db/source.csv");
        assertTrue(data != null);
    }
}
