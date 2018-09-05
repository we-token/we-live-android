package show.we.lib.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import show.we.lib.widget.gif.GifSpan;


/**
 * 表情
 *
 * @author ll
 * @version 1.0.0
 */
public class EmoticonUtils {

    private static final int EMOTICON_GIF_SIZE_DIP = 24;
    private static final float FLOAT_NUMBER = 0.5f;

    /**
     * 显示一个表情
     *
     * @param context              上下文对象
     * @param attachTextView       用于view的重绘
     * @param stringBuilder        设置span的SpannableStringBuilder
     * @param startPos             设置span的开始位置
     * @param endPos               设置span的结束位置
     * @param defaultColor         默认颜色
     * @param emoticonNamesRes     表情名称数组资源
     * @param isShowFirstFrameOnly 是否仅仅显示第一帧
     */
    public static void loadEmoticon(Context context, View attachTextView, SpannableStringBuilder stringBuilder
            , int startPos, int endPos, int defaultColor, int emoticonNamesRes, boolean isShowFirstFrameOnly) {

        if (stringBuilder == null || startPos < 0 || startPos > endPos || attachTextView == null) {
            throw new IllegalArgumentException("loadEmoticon Arguments error!");
        }

        String[] emoticonNames = context.getResources().getStringArray(emoticonNamesRes);

        String subStr = stringBuilder.subSequence(startPos, endPos).toString();
        String[] splits = subStr.split("/");
        if (splits == null || splits.length == 0) {
            return;
        }
        int start = startPos + splits[0].length();
        stringBuilder.setSpan(new ForegroundColorSpan(defaultColor), startPos, start, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        for (int i = 1; i < splits.length; i++) {
            String split = "/" + splits[i];
            boolean bFound = false;
            for (int j = 0; j < emoticonNames.length; j++) {
                String expression = emoticonNames[j];
                if (split.startsWith(expression)) {
                    int size = (int) (context.getResources().getDisplayMetrics().density * EMOTICON_GIF_SIZE_DIP + FLOAT_NUMBER);
                    stringBuilder.setSpan(new GifSpan(context, GifCacheUtils.getExpressionGifIcon(context, j), attachTextView
                            , DynamicDrawableSpan.ALIGN_BASELINE, isShowFirstFrameOnly, size, size)
                            , start, start + expression.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    if (expression.length() < split.length()) {
                        stringBuilder.setSpan(new ForegroundColorSpan(defaultColor), start + expression.length()
                                , start + split.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }

                    bFound = true;
                    break;
                }
            }
            if (!bFound) {
                stringBuilder.setSpan(new ForegroundColorSpan(defaultColor), start, start + split.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            start += split.length();
        }
    }
}
