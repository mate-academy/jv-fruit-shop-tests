package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import java.io.File;

public class Utils {
    public static FruitTransaction createTransaction(FruitTransaction.Operation operation,
                                                     String fruitName,
                                                     int quantity) {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(operation);
        fruitTransaction.setFruit(fruitName);
        fruitTransaction.setQuantity(quantity);
        return fruitTransaction;
    }

    public static String pathFix(String path) {
        path = path.replace("\\", File.separator);
        path = path.replace("/", File.separator);
        return path;
    }
}
