package core.basesyntax.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.exceptions.InvalidOperationException;
import core.basesyntax.transaction.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @AfterEach
    void tearDown() {
        FruitStorage.getFruitsStorage().clear();
    }

    @Test
    void convertFromCsv_Ok() {
        FruitTransaction bananaBalance = new FruitTransaction();
        bananaBalance.setFruitName("banana");
        bananaBalance.setOperation(FruitTransaction.Operation.B);
        bananaBalance.setQuantity(10);

        FruitTransaction bananaSupply = new FruitTransaction();
        bananaSupply.setFruitName("banana");
        bananaSupply.setOperation(FruitTransaction.Operation.S);
        bananaSupply.setQuantity(15);

        FruitTransaction appleBalance = new FruitTransaction();
        appleBalance.setFruitName("apple");
        appleBalance.setOperation(FruitTransaction.Operation.B);
        appleBalance.setQuantity(20);

        List<String> inputData = List.of(
                "type,fruit,quantity","b,banana,10", "b,apple,20", "s,banana,15");
        List<FruitTransaction> expected = List.of(bananaBalance, appleBalance, bananaSupply);
        List<FruitTransaction> actual = dataConverter.convertFromCsv(inputData);

        assertEquals(expected, actual);
    }

    @Test
    void convertFromCsv_invalidOperation_NotOk() {
        List<String> inputData = List.of(
                "type,fruit,quantity","null,banana,10", "b,apple,20", "s,banana,15");

        assertThrows(InvalidOperationException.class,
                () -> dataConverter.convertFromCsv(inputData));
    }

    @Test
    void convertFromCsv_invalidFruit_NotOk() {
        List<String> inputData = List.of(
                "type,fruit,quantity","b,null,10", "b,apple,20", "s,banana,15");

        assertThrows(InvalidOperationException.class,
                () -> dataConverter.convertFromCsv(inputData));
    }

    @Test
    void convertFromCsv_zeroQuantity_NotOk() {
        List<String> inputData = List.of(
                "type,fruit,quantity","b,banana,0", "b,apple,20", "s,banana,15");

        assertThrows(InvalidOperationException.class,
                () -> dataConverter.convertFromCsv(inputData));
    }

    @Test
    void convertFromCsv_negativeQuantity_NotOk() {
        List<String> inputData = List.of(
                "type,fruit,quantity","b,banana,-20", "b,apple,20", "s,banana,15");

        assertThrows(InvalidOperationException.class,
                () -> dataConverter.convertFromCsv(inputData));
    }

    @Test
    void convertFromCsv_nonNumericQuantity_NotOk() {
        List<String> inputData = List.of(
                "type,fruit,quantity","b,banana,test", "b,apple,20", "s,banana,15");

        assertThrows(InvalidOperationException.class,
                () -> dataConverter.convertFromCsv(inputData));
    }
}
