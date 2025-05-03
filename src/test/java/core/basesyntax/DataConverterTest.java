package core.basesyntax;

import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DataConverterTest {
    private static final FruitTransaction ORANGE_FRUIT =
            new FruitTransaction(FruitTransaction.Operation.BALANCE,
            "orange", 20);
    private static final String HEADER = "type,fruit,quantity";
    private static final String CORRECT_LINE_IN_CSV_FILE = "b,orange,20";
    private static final String WRONG_LINE_IN_CSV_FILE = "b,orange,20,something";
    private static DataConverter dataConverter;

    @BeforeAll
    static void initializeDataConverter() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void dataConverter_wrongList_notOk() {
        List<String> wrongList = new ArrayList<>();
        wrongList.add(HEADER);
        wrongList.add(WRONG_LINE_IN_CSV_FILE);
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransactions(wrongList));
    }

    @Test
    void dataConverter_correctList_ok() {
        List<FruitTransaction> expectedList = new ArrayList<>();
        expectedList.add(ORANGE_FRUIT);
        List<String> actualList = new ArrayList<>();
        actualList.add(HEADER);
        actualList.add(CORRECT_LINE_IN_CSV_FILE);
        List<FruitTransaction> actualFruitTransaction = dataConverter
                .convertToTransactions(actualList);
        Assertions.assertEquals(expectedList.get(0).getFruit(),
                actualFruitTransaction.get(0).getFruit());
        Assertions.assertEquals(expectedList.get(0).getQuantity(),
                actualFruitTransaction.get(0).getQuantity());
        Assertions.assertEquals(expectedList.get(0).getOperation(),
                actualFruitTransaction.get(0).getOperation());
    }
}
