package core.basesyntax.service.imp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ConvertData;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ConvertDataImpTest {
    private static ConvertData convertData;
    private static List<FruitTransaction> checkTransactionsList;
    private static List<String> checkTransactionsStrings;
    private static final String BANANA = "banana";

    @BeforeAll
    static void beforeALl() {
        convertData = new ConvertDataImp();
        checkTransactionsList = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 102),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 40),
                new FruitTransaction(FruitTransaction.Operation.RETURN, BANANA, 30)
        );
        checkTransactionsStrings = List.of(
                "type;fruit;quantity",
                "b;banana;20",
                "s;banana;102",
                "p;banana;40",
                "r;banana;30"
        );
    }

    @Test
    void convertToTransaction_emptyFile_notOk() {
        assertThrows(
                RuntimeException.class, () -> convertData.convertToTransaction(
                        new ArrayList<>()
                ));
    }

    @Test
    void convertToTransaction_illegalTextFormat_notOk() {
        List<String> transactionList = List.of("type", "fruit", "quantity");
        assertThrows(
                IllegalArgumentException.class, () -> convertData.convertToTransaction(
                        transactionList
                ));
    }

    @Test
    void convertToTransaction_Ok() {
        assertEquals(
                checkTransactionsList,
                convertData.convertToTransaction(checkTransactionsStrings)
        );
    }
}
