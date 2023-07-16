package core.basesyntax.strategy;

import core.basesyntax.handler.*;
import core.basesyntax.utility.*;

public interface ShopOperationStrategy {
    ShopOperationHandler get(Operation operation);
}
