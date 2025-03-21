package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.infrastructure.DataConverter;
import core.basesyntax.infrastructure.DataConverterImpl;
import core.basesyntax.infrastructure.db.FileReader;
import core.basesyntax.infrastructure.db.FileReaderImpl;
import core.basesyntax.infrastructure.db.FileWriter;
import core.basesyntax.infrastructure.db.FileWriterImpl;
import core.basesyntax.infrastructure.db.Storage;
import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.OperationStrategyImpl;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.service.operations.BalanceOperation;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.PurchaseOperation;
import core.basesyntax.service.operations.ReturnOperation;
import core.basesyntax.service.operations.SupplyOperation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainTest {
    private static final String OPERATION_LIST_FILE_PATH
            = "src/main/resources/operationslist.csv";
    private static final String DB_FILE_PATH
            = "src/main/resources/database.csv";

    @Test
    void applicationWorkResultOk() {
        List<String> expected = new ArrayList<>();
        expected.add("banana,152");
        expected.add("apple,90");

        Main.main(new String[0]);
        FileReader reader = new FileReaderImpl();
        List<String> actual = reader.read(DB_FILE_PATH);

        assertEquals(actual, expected);
    }
}
