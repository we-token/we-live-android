package show.we.lib.control;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by CG on 14-4-1.
 * 一个组对象对应一个Activity对象
 * @author ll
 * @version 3.7.0
 */
public final class ObserverGroup {

    private ObserverGroupType mGroupType;

    private ObserverGroup(ObserverGroupType type) {
        mGroupType = type;
    }

    private ObserverGroupType getType() {
        return mGroupType;
    }

    // 检查使用
    /** 设置某一组类型的对象的最大数量 */
    private static Map<ObserverGroupType, Integer> mGroupDefMaxCount = new HashMap<ObserverGroupType, Integer>();
    /** 设置某一组类型的对象的set */
    private static Map<ObserverGroupType, Set<ObserverGroup>> mGroupMap = new HashMap<ObserverGroupType, Set<ObserverGroup>>();

    static void setGroupDefMaxCount(ObserverGroupType type, int count) {
        mGroupDefMaxCount.put(type, count);
    }

    private static void addGroup(ObserverGroup group) {
        int maxCount = 2;
        if (mGroupDefMaxCount.get(group.getType()) != null) {
            maxCount = mGroupDefMaxCount.get(group.getType());
        }

        Set<ObserverGroup> observerGroups = mGroupMap.get(group.getType());
        if (observerGroups == null) {
            observerGroups = new HashSet<ObserverGroup>(2);
            mGroupMap.put(group.getType(), observerGroups);
        }
        if (observerGroups.size() < maxCount) {
            observerGroups.add(group);
        } else {
            try {
				throw new IllegalArgumentException("repeat register, group = " + group.getType());
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    }

    static void removeGroup(ObserverGroup group) {
        Set<ObserverGroup> observerGroups = mGroupMap.get(group.getType());
        if (observerGroups != null) {
            observerGroups.remove(group);
        }
    }

    private static ObserverGroup mMainGroup;
    private static ObserverGroup mLiveGroup;
    private static ObserverGroup mSongOrderGroup;
    private static ObserverGroup mFamilyDetailsGroup;

    /**
     * createMainGroup
     * @return ObserverGroup
     */
    public static ObserverGroup createMainGroup() {
        mMainGroup = new ObserverGroup(ObserverGroupType.MAIN_ACTIVITY);
        addGroup(mMainGroup);
        return mMainGroup;
    }

    /**
     * getMainGroup
     * @return ObserverGroup
     */
    public static ObserverGroup getMainGroup() {
        return mMainGroup;
    }

    /**
     * createLiveGroup
     * @return ObserverGroup
     */
    public static ObserverGroup createLiveGroup() {
        mLiveGroup = new ObserverGroup(ObserverGroupType.LIVE_ACTIVITY);
        addGroup(mLiveGroup);
        return mLiveGroup;
    }

    /**
     * getLiveGroup
     * @return ObserverGroup
     */
    public static ObserverGroup getLiveGroup() {
        return mLiveGroup;
    }

    /**
     * createSongOrderGroup
     * @return ObserverGroup
     */
    public static ObserverGroup createSongOrderGroup() {
        mSongOrderGroup = new ObserverGroup(ObserverGroupType.SONG_ORDER_ACTIVITY);
        addGroup(mSongOrderGroup);
        return mSongOrderGroup;
    }

    /**
     * getSongOrderGroup
     * @return ObserverGroup
     */
    public static ObserverGroup getSongOrderGroup() {
        return mSongOrderGroup;
    }

    /**
     * creatFamilyDetailsGroup
     * @return ObserverGroup
     */
    public static ObserverGroup creatFamilyDetailsGroup() {
        mFamilyDetailsGroup = new ObserverGroup(ObserverGroupType.FAMILY_DETAILS_ACTIVITY);
        addGroup(mFamilyDetailsGroup);
        return mFamilyDetailsGroup;
    }

    /**
     * getFamilyDetailsGroup
     * @return ObserverGroup
     */
    public static ObserverGroup getFamilyDetailsGroup() {
        return mFamilyDetailsGroup;
    }
}
