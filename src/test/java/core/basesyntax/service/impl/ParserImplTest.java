package core.basesyntax.service.impl;

import core.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ParserImplTest {
    private static final Parser parser = new ParserImpl();
    private static final List<String> list = new ArrayList<>();

    @Before
    public void setUp() {
        list.add("type,fruit,quantity");
    }

    @Test(expected = RuntimeException.class)
    public void parseString_notOk() {
        list.add("");
        parser.parse(list);
    }

    @Test(expected = RuntimeException.class)
    public void parseInvalidValue_notOk() {
        list.add("m,pineapple,30");
        parser.parse(list);
    }

    @After
    public void clearList() {
        list.clear();
    }
}
