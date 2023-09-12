package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReaderServiceImplTest {
    private static final String NONEXISTENT_FILE = "src/test/resources/notExist.csv";
    private static final String VALID_FILE = "src/test/resources/test.csv";
    private static final String HEADER = "type,fruit,quantity";
    private static final String REGULAR_LINE = "b,banana,20";
    private static ReaderService reader;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        reader = new ReaderServiceImpl();
    }

    @Test
    public void read_nonExistingFile_notOk() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Can't get data from file " + NONEXISTENT_FILE);
        reader.read(NONEXISTENT_FILE);
    }

    @Test
    public void read_regularCase_Ok() {
        List<String> expectedList = List.of(HEADER, REGULAR_LINE);
        assertEquals(expectedList, reader.read(VALID_FILE));
    }

    @Test
    public void read_nullAsArgument_notOk() {
        thrown.expect(NullPointerException.class);
        reader.read(null);
    }
}

