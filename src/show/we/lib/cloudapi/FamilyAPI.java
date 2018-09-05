package show.we.lib.cloudapi;

import show.we.sdk.request.BaseResult;
import show.we.sdk.request.GetMethodRequest;
import show.we.sdk.request.PostMethodRequestV2;
import show.we.sdk.request.Request;
import show.we.sdk.util.EnvironmentUtils;
import show.we.lib.config.Config;
import show.we.lib.model.*;

/**
 * Created by CG on 13-11-14.
 *
 * @author ll
 * @version 3.1.0
 */
public final class FamilyAPI {

    private static final String METHOD_FAMILY_LIST = "public/family_list";
    private static final String METHOD_FAMILY_INFO = "public/family_info_client";
    private static final String METHOD_FAMILY_MEMBER = "public/family_members";
    private static final String METHOD_FAMILY_STAR = "public/family_stars";
    private static final String METHOD_MEMBER_APPLY = "member/apply";
    private static final String METHOD_MEMBER_DISAPPLY = "member/cancel";
    private static final String METHOD_APPLY_RECORD = "user/member_apply_record";
    private static final String METHOD_EXIT_FAMILY = "user/exit_family";
    private static final String METHOD_APPLY_CREATE_FAMILY = "user/family_apply";
    private static final String METHOD_LIVE_FAMILY_RANK = "public/room_familys";
    private static final String METHOD_FAMILY_TOPIC_LIST = "topic/list";
    private static final String METHOD_FAMILY_TOPIC_INFO = "topic/info";
    private static final String METHOD_FAMILY_TOPIC_COMMENTS = "topic/comments";
    private static final String METHOD_FAMILY_ADD_TOPIC = "topicmg/add";
    private static final String METHOD_FAMILY_ADD_COMMENT = "comments/add";
    private static final String METHOD_FAMILY_COMMENT_REPLY = "comments/reply";
    private static final String METHOD_FAMILY_EDIT_NOTICE = "family/edit_notices";
    private static final String METHOD_FAMILY_EDIT_PIC = "family/edit_pic";
    private static final String METHOD_FAMILY_CLOSE = "family/close";
    private static final String METHOD_FAMILY_CHOOSE_LEADER = "member/choose_leader";
    private static final String METHOD_FAMILY_KICK_OUT = "family/kick";
    private static final String METHOD_FAMILY_APPLY_LIST = "member/list";
    private static final String METHOD_FAMILY_APPLY_HANDLE = "member/handle";
    private static final String METHOD_FAMILY_MEMBER_APPLY_RECORD = "user/member_apply_record";

    private static final String KEY_SIZE = "size";
    private static final String KEY_PAGE = "page";
    private static final String KEY_TYPE = "type";
    private static final String KEY_SORT = "sort";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_AUTH_CODE = "auth_code";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_FAMILY_NOTICE = "family_notice";
    private static final String KEY_FAMILY_PIC = "family_pic";
    private static final String KEY_FAMILY_NAME = "family_name";
    private static final String KEY_BADGE_NAME = "badge_name";
    private static final String KEY_STATUS = "status";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_ID = "_id";

    private static final String DEFAULT_PIC = "http://img.show.dongting.com/7/7/1383227235399.jpg";
    private static final String DEFAULT_NOTICE = "天天家族";

    /**
     * 获取家族列表
     *
     * @param size 一页大小
     * @param page 第几页
     * @param type 排序类型
     * @param sort 升序降序
     * @return Request
     */
    public static Request<FamilyListResult> familyList(int size, int page, int type, int sort) {
        return new GetMethodRequest<FamilyListResult>(FamilyListResult.class, Config.getAPIHost(), METHOD_FAMILY_LIST)
                .addArgument(KEY_SIZE, size)
                .addArgument(KEY_PAGE, page)
                .addArgument(KEY_TYPE, type)
                .addArgument(KEY_SORT, sort);
    }

    /**
     * 获取家族信息
     *
     * @param familyId familyId
     * @param page     page
     * @param size     size
     * @return Request
     */
    public static Request<FamilyInfoResult> familyInfo(long familyId, int page, int size) {
        return new GetMethodRequest<FamilyInfoResult>(FamilyInfoResult.class, Config.getAPIHost(), METHOD_FAMILY_INFO)
                .addUrlArgument(familyId)
                .addArgument(KEY_PAGE, page)
                .addArgument(KEY_SIZE, size);
    }

    /**
     * 获取家族成员
     *
     * @param familyId familyId
     * @param size     一页大小
     * @param page     第几页
     * @return Request
     */
    public static Request<FamilyMemberListResult> familyMemberList(long familyId, int size, int page) {
        return new GetMethodRequest<FamilyMemberListResult>(FamilyMemberListResult.class, Config.getAPIHost(), METHOD_FAMILY_MEMBER)
                .addUrlArgument(familyId)
                .addArgument(KEY_SIZE, size)
                .addArgument(KEY_PAGE, page);
    }

