package core.basesyntax;

import core.basesyntax.dao.FruitRepository;
import core.basesyntax.dao.impl.FruitRepositoryImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.enums.Operation;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.FileService;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.handler.OperationHandler;
import core.basesyntax.service.handler.OperationStrategy;
import core.basesyntax.service.handler.impl.BalanceOperationHandler;
import core.basesyntax.service.handler.impl.OperationStrategyImpl;
import core.basesyntax.service.handler.impl.PurchaseOperationHandler;
import core.basesyntax.service.handler.impl.ReturnOperationHandler;
import core.basesyntax.service.handler.impl.SupplyOperationHandler;
import core.basesyntax.service.impl.DataConverterImpl;
import core.basesyntax.service.impl.FileServiceImpl;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import core.basesyntax.service.impl.ShopServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Feel free to remove this class and create your own.
 */
public class HelloWorld {
    public static void main(String[] arg) {
    }
}
