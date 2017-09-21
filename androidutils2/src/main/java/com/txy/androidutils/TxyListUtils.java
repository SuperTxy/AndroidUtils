package com.txy.androidutils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Apple on 17/9/11.
 */

public class TxyListUtils {

    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    public static <T> int indexOfObj(List<T> list, T t) {
        if (!isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == t) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static <T> int size(List<T> list) {
        return list == null ? 0 : list.size();
    }

    public static <T> List<T> fromObj2List(T t) {
        List<T> list = new ArrayList<>();
        list.add(t);
        return list;
    }
}
