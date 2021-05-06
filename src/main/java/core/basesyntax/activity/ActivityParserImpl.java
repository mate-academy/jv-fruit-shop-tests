package core.basesyntax.activity;

public class ActivityParserImpl implements ActivityParser {

    @Override
    public Activities parseActivity(String line) {
        if (!line.contains(",")) {
            throw new RuntimeException("Invalid data input style");
        }
        String[] splitLine = line.split(",");
        String activity = splitLine[0].trim();
        switch (activity) {
            case "b": return Activities.BALANCE;
            case "s": return Activities.SUPPLY;
            case "p": return Activities.PURCHASE;
            case "r": return Activities.RETURN;
            default: throw new RuntimeException("There is no such operation: "
                    + activity);
        }
    }
}
