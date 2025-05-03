package core.basesyntax.model;

import java.util.Objects;

public class CsvLineDto {
    private final String operation;
    private final String fruitName;
    private final String number;

    public CsvLineDto(String operation, String fruitName, String number) {
        this.operation = operation;
        this.fruitName = fruitName;
        this.number = number;
    }

    public String getOperation() {
        return operation;
    }

    public String getFruitName() {
        return fruitName;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CsvLineDto that = (CsvLineDto) o;
        return Objects.equals(operation, that.operation)
                && Objects.equals(fruitName, that.fruitName)
                && Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruitName, number);
    }
}
