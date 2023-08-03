package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.interfaces.TransactionParser;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportParserImplTest {
    private static final String COMMA_DIVIDER = ",";
    private static final String REPORT_HEADING = "fruit,quantity";

    private TransactionParser<String, Map<Fruit, Integer>> reportParser;

    @BeforeEach
    void setUp() {
        reportParser = new ReportParserImpl();
    }

    @Test
    void parse() {
        Map<Fruit, Integer> storage = new HashMap<>(Map.of(Fruit.BANANA,165));
        String expected = REPORT_HEADING + System.lineSeparator()
                + "banana" + COMMA_DIVIDER + "165";
        String actual = reportParser.parse(storage);
        Assertions.assertEquals(expected, actual, "Parser doesn't parse data correctly!");
    }
}
