package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ConvertData;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConvertDataImplTest {
    private static ConvertData convertData;
    private static List<FruitTransaction> checkTransactionsList;
    private static List<String> checkTransactionsStrings;
    private static final String BANANA = "banana";
    private static final FruitTransaction BALANCE_OPERATION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, 20);
    private static final FruitTransaction SUPPLY_OPERATION =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 102);
    private static final FruitTransaction PURCHASE_OPERATION =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 40);
    private static final FruitTransaction RETURN_OPERATION =
            new FruitTransaction(FruitTransaction.Operation.RETURN, BANANA, 30);
    private static final String FIRST_STRING = "type;fruit;quantity";
    private static final String BANANA_BALANCE = "b;banana;20";
    private static final String BANANA_SUPPLY = "s;banana;102";
    private static final String BANANA_PURCHASE = "p;banana;40";
    private static final String BANANA_RETURN = "r;banana;30";

    @BeforeEach
    void setUp() {
        convertData = new ConvertDataImpl();
        checkTransactionsList = List.of(
                BALANCE_OPERATION,
                SUPPLY_OPERATION,
                PURCHASE_OPERATION,
                RETURN_OPERATION
        );
        checkTransactionsStrings = List.of(
                FIRST_STRING,
                BANANA_BALANCE,
                BANANA_SUPPLY,
                BANANA_PURCHASE,
                BANANA_RETURN
        );
    }

    @Test
    void convertToTransaction_emptyFile_throwsException() {
        assertThrows(
                RuntimeException.class, () -> convertData.convertToTransaction(
                        Collections.emptyList()
                ));
    }

    @Test
    void convertToTransaction_illegalTextFormat_throwsException() {
        List<String> transactionList = List.of("type", "fruit", "quantity");
        assertThrows(
                IllegalArgumentException.class, () -> convertData.convertToTransaction(
                        transactionList
                ));
    }

    @Test
    void convertToTransaction_validFile_Ok() {
        assertEquals(
                checkTransactionsList,
                convertData.convertToTransaction(checkTransactionsStrings)
        );
    }
}
