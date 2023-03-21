package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.io.File;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReaderServiceImplTest {
    private static final String VALID_FILE = "src/test/resources/test.csv";
    private static final String NON_EXISTENT_PATH = "src/test/resources/test123123.csv";
    private static final String HEADER = "type,fruit,quantity";
    private static final String VALID_LINE = "s,apple,55";
    private static ReaderService reader = new ReaderServiceImpl();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        reader = new ReaderServiceImpl();
    }

    @Test
    public void readDataFromFile_ValidInput_Ok() {
        List<String> expected = List.of(HEADER, VALID_LINE);
        List<String> actual = reader.readDataFromFile(new File(VALID_FILE));
        assertEquals(expected, actual);
    }

    @Test
    public void readDataFromFile_NonExistentFilePath_NotOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Can't read data from the file "
                + new File(NON_EXISTENT_PATH).getName());
        reader.readDataFromFile(new File(NON_EXISTENT_PATH));
    }

    @Test
    public void readDataFromFile_NullArgumentAsInput_NotOk() {
        exceptionRule.expect(NullPointerException.class);
        reader.readDataFromFile(null);
    }
}
