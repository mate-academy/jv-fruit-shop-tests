package core.basesyntax.operations;

import core.basesyntax.model.FruitTransaction;

public interface OperationHandler {
    /*
    b - balance, the remnants of fruits at the beginning of the working day
    s - supply, means you are receiving new fruits from suppliers
    p - purchase, means someone has bought some fruit
    r - return, means someone who have bought the fruits now returns them back
    */
    int execute(FruitTransaction fruitTransaction);
}
