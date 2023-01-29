package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.serviceimpl.ParseLineImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ParseLineServiceTest {
    private static ParseLineImpl parseLine;

    @Test
    public void parseLineTest_isParseData_OK() {
        parseLine = new ParseLineImpl();
        List<String> lines = new ArrayList<>();
        lines.add("b,apple,20");
        lines.add("s,apple,10");
        lines.add("b,banana,120");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,"apple",20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,"apple",10));
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,"banana",120));
        List<FruitTransaction> actual = parseLine.getFruitTransaction(lines);
        assertEquals(expected, actual);
    }
}
