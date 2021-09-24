package core.dao;

public interface Validator<T> {
    boolean validate(String data);
}
