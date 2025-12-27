package ecomet;
import java.util.*;
import java.util.stream.Collectors;

public class RecommendationEngine {

    public static List<Product> recommend(Product viewed, List<Product> all) {
        return all.stream()
            .filter(p -> !p.getName().equals(viewed.getName()))
            .sorted((a,b)-> score(b, viewed) - score(a, viewed))
            .limit(4)
            .collect(Collectors.toList());
    }

    private static int score(Product p, Product v){
        int s = 0;
        if(p.getCategory().equals(v.getCategory())) s+=5;
        if(Math.abs(p.getPrice() - v.getPrice()) <= 500) s+=3;
        return s;
    }
}