    /**
     * 获取家族成员
     *
     * @param familyId familyId
     * @param size     一页大小
     * @param page     第几页
     * @return Request
     */
    public static Request<FamilyStarListResult> familyStarList(long familyId, int size, int page) {
        return new GetMethodRequest<FamilyStarListResult>(FamilyStarListResult.class, Config.getAPIHost(), METHOD_FAMILY_STAR)
                .addUrlArgument(familyId)
                .addArgument(KEY_SIZE, size)
                .addArgument(KEY_PAGE, page);
    }

    /**
     * 申请加入家族
     *
     * @param accessToken accessToken
     * @param familyId    familyId
     * @return Request
     */
    public static Request<BaseResult> applyJoin(String accessToken, long familyId) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_MEMBER_APPLY)
                .addUrlArgument(accessToken)
                .addUrlArgument(familyId)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 退出家族
     *
     * @param accessToken accessToken
     * @return Request
     */
    public static Request<BaseResult> exitFamily(String accessToken) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_EXIT_FAMILY)
                .addUrlArgument(accessToken)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 家族拥护榜
     *
     * @param roomId roomId
     * @param size   size
     * @return Request
     */
    public static Request<LiveFamilyRankResult> liveFamilyRank(long roomId, int size) {
        return new GetMethodRequest<LiveFamilyRankResult>(LiveFamilyRankResult.class, Config.getAPIHost(), METHOD_LIVE_FAMILY_RANK)
                .addUrlArgument(roomId)
                .addArgument(KEY_SIZE, size);
    }

    /**
     * familyTopicList
     *
     * @param familyId familyId
     * @param size     size
     * @param page     page
     * @return Request
     */
    public static Request<FamilyTopicListResult> familyTopicList(long familyId, int size, int page) {
        return new GetMethodRequest<FamilyTopicListResult>(FamilyTopicListResult.class, Config.getAPIHost(), METHOD_FAMILY_TOPIC_LIST)
                .addUrlArgument(familyId)
                .addArgument(KEY_SIZE, size)
                .addArgument(KEY_PAGE, page);
    }

    /**
     * topicInfo
     *
     * @param topicId topicId
     * @return Request
     */
    public static Request<TopicInfoResult> topicInfo(String topicId) {
        return new GetMethodRequest<TopicInfoResult>(TopicInfoResult.class, Config.getAPIHost(), METHOD_FAMILY_TOPIC_INFO)
                .addUrlArgument(topicId);
    }

    /**
     * topicComment
     *
     * @param topicId topicId
     * @param size    size
     * @param page    page
     * @return Request
     */
    public static Request<TopicCommentResult> topicComment(String topicId, int size, int page) {
        return new GetMethodRequest<TopicCommentResult>(TopicCommentResult.class, Config.getAPIHost(), METHOD_FAMILY_TOPIC_COMMENTS)
                .addUrlArgument(topicId)
                .addArgument(KEY_SIZE, size)
                .addArgument(KEY_PAGE, page);
    }

    /**
     * addTopic
     *
     * @param accessToken accessToken
     * @param familyId    familyId
     * @param title       title
     * @param content     content
     * @param authCode    authCode
     * @return Request
     */
    public static Request<BaseResult> addTopic(String accessToken, long familyId, String title, String content, String authCode) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_FAMILY_ADD_TOPIC)
                .addUrlArgument(accessToken)
                .addUrlArgument(familyId)
                .addArgument(KEY_TITLE, title)
                .addArgument(KEY_CONTENT, content)
                .addArgument(KEY_AUTH_CODE, authCode)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * addTopic
     *
     * @param accessToken accessToken
     * @param topicId     topicId
     * @param content     content
     * @param authCode    authCode
     * @return Request
     */
    public static Request<BaseResult> addComment(String accessToken, String topicId, String content, String authCode) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_FAMILY_ADD_COMMENT)
                .addUrlArgument(accessToken)
                .addUrlArgument(topicId)
                .addArgument(KEY_CONTENT, content)
                .addArgument(KEY_AUTH_CODE, authCode)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * addTopic
     *
     * @param accessToken accessToken
     * @param commentId   commentId
     * @param content     content
     * @param authCode    authCode
     * @return Request
     */
    public static Request<BaseResult> addCommentReply(String accessToken, String commentId, String content, String authCode) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_FAMILY_COMMENT_REPLY)
                .addUrlArgument(accessToken)
                .addUrlArgument(commentId)
                .addArgument(KEY_CONTENT, content)
                .addArgument(KEY_AUTH_CODE, authCode)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 修改家族公告
     * @param accessToken accessToken
     * @param notice   notice
     * @return Request
     */
    public static Request<BaseResult> editFamilyNotice(String accessToken, String notice) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_FAMILY_EDIT_NOTICE)
                .addUrlArgument(accessToken)
                .addArgument(KEY_FAMILY_NOTICE, notice)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 修改家族徽章图片
     * @param accessToken accessToken
     * @param picUrl   picUrl
     * @return Request
     */
    public static Request<BaseResult> editFamilyPic(String accessToken, String picUrl) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_FAMILY_EDIT_PIC)
                .addUrlArgument(accessToken)
                .addArgument(KEY_FAMILY_PIC, picUrl)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 解散家族
     * @param accessToken accessToken
     * @return Request
     */
    public static Request<BaseResult> closeFamily(String accessToken) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_FAMILY_CLOSE)
                .addUrlArgument(accessToken)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 申请创建家族API
     * @param accessToken accessToken
     * @param familyName  familyName
     * @param familyBadge familyBadge
     * @return Request
     */
    public static Request<BaseResult> applyCreateFamily(String accessToken, String familyName, String familyBadge) {
        return new PostMethodRequestV2<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_APPLY_CREATE_FAMILY + "/" + accessToken)
                .addArgument(KEY_FAMILY_NAME, familyName)
                .addArgument(KEY_BADGE_NAME, familyBadge)
                .addArgument(KEY_FAMILY_PIC, DEFAULT_PIC)
                .addArgument(KEY_FAMILY_NOTICE, DEFAULT_NOTICE);
    }

    /**
     *  getUserFamilyApplyResult
     * @param accessToken accessToken
     * @return    getUserFamilyApplyResult
     */
    public static Request<FamilyApplyRecordResult> getUserFamilyApplyResult(String accessToken) {
        return new GetMethodRequest<FamilyApplyRecordResult>(FamilyApplyRecordResult.class, Config.getAPIHost(), METHOD_APPLY_RECORD)
                .addUrlArgument(accessToken);
    }

    /**
     * requestDisapplyFamily
     * @param accessToken accessToken
     * @param applyId applyId
     * @return requestDisapplyFamily
     */
    public static Request<BaseResult> requestCancleApplyFamily(String accessToken, String applyId) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_MEMBER_DISAPPLY)
                .addUrlArgument(accessToken)
                .addUrlArgument(applyId);
    }

    /**
     * requestChooseMemberType
     * @param accessToken accessToken
     * @param userId userId
     * @param leaderType leaderType
     * @return baserequest
     */
    public static Request<BaseResult> requestChooseMemberType(String accessToken, long userId, int leaderType) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_FAMILY_CHOOSE_LEADER)
                .addUrlArgument(accessToken)
                .addUrlArgument(userId)
                .addUrlArgument(leaderType);
    }

    /**
     * requestKickOutMember
     * @param accessToken accessToken
     * @param userId userId
     * @return requestKickOutMember
     */
    public static Request<BaseResult> requestKickOutMember(String accessToken, long userId) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_FAMILY_KICK_OUT)
                .addUrlArgument(accessToken)
                .addUrlArgument(userId);
    }

    /**
     * requestFamilyApplyList
     * @param accessToken accessToken
     * @param page page
     * @param size size
     * @return Request
     */
    public static Request<FamilyApplyListResult> requestFamilyApplyList(String accessToken, int page, int size) {
        return new GetMethodRequest<FamilyApplyListResult>(FamilyApplyListResult.class, Config.getAPIHost(), METHOD_FAMILY_APPLY_LIST)
                .addUrlArgument(accessToken)
                .addArgument(KEY_SIZE, size)
                .addArgument(KEY_PAGE, page);
    }

    /**
     * requestHandleApplyMember
     * @param accessToken accessToken
     * @param applyId applyId
     * @param status status
     * @return Request
     */
    public static Request<BaseResult> requestHandleApplyMember(String accessToken, String applyId, int status) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_FAMILY_APPLY_HANDLE)
                .addUrlArgument(accessToken)
                .addArgument(KEY_ID, applyId)
                .addArgument(KEY_STATUS, status);
    }

    /**
     * 请求家族申请记录
     * @param accessToken accessToken
     * @return request
     */
    public static Request<FamilyMemberApplyRecord> requestMemberApplyRecord(String accessToken) {
        return new GetMethodRequest<FamilyMemberApplyRecord>(FamilyMemberApplyRecord.class, Config.getAPIHost(),
                METHOD_FAMILY_MEMBER_APPLY_RECORD)
                .addUrlArgument(accessToken);
    }
}
