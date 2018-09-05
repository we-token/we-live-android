package show.we.lib.cloudapi;

import show.we.sdk.request.BaseResult;
import show.we.sdk.request.GetMethodRequest;
import show.we.sdk.request.Request;
import show.we.sdk.util.EnvironmentUtils;
import show.we.lib.config.Config;
import show.we.lib.model.OrderedSongsResult;
import show.we.lib.model.PresetSongsResult;

/**
 * 点歌相关API
 *
 * @author ll
 * @version 1.0.0
 */
public final class SongOrderAPI {

    private static final String METHOD_SHOW_ORDER_LIST = "show/order_list";
    private static final String METHOD_SHOW_SONG_LIST = "show/song_list";
    private static final String METHOD_ORDER_SONG = "song/order";

    /**
     * 请求已经点歌列表
     *
     * @param liveId 一场直播的id
     * @return Http请求实例
     */
    public static Request<OrderedSongsResult> orderedSongList(String liveId) {
        return new GetMethodRequest<OrderedSongsResult>(OrderedSongsResult.class, Config.getAPIHost(), METHOD_SHOW_ORDER_LIST)
                .addUrlArgument(liveId);
    }

    /**
     * 请求某个主播的预设歌单
     *
     * @param starId 主播id
     * @return Http请求实例
     */
    public static Request<PresetSongsResult> presetSongList(long starId) {
        return new GetMethodRequest<PresetSongsResult>(PresetSongsResult.class, Config.getAPIHost(), METHOD_SHOW_SONG_LIST)
                .addUrlArgument(starId);
    }

    /**
     * 请求点歌
     *
     * @param accessToken accessToken
     * @param liveId      一场直播的id
     * @param songName    歌曲名
     * @return Http请求实例
     */
    public static Request<BaseResult> orderSong(String accessToken, String liveId, String songName) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_ORDER_SONG)
                .addUrlArgument(accessToken)
                .addUrlArgument(liveId)
                .addArgument("song_name", songName)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }
}
