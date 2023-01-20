package core.basesyntax.service.impl;

import java.util.List;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileReaderService;
import org.junit.Test;

public class CsvFileReaderServiceImplTest {
    private static final String VALID_PATH = "src/main/resources/inputData.csv";
    FileReaderService fileReaderService;

    @Test
    void inputData_validPath_isOk() {
        fileReaderService = new CsvFileReaderServiceImpl();

        List<FruitTransaction> actual = fileReaderService.readFromFile(VALID_PATH);
        List<FruitTransaction> expected = List.of();


    }


}