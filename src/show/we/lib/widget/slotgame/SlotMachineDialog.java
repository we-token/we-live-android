package show.we.lib.widget.slotgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.*;

import show.we.sdk.cache.ImageCache;
import show.we.sdk.request.BaseResult;
import show.we.sdk.util.ConstantUtils;
import show.we.sdk.util.StringUtils;
import show.we.lib.R;
import show.we.lib.cloudapi.BaseRequestCallback;
import show.we.lib.cloudapi.GiftAPI;
import show.we.lib.cloudapi.ShopAPI;
import show.we.lib.cloudapi.UserSystemAPI;
import show.we.lib.config.CacheObjectKey;
import show.we.lib.config.Enums;
import show.we.lib.control.DataChangeNotification;
import show.we.lib.control.IssueKey;
import show.we.lib.control.OnDataChangeObserver;
import show.we.lib.model.Finance;
import show.we.lib.model.GiftListResult;
import show.we.lib.model.MountListResult;
import show.we.lib.model.SlotWinResult;
import show.we.lib.ui.LiveCommonData;
import show.we.lib.utils.*;
import show.we.lib.widget.animation.AnimationFactory;
import show.we.lib.widget.dialog.BaseDialog;
import show.we.lib.widget.wheelview.OnWheelChangedListener;
import show.we.lib.widget.wheelview.OnWheelScrollListener;
import show.we.lib.widget.wheelview.WheelView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * 摇奖游戏对话框
 * @author ll
 * @version 1.0.0
 */
