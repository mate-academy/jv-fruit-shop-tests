package core.basesyntax;

import java.util.ArrayList;
import java.util.List;

public class FruitCounterImpl implements FruitCounter {
    private static final int INDEX_OPERATION = 0;
    private static final int INDEX_FRUIT = 1;
    private static final int INDEX_AMOUNT = 2;

    public List<String> countFruits(List<String> uncountedFruits) {
        List<String> fruitTypes = new ArrayList<>();
        List<Integer> fruitAmount = new ArrayList<>();

        for (String uncountedFruit : uncountedFruits) {
            String[] line = uncountedFruit.split(",");
            int currentFruitIndex;
            switch (line[INDEX_OPERATION]) {
                case "b":
                    fruitTypes.add(line[INDEX_FRUIT]);
                    fruitAmount.add(Integer.parseInt(line[INDEX_AMOUNT]));
                    break;
                case "s":
                case "r":
                    currentFruitIndex = fruitTypes.indexOf(line[INDEX_FRUIT]);
                    fruitAmount.set(currentFruitIndex,
                            fruitAmount.get(currentFruitIndex)
                                    + Integer.parseInt(line[INDEX_AMOUNT]));
                    break;
                case "p":
                    currentFruitIndex = fruitTypes.indexOf(line[INDEX_FRUIT]);
                    fruitAmount.set(currentFruitIndex,
                            fruitAmount.get(currentFruitIndex)
                                    - Integer.parseInt(line[INDEX_AMOUNT]));
                    if (fruitAmount.get(currentFruitIndex) < 0) {
                        throw new RuntimeException("Do not have enough fruits");
                    }
                    break;
                default:
                    throw new RuntimeException("Invalid operation. "
                            + "Allowed operations are: b - for balance, "
                            + "s - for supply, r - for return, p - for purchase");
            }
        }
        List<String> countedFruits = new ArrayList<>();
        countedFruits.add("fruit,quantity");
        for (int i = 0; i < fruitTypes.size(); i++) {
            countedFruits.add(fruitTypes.get(i) + "," + fruitAmount.get(i));
        }
        return countedFruits;
    }
}
