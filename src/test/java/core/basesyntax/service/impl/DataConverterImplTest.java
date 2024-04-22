package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {

    private static DataConverter dataConverter;
    private static List<FruitTransaction> checkTransList;
    private static List<String> checkTransStrings;
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final FruitTransaction BALANCE_OPERATION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, 20);
    private static final FruitTransaction SUPPLY_OPERATION =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 100);
    private static final FruitTransaction PURCHASE_OPERATION =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 13);
    private static final FruitTransaction RETURN_OPERATION =
            new FruitTransaction(FruitTransaction.Operation.RETURN, BANANA, 10);
    private static final String FIRST_LINE = "type,fruit,quantity";
    private static final String BANANA_BALANCE = "b,banana,20";
    private static final String BANANA_SUPPLY = "s,banana,100";
    private static final String BANANA_PURCHASE = "p,banana,13";
    private static final String BANANA_RETURN = "r,banana,10";

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
        checkTransList = List.of(
                BALANCE_OPERATION,
                SUPPLY_OPERATION,
                PURCHASE_OPERATION,
                RETURN_OPERATION
        );
        checkTransStrings = List.of(
                FIRST_LINE,
                BANANA_BALANCE,
                BANANA_SUPPLY,
                BANANA_PURCHASE,
                BANANA_RETURN
        );
    }

    @Test
    void emptyFile_NotOK() {
        assertThrows(
                RuntimeException.class, () -> dataConverter.convertToTransaction(
                        Collections.emptyList()
                ));
    }

    @Test
    void wrongTextFormat_NotOK() {
        List<String> transList = List.of("type","fruit", "quantity");
        assertThrows(
                IllegalArgumentException.class, () -> dataConverter.convertToTransaction(transList
                ));
    }

    @Test
    void validFile_OK() {
        assertEquals(
                checkTransList,
                dataConverter.convertToTransaction(checkTransStrings)
        );
    }
}
