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
    private ParserService parserService = new ParserServiceImpl();

    @BeforeAll
    static void beforeAll() {
        data = List.of("b,banana,20");
    }

    @Test
    public void parserService_NullData() {
        assertThrows(RuntimeException.class, () -> parserService.parseOperations(null));
    }

    @Test
    void emptyDataFromFile() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> parserService.parseOperations(Arrays.asList()));
        assertEquals("Empty data from file", exception.getMessage());
    }

    @Test
    void parsedData_Ok() {
        List<FruitTransaction> result = parserService.parseOperations(data);
        FruitTransaction fruit = result.get(0);
        assertEquals(1, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, fruit.getOperation());
        assertEquals("banana", fruit.getFruit());
        assertEquals(20, fruit.getQuantity());
    }
}
