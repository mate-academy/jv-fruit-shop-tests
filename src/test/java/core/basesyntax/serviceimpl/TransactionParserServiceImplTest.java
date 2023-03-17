package core.basesyntax.serviceimpl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParserService;
import org.junit.Test;

public class TransactionParserServiceImplTest {
    private static final String TWO_WORDS_WITHOUT_SEPARATOR = "abra codabra";
    private static final String FOUR_WORDS_WITH_SEPARATOR = "abra,codabra,codabra,abra";
    private final TransactionParserService parser = new TransactionParserServiceImpl();

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
        FruitTransaction actual = parser.saveToStorage("b,apricot,1000");
        String code = actual.getOperation().getCode();
        String fruit = actual.getFruit();
        int quantity = actual.getQuantity();
        assertTrue(code.equals("b") && fruit.equals("apricot") && quantity == 1000);
    }
}
