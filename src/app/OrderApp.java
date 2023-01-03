package app;

import app.product.Product;
import app.product.ProductRepository;

import java.util.Scanner;

public class OrderApp {

    public void start(){

        Scanner scanner = new Scanner(System.in);

        ProductRepository productRepository = new ProductRepository();
        Product[] products = productRepository.getAllProducts();
        Menu menu = new Menu((products));

        // Cart 클래스 인스턴스화
        Cart cart = new Cart(productRepository, menu); // 추가

        System.out.println("BurgerQueen Order Service");

        while (true) {
            menu.printMenu(); // 🟥 추가
            String input = scanner.nextLine();

            if (input.equals("+")) {
                //주문 내역 출력
                break;
            }
		    else {
                int menuNumber = Integer.parseInt(input);

                if(menuNumber == 0) cart.printCart();
                else if(1 <= menuNumber && menuNumber <= products.length)
                    cart.addToCart(menuNumber);
            }
        }
    }
}
