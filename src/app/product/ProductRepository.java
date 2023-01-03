package app.product;

import app.product.subproduct.*;

public class ProductRepository {
    private final Product[] products = {
            new Hamburger(1, "새우버거", 3500, 500, false, 4500),
            new Hamburger(2, "치킨버거", 4000, 600, false, 5000),
            new Side(3, "감자튀김", 1000, 300, 1),
            new Side(4, "어니언링", 1000, 300, 1),
            new Drink(5, "코카콜라", 1000, 200, true),
            new Drink(6, "제로콜라", 1000, 0, true),

    };

    // 외부에서 접근할 수 있도록 Getter 역할을 해줌
    public Product[] getAllProducts() {
        return products;
    }

    // 상품 검색 기능을 자율적으로 수행할 수 있도록 상품품 검색코드를 여기에 옮겨줌
    // -> Cart에서 findById()만 호출 하면 됨
    public Product findById(int productId) {
        for(Product product : products) {
            if(product.getId() == productId) return product;
        }
        return null;
    }
}
