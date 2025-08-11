package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.converter.DataConverter;
import core.basesyntax.converter.DataConverterImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private DataConverter dataConverter = new DataConverterImpl();

    @BeforeEach
    public void setup() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    public void convertEmptyString_throwsException() {
        List<String> emptyStringList = new ArrayList<>();
        emptyStringList.add("");
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(emptyStringList));
    }

    @Test
    public void convertStringArgumentsAmountAreNotThree_throwsException() {
        List<String> tooManyArgumentsList = new ArrayList<>();
        tooManyArgumentsList.add("1,2,3,4");
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(tooManyArgumentsList));
    }

    @Test
    public void convertStringArgumentsEqualThree_getNewTransaction() {
        List<String> correctList = new ArrayList<>();
        correctList.add("b,apple,3");
        FruitTransaction transaction = dataConverter
                .convertToTransaction(correctList)
                .get(0);

        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation());
        assertEquals("apple", transaction.getFruit());
        assertEquals(3, transaction.getQuantity());
    }
}
