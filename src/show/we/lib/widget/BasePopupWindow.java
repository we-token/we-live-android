package show.we.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;
import show.we.sdk.util.ConstantUtils;
import show.we.lib.utils.DisplayUtils;

/**
 * Created by CG on 14-2-27.
 * 提供一些显示的解决方案
 * <p>提供一些显示的解决方案</p>
 * <p>设置宽高后，坐标系的中心及方向可能会变化--使用时要注意</p>
 * @author ll
 * @version 4.0.0
 */
public class BasePopupWindow extends PopupWindow {

	/**
	 * BasePopupWindow
	 * @param context context
	 */
	public BasePopupWindow(Context context) {
		super(context);
	}

	/**
	 * BasePopupWindow
	 * @param context context
	 * @param attrs attrs
	 */
	public BasePopupWindow(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * BasePopupWindow
	 * @param context context
	 * @param attrs attrs
	 * @param defStyle defStyle
	 */
	public BasePopupWindow(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * BasePopupWindow
	 */
	public BasePopupWindow() {
	}

	/**
	 * BasePopupWindow
	 * @param contentView contentView
	 */
	public BasePopupWindow(View contentView) {
		super(contentView);
	}

	/**
	 * BasePopupWindow
	 * @param width width
	 * @param height height
	 */
	public BasePopupWindow(int width, int height) {
		super(width, height);
	}

	/**
	 * BasePopupWindow
	 * @param contentView contentView
	 * @param width width
	 * @param height height
	 */
	public BasePopupWindow(View contentView, int width, int height) {
		super(contentView, width, height);
	}

	/**
	 * BasePopupWindow
	 * @param contentView contentView
	 * @param width width
	 * @param height height
	 * @param focusable focusable
	 */
	public BasePopupWindow(View contentView, int width, int height, boolean focusable) {
		super(contentView, width, height, focusable);
	}

	/**
	 * showWithSameWidth
	 * @param anchor anchor
	 * @param heightOffset heightOffset(px)
	 */
	public void showWithSameWidth(View anchor, int heightOffset) {
		showWithSameWidth(anchor, heightOffset, 0);
	}

	/**
	 * showWithSameWidth
	 * @param anchor anchor
	 * @param heightOffset heightOffset(px)
	 * @param maxHeight popupWindow最大高度
	 */
	public void showWithSameWidth(View anchor, int heightOffset, int maxHeight) {
		show(anchor, heightOffset, anchor.getMeasuredWidth(), maxHeight);
	}

	/**
	 * show
	 * @param anchor anchor
	 * @param heightOffset heightOffset(px)
	 * @param width 宽度
	 * @param maxHeight popupWindow最大高度
	 */
	public void show(View anchor, int heightOffset, int width, int maxHeight) {
		int defaultMaxHeight = DisplayUtils.getHeightPixels() - DisplayUtils.dp2px(ConstantUtils.HUNDRED);
		maxHeight = maxHeight <= 0 ? defaultMaxHeight : maxHeight;
		getContentView().measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)
				, View.MeasureSpec.makeMeasureSpec(maxHeight, View.MeasureSpec.AT_MOST));
		setWidth(width);
		setHeight(getContentView().getMeasuredHeight());
		showAsDropDown(anchor, 0, heightOffset);
	}

	/**
	 * 显示在某个锚点的上面(和锚点相同的宽度)
	 * @param anchor 某个锚点的上面
	 * @param heightOffset heightOffset(px)
	 */
	public void showUpWithSameWidth(View anchor, int heightOffset) {
		showUpWithSameWidth(anchor, heightOffset, 0);
	}

	/**
	 * 显示在某个锚点的上面(和锚点相同的宽度)
	 * @param anchor 某个锚点的上面
	 * @param heightOffset heightOffset(px)
	 * @param maxHeight popupWindow最大高度
	 */
	public void showUpWithSameWidth(View anchor, int heightOffset, int maxHeight) {
		showUp(anchor, heightOffset, anchor.getMeasuredWidth(), maxHeight);
	}

	/**
	 * 显示在某个锚点的上面
	 * @param anchor 某个锚点的上面
	 * @param heightOffset heightOffset(px)
	 * @param width 宽度
	 * @param maxHeight popupWindow最大高度
	 */
	public void showUp(View anchor, int heightOffset, int width, int maxHeight) {
		int defaultMaxHeight = DisplayUtils.getHeightPixels() - DisplayUtils.dp2px(ConstantUtils.HUNDRED);
		maxHeight = maxHeight <= 0 ? defaultMaxHeight : maxHeight;
		getContentView().measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)
				, View.MeasureSpec.makeMeasureSpec(maxHeight, View.MeasureSpec.AT_MOST));
		int selfHeight = getContentView().getMeasuredHeight();
		int anchorHeight = anchor.getMeasuredHeight();
		int yOff = -(selfHeight + anchorHeight);
		setWidth(width);
		setHeight(selfHeight);
		showAsDropDown(anchor, 0, yOff + heightOffset);
	}

	/**
	 * 显示在某个锚点的上面
	 * @param anchor 某个锚点的上面
	 * @param heightOffset heightOffset(px)
	 * @param width 宽度
	 */
	public void showUpAndCenterHorizontal(View anchor, int heightOffset, int width) {
		showUpAndCenterHorizontal(anchor, heightOffset, width, 0);
	}

	/**
	 * 显示在某个锚点的上面，并水平居中
	 * @param anchor 某个锚点的上面
	 * @param heightOffset heightOffset(px)
	 * @param width 宽度
	 * @param maxHeight popupWindow最大高度
	 */
	public void showUpAndCenterHorizontal(View anchor, int heightOffset, int width, int maxHeight) {
		int defaultMaxHeight = DisplayUtils.getHeightPixels() - DisplayUtils.dp2px(ConstantUtils.HUNDRED);
		maxHeight = maxHeight <= 0 ? defaultMaxHeight : maxHeight;
		getContentView().measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)
				, View.MeasureSpec.makeMeasureSpec(maxHeight, View.MeasureSpec.AT_MOST));
		int selfHeight = getContentView().getMeasuredHeight();
		int anchorWidth = anchor.getMeasuredWidth();
		int anchorHeight = anchor.getMeasuredHeight();
		int xOff = (anchorWidth - width) / 2;
		int yOff = -(selfHeight + anchorHeight);
		setWidth(width);
		setHeight(selfHeight);
		showAsDropDown(anchor, xOff, yOff + heightOffset);
	}
}
