package core.basesyntax.service.readfromfileservice.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.readfromfileservice.ReadFromFileService;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReadFromFileCsvServiceImplTest {
    private final ReadFromFileService readFromFileService = new ReadFromFileCsvServiceImpl();

    @Test
    void returnEmptyListOfFruitTransAction_ok() {
        String filePath
                = "src/main/resources/information_about_activities_in_empty_store.csv";
        List<FruitTransaction> listOfFruitTransaction
                = readFromFileService.readFromCsvFile(filePath);
        assertTrue(listOfFruitTransaction.isEmpty());
    }

    @Test
    void getEmptyListOfFruitTransAction_notOk() {
        String filePath
                = "src/main/resources/information_about_activities_in_store.csv";
        List<FruitTransaction> listOfFruitTransaction
                = readFromFileService.readFromCsvFile(filePath);
        assertFalse(listOfFruitTransaction.isEmpty());
    }

    @Test
    void getExceptionFileDoesNotExist_ok() {
        String filePath
                = "src/main/resources/file_is_now_exist.csv";
        assertThrows(RuntimeException.class, () -> readFromFileService.readFromCsvFile(filePath));
    }
}
