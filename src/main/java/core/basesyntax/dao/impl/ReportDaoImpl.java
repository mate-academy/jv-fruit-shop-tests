package core.basesyntax.dao.impl;

import core.basesyntax.dao.ReportDao;
import core.basesyntax.db.DataBase;
import core.basesyntax.db.impl.DataBaseImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class ReportDaoImpl implements ReportDao {
    private final DataBase dataBase = new DataBaseImpl();

    @Override
    public Map<String, Integer> getReport() {
        return dataBase.getFruitTransactions();
    }

    @Override
    public void updateReport(FruitTransaction fruitTransaction) {
        dataBase.addFruitTransaction(fruitTransaction);
    }

    @Override
    public int getBalanceFruitTransaction(FruitTransaction fruitTransaction) {
        return getReport().get(fruitTransaction.getFruit());
    }
}
