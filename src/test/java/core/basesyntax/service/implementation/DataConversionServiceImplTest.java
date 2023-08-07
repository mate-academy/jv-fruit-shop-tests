package core.basesyntax.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConversionServiceImplTest {
    private DataConversionServiceImpl dataConversionService;
    private FileReaderService fileReaderService;
    private String newFile;
    private String fileLines;

    @BeforeEach
    void setUp() {
        dataConversionService = new DataConversionServiceImpl();
        fileReaderService = new FileReaderServiceImpl();
        newFile = "src/test/resources/testData.csv";
        fileLines = fileReaderService.read(newFile);
    }

    @Test
    void convert_validFile_ok() {
        List<FruitTransaction> transactions = dataConversionService.convert(fileLines);
        FruitTransaction actualTransaction = new FruitTransaction();
        actualTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        actualTransaction.setFruit("banana");
        actualTransaction.setQuantity(20);
        FruitTransaction firstTransaction = transactions.get(0);
        assertEquals(actualTransaction.getOperation(), firstTransaction.getOperation());
        assertEquals(actualTransaction.getFruit(), firstTransaction.getFruit());
        assertEquals(actualTransaction.getQuantity(), firstTransaction.getQuantity());
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
