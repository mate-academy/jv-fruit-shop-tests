package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.ParserService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceImplTest {
    private static ParserService parserService;
    private List<String> lines;

    @BeforeClass
    public static void beforeClass() {
        parserService = new ParserServiceImpl();
    }

    @Before
    public void setUp() throws IOException {
        lines = Files.readAllLines(Path.of("src/test/resources/read.csv"));
    }

    @Test
    public void parse_validData_OK() {
        List<Transaction> actual = parserService.parse(lines);
        List<Transaction> expected = new ArrayList<>();
        expected.add(new Transaction("b", new Fruit("banana"), 20));
        expected.add(new Transaction("s", new Fruit("banana"), 100));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void parse_NullList_NotOK() {
        List<Transaction> actual = parserService.parse(null);
    }

    @After
    public void tearDown() {
        lines.clear();
    }
}
