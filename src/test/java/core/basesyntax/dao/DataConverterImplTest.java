package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverter dataConverter = new DataConverterImpl();

    @Test
    void convertToTransaction_validListOfTransaction() {
        List<String> inputData = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5");
        List<FruitTransaction> fruitTransactions = dataConverter.convertToTransaction(inputData);
        int expected = inputData.size() - 1;
        Assert.assertEquals(String.format("Invalid quantity of operations!"
                + " Expected: " + expected + "Atual: "
                + fruitTransactions.size()), expected, fruitTransactions.size());
    }

    @Test
    public void convertToTransaction_differentFruits_returnsListOfTransactions() {
        List<String> input = List.of("type,fruit,quantity", "b,banana,10", "s,apple,20",
                "p,orange,5", "r,grape,3", "b,kiwi,15");
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(input);
        assertEquals(5, transactions.size());
    }

    @Test
    public void convertToTransaction_nullInput_returnsEmptyList() {
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(null);
        assertEquals(0, transactions.size());
    }

    @Test
    public void convertToTransaction_invalidOperationCode_throwsIllegalArgumentException() {
        List<String> input = List.of("type,fruit,quantity", "x,banana,10");
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(input));
    }

    @Test
    public void convertToTransaction_invalidQuantity_throwsNumberFormatException() {
        List<String> input = List.of("type,fruit,quantity", "b,banana,abc");
        assertThrows(NumberFormatException.class, () -> dataConverter.convertToTransaction(input));
    }
}
