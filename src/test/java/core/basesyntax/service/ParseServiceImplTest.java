package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.FruitShopException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParseServiceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParseServiceImplTest {
    private static ParseService parseService;

    @BeforeAll
    static void beforeAll() {
        parseService = new ParseServiceImpl();
    }

    @Test
    void parseData_null_NotOk() {
        List<String> emptyList = new ArrayList<>();
        assertThrows(FruitShopException.class, () -> parseService.parseInputData(emptyList));
    }

    @Test
    void parseData_correct_Ok() {
        List<String> inputData = new ArrayList<>(Arrays.asList(
                "type,fruit,quantity",
                "b,banana,100",
                "s,banana,100",
                "p,banana,100",
                "r,banana,100"));
        List<FruitTransaction> parseData = parseService.parseInputData(inputData);
        FruitTransaction balanceTransaction = parseData.get(0);
        assertEquals(FruitTransaction.Operation.BALANCE, balanceTransaction.getOperation());
        assertEquals("banana", balanceTransaction.getFruit());
        assertEquals(100, balanceTransaction.getQuantity());

        FruitTransaction supplyTransaction = parseData.get(1);
        assertEquals(FruitTransaction.Operation.SUPPLY, supplyTransaction.getOperation());
        assertEquals("banana", supplyTransaction.getFruit());
        assertEquals(100, supplyTransaction.getQuantity());

        FruitTransaction purchaseTransaction = parseData.get(2);
        assertEquals(FruitTransaction.Operation.PURCHASE, purchaseTransaction.getOperation());
        assertEquals("banana", purchaseTransaction.getFruit());
        assertEquals(100, purchaseTransaction.getQuantity());

        FruitTransaction returnTransaction = parseData.get(3);
        assertEquals(FruitTransaction.Operation.RETURN, returnTransaction.getOperation());
        assertEquals("banana", returnTransaction.getFruit());
        assertEquals(100, returnTransaction.getQuantity());
    }
}
