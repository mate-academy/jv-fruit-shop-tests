package core.basesyntax.service.file.actions;

import core.basesyntax.db.FruitTransactionDb;

public interface WriterFile {
    void writeReportFruitShop(FruitTransactionDb fruitStoreDB, String pathFileReport);
}
