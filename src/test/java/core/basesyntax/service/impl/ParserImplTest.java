package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.models.Fruit;
import core.basesyntax.models.Transaction;
import core.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserImplTest {
    private static Parser parser;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserImpl();
    }

    @Test
    public void parse_validData_Ok() {
        List<Transaction> expectedList = new ArrayList<>();
        expectedList.add(new Transaction("a", new Fruit("Apple"), 20));
        expectedList.add(new Transaction("b", new Fruit("Banana"), 30));
        List<String> inputData = new ArrayList<>();
        inputData.add("a,Apple,20");
        inputData.add("b,Banana,30");
        List<Transaction> actualList = parser.parse(inputData);
        assertEquals(expectedList, actualList);
    }

    @Test
    public void parse_invalidData_NotOk() {
        List<String> inputData = new ArrayList<>();
        inputData.add("as,Apple,2sad0");
        inputData.add("bwq,dsBanana,3dgs0");
        inputData.add("invalidString");
        List<Transaction> actualList = parser.parse(inputData);
        assertTrue(actualList.isEmpty());
    }

    @Test
    public void parse_mixedData_Ok() {
        List<String> inputData = new ArrayList<>();
        inputData.add("as,Apple,2sad0");
        inputData.add("b,Apple,20");
        inputData.add("bwq,dsBanana,3dgs0");
        inputData.add("invalidString");
        List<Transaction> actualList = parser.parse(inputData);
        Transaction transactionExpected = new Transaction("b", new Fruit("Apple"), 20);
        assertEquals(transactionExpected.getType(), actualList.get(0).getType());
        assertEquals(transactionExpected.getFruit(), actualList.get(0).getFruit());
        assertEquals(transactionExpected.getQuantity(), actualList.get(0).getQuantity());
    }
}
