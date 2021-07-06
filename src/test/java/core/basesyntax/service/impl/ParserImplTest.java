package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserImplTest {
    private static Parser parser;
    private static List<Transaction> correctFruit;
    private static final String CORRECT_DATA = "p,fruit,21";

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserImpl(new ValidatorImpl());
        correctFruit = new ArrayList<>();
        correctFruit.add(new Transaction(OperationType.PURCHASE, "fruit", 21));
    }

    @After
    public void setUp() {
        Storage.getFruits().clear();
    }

    @Test
    public void parseFromEmptyList_NotOk() {
        List<String> testLines = new ArrayList<>();
        List<Transaction> actual = parser.parseLines(testLines);
        List<Transaction> expected = new ArrayList<>();
        assertEquals(expected, actual);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void parseListWithInvalidValue_NotOk() {
        List<String> testLines = new ArrayList<>();
        testLines.add("type,fruit,quantity");
        List<Transaction> actual = parser.parseLines(testLines);
        assertEquals(correctFruit.get(0).getQuantity(), actual.get(0).getQuantity());
        assertEquals(correctFruit.get(0).getName(), actual.get(0).getName());
        assertEquals(correctFruit.get(0).getOperationType(), actual.get(0).getOperationType());
    }

    @Test
    public void parseListWithValidValue_Ok() {
        List<String> testLines = new ArrayList<>();
        testLines.add("type,fruit,quantity");
        testLines.add(CORRECT_DATA);
        List<Transaction> actual = parser.parseLines(testLines);
        assertEquals(correctFruit.get(0).getQuantity(), actual.get(0).getQuantity());
        assertEquals(correctFruit.get(0).getName(), actual.get(0).getName());
        assertEquals(correctFruit.get(0).getOperationType(), actual.get(0).getOperationType());
    }
}
