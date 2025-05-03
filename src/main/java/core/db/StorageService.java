package core.db;

public interface StorageService<T, U> {
    T put(T value, U type);
}
