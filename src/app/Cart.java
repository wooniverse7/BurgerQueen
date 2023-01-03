package app;

import app.product.Product;
import app.product.ProductRepository;
import app.product.subproduct.BurgerSet;
import app.product.subproduct.Drink;
import app.product.subproduct.Hamburger;
import app.product.subproduct.Side;

import java.util.Scanner;

public class Cart {
    private Product[] items = new Product[0];
    private Scanner scanner = new Scanner(System.in);

    private ProductRepository productRepository;

    public Cart(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public void printCart() {
        System.out.println("🧺 장바구니");
        System.out.println("-".repeat(60));

        //	여기에 장바구니 상품들을 옵션 정보와 함께 출력
        System.out.println("-".repeat(60));
        System.out.printf("합계 : %d원\n", 5000); // 금액 합계

        System.out.println("이전으로 돌아가려면 엔터를 누르세요. ");
        scanner.nextLine();
    }

    private void printCartItemDetails() {
        for (Product product: items) {
            //if (product가 BurgerSet의 인스턴스라면) 세트 정보 출력
            if (product instanceof BurgerSet) {
                BurgerSet burgerSet = (BurgerSet) product; // 다운 캐스팅
                System.out.printf(
                        "  %s %6d원 (%s(케첩 %d개), %s(빨대 %s))\n",
                        product.getName(),
                        product.getPrice(),
                        burgerSet.getSide().getName(),
                        burgerSet.getSide().getKetchup(),
                        burgerSet.getDrink().getName(),
                        burgerSet.getDrink().hasStraw() ? "있음" : "없음"
                );
            }
	        //else if (product가 Hamburger의 인스턴스라면) (단품) 출력
            else if (product instanceof Hamburger){
                System.out.printf(
                        "  %-8s %6d원 (단품)\n",
                        product.getName(),
                        product.getPrice()
                );
            }
	        //else if (product가 Side의 인스턴스라면) 케첩 개수 출력
            else if(product instanceof Side){
                System.out.printf(
                        "  %-8s %6d원 (빨대 %s)\n",
                        product.getName(),
                        product.getPrice(),
                        ((Side) product).getKetchup()
                );
            }
	        //else if (product가 Drinks의 인스턴스라면) 빨대 유무 출력
            else if (product instanceof Drink) {
                System.out.printf(
                        "  %-8s %6d원 (빨대 %s)\n",
                        product.getName(),
                        product.getPrice(),
                        ((Drink) product).hasStraw() ? "있음" : "없음"  // 아래 설명 참고
                );
            }
        }
    }

    // 상품 금액 합계 구하기 메서드
    private int calculateTotalPrice() {
        int totalPrice = 0;
        for(Product product : items){
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

    public void addToCart(int productId){
        // 2.2.1.
        //Product product = productId를 통해 productId를 id로 가지는 상품 찾기
        // 2.2.2.
        //상품 옵션 설정 // chooseOption()
        // 2.2.3.
        //if (product가 Hamburger의 인스턴스이고, isBurgerSet이 true라면) {
        //    product = 세트 구성 // composeSet();
        //}
        // 2.2.4.
        //items에 product 추가
        //"[📣] XXXX를(을) 장바구니에 담았습니다." 출력
        Product product;

        for (Product element : productRepository.getAllProducts())
    }
}
