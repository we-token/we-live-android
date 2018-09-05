package show.we.lib.utils;

/**
 * 如果对象为空根据对象类型new一个
 * @author ll
 * @version 1.0.0
 */
public class NullObjectAssert {

    /**
     * 如果对象为空根据对象类型new一个
     * @param <T> T
     * @param cls cls
     * @param obj t
     * @return obj
     */
    public static <T> T assertNull(T obj, Class<T> cls) {
        try {
            return obj == null ? cls.newInstance() : obj;
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("assetNull InstantiationException: Class[" + cls + "]");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("assetNull IllegalAccessException: Class[" + cls + "]");
        }
    }
}
