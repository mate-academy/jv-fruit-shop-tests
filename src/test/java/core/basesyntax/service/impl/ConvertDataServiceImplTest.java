package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReadDataFromFileService;
import core.basesyntax.service.exception.NoSuchOperationException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConvertDataServiceImplTest {
    private static final String PATH_TRANSACTIONS = "src/main/resources/balanceFruits.csv";
    private static ConvertDataServiceImpl convert;
    private static ReadDataFromFileService readData;
    private List<String> dataFromFile;

    @BeforeAll
    static void beforeAll() {
        convert = new ConvertDataServiceImpl();
        readData = new ReadDataFromFileServiceImpl();
    }

    @BeforeEach
    void setUp() {
        dataFromFile = readData.readFromFile(PATH_TRANSACTIONS);
    }

    @Test
    void processingData_returnListFruitTrans_Ok() {
        List<FruitTransaction> listFruit = convert.processingData(dataFromFile);
        Assertions.assertEquals(listFruit.size(), dataFromFile.size());
    }

    @Test
    void processingData_emptyString_NotOk() {
        List<String> list = List.of("", "r,apple,15");
        Assertions.assertThrows(NoSuchOperationException.class, () -> convert.processingData(list));
    }

    @Test
    void processingData_WrongOperation_NotOk() {
        List<String> list = List.of("t,banana,15", "r,apple,15");
        Assertions.assertThrows(NoSuchOperationException.class, () -> convert.processingData(list));
    }

    @Test
    void processingData_NegativeQuality_NotOk() {
        List<String> list = List.of("b,banana,-15", "r,apple,15");
        Assertions.assertThrows(NoSuchOperationException.class, () -> convert.processingData(list));
    }

    @Test
    void processingData_WrongDelimiter_NotOk() {
        List<String> list = List.of("b.banana,15", "r,apple,15");
        Assertions.assertThrows(NoSuchOperationException.class, () -> convert.processingData(list));
    }

    @Test
    void processingData_WrongFormatQuality_Ok() {
        List<String> list = List.of("b,banana,1o", "r,apple,15");
        Assertions.assertThrows(NoSuchOperationException.class, () -> convert.processingData(list));
    }

}
