package hr.fer.zemris.ooup.lab2.model.plugins;

import hr.fer.zemris.ooup.lab2.model.Animal.Animal;

/**
 * Created by ivan on 5/28/15.
 */
public class Tiger extends Animal {
    private String animalName;

    public Tiger(String name) {
        animalName = name;
    }

    @Override
    public String name() {
        return animalName;
    }

    @Override
    public String greet() {
        return "Mijau!";
    }

    @Override
    public String menu() {
        return "mlako mlijeko";
    }
}
