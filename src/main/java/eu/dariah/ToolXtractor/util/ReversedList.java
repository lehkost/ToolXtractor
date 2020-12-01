package eu.dariah.ToolXtractor.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ReversedList<T> implements Iterable<T> {
    private final List<T> original;

    public ReversedList(List<T> original) {
        this.original = original;
    }

    public Iterator<T> iterator() {
        final ListIterator<T> i = original.listIterator(original.size());

        return new Iterator<T>() {
            public boolean hasNext() { return i.hasPrevious(); }
            public T next() { return i.previous(); }
            public void remove() { i.remove(); }
        };
    }

    public static <T> ReversedList<T> reversed(Iterable<T> original) {
        List<T> result = new ArrayList<T>();
        original.forEach(result::add);
        return new ReversedList<T>(result);
    }
}