public class SlotMachineDialog extends BaseDialog implements View.OnClickListener
        , SlotRockerView.OnRockerPullListener, OnDataChangeObserver
        , SlotRockerView.DataProvider, SensorEventListener {

    private static final int VISIBLE_ITEM_COUNT = 1;
    private static final int SCROLL_DISTANCE_INIT = 90;
    private static final long STOP_LEFT_SCROLL_DELAY = 3000;
    private static final long STOP_CENTER_SCROLL_DELAY = STOP_LEFT_SCROLL_DELAY + ConstantUtils.MILLIS_PER_SECOND;
    private static final long STOP_RIGHT_SCROLL_DELAY = STOP_CENTER_SCROLL_DELAY + ConstantUtils.MILLIS_PER_SECOND;
    /**  播放中奖声音延迟  **/
    public static final long PLAY_WIN_SOUND_DELAY = ConstantUtils.MILLIS_PER_SECOND + STOP_RIGHT_SCROLL_DELAY;

    private static final int SPEED_SHRESHOLD = 4500;
    private static final int INTERVAL_TIME_UNIT = 10000;
    private static final int UPTATE_INTERVAL_TIME = 80;
    private static final int MIN_SHAKE_INTERVAL = 1500;

    private static final int MSG_PLAY_WIN = 1;

    private boolean mIsSoundOn = true;
    private float mLastX;
    private float mLastY;
    private float mLastZ;
    private long mLastUpdateTime;
    private long mLastShakeTime;

    private WheelView mWheelViewLeft;
    private WheelView mWheelViewCenter;
    private WheelView mWheelViewRight;

    private TextView mWoodHandlerTextView;
    private TextView mIronHandlerTextView;
    private TextView mGoldHandlerTextView;

    private ImageView mFlashImageView1;
    private ImageView mFlashImageView2;
    private ImageView mFlashImageView3;

    private TextView mResultTextView;

    private SlotRockerView mSlotRockerView;
    private View mSwitcherLayout;

    private Enums.HammerType mSelectedHammerType = Enums.HammerType.WOOD;

    private String mRewardName;

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private SoundPool mSoundPool;
    private HashMap<Integer, Integer> mSoundPoolMap = new HashMap<Integer, Integer>();

    /**
     * 抽奖游戏dialog
     * @param context mContext
     */
    public SlotMachineDialog(Context context) {
        super(context, R.layout.slot_machine);
        mSlotRockerView = (SlotRockerView) findViewById(R.id.slot_rocker);
        mSlotRockerView.setOnRockerPullListener(this);
        mSlotRockerView.setDataProvider(this);
        mWoodHandlerTextView = (TextView) findViewById(R.id.wood_handler);
        mIronHandlerTextView = (TextView) findViewById(R.id.iron_handler);
        mGoldHandlerTextView = (TextView) findViewById(R.id.gold_handler);
        mFlashImageView1 = (ImageView) findViewById(R.id.slot_flash_1);
        mFlashImageView2 = (ImageView) findViewById(R.id.slot_flash_2);
        mFlashImageView3 = (ImageView) findViewById(R.id.slot_flash_3);
        mResultTextView = (TextView) findViewById(R.id.slot_result);
        mSwitcherLayout = findViewById(R.id.switch_handler_layout);
        ((CheckBox) findViewById(R.id.ckb_slot_sound)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mIsSoundOn = isChecked;
            }
        });
        ((AnimationDrawable) mFlashImageView1.getBackground()).start();
        ((AnimationDrawable) mFlashImageView2.getBackground()).start();
        ((AnimationDrawable) mFlashImageView3.getBackground()).start();
        mWoodHandlerTextView.setSelected(true);
        mWoodHandlerTextView.setOnClickListener(this);
        mIronHandlerTextView.setOnClickListener(this);
        mGoldHandlerTextView.setOnClickListener(this);
        findViewById(R.id.btn_switch_handler).setOnClickListener(this);
        findViewById(R.id.btn_close_slot).setOnClickListener(this);
        loadRewardPic(Enums.HammerType.WOOD);
        loadRewardPic(Enums.HammerType.IRON);
        loadRewardPic(Enums.HammerType.GOLD);
        initWheels();

        loadSound();
        openSensor();

        DataChangeNotification.getInstance().addObserver(IssueKey.SLOT_WIN_ROOM, this);
    }

    private void loadSound() {
        mSoundPool = new SoundPool(2, AudioManager.STREAM_SYSTEM, 0);
        new Thread() {
            public void run() {
                try {
                    mSoundPoolMap.put(0, mSoundPool.load(getContext().getAssets().openFd("sound/shake_sound_male.mp3"), 1));
                    mSoundPoolMap.put(1, mSoundPool.load(getContext().getAssets().openFd("sound/shake_match.mp3"), 1));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } .start();
    }

    private void releaseSound() {
        mSoundPoolMap.clear();
        mSoundPool.release();
    }

    private void openSensor() {
        mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager != null) {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        if (mSensor != null) {
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    private void closeSensor() {
        mSensorManager.unregisterListener(this);
        mLastX = 0;
        mLastY = 0;
        mLastZ = 0;
        mLastUpdateTime = 0;
    }
    private void loadRewardPic(final Enums.HammerType hammerType) {
        List<SlotRewardItem> rewardList = hammerType.getCachedRewardList();
        boolean hasEmptyPicUrl = false;
        if (rewardList != null) {
            for (SlotRewardItem item : rewardList) {
                if (StringUtils.isEmpty(item.getRewardPicUrl())) {
                    hasEmptyPicUrl = true;
                }
            }
        }
        if (hasEmptyPicUrl || rewardList == null) {
            loadGiftPic(hammerType);
        }
    }

    private void loadGiftPic(final Enums.HammerType hammerType) {
        GiftListResult giftListResult = (GiftListResult) CacheUtils.getObjectCache().get(CacheObjectKey.GIFT_LIST_KEY);
        if (giftListResult == null) {
            GiftAPI.giftList().execute(new BaseRequestCallback<GiftListResult>() {
                @Override
                public void onRequestSucceed(GiftListResult dataResult) {
                    adjustGiftPic(dataResult, hammerType.getNoPicRewardList(), hammerType);
                }
                @Override
                public void onRequestFailed(GiftListResult dataResult) {
                }
            });
        } else {
            adjustGiftPic(giftListResult, hammerType.getNoPicRewardList(), hammerType);
        }
    }

    private void adjustGiftPic(GiftListResult giftListResult, final List<SlotRewardItem> rewardList, final Enums.HammerType hammerType) {
        for (SlotRewardItem item : rewardList) {
            if (StringUtils.isEmpty(item.getRewardPicUrl())) {
                List<GiftListResult.Gift> giftList = giftListResult.getData().getGiftList();
                for (GiftListResult.Gift gift : giftList) {
                    if (gift.getName().equals(item.getGiftOrCarName())) {
                        String url = gift.getPicUrl();
                        item.setRewardPicUrl(url);
                        if (!StringUtils.isEmpty(url)) {
                            Bitmap picture = CacheUtils.getImageCache().getBitmapFromMemCache(url, null, Integer.MAX_VALUE, Integer.MAX_VALUE);
                            if (picture == null) {
                                CacheUtils.getImageCache().loadImageAsync(url, Integer.MAX_VALUE, Integer.MAX_VALUE, new ImageCache.Callback() {
                                    @Override
                                    public void imageLoaded(String s, int i, int i2, Bitmap bitmap) {
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }

        loadMountPic(rewardList, hammerType);
    }

    private void loadMountPic(final List<SlotRewardItem> rewardList, final Enums.HammerType hammerType) {
        MountListResult mountListResult = (MountListResult) CacheUtils.getObjectCache().get(CacheObjectKey.MOUNT_MALL_KEY);
        if (mountListResult == null) {
            ShopAPI.mountMall().execute(new BaseRequestCallback<MountListResult>() {
                @Override
                public void onRequestSucceed(MountListResult dataResult) {
                    adjustMountPic(dataResult, rewardList, hammerType);
                }
                @Override
                public void onRequestFailed(MountListResult dataResult) {
                }
            });
        } else {
            adjustMountPic(mountListResult, rewardList, hammerType);
        }
    }

    private void adjustMountPic(MountListResult mountListResult, List<SlotRewardItem> rewardList, Enums.HammerType hammerType) {
        for (SlotRewardItem item : rewardList) {
            if (StringUtils.isEmpty(item.getRewardPicUrl())) {
                List<MountListResult.MountItem> mountList = mountListResult.getDataList();
                for (MountListResult.MountItem mount : mountList) {
                    if (mount.getName().equals(item.getGiftOrCarName())) {
                        String url = mount.getPicUrl();
                        item.setRewardPicUrl(url);
                        if (!StringUtils.isEmpty(url)) {
                            Bitmap picture = CacheUtils.getImageCache().getBitmapFromMemCache(url, null, Integer.MAX_VALUE, Integer.MAX_VALUE);
                            if (picture == null) {
                                CacheUtils.getImageCache().loadImageAsync(url, Integer.MAX_VALUE, Integer.MAX_VALUE, new ImageCache.Callback() {
                                    @Override
                                    public void imageLoaded(String s, int i, int i2, Bitmap bitmap) {
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }
        cacheRewardList(hammerType, rewardList);
    }

    private void cacheRewardList(Enums.HammerType hammerType, List<SlotRewardItem> rewardList) {
        if (hammerType == Enums.HammerType.WOOD) {
            CacheUtils.getObjectCache().add(CacheObjectKey.WOOD_REWARD_LIST, rewardList);
        } else if (hammerType == Enums.HammerType.IRON) {
            CacheUtils.getObjectCache().add(CacheObjectKey.IRON_REWARD_LIST, rewardList);
        } else if (hammerType == Enums.HammerType.GOLD) {
            CacheUtils.getObjectCache().add(CacheObjectKey.GOLD_REWARD_LIST, rewardList);
        }
    }

    private OnWheelScrollListener mScrolledListener = new OnWheelScrollListener() {
        public void onScrollingStarted(WheelView wheel) {
            wheel.enableTouchScroll(false);
        }
        public void onScrollingFinished(WheelView wheel) {
            if (!mWheelViewLeft.isScrolling() && !mWheelViewCenter.isScrolling() && !mWheelViewRight.isScrolling()) {
                checkWin();
                ((AnimationDrawable) mFlashImageView1.getBackground()).start();
                ((AnimationDrawable) mFlashImageView2.getBackground()).start();
                ((AnimationDrawable) mFlashImageView3.getBackground()).start();
            }
            wheel.enableTouchScroll(true);
        }
    };

    private OnWheelChangedListener mChangedListener = new OnWheelChangedListener() {
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (!mWheelViewLeft.isScrolling() && !mWheelViewCenter.isScrolling() && !mWheelViewRight.isScrolling()) {
                checkWin();
            }
        }
    };

    private void smoothStopScrolling(final WheelView wheel, final int destItem, final long delay) {
        mMessageHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isShowing()) {
                    int currentItem = wheel.getCurrentItem();
                    int scrollItem = destItem - currentItem + wheel.getViewAdapter().getItemsCount();
                    wheel.scroll(scrollItem, (int) delay);
                }
            }
        }, delay);
    }

    private void initWheel(WheelView wheelView) {
        SlotMachineAdapter adapter = new SlotMachineAdapter(getContext());
        adapter.updateRewardType(mSelectedHammerType);
        wheelView.setViewAdapter(adapter);
        int count = wheelView.getViewAdapter().getItemsCount();
        wheelView.setCurrentItem(count > 0 ? new Random().nextInt(count) : 0);
        wheelView.addChangingListener(mChangedListener);
        wheelView.addScrollingListener(mScrolledListener);
        wheelView.setCyclic(true);
        wheelView.enableTouchScroll(false);
        wheelView.setIsDrawShadow(false);
        wheelView.setVisibleItems(VISIBLE_ITEM_COUNT);
    }

    /**
     * Initializes wheels
     */
    private void initWheels() {
        mWheelViewLeft = (WheelView) findViewById(R.id.slot_1);
        initWheel(mWheelViewLeft);
        mWheelViewCenter = (WheelView) findViewById(R.id.slot_2);
        initWheel(mWheelViewCenter);
        mWheelViewRight = (WheelView) findViewById(R.id.slot_3);
        initWheel(mWheelViewRight);
    }

    private void checkWin() {
        if (isWin()) {
            if (!StringUtils.isEmpty(mRewardName)) {
                mResultTextView.setText("恭喜您摇得" + mRewardName);
            }
        }
    }

    /**
     * @return true
     */
    private boolean isWin() {
        int left = mWheelViewLeft.getCurrentItem();
        int center = mWheelViewCenter.getCurrentItem();
        int right = mWheelViewRight.getCurrentItem();
        return left == center && center == right;
    }

    private void updateSlotAdapter() {
        ((SlotMachineAdapter) mWheelViewLeft.getViewAdapter()).updateRewardType(mSelectedHammerType);
        ((SlotMachineAdapter) mWheelViewCenter.getViewAdapter()).updateRewardType(mSelectedHammerType);
        ((SlotMachineAdapter) mWheelViewRight.getViewAdapter()).updateRewardType(mSelectedHammerType);
    }

    private void toggleSwitcher() {
        boolean isVisible = mSwitcherLayout.getVisibility() == View.VISIBLE;
        mSwitcherLayout.setVisibility(isVisible ? View.INVISIBLE : View.VISIBLE);
        int height = mSwitcherLayout.getHeight();
        final long duration = 300;
        mSwitcherLayout.startAnimation(AnimationFactory.buildTranslateAnimation(0, 0, isVisible ? 0 : height, isVisible ? height : 0, duration));
    }

    private void hideSwitcher() {
        boolean isVisible = mSwitcherLayout.getVisibility() == View.VISIBLE;
        if (isVisible) {
            toggleSwitcher();
        }
    }

    @Override
    public void onClick(View v) {
        if (!mWheelViewRight.isScrolling()) {
            int i = v.getId();
            if (i == R.id.btn_switch_handler) {
                toggleSwitcher();
            } else if (i == R.id.wood_handler) {
                mWoodHandlerTextView.setSelected(true);
                mIronHandlerTextView.setSelected(false);
                mGoldHandlerTextView.setSelected(false);
                mSelectedHammerType = Enums.HammerType.WOOD;
                updateSlotAdapter();
                mSlotRockerView.selectWoodRocker();
                hideSwitcher();
            } else if (i == R.id.iron_handler) {
                mWoodHandlerTextView.setSelected(false);
                mIronHandlerTextView.setSelected(true);
                mGoldHandlerTextView.setSelected(false);
                mSelectedHammerType = Enums.HammerType.IRON;
                updateSlotAdapter();
                mSlotRockerView.selectIronRocker();
                hideSwitcher();
            } else if (i == R.id.gold_handler) {
                mWoodHandlerTextView.setSelected(false);
                mIronHandlerTextView.setSelected(false);
                mGoldHandlerTextView.setSelected(true);
                mSelectedHammerType = Enums.HammerType.GOLD;
                updateSlotAdapter();
                mSlotRockerView.selectGoldRocker();
                hideSwitcher();
            } else if (i == R.id.btn_close_slot) {
                dismiss();
            }
        }
    }

    @Override
    public void onRockerPulled() {
        if (mIsSoundOn) {
            mSoundPool.play(mSoundPoolMap.get(0), 1, 1, 0, 0, (float) 1.0);
        }
        if (isEnoughMoney()) {
            ((AnimationDrawable) mFlashImageView1.getBackground()).stop();
            ((AnimationDrawable) mFlashImageView2.getBackground()).stop();
            ((AnimationDrawable) mFlashImageView3.getBackground()).stop();
            requestHitEgg(LiveCommonData.getRoomId(), mSelectedHammerType);
            mWheelViewLeft.scroll(SCROLL_DISTANCE_INIT, (int) ConstantUtils.MILLS_PER_MIN);
            mWheelViewCenter.scroll(SCROLL_DISTANCE_INIT, (int) ConstantUtils.MILLS_PER_MIN);
            mWheelViewRight.scroll(SCROLL_DISTANCE_INIT, (int) ConstantUtils.MILLS_PER_MIN);
            mSwitcherLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onDataChanged(IssueKey issue, Object o) {
        if (IssueKey.SLOT_WIN_ROOM.equals(issue)) {
            if (o instanceof SlotWinResult.Data) {
                SlotWinResult.Data data = (SlotWinResult.Data) o;
                if (data.getUser().getId() == UserInfoUtils.getUserInfo().getData().getId()) {
                    RequestUtils.requestUpdateUserInfo(getContext(), true, false, true, false, false, false);
                    String rewardKey = data.getRewardKey();
                    List<SlotRewardItem> rewardItemList = mSelectedHammerType.getNoPicRewardList();
                    int index = 0;
                    for (int i = 0; i < rewardItemList.size(); i++) {
                        SlotRewardItem item = rewardItemList.get(i);
                        if (rewardKey.equals(item.getRewardKey())) {
                            index = i;
                            mRewardName = item.getRewardName();
                        }
                    }
                    smoothStopScrolling(mWheelViewLeft, index, STOP_LEFT_SCROLL_DELAY);
                    smoothStopScrolling(mWheelViewCenter, index, STOP_CENTER_SCROLL_DELAY);
                    smoothStopScrolling(mWheelViewRight, index, STOP_RIGHT_SCROLL_DELAY);
                    mMessageHandler.sendEmptyMessageDelayed(MSG_PLAY_WIN, PLAY_WIN_SOUND_DELAY);
                }
            }
        }
    }

    private boolean isEnoughMoney() {
        Finance finance = UserInfoUtils.getUserInfo().getData().getFinance();
        long coins = finance != null ? finance.getCoinCount() : 0;
        boolean enough = coins >= mSelectedHammerType.getCost();
        if (!enough) {
            PromptUtils.showToast(R.string.money_not_enough, Toast.LENGTH_SHORT);
            Utils.entryRechargeActivity(getContext());
        }
        return enough;
    }

    private void requestHitEgg(final long roomId, Enums.HammerType hammerType) {
        if (CacheUtils.getObjectCache().contain(CacheObjectKey.ACCESS_TOKEN)) {
            UserSystemAPI.hitEgg(UserInfoUtils.getAccessToken(), roomId, hammerType).execute(new BaseRequestCallback<BaseResult>() {
                @Override
                public void onRequestSucceed(BaseResult baseResult) {

                }

                @Override
                public void onRequestFailed(BaseResult baseResult) {
                    mResultTextView.setText(R.string.slot_request_fail);
                    smoothStopScrolling(mWheelViewLeft, 0, STOP_LEFT_SCROLL_DELAY);
                    smoothStopScrolling(mWheelViewCenter, 0, STOP_LEFT_SCROLL_DELAY);
                    smoothStopScrolling(mWheelViewRight, 0, STOP_LEFT_SCROLL_DELAY);
                }
            });
        } else {
            PromptUtils.showToast(R.string.default_unlogin_hint, Toast.LENGTH_SHORT);
        }
    }

    @Override
    public boolean isScrolling() {
        return mWheelViewLeft.isScrolling() || mWheelViewCenter.isScrolling() || mWheelViewRight.isScrolling();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        long currentUpdateTime = System.currentTimeMillis();
        long timeInterval = currentUpdateTime - mLastUpdateTime;
        if (timeInterval < UPTATE_INTERVAL_TIME) {
            return;
        }
        mLastUpdateTime = currentUpdateTime;

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        boolean isShake = timeInterval > 0 ? Math.sqrt(Math.pow(x - mLastX, 2) + Math.pow(y - mLastY, 2) + Math.pow(z - mLastZ, 2))
                / timeInterval * INTERVAL_TIME_UNIT >= SPEED_SHRESHOLD : false;

        mLastX = x;
        mLastY = y;
        mLastZ = z;

        if (isShake) {
            processShakeEvent();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void processShakeEvent() {
        if (System.currentTimeMillis() - mLastShakeTime < MIN_SHAKE_INTERVAL) {
            return;
        }
        mLastShakeTime = System.currentTimeMillis();
        if (isScrolling()) {
            return;
        }
        mSlotRockerView.start();
        mSwitcherLayout.setVisibility(View.INVISIBLE);
    }

    private Handler mMessageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            removeMessages(msg.what);
            if (MSG_PLAY_WIN == msg.what) {
                if (isShowing() && mIsSoundOn) {
                    mSoundPool.play(mSoundPoolMap.get(1), 1, 1, 0, 0, (float) 1.0);
                }
            }
        }
    };

    @Override
    public void dismiss() {
        releaseSound();
        closeSensor();
        DataChangeNotification.getInstance().removeObserver(this);
        super.dismiss();
    }
}
