package core.basesyntax;

import static core.basesyntax.model.BaseForModel.APPLE;
import static core.basesyntax.model.BaseForModel.BANANA;
import static core.basesyntax.model.BaseForModel.TR_BALANCE_APPLE_100;
import static core.basesyntax.model.BaseForModel.TR_BALANCE_BANANA_20;
import static core.basesyntax.model.BaseForModel.TR_PURCHASE_APPLE_20;
import static core.basesyntax.model.BaseForModel.TR_PURCHASE_BANANA_13;
import static core.basesyntax.model.BaseForModel.TR_PURCHASE_BANANA_3000;
import static core.basesyntax.model.BaseForModel.TR_PURCHASE_BANANA_5;
import static core.basesyntax.model.BaseForModel.TR_RETURN_APPLE_10;
import static core.basesyntax.model.BaseForModel.TR_SUPPLY_BANANA_100;
import static core.basesyntax.model.BaseForModel.TR_SUPPLY_BANANA_50;

import core.basesyntax.db.Storage;
import java.util.List;

public interface Base {
    int NUMBER_3000 = 3000;
    int NUMBER_100 = 100;
    int NUMBER_50 = 50;
    int NUMBER_35 = 35;
    int NUMBER_20 = 20;
    int NUMBER_13 = 13;
    int NUMBER_10 = 10;
    int NUMBER_5 = 5;
    int APPLE_IN_BALANCE = 90;
    int BANANA_IN_BALANCE = 152;
    String FILE_TXT = "src/test/resources/file.txt";
    String UNKNOWN_TXT = "unknown.txt";
    String WRONG_PATH = "wrong/path/";
    String REPORT_TXT = "src/test/resources/report.txt";
    String SAMPLE_REPORT_TXT = "src/test/resources/sample_report.txt";
    String FILE_WITH_WRONG_OPERATION = "src/test/resources/file_with_wrong_operation.txt";
    String FILE_WITH_WRONG_QUANTITY = "file_with_wrong_quantity.txt";
    String REPORT_LINE_00 = "fruit,quantity";
    String REPORT_LINE_01 = "banana,152";
    String REPORT_LINE_02 = "apple,90";

    /**
     * balance data:
     * banana,152
     * apple,90
     */
    static void daoInitWithBalanceData() {
        Storage.fruitTransactions.put(BANANA, List.of(TR_BALANCE_BANANA_20, TR_SUPPLY_BANANA_100,
                TR_PURCHASE_BANANA_13, TR_PURCHASE_BANANA_5, TR_SUPPLY_BANANA_50));
        Storage.fruitTransactions.put(APPLE, List.of(TR_BALANCE_APPLE_100, TR_RETURN_APPLE_10,
                TR_PURCHASE_APPLE_20));
    }

    static void daoInitWithNegativeBalanceData() {
        Storage.fruitTransactions.put(BANANA, List.of(TR_BALANCE_BANANA_20, TR_SUPPLY_BANANA_100,
                TR_PURCHASE_BANANA_13, TR_PURCHASE_BANANA_5, TR_PURCHASE_BANANA_3000));

        Storage.fruitTransactions.put(APPLE, List.of(TR_BALANCE_APPLE_100, TR_RETURN_APPLE_10,
                TR_PURCHASE_APPLE_20));
    }
}
