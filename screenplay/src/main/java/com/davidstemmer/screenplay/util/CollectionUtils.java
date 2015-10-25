package com.davidstemmer.screenplay.util;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by weefbellington on 10/11/15.
 */
public class CollectionUtils {
    public static  <T, C extends Collection<T>>
    C fromIterator(Iterator iter, C retValue) {
        while (iter.hasNext()) {
            retValue.add((T)iter.next());
        }
        return retValue;
    }

    public static  <T, C extends Collection<T>>
    C difference(C first, C second, C retValue) {
        Set<T> diff = new LinkedHashSet<>(first);
        diff.removeAll(second);
        retValue.addAll(diff);
        return retValue;
    }

    public static <T> ArrayDeque<T> emptyQueue(Class<T> clazz) {
        return new ArrayDeque<>();
    }
}
