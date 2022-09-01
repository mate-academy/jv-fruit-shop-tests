package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String VALID_FILE = "src/test/resources/validFile.csv";
    private static final int FIRST_LINE = 0;
    private static final int SECOND_LINE = 1;
    private static final int THIRD_LINE = 2;
    private static ReaderService reader;

    @BeforeClass
    public static void beforeClass() {
        reader = new ReaderServiceImpl();
    }

    @Test
    public void validFile_ok() {
        List<String> strings = reader.readFromFile(VALID_FILE);
        Assert.assertEquals("Expected number of lines is 3", 3, strings.size());
        Assert.assertEquals("Expected title is 'fruit,quantity'",
                "fruit,quantity", strings.get(FIRST_LINE));
        Assert.assertEquals("Expected second line is 'b,apple,10'",
                "b,apple,10", strings.get(SECOND_LINE));
        Assert.assertEquals("Expected third line is 'r,banana,20'",
                "r,banana,20", strings.get(THIRD_LINE));
    }
}
