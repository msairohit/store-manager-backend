package com.rohit.storemanager;

import java.util.ArrayList;
import java.util.List;

public class optum {

    public static void main(String[] args) {
        test();
    }

//    m1, w1, w2, w3
//            (m1, w1, w2), (m1, w1, w3) and (m1, w2, w3)
    private static List<List<String>> test() {
        int males = 1;
        int females = 3;

        if (males < 1 || females < 1) {
            return new ArrayList<>();
        }
/*
        males -= 1;
        females -= 1;*/

        List<String> res = new ArrayList<>();

        for (int i = 0; i < females;i++) {
            res.add("w"+ i);
        }
        for (int i = 0; i < males;i++) {
            res.add("m"+ i);
        }
        int total = males + females;
        for (int i = 0; i < total;i++) {

        }

        return new ArrayList<>();

    }
}
