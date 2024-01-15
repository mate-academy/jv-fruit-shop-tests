package core.basesyntax.model.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataParserImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataParserImplTest {
    private static final String PURCHASE_TEST = "p,banana,13";
    private static final String QUANTITY_TEST = "b,banana,20";
    private static final String RETURN_TEST = "r,apple,10";
    private static final String SUPPLY_TEST = "s,banana,100";
    private static final String UNEXPECTED_FRUIT = "UnknownFruit";
    private static final int UNEXPECTED_AMOUNT = 0;
    private static final String UNEXPECTED_OPERATION = "a";
    private static final int INDEX = 0;
    private DataParserImpl dataParser;

    @BeforeEach
    public void setUp() {
        dataParser = new DataParserImpl();
    }

    @Test
    void parseData_purchaseTest_notOk() {
        List<String> purchaseTestFruit = List.of(PURCHASE_TEST);
        List<FruitTransaction> parsedFruit = dataParser.parse(purchaseTestFruit);
        assertNotEquals(UNEXPECTED_FRUIT, parsedFruit.get(INDEX).getName(),
                "The fruit is unknown");
        assertNotEquals(UNEXPECTED_OPERATION, parsedFruit.get(INDEX).getType().getCode(),
                "The operation is unknown");
        assertFalse(UNEXPECTED_AMOUNT > parsedFruit.get(INDEX).getAmount(),
                "The fruit's amount isn't correct");
    }

    @Test
    void parseData_purchaseTest_ok() {
        List<String> purchaseTestFruit = List.of(PURCHASE_TEST);
        List<FruitTransaction> parsedFruit = dataParser.parse(purchaseTestFruit);
        assertEquals("p", parsedFruit.get(INDEX).getType().getCode());
        assertEquals("banana", parsedFruit.get(INDEX).getName());
        assertEquals(13, parsedFruit.get(INDEX).getAmount());
    }

    @Test
    void parseData_quantityTest_notOk() {
        List<String> purchaseTestFruit = List.of(QUANTITY_TEST);
        List<FruitTransaction> parsedFruit = dataParser.parse(purchaseTestFruit);
        assertNotEquals(UNEXPECTED_FRUIT, parsedFruit.get(INDEX).getName(),
                "The fruit is unknown");
        assertNotEquals(UNEXPECTED_OPERATION, parsedFruit.get(INDEX).getType().getCode(),
                "The operation is unknown");
        assertFalse(UNEXPECTED_AMOUNT > parsedFruit.get(INDEX).getAmount(),
                "The fruit's amount isn't correct");
    }

    @Test
    void parseData_quantityTest_ok() {
        List<String> purchaseTestFruit = List.of(QUANTITY_TEST);
        List<FruitTransaction> parsedFruit = dataParser.parse(purchaseTestFruit);
        assertEquals("b", parsedFruit.get(INDEX).getType().getCode());
        assertEquals("banana", parsedFruit.get(INDEX).getName());
        assertEquals(20, parsedFruit.get(INDEX).getAmount());
    }

    @Test
    void parseData_returnTest_notOk() {
        List<String> purchaseTestFruit = List.of(RETURN_TEST);
        List<FruitTransaction> parsedFruit = dataParser.parse(purchaseTestFruit);
        assertNotEquals(UNEXPECTED_FRUIT, parsedFruit.get(INDEX).getName(),
                "The fruit is unknown");
        assertNotEquals(UNEXPECTED_OPERATION, parsedFruit.get(INDEX).getType().getCode(),
                "The operation is unknown");
        assertFalse(UNEXPECTED_AMOUNT > parsedFruit.get(INDEX).getAmount(),
                "The fruit's amount isn't correct");
    }

    @Test
    void parseData_returnTest_ok() {
        List<String> purchaseTestFruit = List.of(RETURN_TEST);
        List<FruitTransaction> parsedFruit = dataParser.parse(purchaseTestFruit);
        assertEquals("r", parsedFruit.get(INDEX).getType().getCode());
        assertEquals("apple", parsedFruit.get(INDEX).getName());
        assertEquals(10, parsedFruit.get(INDEX).getAmount());
    }

    @Test
    void parseData_supplyTest_notOk() {
        List<String> purchaseTestFruit = List.of(SUPPLY_TEST);
        List<FruitTransaction> parsedFruit = dataParser.parse(purchaseTestFruit);
        assertNotEquals(UNEXPECTED_FRUIT, parsedFruit.get(INDEX).getName(),
                "The fruit is unknown");
        assertNotEquals(UNEXPECTED_OPERATION, parsedFruit.get(INDEX).getType().getCode(),
                "The operation is unknown");
        assertFalse(UNEXPECTED_AMOUNT > parsedFruit.get(INDEX).getAmount(),
                "The fruit's amount isn't correct");
    }

    @Test
    void parseData_supplyTest_ok() {
        List<String> purchaseTestFruit = List.of(SUPPLY_TEST);
        List<FruitTransaction> parsedFruit = dataParser.parse(purchaseTestFruit);
        assertEquals("s", parsedFruit.get(INDEX).getType().getCode());
        assertEquals("banana", parsedFruit.get(INDEX).getName());
        assertEquals(100, parsedFruit.get(INDEX).getAmount());
    }
}
