package core.basesyntax;

import static org.hamcrest.core.StringStartsWith.startsWith;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReaderServiceImplTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void readFile_Exception() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(startsWith("Can't read file"));
        ReaderService readerService = new ReaderServiceImpl();
        readerService.readFromFile("src/main/resources/undefinedFile.csv");
    }

    @Test
    public void readFileTest() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("    b,banana,20");
        expected.add("    b,apple,100");

        ReaderService readerService = new ReaderServiceImpl();
        List<String> actual = readerService.readFromFile("src/test/resources/dayActivity_test.csv");

        Assert.assertEquals(expected, actual);
    }
}
