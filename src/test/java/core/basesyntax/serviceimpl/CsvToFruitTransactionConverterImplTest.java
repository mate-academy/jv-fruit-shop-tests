package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvToFruitTransactionConverterImplTest {
    private CsvToFruitTransactionConverterImpl csvToFruitTransactionConverter;
    private List<String> inputReport;

    @BeforeEach
    void setUp() {
        csvToFruitTransactionConverter = new CsvToFruitTransactionConverterImpl();
        inputReport = List.of("s,banana,50","b,apple,25");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void convertCsvToFruitTransaction_correct_Ok() {
        List<FruitTransaction> expected = List
                .of(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 50),
                        new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple",25));
        List<FruitTransaction> actual = csvToFruitTransactionConverter
                .convertCsvToFruitTransaction(inputReport);
        assertEquals(expected,actual);
    }

    @Test
    void convertCsvToFruitTransaction_invalidFormat_notOk() {
        List<String> invalidInput = List.of("s,banana,50", "b,apple");
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> csvToFruitTransactionConverter.convertCsvToFruitTransaction(invalidInput));
    }
}
