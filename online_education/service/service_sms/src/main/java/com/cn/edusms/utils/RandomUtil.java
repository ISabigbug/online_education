package com.cn.edusms.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RandomUtil {
    private static final Random random = new Random();
    private static final DecimalFormat fourdf = new DecimalFormat("0000");
    private static final DecimalFormat sixdf = new DecimalFormat("000000");

    public RandomUtil() {
    }

    public static String getFourBitRandom() {
        return fourdf.format((long)random.nextInt(10000));
    }

    public static String getSixBitRandom() {
        return sixdf.format((long)random.nextInt(1000000));
    }

    public static ArrayList getRandom(List list, int n) {
        Random random = new Random();
        HashMap<Object, Object> hashMap = new HashMap();

        for(int i = 0; i < list.size(); ++i) {
            int number = random.nextInt(100) + 1;
            hashMap.put(number, i);
        }

        Object[] robjs = hashMap.values().toArray();
        ArrayList r = new ArrayList();

        for(int i = 0; i < n; ++i) {
            r.add(list.get((Integer)robjs[i]));
            System.out.print(list.get((Integer)robjs[i]) + "\t");
        }

        System.out.print("\n");
        return r;
    }
}
