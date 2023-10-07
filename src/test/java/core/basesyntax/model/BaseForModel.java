package core.basesyntax.model;

import static core.basesyntax.Base.NUMBER_10;
import static core.basesyntax.Base.NUMBER_100;
import static core.basesyntax.Base.NUMBER_13;
import static core.basesyntax.Base.NUMBER_20;
import static core.basesyntax.Base.NUMBER_3000;
import static core.basesyntax.Base.NUMBER_35;
import static core.basesyntax.Base.NUMBER_5;
import static core.basesyntax.Base.NUMBER_50;

public interface BaseForModel {
    Fruit APPLE = new Fruit("apple");
    Fruit BANANA = new Fruit("banana");
    Fruit MANGO = new Fruit("mango");
    Fruit ORANGE = new Fruit("orange");
    FruitTransaction TR_SUPPLY_APPLE_50
            = new FruitTransaction(Operation.SUPPLY, APPLE, NUMBER_50);
    FruitTransaction TR_BALANCE_BANANA_100
            = new FruitTransaction(Operation.BALANCE, BANANA, NUMBER_100);
    FruitTransaction TR_RETURN_MANGO_10
            = new FruitTransaction(Operation.RETURN, MANGO, NUMBER_10);
    FruitTransaction TR_PURCHASE_ORANGE_35
            = new FruitTransaction(Operation.PURCHASE, ORANGE, NUMBER_35);
    FruitTransaction TR_PURCHASE_BANANA_3000
            = new FruitTransaction(Operation.PURCHASE, BANANA, NUMBER_3000);

    //b,banana,20
    FruitTransaction TR_BALANCE_BANANA_20
            = new FruitTransaction(Operation.BALANCE, BANANA, NUMBER_20);
    //b,apple,100
    FruitTransaction TR_BALANCE_APPLE_100
            = new FruitTransaction(Operation.BALANCE, APPLE, NUMBER_100);
    //s,banana,100
    FruitTransaction TR_SUPPLY_BANANA_100
            = new FruitTransaction(Operation.SUPPLY, BANANA, NUMBER_100);
    //p,banana,13
    FruitTransaction TR_PURCHASE_BANANA_13
            = new FruitTransaction(Operation.PURCHASE, BANANA, NUMBER_13);
    //r,apple,10
    FruitTransaction TR_RETURN_APPLE_10
            = new FruitTransaction(Operation.RETURN, APPLE, NUMBER_10);
    //p,apple,20
    FruitTransaction TR_PURCHASE_APPLE_20
            = new FruitTransaction(Operation.PURCHASE, APPLE, NUMBER_20);
    //p,banana,5
    FruitTransaction TR_PURCHASE_BANANA_5
            = new FruitTransaction(Operation.PURCHASE, BANANA, NUMBER_5);
    //s,banana,50
    FruitTransaction TR_SUPPLY_BANANA_50
            = new FruitTransaction(Operation.SUPPLY, BANANA, NUMBER_50);
}
