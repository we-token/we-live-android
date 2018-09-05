package show.we.lib.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import show.we.sdk.cache.ImageCache;
import show.we.sdk.util.SDKVersionUtils;
import show.we.sdk.util.SecurityUtils;
import show.we.sdk.util.StringUtils;
import show.we.lib.R;

import java.io.File;

/**
 * Created by CG on 13-10-28.
 *
 * @author ll
 * @version 3.0.0
 */
public class ImageUtils {

    /**
     * 清除指定url，指定缓存名的缓存
     *
     * @param url    图片url
     * @param width  图片宽度   <b>原图大小传递Integer.MAX_VALUE</b>
     * @param height 图片高度  <b>原图大小传递Integer.MAX_VALUE</b>
     */
    public static void clearImageCache(String url, int width, int height) {
        if (!StringUtils.isEmpty(url)) {
            CacheUtils.getImageCache().clearCache(url, width, height);
        }
    }

    /**
     * 请求并在ImageView上显示一张图片
     *
     * @param view   显示图片的view
     * @param url    图片url
     * @param width  图片的宽度
     * @param height 图片的高度
     * @param resDef 默认图片资源
     */
    public static void requestImage(final ImageView view, String url, int width, int height, int resDef) {
        view.setTag(url);
        if (!StringUtils.isEmpty(url)) {
            Bitmap picture = CacheUtils.getImageCache().getBitmapFromMemCache(url, null, width, height);
            if (picture == null) {
                Utils.setImageResourceSafely(view, resDef);
                CacheUtils.getImageCache().loadImageAsync(url, width, height, new ImageCache.Callback() {
                    @Override
                    public void imageLoaded(String s, int i, int i2, Bitmap bitmap) {
                        if (s != null && s.equals(view.getTag())) {
                            view.setImageBitmap(bitmap);
                            view.startAnimation(AnimationUtils.loadAnimation(view.getContext(), R.anim.load_imgage_fade_in));
                        }
                    }
                });
            } else {
                view.setImageBitmap(picture);
            }
        } else {
            Utils.setImageResourceSafely(view, resDef);
        }
    }

    /**
     * 请求并在ImageView上显示一张图片,但不使用缓存
     *
     * @param view   显示图片的view
     * @param url    图片url
     * @param width  图片的宽度
     * @param height 图片的高度
     * @param resDef 默认图片资源
     */
    public static void requestImageWithoutCache(final ImageView view, String url, int width, int height, int resDef) {
        clearImageCache(url, width, height);
        requestImage(view, url, width, height, resDef);
    }

    /**
     * 获取已缓存图片的本地路径
     *
     * @param url       url
     * @param cacheName cacheName
     * @return 路径
     */
    public static String getCacheImageDir(String url, String cacheName) {
        StringBuilder sb = new StringBuilder(CacheUtils.getImageCache().getDiskCacheDir())
                .append(File.separator)
                .append(StringUtils.isEmpty(cacheName) ? SecurityUtils.MD5.get32MD5String(url) : cacheName);
        return sb.toString();
    }

    /**
     * 缓存图片文件
     *
     * @param url      url
     * @param width    width
     * @param height   height
     * @param callback callback
     */
    public static void loadImageToCache(String url, int width, int height, ImageCache.Callback callback) {
        CacheUtils.getImageCache().loadImageAsync(url, width, height, callback);
    }

    /**
     * 剪切中间的bitmap
     * @param bitmap bitmap
     * @param squareLength squareLength
     * @return Bitmap
     */
    public static Bitmap centerCorpBitmap(Bitmap bitmap, int squareLength) {
        Bitmap newBitmap = null;
        int imageSquareLength = Math.min(bitmap.getWidth(), bitmap.getHeight());
        int croppedLength = Math.min(imageSquareLength, squareLength);
        float scale = (float) croppedLength / imageSquareLength;
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        int x = 0;
        int y = 0;
        if (bitmap.getWidth() >= bitmap.getHeight()) {
            x = bitmap.getWidth() / 2 - bitmap.getHeight() / 2;
        } else {
            y = bitmap.getHeight() / 2 - bitmap.getWidth() / 2;
        }
        try {
            newBitmap = Bitmap.createBitmap(bitmap, x, y, imageSquareLength, imageSquareLength, matrix, true);
            if (SDKVersionUtils.hasHoneycombMR1()) {
                newBitmap.setHasAlpha(bitmap.hasAlpha());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return newBitmap;
    }
}
