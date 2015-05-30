package hr.fer.zemris.ooup.lab3.funkyeditor.plugins;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by ivan on 5/30/15.
 */
public class PluginFactory {


    public static Plugin newInstance(String pluginName) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<Plugin> clazz = null;
        clazz = (Class<Plugin>)Class.forName("plugins."+pluginName);

        Constructor<?> ctr = clazz.getConstructor();

        return (Plugin)ctr.newInstance();
    }
}
