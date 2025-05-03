package core.basesyntax.fruitrecord;

import core.basesyntax.service.activities.TypeOfActivities;
import java.util.Objects;

public class FruitRecord {
    public static final int ACTIVITIES_INDEX = 0;
    public static final int FRUIT_NAME_INDEX = 1;
    public static final int FRUIT_VALUE_INDEX = 2;
    private TypeOfActivities operationType;
    private String fruit;
    private int amount;

    public FruitRecord(String[] fruitRecordData) {
        TypeOfActivities activities = TypeOfActivities.BALANCE;
        this.operationType = activities.getActivities(fruitRecordData[ACTIVITIES_INDEX]);
        this.fruit = fruitRecordData[FRUIT_NAME_INDEX];
        this.amount = Integer.parseInt(fruitRecordData[FRUIT_VALUE_INDEX]);
    }

    public TypeOfActivities getOperationType() {
        return operationType;
    }

    public String getFruit() {
        return fruit;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FruitRecord that = (FruitRecord) o;
        return amount == that.amount && operationType == that.operationType
                && Objects.equals(fruit, that.fruit);
    }
}
