interface Beverage{
    int cost();
    String description();
}
abstract class Decorator implements Beverage{
     Beverage beverage;
     public Decorator(Beverage beverage) {
        this.beverage = beverage;
     }
}
class Espresso implements Beverage{
    @Override
    public int cost() {
        return 90;
    }
    @Override
    public String description() {
        return "Espresso";
    }
}
class HouseBlend  implements Beverage{
    @Override
    public int cost() {
        return 100;
    }
    @Override
    public String description() {
        return "HouseBlend";
    }
}
class Milk extends Decorator {

    Milk (Beverage beverage){
        super(beverage);
    }
    @Override
    public int cost() {
        return beverage.cost()+20;
    }
    @Override
    public String description() {
        return beverage.description()+" "+"Milk";
    }
}
class Carmel extends Decorator {
    Carmel(Beverage beverage) {
        super(beverage);
    }

    @Override
    public int cost() {
        return beverage.cost()+30;
    }
    public String description() {
        return  beverage.description()+" "+"Carmel";
    }
}
class Whip extends Decorator {

    Whip(Beverage beverage) {
        super(beverage);
    }
    @Override
    public int cost() {
        return beverage.cost()+40;
    }
    @Override
    public String description() {
        return beverage.description()+" "+"Whip";
    }
}
class Sugar extends Decorator {

    Sugar(Beverage beverage) {
        super(beverage);
    }
    @Override
    public int cost() {
        return beverage.cost()+50;
    }
    @Override
    public String description() {
        return beverage.description()+" "+"sugar";
    }
}


public class DecoratorPattern {

    public static void main(String[] args) {
        Beverage beverage = new Milk(new Carmel( new Carmel( new Espresso())));
        System.out.printf(beverage.cost()+" "+beverage.description());

    }
}

