package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ConvertFromDataStringToList;
import core.basesyntax.service.CsvFileReader;
import core.basesyntax.service.ReportCreator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private ReportCreator<FruitTransaction> reportCreator;
    private CsvFileReader csvFileReader;
    private ConvertFromDataStringToList<FruitTransaction> convertor;

    @BeforeEach
    void setUp() {
        reportCreator = new ReportCreatorImpl();
        csvFileReader = new CsvFileReaderImpl();
        convertor = new ConvertFromDataStringToListImpl();
        Storage.storage.clear();
    }

    @Test
    void createReport_validInput_ok() {
        String transaction = csvFileReader.read("src/main/resources/input.csv");
        List<FruitTransaction> fruitTransactions = convertor.convert(transaction);

        String actualReport = reportCreator.createReport(Storage.storage, fruitTransactions);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        assertEquals(expectedReport, actualReport);
    }

    @Test
    void createReport_nullList_notOk() {
        Map<String, Integer> report = new HashMap<>();
        assertThrows(RuntimeException.class,
                () -> reportCreator.createReport(report, null));
    }

    @Test
    void createReport_negativeQuantity_notOk() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(Operation.PURCHASE,"apple", 10)
        );

        Map<String, Integer> report = new HashMap<>();
        report.put("Apple", -5);

        assertThrows(RuntimeException.class,
                () -> reportCreator.createReport(Storage.storage, transactions));
    }
}
