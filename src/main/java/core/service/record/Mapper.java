package core.service.record;

public interface Mapper<T, U> {
    U mappingToObject(T value);
}
