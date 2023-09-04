package core.basesyntax.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConversionServiceImplTest {
    private static String SEPARATOR = System.lineSeparator();
    private static DataConversionServiceImpl dataConversionService;
    private String fileLines;

    @BeforeAll
    static void beforeAll() {
        dataConversionService = new DataConversionServiceImpl();
    }

    @BeforeEach
    void setUp() {
        fileLines = "type,fruit,quantity" + SEPARATOR
                + "b,banana,20" + SEPARATOR
                + "b,apple,100" + SEPARATOR
                + "s,banana,100" + SEPARATOR
                + "p,banana,13" + SEPARATOR
                + "r,apple,10" + SEPARATOR
                + "p,apple,20" + SEPARATOR
                + "p,banana,5" + SEPARATOR
                + "s,banana,50";
    }

    @Test
    void convert_validFile_ok() {
        List<FruitTransaction> transactions = dataConversionService.convert(fileLines);
        FruitTransaction expectedTransaction = new FruitTransaction();
        expectedTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        expectedTransaction.setFruit("banana");
        expectedTransaction.setQuantity(20);
        FruitTransaction firstTransaction = transactions.get(0);
        assertEquals(expectedTransaction.getOperation(), firstTransaction.getOperation());
        assertEquals(expectedTransaction.getFruit(), firstTransaction.getFruit());
        assertEquals(expectedTransaction.getQuantity(), firstTransaction.getQuantity());
    }

    @Test
    void convert_nullFile_notOk() {
        assertThrows(RuntimeException.class, () -> dataConversionService.convert(null));
    }

    @AfterEach
    public void afterEachTest() {
        Storage.STORAGE.clear();
    }
}
