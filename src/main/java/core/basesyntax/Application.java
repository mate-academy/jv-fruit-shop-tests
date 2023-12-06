package core.basesyntax;

import core.basesyntax.db.FruitTransactionDb;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ServiceFruitShop;
import core.basesyntax.service.ServiceFruitShopImpl;
import core.basesyntax.service.file.actions.ParserFile;
import core.basesyntax.service.file.actions.ReaderFile;
import core.basesyntax.service.file.actions.WriterFile;
import core.basesyntax.service.file.actions.impl.ParserFileImpl;
import core.basesyntax.service.file.actions.impl.ReaderFileImpl;
import core.basesyntax.service.file.actions.impl.WriterFileImpl;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.strategy.impl.BalanceStrategy;
import core.basesyntax.strategy.impl.PurchaseStrategy;
import core.basesyntax.strategy.impl.ReturnStrategy;
import core.basesyntax.strategy.impl.SupplyStrategy;
import java.util.List;
import java.util.Map;

public class Application {
    private static final String FILE_INFO_FRUIT_SHOP_PATH
            = "src/main/java/core/basesyntax/resources/fruitShop.csv";
    private static final String FILE_REPORT_FRUIT_SHOP_PATH
            = "src/main/java/core/basesyntax/resources/reportFruitShop.csv";
    private static final Map<FruitTransaction.Operation, Strategy> OPERATION_STRATEGY_MAP = Map.of(
            FruitTransaction.Operation.BALANCE, new BalanceStrategy(),
            FruitTransaction.Operation.PURCHASE, new PurchaseStrategy(),
            FruitTransaction.Operation.RETURN, new ReturnStrategy(),
            FruitTransaction.Operation.SUPPLY, new SupplyStrategy());

    public static void main(String[] args) {
        ReaderFile reader = new ReaderFileImpl();
        List<String> fruitTransactionReader = reader.readFileFruitShop(FILE_INFO_FRUIT_SHOP_PATH);

        ParserFile parser = new ParserFileImpl();
        List<FruitTransaction> fruitTransactionParser = parser
                .parseFruitShop(fruitTransactionReader);

        ServiceFruitShop serviceFruitShop = new ServiceFruitShopImpl(OPERATION_STRATEGY_MAP);
        FruitTransactionDb fruitTransactionDB = serviceFruitShop
                .processTransaction(fruitTransactionParser);

        WriterFile writer = new WriterFileImpl();
        writer.writeReportFruitShop(fruitTransactionDB, FILE_REPORT_FRUIT_SHOP_PATH);
    }
}
