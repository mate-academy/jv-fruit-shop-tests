package core.basesyntax.dao;

import static core.basesyntax.db.Warehouse.warehouse;
import core.basesyntax.db.Warehouse;
import java.util.Map;

public class WarehouseDaoImpl implements WarehouseDao {
    @Override
    public void setQuantity(String fruit, Integer quantity) {
        if (fruit != null && quantity != null) {
            warehouse.put(fruit, quantity);
        } else {
            throw new RuntimeException("Input parameters can't be null");
        }
    }

    @Override
    public int getQuantity(String fruit) {
        return warehouse.get(fruit);
    }

    @Override
    public boolean isPresent(String fruit) {
        return warehouse.containsKey(fruit);
    }

    @Override
    public Map<String, Integer> getLeftovers() {
        return Warehouse.warehouse;
    }
}
