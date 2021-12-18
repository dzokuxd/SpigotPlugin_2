package pl.spigotplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtils
{
    public static Class<?> getCraftClass(final String name) {
        final String className = "net.minecraft.server." + getVersion() + name;
        Class<?> c = null;
        try {
            c = Class.forName(className);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    public static Class<?> getBukkitClass(final String name) {
        final String className = "org.bukkit.craftbukkit." + getVersion() + name;
        Class<?> c = null;
        try {
            c = Class.forName(className);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    public static Object getHandle(final Entity entity) {
        try {
            return getMethod(entity.getClass(), "getHandle").invoke(entity);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getHandle(final World world) {
        try {
            return getMethod(world.getClass(), "getHandle").invoke(world);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Field getField(final Class<?> cl, final String field_name) {
        try {
            return cl.getDeclaredField(field_name);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setValue(final Field f, final Object o, final Object v) {
        try {
            f.setAccessible(true);
            f.set(o, v);
            f.setAccessible(false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Method getMethod(final Class<?> cl, final String method, final Class<?>... args) {
        Method[] methods;
        for (int length = (methods = cl.getMethods()).length, i = 0; i < length; ++i) {
            final Method m = methods[i];
            if (m.getName().equals(method) && classListEqual(args, m.getParameterTypes())) {
                return m;
            }
        }
        return null;
    }

    public static Method getMethod(final Class<?> cl, final String method) {
        Method[] methods;
        for (int length = (methods = cl.getMethods()).length, i = 0; i < length; ++i) {
            final Method m = methods[i];
            if (m.getName().equals(method)) {
                return m;
            }
        }
        return null;
    }

    public static boolean classListEqual(final Class<?>[] l1, final Class<?>[] l2) {
        boolean equal = true;
        if (l1.length != l2.length) {
            return false;
        }
        for (int i = 0; i < l1.length; ++i) {
            if (l1[i] != l2[i]) {
                equal = false;
                break;
            }
        }
        return equal;
    }

    public static String getVersion() {
        final String name = Bukkit.getServer().getClass().getPackage().getName();
        return (name.substring(name.lastIndexOf(46) + 1)) + ".";
    }
}

