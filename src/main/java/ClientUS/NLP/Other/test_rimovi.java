package ClientUS.NLP.Other;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class test_rimovi {
        public static void main(String[] args) {
            List<r_rel> list = new ArrayList<>();
            list.add(new r_rel("ciao","mondo"));
            list.add(new r_rel("ciao","mondo"));
            list.add(new r_rel("ciao","mondo"));
            list.add(new r_rel("ciao","bello"));
            list.add(new r_rel("ciao","gino"));
            list.add(new r_rel("ciao","peppe"));

            Set<r_rel> set = new TreeSet<>(list);
            list.clear();
            list.addAll(set);

            System.out.println(list);
        }
}
