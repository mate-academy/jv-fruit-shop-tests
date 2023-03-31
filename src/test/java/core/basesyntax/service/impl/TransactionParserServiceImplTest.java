package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParserService;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserServiceImplTest {
    private static final String TWO_WORDS_WITHOUT_SEPARATOR = "abra codabra";
    private static final String FOUR_WORDS_WITH_SEPARATOR = "abra,codabra,codabra,abra";
    private static TransactionParserService parser;

    @BeforeClass
    public static void beforeClass() {
        parser = new TransactionParserServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void parse_inputNull_notOk() {
        parser.saveToStorage(null);
    }

    @Test(expected = RuntimeException.class)
    public void parse_wrongDataFormatLess_notOk() {
        parser.saveToStorage(TWO_WORDS_WITHOUT_SEPARATOR);
    }

    @Test(expected = RuntimeException.class)
    public void parse_WrongDataFormatMore_notOk() {
        parser.saveToStorage(FOUR_WORDS_WITH_SEPARATOR);
    }

    @Test
    public void parse_validData_ok() {
        String expected = "b,apricot,1000";
        FruitTransaction actual = parser.saveToStorage(expected);
        String code = actual.getOperation().getCode();
        String fruit = actual.getFruit();
        int quantity = actual.getQuantity();
        assertEquals("Code must be same!","b", code);
        assertEquals("Fruit must be same!", "apricot", fruit);
        assertEquals("Quantity must be same!", 1000, quantity);
    }
}
