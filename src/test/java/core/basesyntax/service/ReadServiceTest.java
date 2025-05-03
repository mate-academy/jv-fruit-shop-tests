package core.basesyntax.service;

import core.basesyntax.service.impl.ReadServiceImpl;
import java.io.File;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadServiceTest {
    private static final String WRONG_FILE_PATH = "src/main/resources/wrong.csv";
    private static final String FILE_PATH = "src/main/resources/input.csv";
    private static String expectedContent;
    private static ReadService readService;

    @BeforeClass
    public static void beforeAll() {
        readService = new ReadServiceImpl();
        expectedContent = initExpectedContent();
    }

    @Test(expected = RuntimeException.class)
    public void readService_wrongPath_notOk() {
        readService.read(new File(WRONG_FILE_PATH));
    }

    @Test
    public void readService_correct_Ok() {
        List<String> read = readService.read(new File(FILE_PATH));
        String actual = String.join(System.lineSeparator(), read);
        Assert.assertEquals(System.lineSeparator()
                + "Content must be equals but expected: "
                + System.lineSeparator()
                + expectedContent + System.lineSeparator()
                + "and actual: " + System.lineSeparator()
                + actual, expectedContent, actual);
    }

    private static String initExpectedContent() {
        return "    type,fruit,quantity" + System.lineSeparator()
                + "    b,banana,20" + System.lineSeparator()
                + "    b,apple,100" + System.lineSeparator()
                + "    s,banana,100" + System.lineSeparator()
                + "    p,banana,13" + System.lineSeparator()
                + "    r,apple,10" + System.lineSeparator()
                + "    p,apple,20" + System.lineSeparator()
                + "    p,banana,5" + System.lineSeparator()
                + "    s,banana,50";
    }
}
