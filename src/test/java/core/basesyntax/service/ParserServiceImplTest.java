package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.ParserService;
import service.impl.ParserServiceImpl;

public class ParserServiceImplTest {
    private static List<String> data;
    private static ParserService parserService;

    @BeforeAll
    static void beforeAll() {
        parserService = new ParserServiceImpl();
        data = List.of("b,banana,20",
                "s,grape,20",
                "p,banana,10");
    }

    @Test
    public void parserService_NullData_NotOk() {
        assertThrows(RuntimeException.class, () -> parserService.parseOperations(null));
    }

    @Test
    void parserService_emptyDataFromFile_NotOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> parserService.parseOperations(Arrays.asList()));
        assertEquals("Empty data from file", exception.getMessage());
    }

    @Test
    void parserService_Ok() {
        List<FruitTransaction> result = parserService.parseOperations(data);
        assertEquals(3, result.size());
        FruitTransaction fruitBananaBalance = result.get(0);
        assertEquals(FruitTransaction.Operation.BALANCE, fruitBananaBalance.getOperation());
        assertEquals("banana", fruitBananaBalance.getFruit());
        assertEquals(20, fruitBananaBalance.getQuantity());

        FruitTransaction fruitGrapeSupply = result.get(1);
        assertEquals(FruitTransaction.Operation.SUPPLY, fruitGrapeSupply.getOperation());
        assertEquals("grape", fruitGrapeSupply.getFruit());
        assertEquals(20, fruitGrapeSupply.getQuantity());

        FruitTransaction fruitBananaPurchase = result.get(2);
        assertEquals(FruitTransaction.Operation.PURCHASE, fruitBananaPurchase.getOperation());
        assertEquals("banana", fruitBananaPurchase.getFruit());
        assertEquals(10, fruitBananaPurchase.getQuantity());
    }
}
