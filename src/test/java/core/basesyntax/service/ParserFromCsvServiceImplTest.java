package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.FruitShopException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.interfaces.ParserFromCsvService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserFromCsvServiceImplTest {
    private static final String NAME_OF_FRUIT_BANANA = "banana";
    private static final int QUANTITY_OF_OPERATION = 20;
    private static final int FIRST_ELEMENT = 0;
    private static FruitTransaction fruitTransaction;
    private static ParserFromCsvService parser;
    private static List<String> EXPECTED = List.of(
            "type,fruit,quantity",
            "b,banana,20"
    );

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserFromCsvServiceImpl();
        fruitTransaction = new FruitTransaction();
    }

    @Test(expected = FruitShopException.class)
    public void parse_emptyList_notOk() {
        parser.parse(new ArrayList<>());
    }

    @Test
    public void parse_correctDataFruit_ok() {
        List<FruitTransaction> actual = parser.parse(EXPECTED);
        String expectedFruit = NAME_OF_FRUIT_BANANA;
        String actualFruit = actual.get(FIRST_ELEMENT).getFruit();
        assertEquals(expectedFruit, actualFruit);
    }

    @Test
    public void parse_correctDataQuantity_ok() {
        List<FruitTransaction> actual = parser.parse(EXPECTED);
        int expectedQuantity = QUANTITY_OF_OPERATION;
        int actualQuantity = actual.get(FIRST_ELEMENT).getQuantity();
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void parse_correctOperation_ok() {
        List<FruitTransaction> actual = parser.parse(EXPECTED);
        FruitTransaction.Operation expectedOperation = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actualOperation = actual.get(FIRST_ELEMENT).getOperation();
        assertEquals(expectedOperation, actualOperation);
    }
}
