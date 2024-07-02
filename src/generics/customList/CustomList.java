package generics.customList;

public interface CustomList<T>{

    void add(T element);

    T remove(int index);

    boolean contains(T element);

    void swap(int firstIndex, int secondIndex);

    int countGreaterThan(T element);

    public T getMax();

    public T getMin();
}