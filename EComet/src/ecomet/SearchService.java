package ecomet;
import java.util.List;
import java.util.stream.Collectors;

public class SearchService {

    public static List<Product> search(List<Product> products, String query) {
        if(query == null || query.trim().isEmpty()){ 
            return products;
        }

        final String keyword = query.toLowerCase(); // effectively final for lambda

        return products.stream()
            .filter(p ->
                p.getName().toLowerCase().contains(keyword) ||
                (p.getTags() != null && p.getTags().toLowerCase().contains(keyword))
            )
            .collect(Collectors.toList()); // import used here
    }
}
