package app;

import app.product.Product;
import app.product.subproduct.Drink;
import app.product.subproduct.Hamburger;
import app.product.subproduct.Side;

public class Menu {
    private Product[] products;

    public Menu(Product[] products) {
        this.products = products;
    }

    // 글자만 출력하므로 리턴값 void
    // 메뉴만 보여주면 되는 메서드이므로 입력값(매개변수) 없음
    public void printMenu() {
        System.out.println("[🔻] 메뉴");
        System.out.println("-".repeat(60));

        printHamburgers(true);
        printSides(true);
        printDrinks(true);

        System.out.println();
        System.out.println("🧺 (0) 장바구니");
        System.out.println("📦 (+) 주문하기");
        System.out.println("-".repeat(60));
        System.out.print("[📣] 메뉴를 선택해주세요 : ");
    }

    protected void printSides(boolean printPrice) {
        System.out.println("🍟 사이드");
        //여기에서 사이드 출력
        for(Product product : products) {
            if(product instanceof Side) {
                printEachMenu(product, printPrice);
            }
        }
        System.out.println();
    }

    protected void printDrinks(boolean printPrice) {
        System.out.println("🥤 음료");
        //여기에서 음료 출력
        for(Product product : products) {
            if(product instanceof Drink) {
                printEachMenu(product, printPrice);
            }
        }
        System.out.println();
    }

    protected void printHamburgers(boolean printPrice) {
        System.out.println("🍔 햄버거");
        for (Product product : products) {
            if(product instanceof Hamburger){
                printEachMenu(product, printPrice);
            }
        }
        System.out.println();
    }

    private void printEachMenu(Product product, boolean printPrice) {
        // printPrice가 true이면 가격을 포함하여 메뉴 출력
        // false이면 가격 표기 X
        if(printPrice) System.out.printf("  (%d) %s %5dkcal %5d원\n",
                product.getId(), product.getName(), product.getKcal(), product.getPrice()
        );
        else System.out.printf("  (%d) %s %5dkcal\n",
                product.getId(), product.getName(), product.getKcal());
    }
}
