package core.basesyntax.fruit.dto;

public class FruitDtoParserImpl implements FruitDtoParser {
    private static final String IS_NUMERIC = "[0-9]+";

    @Override
    public FruitDto parseFruitDto(String line) {
        if (!line.contains(",")) {
            throw new RuntimeException("Bad input style");
        }
        String[] splitLine = line.split(",");
        String fruit = splitLine[1].trim();
        String amount = splitLine[2].trim();
        if (!amount.matches(IS_NUMERIC)) {
            throw new RuntimeException("Invalid input");
        }
        return new FruitDto(fruit, Integer.parseInt(amount));
    }
}
