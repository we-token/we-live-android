package show.we.lib.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.CharacterStyle;
import android.text.style.URLSpan;
import android.widget.TextView;
import show.we.sdk.cache.ImageCache;
import show.we.lib.R;
import show.we.lib.utils.CacheUtils;

/**
 *
 * @author ll
 * @version 1.0.0
 */
public class HtmlTextUtils {

    /**
     * 给textView设置Html文本样式
     * @param textView textView
     * @param source source
     */
    public static void setHtmlText(final TextView textView, final String source) {
        Html.ImageGetter imageGetter = new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String url) {
                Bitmap picture = CacheUtils.getImageCache().getBitmapFromMemCache(url, null, Integer.MAX_VALUE, Integer.MAX_VALUE);
                if (picture == null) {
                    CacheUtils.getImageCache().loadImageAsync(url, Integer.MAX_VALUE, Integer.MAX_VALUE, new ImageCache.Callback() {
                        @Override
                        public void imageLoaded(String url, int width, int height, Bitmap bitmap) {
                            setHtmlText(textView, source);
                        }
                    });
                    return textView.getResources().getDrawable(R.drawable.app_icon);
                } else {
                    return new BitmapDrawable(picture);
                }
            }
        };

        Spanned spanned = Html.fromHtml(source, imageGetter, null);
        SpannableStringBuilder builder = new SpannableStringBuilder(spanned);
        CharacterStyle[] style = spanned.getSpans(0, spanned.length(), CharacterStyle.class);
        for (int i = 0; i < style.length; i++) {
            if (style[i] instanceof URLSpan) {
                URLSpan urlSpan = (URLSpan) style[i];
                int start = builder.getSpanStart(urlSpan);
                int end = builder.getSpanEnd(urlSpan);
                builder.removeSpan(urlSpan);
                builder.setSpan(new ShowURLSpan(textView.getContext(), urlSpan.getURL(), builder.subSequence(start, end).toString())
                        , start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(builder);
    }
}
