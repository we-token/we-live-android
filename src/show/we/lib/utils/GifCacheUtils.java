package show.we.lib.utils;

import android.content.Context;

import show.we.lib.widget.gif.Frame;
import show.we.lib.widget.gif.GifParser;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * gif图片的缓存
 *
 * @author ll
 * @version 1.0.0
 */
public class GifCacheUtils {

    private static final int EXPRESSION_GIF_COUNT = 90;
    private static final int USER_LEVEL_GIF_COUNT = 26;
    private static final int STAR_LEVEL_GIF_COUNT = 56;

    private static final String EXPRESSION_GIF_PREFIX = "gif_expression_";
    private static final String USER_LEVEL_GIF_PREFIX = "gif_user_level_";
    private static final String STAR_LEVEL_GIF_PREFIX = "gif_star_level_";

    private static final Map<Type, SoftReference<List<Frame>>[]> GIF_FRAME_LIST_MAP = new HashMap<Type, SoftReference<List<Frame>>[]>();
    private static final List<List<Frame>> EMOTICON_GIF_LIST = new ArrayList<List<Frame>>();

    private static final String RAW = "raw";

    /**
     * gif动画类型
     */
    public enum Type {
        /**
         * 表情 *
         */
        EXPRESSION(EXPRESSION_GIF_PREFIX, EXPRESSION_GIF_COUNT),
        /**
         * 用户等级 *
         */
        USER_LEVEL(USER_LEVEL_GIF_PREFIX, USER_LEVEL_GIF_COUNT),
        /**
         * 主播等级 *
         */
        STAR_LEVEL(STAR_LEVEL_GIF_PREFIX, STAR_LEVEL_GIF_COUNT);

        private String mResNamePrefix;
        private int mCount;
        private int mDigit;

        private Type(String resNamePrefix, int count) {
            mResNamePrefix = resNamePrefix;
            mCount = count;
            mDigit = Integer.toString(count).length();
        }

        private String getResNamePrefix() {
            return mResNamePrefix;
        }

        /**
         * 获取gif图片的个数
         *
         * @return gif图片的个数
         */
        public int getCount() {
            return mCount;
        }

        /**
         * 获取gif资源文件后缀的位数 (如：3位 -> gif_expression_001) *
         */
        private int getDigit() {
            return mDigit;
        }
    }

    /**
     * 初始化
     */
    public static void init() {
        new Thread() {
            @Override
            public void run() {
                for (Type type : Type.values()) {
                    GIF_FRAME_LIST_MAP.put(type, new SoftReference[type.getCount()]);
                }
            }
        }.start();
    }

    /**
     * loadEmoticon
     *
     * @param context context
     */
    public static void loadEmoticon(final Context context) {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < EXPRESSION_GIF_COUNT; i++) {
                    EMOTICON_GIF_LIST.add(getExpressionGifIcon(context, i));
                }
            }
        }.start();
    }

    /**
     * clearEmoticon
     */
    public static void clearEmoticon() {
        EMOTICON_GIF_LIST.clear();
    }

    private static List<Frame> parseGif(Context context, Type type, int index) {
        int resId = getGifResId(context, type, index);
        List<Frame> frameList = new ArrayList<Frame>();
        new GifParser().doParse(context.getResources().openRawResource(resId), frameList);
        return frameList;
    }

    private static int getGifResId(Context context, Type type, int index) {
        String ext = String.format("%0" + type.getDigit() + "d", index);
        return context.getResources().getIdentifier(type.getResNamePrefix() + ext, RAW, context.getPackageName());
    }

    /**
     * 获取表情Gif图标的所有帧
     *
     * @param index   表情index
     * @param context 上下文
     * @return gif图标的所有帧
     */
    public static List<Frame> getExpressionGifIcon(Context context, int index) {
        index = Math.abs(index) >= EXPRESSION_GIF_COUNT ? EXPRESSION_GIF_COUNT - 1 : Math.abs(index);
        return getGifIcon(context, Type.EXPRESSION, index);
    }

    /**
     * 获取主播等级Gif图标的所有帧
     *
     * @param level   主播等级
     * @param context 上下文
     * @return 资源解析对象
     */
    public static List<Frame> getStarLevelGifIcon(Context context, int level) {
        level = Math.abs(level) >= STAR_LEVEL_GIF_COUNT ? STAR_LEVEL_GIF_COUNT - 1 : Math.abs(level);
        return getGifIcon(context, Type.STAR_LEVEL, level);
    }


    /**
     * 获取用户等级Gif图标的所有帧
     *
     * @param level   用户等级
     * @param context 上下文
     * @return 资源解析对象
     */
    public static List<Frame> getUserLevelGifIcon(Context context, int level) {
        level = Math.abs(level) >= USER_LEVEL_GIF_COUNT ? USER_LEVEL_GIF_COUNT - 1 : Math.abs(level);
        return getGifIcon(context, Type.USER_LEVEL, level);
    }

    /**
     * 获取gif图标的所有帧
     *
     * @param type         EXPRESSION or USER_LEVEL or STAR_LEVEL
     * @param levelOrIndex 表情index或者主播/用户等级
     * @return gif图标的所有帧
     */
    private static List<Frame> getGifIcon(Context context, Type type, int levelOrIndex) {
        try {
            SoftReference<List<Frame>> gifRef = GIF_FRAME_LIST_MAP.get(type)[levelOrIndex];
            if (gifRef == null || gifRef.get() == null) {
                List<Frame> frameList;
                if (type == Type.EXPRESSION && EMOTICON_GIF_LIST.size() > levelOrIndex) {
                    frameList = EMOTICON_GIF_LIST.get(levelOrIndex);
                } else {
                    frameList = parseGif(context, type, levelOrIndex);
                }
                gifRef = new SoftReference<List<Frame>>(frameList);
                GIF_FRAME_LIST_MAP.get(type)[levelOrIndex] = gifRef;
            }
            return gifRef.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
