package core.basesyntax;

import core.basesyntax.db.HandlerStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecord;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.RecordListMakerService;
import core.basesyntax.service.StorageInfoUpdateService;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import core.basesyntax.service.impl.RecordListMakerServiceImpl;
import core.basesyntax.service.impl.ReportMakerServiceImpl;
import core.basesyntax.service.impl.StorageInfoUpdateServiceImpl;
import core.basesyntax.strategy.BalanceOperationHandlerImpl;
import core.basesyntax.strategy.DecreaseAmountOperationHandlerImpl;
import core.basesyntax.strategy.IncreaseAmountOperationHandlerImpl;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageInfoUpdateServiceTest {
    private static final String INPUT_FILE_NAME = "src/main/resources/fruits.csv";
    private static StorageInfoUpdateService storageInfoUpdateService;
    private static List<FruitRecord> recordList;

    @BeforeAll
    public static void initialize() {
        Map<FruitRecord.Operation, OperationHandler> handlerMap = Map.of(
                FruitRecord.Operation.PURCHASE, new DecreaseAmountOperationHandlerImpl(),
                FruitRecord.Operation.SUPPLY, new IncreaseAmountOperationHandlerImpl(),
                FruitRecord.Operation.BALANCE, new BalanceOperationHandlerImpl(),
                FruitRecord.Operation.RETURN, new IncreaseAmountOperationHandlerImpl()
        );
        storageInfoUpdateService = new StorageInfoUpdateServiceImpl(new HandlerStorage(handlerMap));
        recordList = new RecordListMakerServiceImpl()
                .getFruitRecordList(new FileReaderServiceImpl()
                        .readRowsFromFile(INPUT_FILE_NAME));
    }

    @Test
    public void updatingStorage_Ok() {
        storageInfoUpdateService.updateStorageInfo(recordList);
        Map<Fruit, Integer> actual = Storage.getStorage();
        Map<Fruit, Integer> expected = Map.of(new Fruit("banana"), 152, new Fruit("apple"), 90);
        assertEquals(actual, expected);
    }
}
