package hr.fer.zemris.ooup.lab2.model.Animal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by ivan on 5/28/15.
 */
public  class AnimalFactory {

    public static Animal newInstance(String animalKind, String name) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<Animal> clazz = null;
        clazz = (Class<Animal>)Class.forName("hr.fer.zemris.ooup.lab2.model.plugins."+animalKind);

        Constructor<?> ctr = clazz.getConstructor(String.class);

        return (Animal)ctr.newInstance(name);
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Animal modrobradi = newInstance("Parrot", "Modrobradi");

        modrobradi.animalPrintGreeting();
        modrobradi.animalPrintMenu();
    }
}
