package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvParserServiceTest {
    private static CsvParserService csvParserService;

    @BeforeAll
    static void setUp() {
        csvParserService = new CsvParserService();
    }

    @Test
    void parse_emptyList_ok() {
        List<String> csvLines = Arrays.asList();

        List<FruitTransaction> fruitTransactions = csvParserService.parseCsv(csvLines);

        assertNotNull(fruitTransactions);
        assertEquals(0, fruitTransactions.size());
    }

    @Test
    void parse_lines_ok() {
        List<String> csvLines = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );

        List<FruitTransaction> fruitTransactions = csvParserService.parseCsv(csvLines);

        assertNotNull(fruitTransactions);
        assertEquals(8, fruitTransactions.size());

        FruitTransaction fruitTransaction1 = fruitTransactions.get(0);
        assertNotNull(fruitTransaction1);
        assertEquals(FruitTransaction.Operation.BALANCE, fruitTransaction1.getOperation());
        assertEquals("banana", fruitTransaction1.getFruit());
        assertEquals(20, fruitTransaction1.getQuantity());

        FruitTransaction fruitTransaction2 = fruitTransactions.get(1);
        assertNotNull(fruitTransaction2);
        assertEquals(FruitTransaction.Operation.BALANCE, fruitTransaction2.getOperation());
        assertEquals("apple", fruitTransaction2.getFruit());
        assertEquals(100, fruitTransaction2.getQuantity());

        FruitTransaction fruitTransaction3 = fruitTransactions.get(2);
        assertNotNull(fruitTransaction3);
        assertEquals(FruitTransaction.Operation.SUPPLY, fruitTransaction3.getOperation());
        assertEquals("banana", fruitTransaction3.getFruit());
        assertEquals(100, fruitTransaction3.getQuantity());

        FruitTransaction fruitTransaction4 = fruitTransactions.get(3);
        assertNotNull(fruitTransaction4);
        assertEquals(FruitTransaction.Operation.PURCHASE, fruitTransaction4.getOperation());
        assertEquals("banana", fruitTransaction4.getFruit());
        assertEquals(13, fruitTransaction4.getQuantity());

        FruitTransaction fruitTransaction5 = fruitTransactions.get(4);
        assertNotNull(fruitTransaction5);
        assertEquals(FruitTransaction.Operation.RETURN, fruitTransaction5.getOperation());
        assertEquals("apple", fruitTransaction5.getFruit());
        assertEquals(10, fruitTransaction5.getQuantity());

        FruitTransaction fruitTransaction6 = fruitTransactions.get(5);
        assertNotNull(fruitTransaction6);
        assertEquals(FruitTransaction.Operation.PURCHASE, fruitTransaction6.getOperation());
        assertEquals("apple", fruitTransaction6.getFruit());
        assertEquals(20, fruitTransaction6.getQuantity());

        FruitTransaction fruitTransaction7 = fruitTransactions.get(6);
        assertNotNull(fruitTransaction7);
        assertEquals(FruitTransaction.Operation.PURCHASE, fruitTransaction7.getOperation());
        assertEquals("banana", fruitTransaction7.getFruit());
        assertEquals(5, fruitTransaction7.getQuantity());

        FruitTransaction fruitTransaction8 = fruitTransactions.get(7);
        assertNotNull(fruitTransaction8);
        assertEquals(FruitTransaction.Operation.SUPPLY, fruitTransaction8.getOperation());
        assertEquals("banana", fruitTransaction8.getFruit());
        assertEquals(50, fruitTransaction8.getQuantity());
    }

    @Test
    void parse_largeQuantity_ok() {
        List<String> csvLines = Arrays.asList(
                "type,fruit,quantity",
                "p,apple,1000000"
        );
        List<FruitTransaction> fruitTransactions = csvParserService.parseCsv(csvLines);
        assertNotNull(fruitTransactions);
        assertEquals(1, fruitTransactions.size());

        FruitTransaction fruitTransaction1 = fruitTransactions.get(0);
        assertNotNull(fruitTransaction1);
        assertEquals(FruitTransaction.Operation.PURCHASE, fruitTransaction1.getOperation());
        assertEquals("apple", fruitTransaction1.getFruit());
        assertEquals(1000000, fruitTransaction1.getQuantity());
    }

    @Test
    void parse_zeroQuantity_ok() {
        List<String> csvLines = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,0"
        );

        List<FruitTransaction> fruitTransactions = csvParserService.parseCsv(csvLines);
        assertNotNull(fruitTransactions);
        assertEquals(1, fruitTransactions.size());

        FruitTransaction fruitTransaction1 = fruitTransactions.get(0);
        assertNotNull(fruitTransaction1);
        assertEquals(FruitTransaction.Operation.BALANCE, fruitTransaction1.getOperation());
        assertEquals("banana", fruitTransaction1.getFruit());
        assertEquals(0, fruitTransaction1.getQuantity());
    }

    @Test
    void parse_lines_notOk() {
        String line = "b,apple,invalidQuantity";

        assertThrows(RuntimeException.class,
                () -> csvParserService.parseCsvLine(line));
    }

    @Test
    void parse_emptyLines_notOk() {
        String line = "";

        assertThrows(RuntimeException.class,
                () -> csvParserService.parseCsvLine(line));
    }
}
