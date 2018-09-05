package show.we.lib.ui;

/**
 * Created by CG on 13-11-5.
 *
 * @author ll
 * @version 3.0.0
 */
public interface OnFavStarsChangedListener {
    /**
     * onFavStarsChanged
     *
     * @param isSuccess       isSuccess
     * @param isRequestFollow isRequestFollow
     * @param starId          starId
     */
    void onFavStarsChanged(boolean isSuccess, boolean isRequestFollow, long starId);
}
