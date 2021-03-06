package net.meerkat.collections;

import net.meerkat.collections.list.Lists;

import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/**
 * @author Ollie
 */
public abstract class Collections {

    protected Collections() {
    }

    @Deprecated
    public static <T> T[] toArray(final Collection<T> collection, final IntFunction<T[]> arrayCreator) {
        return collection.toArray(arrayCreator.apply(collection.size()));
    }

    public static boolean isEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> boolean any(final Collection<? extends T> collection, final Predicate<? super T> predicate) {
        if (collection.isEmpty()) return false;
        if (collection instanceof List && collection instanceof RandomAccess) return Lists.any((List<? extends T>) collection, predicate);
        return Iterables.anyIterated(collection, predicate);
    }

    public static <T> boolean all(final Collection<? extends T> collection, final Predicate<? super T> predicate) {
        if (collection.isEmpty()) return true;
        if (collection instanceof List && collection instanceof RandomAccess) return Lists.all((List<T>) collection, predicate);
        return Iterables.allIterated(collection, predicate);
    }

}
