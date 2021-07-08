package service;

@FunctionalInterface
public interface DataProcessor<J, K> {
    K process(J data);
}
