package show.we.lib.cloudapi;

import show.we.sdk.request.BaseResult;
import show.we.sdk.request.GetMethodRequest;
import show.we.sdk.request.Request;
import show.we.sdk.util.EnvironmentUtils;
import show.we.lib.config.Config;
import show.we.lib.model.OfficialCommentResult;
import show.we.lib.model.OfficialTopicInfoResult;
import show.we.lib.model.OfficialTopicListResult;

/**
 * Created by CG on 14-1-7.
 *
 * @author ll
 * @version 3.4.0
 */
public class OfficialTopicAPI {

    private static final String METHOD_TOPIC_LIST = "topic/list_official";
    private static final String METHOD_TOPIC_ADD = "topicmg/add_official";
    private static final String METHOD_TOPIC_INFO = "topic/info_official";
    private static final String METHOD_COMMENTS = "topic/comments_official";

    private static final String KEY_SIZE = "size";
    private static final String KEY_PAGE = "page";
    private static final String KEY_LABEL = "label";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_AUTH_CODE = "auth_code";

    /**
     * topicList
     *
     * @param size  size
     * @param page  page
     * @param label label
     * @return OfficialTopicListResult
     */
    public static Request<OfficialTopicListResult> topicList(int size, int page, String label) {
        return new GetMethodRequest<OfficialTopicListResult>(OfficialTopicListResult.class, Config.getAPIHost(), METHOD_TOPIC_LIST)
                .addArgument(KEY_SIZE, size)
                .addArgument(KEY_PAGE, page)
                .addArgument(KEY_LABEL, label);
    }

    /**
     * addTopic
     *
     * @param accessToken accessToken
     * @param title       title
     * @param content     content
     * @param label       label
     * @param code        code
     * @return BaseResult
     */
    public static Request<BaseResult> addTopic(String accessToken, String title, String content, String label, String code) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_TOPIC_ADD)
                .addUrlArgument(accessToken)
                .addArgument(KEY_TITLE, title)
                .addArgument(KEY_CONTENT, content)
                .addArgument(KEY_LABEL, label)
                .addArgument(KEY_AUTH_CODE, code)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * topicInfo
     *
     * @param id id
     * @return OfficialTopicInfoResult
     */
    public static Request<OfficialTopicInfoResult> topicInfo(String id) {
        return new GetMethodRequest<OfficialTopicInfoResult>(OfficialTopicInfoResult.class, Config.getAPIHost(), METHOD_TOPIC_INFO)
                .addUrlArgument(id);
    }

    /**
     * officialComments
     *
     * @param id   id
     * @param size size
     * @param page page
     * @return OfficialCommentResult
     */
    public static Request<OfficialCommentResult> officialComments(String id, int size, int page) {
        return new GetMethodRequest<OfficialCommentResult>(OfficialCommentResult.class, Config.getAPIHost(), METHOD_COMMENTS)
                .addUrlArgument(id)
                .addArgument(KEY_SIZE, size)
                .addArgument(KEY_PAGE, page);
    }
}
