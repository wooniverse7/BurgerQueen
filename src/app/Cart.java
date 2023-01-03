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

    // 음료 메뉴를 부분적으로 보여주기 위해 Menu인스턴스를 필드로 정의할 필요가 있다
    private Menu menu;

    public Cart(ProductRepository productRepository, Menu menu) {
        this.productRepository = productRepository;
        this.menu = menu;
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
        Product product = productRepository.findById(productId);

        // 2.2.2.
        //상품 옵션 설정
        chooseOption(product);
        // 2.2.3.
        // 햄버거 세트는 햄버거 중심이므로, 사용자가 선택한 햄버거를 인자로 전달. composeSet에서 이를 받아 호출함
        if (product instanceof Hamburger){
            Hamburger hamburger = (Hamburger) product;
            if(hamburger.isBurgerSet()) product = composeSet(hamburger);
        }
        // 2.2.4. items에 product 추가.
        // +1 확장한 새로운 배열. 기존 배열을 새로운 배열로 복사
        Product[] newItems = new Product[items.length + 1];
        System.arraycopy(items, 0, newItems, 0, items.length); // 복사
        newItems[newItems.length - 1] = product; // 마지막 idx에 제품 입력
        items = newItems;

        System.out.printf("[📣] %s를(을) 장바구니에 담았습니다.\n", product.getName());
    }

    // chooseOption() : 특정 상품에 대해서 옵션을 선택할 수 있게 해주는 역할
    private void chooseOption(Product product){
        // product 타입을 검사한 후,
        // 요구 사항에서 요구하는대로 출력할 수 있도록 안내 문구 입력

        String input;

        if(product instanceof Hamburger){
            System.out.printf(
                    "단품으로 주문하시겠어요? (1)_단품(%d원) (2)_세트(%d원)\n",
                    product.getPrice(),
                    ((Hamburger) product).getBurgerSetPrice()
            );
            input = scanner.nextLine();
            if(input.equals("2")) ((Hamburger) product).setBurgerSet(true);
        }
        else if(product instanceof Side) {
            System.out.println("케첩은 몇개가 필요하신가요?");
            input = scanner.nextLine();
            ((Side) product).setKetchup(Integer.parseInt(input));
        }
        else if (product instanceof Drink) {
            System.out.println("빨대가 필요하신가요? (1)_예 (2)_아니오");
            input = scanner.nextLine();
            if (input.equals("2")) ((Drink) product).setHasStraw(false);
        }
    }

    // composeSet() : 햄버거 세트를 구성해주는 역할
    protected BurgerSet composeSet(Hamburger hamburger){
        System.out.println("사이드를 골라주세요");
        menu.printSides(false);

        String sideId = scanner.nextLine();
        Side side = (Side) productRepository.findById(Integer.parseInt(sideId));
        chooseOption(side);

        System.out.println("음료를 골라주세요.");
        menu.printDrinks(false);

        String drinkId = scanner.nextLine();
        Drink drink = (Drink) productRepository.findById(Integer.parseInt(drinkId));
        chooseOption(drink);

        String name = hamburger.getName() + "세트";
        int price = hamburger.getBurgerSetPrice();
        int kcal = hamburger.getKcal() + side.getKcal() + drink.getKcal();

        return new BurgerSet(name, price, kcal, hamburger, side, drink);
    }
}
