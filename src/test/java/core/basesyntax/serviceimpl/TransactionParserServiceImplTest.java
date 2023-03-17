package core.basesyntax.serviceimpl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParserService;
import org.junit.Test;

import static org.junit.Assert.*;

public class TransactionParserServiceImplTest {
    TransactionParserService parser = new TransactionParserServiceImpl();
    FruitTransaction fruitTransaction = new FruitTransaction();

    @Test(expected = RuntimeException.class)
    public void parse_InputNull_notOk() {
        parser.saveToStorage(null);
    }

    @Test(expected = RuntimeException.class)
    public void parse_WrongDataFormatLess_notOk() {
        parser.saveToStorage("abra codabra");
    }

    @Test(expected = RuntimeException.class)
    public void parse_WrongDataFormatMore_notOk() {
        parser.saveToStorage("abra,codabra,codabra,abra");
    }

    @Test
    public void parse_ValidData_ok() {
        FruitTransaction actual = parser.saveToStorage("b,apricot,1000");
        String code = actual.getOperation().getCode();
        String fruit = actual.getFruit();
        int quantity = actual.getQuantity();
        assertTrue(code.equals("b") && fruit.equals("apricot") && quantity == 1000);
    }
}