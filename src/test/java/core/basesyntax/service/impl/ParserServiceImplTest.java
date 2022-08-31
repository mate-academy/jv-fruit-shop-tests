package core.basesyntax.service.impl;

import core.basesyntax.service.ParserService;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.strategy.impl.BalanceStrategy;
import core.basesyntax.strategy.impl.PurchaseStrategy;
import core.basesyntax.strategy.impl.ReturnStrategy;
import core.basesyntax.strategy.impl.SupplyStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParserServiceImplTest {
    private static List<String> strings;
    private static Map<String, Strategy> strategies;
    private static ParserService parserService;

    @Before
    public void setUp() throws Exception {
        strategies = new HashMap<>();
        strategies.put("b", new BalanceStrategy());
        strategies.put("s", new SupplyStrategy());
        strategies.put("p", new PurchaseStrategy());
        strategies.put("r", new ReturnStrategy());

        parserService = new ParserServiceImpl(strategies);

        strings = new ArrayList<>();
    }

    @Test
    public void parse_emptyListStrings_Ok() {
        Integer expected = 0;
        Integer actual = parserService.parse(strings).size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parse_listStrings_Ok() {
        strings.add("b,apple,100");
        strings.add("s,banana,100");
        strings.add("p,banana,13");
        strings.add("r,apple,10");
        Integer expected = 4;
        Integer actual = parserService.parse(strings).size();
        Assert.assertEquals(expected, actual);
    }
}
