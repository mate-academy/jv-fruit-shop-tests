package service.impl;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.ParserService;

public class ParserServiceImplTest {
    private static ParserService parserService;

    @BeforeClass
    public static void beforeClass() {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void parserService_validWork_ok() {
        String[] expected = new String[]{"p", "apple", "153"};
        Assert.assertArrayEquals(expected, parserService.parser("p,apple,153"));
    }
}
