package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.TransactionParser;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {
    public static final String BANANA = "banana";
    public static final int BANANA_BALANCE = 200;
    public static final int BANANA_PURCHASE = 50;
    private TransactionParser parser;
    private List<String> goodLines = List.of(
            "b,banana,200",
            "p,banana,50"
    );
    private List<String> badLines = List.of(
            "b,banana",
            "p,banana,50"
    );

    @BeforeEach
    void setUp() {
        parser = new TransactionParserImpl();
    }

    @Test
    void parse_ok() {
        FruitTransaction fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setOperation(Operation.BALANCE);
        fruitTransaction1.setFruit(BANANA);
        fruitTransaction1.setQuantity(BANANA_BALANCE);
        FruitTransaction fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setOperation(Operation.PURCHASE);
        fruitTransaction2.setFruit(BANANA);
        fruitTransaction2.setQuantity(BANANA_PURCHASE);

        List<FruitTransaction> expected = List.of(
                fruitTransaction1,
                fruitTransaction2
        );

        List<FruitTransaction> actual = parser.parseData(goodLines);
        assertEquals(expected, actual, "Wrong work of parse method");
    }

    @Test
    void parse_badLine_notOk() {
        assertThrows(RuntimeException.class, () -> parser.parseData(badLines),
                "Method parse can't create FruitTransaction from such lines");
    }

    @Test
    void parse_Null_Input_notOk() {
        assertThrows(RuntimeException.class,
                () -> parser.parseData(null));
    }
}
