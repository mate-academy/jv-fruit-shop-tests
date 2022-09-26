package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ReaderImplTest {
    private static final String FIRST_LINE = "type,fruit,quantity";
    private static final String SECOND_LINE = "b,banana,20";
    private static final String THIRD_LINE = "b,apple,100";

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
        List<String> data =
                reader.readFromFile("src/main/java/core/basesyntax/db/source_three_lines.csv");
        assertNotNull(data);
        List<String> expected = new ArrayList<>();
        expected.add(FIRST_LINE);
        expected.add(SECOND_LINE);
        expected.add(THIRD_LINE);
        assertEquals(expected, data);
    }
}
