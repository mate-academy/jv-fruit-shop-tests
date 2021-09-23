package core.basesyntax.service.parsing;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParsingImplTest {
    private static Parsing parsing;
    private static String INPUT_STRING = "b,orange,20";

    @BeforeClass
    public static void setUp() {
        parsing = new ParsingImpl();
    }

    @Test
    public void parsing_Ok() {
        String[] expected = new String[] {"b", "orange", "20"};
        String[] actual = parsing.parsing(INPUT_STRING);
        Assert.assertArrayEquals(expected, actual);
    }
}
