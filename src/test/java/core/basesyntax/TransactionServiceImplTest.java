package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.database.TransactionServiceImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.TransactionService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class TransactionServiceImplTest {
    private final TransactionService transactionService = new TransactionServiceImpl();
    private List<String> correctList = new ArrayList<>(List.of("type,fruit,quantity",
            "b,banana,20", "b,apple,100", "s,banana,100",
            "p,banana,13", "r,apple,10", "p,apple,20",
            "p,banana,5", "s,banana,50"));

    @Test
    public void transactionData_parameterListIsNull_notOk() {
        assertThrows(NullPointerException.class,
                () -> transactionService.transactionData(null));
    }

    @Test
    public void transactionData_parameterListIsEmpty_notOk() {
        assertThrows(RuntimeException.class,
                () -> transactionService.transactionData(new ArrayList<>()));
    }

    @Test
    public void transactionData_validLineInSplit_notOk() {
        List<String> invalidList = List.of("banana,78apple99","banana,56,78");
        assertThrows(RuntimeException.class,
                () -> transactionService.transactionData(invalidList));
    }

    @Test
    public void transactionData_parameterList_Ok() {
        List<FruitTransaction> actual = transactionService.transactionData(correctList);
        List<FruitTransaction> expected = List.of(
                FruitTransaction.of("b","banana",20),
                FruitTransaction.of("b","apple",100),
                FruitTransaction.of("s","banana",100),
                FruitTransaction.of("p","banana",13),
                FruitTransaction.of("r","apple",10),
                FruitTransaction.of("p","apple",20),
                FruitTransaction.of("p","banana",5),
                FruitTransaction.of("s","banana",50));
        assertEquals(expected,actual);
    }

}
