package core.basesyntax.shopstrategy;

import core.basesyntax.shopoperations.ListOfOperations;
import core.basesyntax.shopoperations.ShopBalanceOperation;

public interface Strategy {
    ShopBalanceOperation get(ListOfOperations type);
}
