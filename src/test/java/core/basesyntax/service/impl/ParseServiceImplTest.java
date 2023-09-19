package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.FruitShopException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParseServiceImplTest {
    private static final int BALANCE_INDEX = 0;
    private static final int SUPPLY_INDEX = 1;
    private static final int PURCHASE_INDEX = 2;
    private static final int RETURN_INDEX = 3;
    private static final String FRUIT = "banana";
    private static final int QUANTITY = 100;
    private static ParseService parseService;
    private static List<String> inputData;
    private static List<String> emptyList;
    private static List<FruitTransaction> parseData;

    @BeforeAll
    static void beforeAll() {
        parseService = new ParseServiceImpl();
    }

    @Test
    void parseNull_NotOk() {
        emptyList = new ArrayList<>();
        assertThrows(FruitShopException.class, () -> parseService.parseInputData(emptyList));
    }

    @Test
    void correctParseData_Ok() {
        inputData = new ArrayList<>(Arrays.asList(
                "type,fruit,quantity",
                "b,banana,100",
                "s,banana,100",
                "p,banana,100",
                "r,banana,100"));
        parseData = parseService.parseInputData(inputData);
        FruitTransaction balanceTransaction = parseData.get(BALANCE_INDEX);
        assertEquals(FruitTransaction.Operation.BALANCE, balanceTransaction.getOperation());
        assertEquals(FRUIT, balanceTransaction.getFruit());
        assertEquals(QUANTITY, balanceTransaction.getQuantity());

        FruitTransaction supplyTransaction = parseData.get(SUPPLY_INDEX);
        assertEquals(FruitTransaction.Operation.SUPPLY, supplyTransaction.getOperation());
        assertEquals(FRUIT, supplyTransaction.getFruit());
        assertEquals(QUANTITY, supplyTransaction.getQuantity());

        FruitTransaction purchaseTransaction = parseData.get(PURCHASE_INDEX);
        assertEquals(FruitTransaction.Operation.PURCHASE, purchaseTransaction.getOperation());
        assertEquals(FRUIT, purchaseTransaction.getFruit());
        assertEquals(QUANTITY, purchaseTransaction.getQuantity());

        FruitTransaction returnTransaction = parseData.get(RETURN_INDEX);
        assertEquals(FruitTransaction.Operation.RETURN, returnTransaction.getOperation());
        assertEquals(FRUIT, returnTransaction.getFruit());
        assertEquals(QUANTITY, returnTransaction.getQuantity());
    }
}
