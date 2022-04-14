package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.OperationType;
import core.basesyntax.model.dtos.FruitDtoTransaction;
import core.basesyntax.services.interfaces.FruitDtoTransactionParser;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FruitDtoTransactionParserImplTest {
    private FruitDtoTransactionParser fruitDtoTransactionParser;
    private List<String> lines;
    private List<FruitDtoTransaction> fruitDtoTransactions;

    @Before
    public void setUp() {
        fruitDtoTransactionParser = new FruitDtoTransactionParserImpl();
        lines = List.of("b,banana,20", "b,apple,50");
    }

    @Test
    public void test_ifParserReturnRightFruitName_Ok() {
        fruitDtoTransactions = fruitDtoTransactionParser.parse(lines);
        String excepted = "apple";
        String actual = fruitDtoTransactions.get(1).getFruitName();
        assertEquals(excepted, actual);
    }

    @Test
    public void test_ifParserReturnRightOperationType_Ok() {
        fruitDtoTransactions = fruitDtoTransactionParser.parse(lines);
        OperationType excepted = OperationType.BALANCE;
        OperationType actual = fruitDtoTransactions.get(1).getOperationType();
        assertEquals(excepted, actual);
    }

    @Test
    public void test_ifParserReturnRightQuantity_Ok() {
        fruitDtoTransactions = fruitDtoTransactionParser.parse(lines);
        Integer excepted = 20;
        Integer actual = fruitDtoTransactions.get(0).getFruitCount();
        assertEquals(excepted, actual);
    }
}
