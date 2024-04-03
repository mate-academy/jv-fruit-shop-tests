package core.basesyntax.service.impl;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionConverter;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class TransactionConverterImplTest {
    public static final TransactionConverter TRANSACTION_CONVERTER = new TransactionConverterImpl();
    public static final List<String> CORRECT_INPUT_DATA =
            Arrays.asList("type,fruit,quantity", "b,banana,20", "b,apple,100",
                    "s,banana,100", "p,banana,13", "p,apple,20", "r,apple,10");
    public static final List<String> INCORRECT_SEPARATED_DATA =
            Arrays.asList("type fruit quantity", "b banana 20", "b apple 100",
                    "s banana 100", "p banana 13", "p apple 20", "r apple 10");
    public static final List<String> NULL_STRING_DATA =
            Arrays.asList("type,fruit,quantity","b,banana,20", null, "b,apple,100");
    public static final List<FruitTransaction> CORRECT_OUTPUT_DATA = Arrays.asList(
            new FruitTransaction(FruitTransaction.Operation.BALANCE,"banana",20),
            new FruitTransaction(FruitTransaction.Operation.BALANCE,"apple",100),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY,"banana",100),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE,"banana",13),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE,"apple",20),
            new FruitTransaction(FruitTransaction.Operation.RETURN,"apple",10));

    @Test
    void convert_correctData_ok() {
        List<FruitTransaction> actual = TRANSACTION_CONVERTER.convert(CORRECT_INPUT_DATA);
        if (actual.size() != CORRECT_OUTPUT_DATA.size()) {
            fail();
        }
        FruitTransaction actualFruitTransaction;
        FruitTransaction correctFruitTransaction;
        for (int i = 0; i < actual.size(); i++) {
            actualFruitTransaction = actual.get(i);
            correctFruitTransaction = CORRECT_OUTPUT_DATA.get(i);
            assertEquals(actualFruitTransaction.getOperation(),
                    correctFruitTransaction.getOperation());
            assertEquals(actualFruitTransaction.getFruit(), correctFruitTransaction.getFruit());
            assertEquals(actualFruitTransaction.getQuantity(),
                    correctFruitTransaction.getQuantity());
        }
    }

    @Test
    void convert_nullStringData_notOk() {
        assertThrows(RuntimeException.class,() -> TRANSACTION_CONVERTER.convert(NULL_STRING_DATA));
    }

    @Test
    void convert_incorrectSeparatedData_notOk() {
        assertThrows(RuntimeException.class,
                () -> TRANSACTION_CONVERTER.convert(INCORRECT_SEPARATED_DATA));
    }
}
