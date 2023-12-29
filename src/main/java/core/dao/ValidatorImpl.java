package core.dao;

public class ValidatorImpl implements Validator<String> {

    public ValidatorImpl() {
    }

    public boolean validate(String value) {
        if (value == null) {
            throw new RuntimeException("FruitOperation has not data. String value is null.");
        }
        String[] data = value.split(",");
        if (data.length != 3) {
            throw new RuntimeException("Not enough data: Value: " + value);
        }
        if (Integer.parseInt(data[2]) < 0) {
            throw new RuntimeException("Quantity must not be < 0");
        }
        return true;
    }
}
