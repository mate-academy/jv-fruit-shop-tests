package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParserImplTest {
    private static Parser parser;

    @BeforeAll
    public static void setUp() {
        parser = new ParserImpl();
    }

    @Test
    public void parse_validData_ok() {
        String data = "b,apple,1";
        FruitTransaction expected = new FruitTransaction(Operation.BALANCE, "apple", 1);
        FruitTransaction actual = parser.parse(data);
        assertEquals(expected, actual, "Error parsing data " + data);

        data = "p,banana,0";
        expected = new FruitTransaction(Operation.PURCHASE, "banana", 0);
        actual = parser.parse(data);
        assertEquals(expected, actual, "Error parsing data " + data);

        data = "s,melon,15";
        expected = new FruitTransaction(Operation.SUPPLY, "melon", 15);
        actual = parser.parse(data);
        assertEquals(expected, actual, "Error parsing data " + data);

        data = "r,pear,7";
        expected = new FruitTransaction(Operation.RETURN, "pear", 7);
        actual = parser.parse(data);
        assertEquals(expected, actual, "Error parsing data " + data);
    }

    @Test
    public void parse_nullData_notOk() {
        assertThrows(RuntimeException.class, () -> parser.parse(null),
                "RuntimeException should be thrown if data string is null!");
    }

    @Test
    public void parse_negativeQuantity_notOk() {
        String data = "b,apple,-1";
        assertThrows(RuntimeException.class, () -> parser.parse(data),
                "RuntimeException should be thrown if quantity is negative");
    }

    @Test
    public void parse_illegalOperation_notOk() {
        String data = "t,apple,4";
        assertThrows(RuntimeException.class, () -> parser.parse(data),
                "RuntimeException should be thrown if operation is illegal");
    }

    @Test
    public void parse_wrongFruitName_notOk() {
        String data = "p,app123le,4";
        assertThrows(RuntimeException.class, () -> parser.parse(data),
                "RuntimeException should be thrown if fruit name is wrong");
    }

    @Test
    public void parse_longNumberInQuantity_notOk() {
        String data = "p,apple,2147483648";
        assertThrows(RuntimeException.class, () -> parser.parse(data),
                "RuntimeException should be thrown if quantity is bigger then "
                        + Integer.MAX_VALUE);
    }

    @Test
    void convertFruitDataToTransactions_onlyHeaderInData_ok() {
        List<String> fruitData = List.of("type,fruit,quantity");
        List<FruitTransaction> expected = new ArrayList<>();
        assertDoesNotThrow(() -> parser.convertFruitDataToTransactions(fruitData));
        List<FruitTransaction> actual = parser.convertFruitDataToTransactions(fruitData);
        assertEquals(expected, actual, "Error creating list of transactions"
                + " from data with only header!");
    }

    @Test
    void convertFruitDataToTransactions_validData_ok() {
        List<String> fruitData = List.of("type,fruit,quantity", "b,banana,10",
                "b,apple,7", "p,banana,3");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 10),
                new FruitTransaction(Operation.BALANCE, "apple", 7),
                new FruitTransaction(Operation.PURCHASE, "banana", 3));
        List<FruitTransaction> actual = parser.convertFruitDataToTransactions(fruitData);
        assertEquals(expected, actual, "Error creating list of transactions!");

        fruitData = new ArrayList<>();
        expected = new ArrayList<>();
        actual = parser.convertFruitDataToTransactions(fruitData);
        assertEquals(expected, actual, "Error creating list of transactions!");
    }

    @Test
    void convertFruitDataToTransactions_nullList_notOk() {
        assertThrows(RuntimeException.class,
                () -> parser.convertFruitDataToTransactions(null),
                "RuntimeException should ve thrown if list of data is null");
    }
}
