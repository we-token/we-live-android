package show.we.lib.utils;

import android.content.Context;
import show.we.sdk.cache.ImageCache;
import show.we.lib.cache.ObjectCache;
import show.we.lib.config.Config;

/**
 * 缓存相关
 *
 * @author ll
 * @version 1.0.0
 */
public class CacheUtils {

    private static ImageCache sImageCache;
    private static ObjectCache sObjectCache;

    /**
     * 初始化缓存
     *
     * @param context context
     */
    public static void init(Context context) {
        try {
            sImageCache = ImageCache.open(Config.IMAGE_CACHE_MEM_PERCENT, Config.getImageCacheFolderPath());
            sObjectCache = ObjectCache.open(Config.OBJECT_CACHE_MEM_PERCENT, Config.getObjectCacheFolderPath());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("CacheUtils init error !");
        }
    }

    /**
     * 获取图片缓存实例
     *
     * @return 图片缓存实例
     */
    public static ImageCache getImageCache() {
        return sImageCache;
    }

    /**
     * 获取对象缓存实例
     *
     * @return 图片缓存实例
     */
    public static ObjectCache getObjectCache() {
        return sObjectCache;
    }

    /**
     * close图片缓存
     */
    public static void close() {
        if (sImageCache != null) {
            sImageCache.close();
        }

        if (sObjectCache != null) {
            sObjectCache.close();
        }
    }
}
