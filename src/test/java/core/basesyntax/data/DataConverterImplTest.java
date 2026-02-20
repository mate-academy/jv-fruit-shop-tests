package core.basesyntax.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static FruitTransaction fruitTransaction;
    private static final String FILE_PATH = "src/test/java/resourses/testFile.csv";
    private static FruitTransaction fruitTransaction2;
    private static FruitTransaction fruitTransaction3;
    private DataConverter dataConverter = new DataConverterImpl();

    @BeforeAll
    static void setUp() {
        fruitTransaction = new FruitTransaction();
        fruitTransaction2 = new FruitTransaction();
        fruitTransaction3 = new FruitTransaction();

        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setQuantity(20);
        fruitTransaction.setFruit("banana");

        fruitTransaction2.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction2.setQuantity(5);
        fruitTransaction2.setFruit("apple");

        fruitTransaction3.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction3.setQuantity(13);
        fruitTransaction3.setFruit("banana");
    }

    @Test
    void linesFromFileIsEmpty_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransactionsListFromLines(new ArrayList<>()));
    }

    @Test
    void validLines_ShouldReturnFruitTransactionList() {
        List<String> inputLines = List.of("type,fruit,quantity",
                "b,banana,20", "b,apple,5", "p,banana,13");
        List<FruitTransaction> expected = List.of(fruitTransaction,
                fruitTransaction2, fruitTransaction3);
        List<FruitTransaction> actual = dataConverter
                .convertToTransactionsListFromLines(inputLines);
        
        assertEquals(expected, actual);
        assertNotNull(actual);
    }

    @Test
    void lineHasLessThan3Arguments_ShouldThrowException() {
        List<String> inputLines = List.of("type,fruit,quantity",
                "b,banana,20", "b,20", "p,banana,13");
        assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransactionsListFromLines(inputLines));
    }
}
