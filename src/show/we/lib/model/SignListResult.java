package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.model.UserInfoResult.Gift;
import show.we.sdk.request.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * 用户当月签到情况
 *
 * @author ll
 * @version 2.5.0
 * @param <One>
 */
public class SignListResult extends BaseResult{
	@SerializedName("data")
    private  Data data;

    public  Data getData() {
    	//System.out.println("111111111111111111111111111111");
        return data;
    }
    

    public static class Data implements Serializable {
    	@SerializedName("one")
    	//private List<One> mOne;
        private One mOne;

        public One getOne() {
        	//System.out.println("2222222222222222222222222");
        	
            return mOne;
        }
    	
        @SerializedName("two")
        //private List<Two> mTwo;
        private Two mTwo;

        public Two getTwo() {
            return mTwo;
        }
        
        @SerializedName("three")
        //private List<Three> mThree;
        private Three mThree;

        public Three getThree() {
            return mThree;
        }
        
        @SerializedName("four")
        //private List<Four> mFour;
        private Four mFour;

        public Four getFour() {
            return mFour;
        }
        
        @SerializedName("five")
        //private List<Five> mFive;
        private Five mFive;

        public Five getFive() {
            return mFive;
        }
        
        @SerializedName("six")
        //private List<Six> mSix;
        private Six mSix;

        public Six getSix() {
            return mSix;
        }
        
        @SerializedName("seven")
        //private List<Seven> mSeven;
        private Seven mSeven;

        public Seven getSeven() {
            return mSeven;
        }
        
        @SerializedName("signin")
        private int sign;

        public int getSign() {
            return sign;
        }
    	
    }
    
    

    public static class One implements Serializable {
    	
    	@SerializedName("_id")
        private String id;

        public String getId(){
            return id;
        }
        
        @SerializedName("task_name")
        private String taskname;

        public String getTaskname() {
            return taskname;
        }
        
        @SerializedName("gift_num")
        private String giftnum;

        public String getGiftnum(){
            return giftnum;
        }
        
        @SerializedName("task_pic_light_url")
        private String lpicture;

        public String getLpicture() {
            return lpicture;
        }
        @SerializedName("task_pic_gray_url")
        private String gpicture;

        public String getGpicture() {
            return gpicture;
        }
        
        @SerializedName("award_name")
        private String awardname;

        public String getAwardname() {
            return awardname;
        }
        
        @SerializedName("task_status")
        private int taskstatus;

        public int getTaskstatus() {
            return taskstatus;
        }
        
       
    }
    
    

    public static class Two implements Serializable {
    	
    	@SerializedName("_id")
        private String id;

        public String getId(){
            return id;
        }
    	
    	@SerializedName("task_name")
        private String taskname;

        public String getTaskname() {
            return taskname;
        }
        
        @SerializedName("award_name")
        private String awardname;

        public String getAwardname() {
            return awardname;
        }
        
        @SerializedName("task_status")
        private int taskstatus;

        public int getTaskstatus() {
            return taskstatus;
        }
        
        @SerializedName("gift_num")
        private String giftnum;

        public String getGiftnum(){
            return giftnum;
        }
        
        @SerializedName("task_pic_light_url")
        private String lpicture;

        public String getLpicture() {
            return lpicture;
        }
        @SerializedName("task_pic_gray_url")
        private String gpicture;

        public String getGpicture() {
            return gpicture;
        }
    }
    
   

    public static class Three implements Serializable {
    	
    	@SerializedName("_id")
        private String id;

        public String getId(){
            return id;
        }
    	
    	@SerializedName("task_name")
        private String taskname;

        public String getTaskname() {
            return taskname;
        }
        
        @SerializedName("award_name")
        private String awardname;

        public String getAwardname() {
            return awardname;
        }
        
        @SerializedName("task_status")
        private int taskstatus;

        public int getTaskstatus() {
            return taskstatus;
        }
        
        @SerializedName("gift_num")
        private String giftnum;

        public String getGiftnum(){
            return giftnum;
        }
        
        @SerializedName("task_pic_light_url")
        private String lpicture;

        public String getLpicture() {
            return lpicture;
        }
        @SerializedName("task_pic_gray_url")
        private String gpicture;

        public String getGpicture() {
            return gpicture;
        }
    }
    
    

    public static class Four implements Serializable {
    	
    	@SerializedName("_id")
        private String id;

        public String getId(){
            return id;
        }
    	
    	@SerializedName("task_name")
        private String taskname;

        public String getTaskname() {
            return taskname;
        }
        
        @SerializedName("award_name")
        private String awardname;

        public String getAwardname() {
            return awardname;
        }
        
        @SerializedName("task_status")
        private int taskstatus;

        public int getTaskstatus() {
            return taskstatus;
        }
        
        @SerializedName("gift_num")
        private String giftnum;

        public String getGiftnum(){
            return giftnum;
        }
        
        @SerializedName("task_pic_light_url")
        private String lpicture;

        public String getLpicture() {
            return lpicture;
        }
        @SerializedName("task_pic_gray_url")
        private String gpicture;

        public String getGpicture() {
            return gpicture;
        }
    }
    
    

    public static class Five implements Serializable {
    	
    	@SerializedName("_id")
        private String id;

        public String getId(){
            return id;
        }
    	
    	@SerializedName("task_name")
        private String taskname;

        public String getTaskname() {
            return taskname;
        }
        
        @SerializedName("award_name")
        private String awardname;

        public String getAwardname() {
            return awardname;
        }
        
        @SerializedName("task_status")
        private int taskstatus;

        public int getTaskstatus() {
            return taskstatus;
        }
        
        @SerializedName("gift_num")
        private String giftnum;

        public String getGiftnum(){
            return giftnum;
        }
        
        @SerializedName("task_pic_light_url")
        private String lpicture;

        public String getLpicture() {
            return lpicture;
        }
        @SerializedName("task_pic_gray_url")
        private String gpicture;

        public String getGpicture() {
            return gpicture;
        }
    }
    
    

    public static class Six implements Serializable {
    	
    	@SerializedName("_id")
        private String id;

        public String getId(){
            return id;
        }
    	
    	@SerializedName("task_name")
        private String taskname;

        public String getTaskname() {
            return taskname;
        }
        
        @SerializedName("award_name")
        private String awardname;

        public String getAwardname() {
            return awardname;
        }
        
        @SerializedName("task_status")
        private int taskstatus;

        public int getTaskstatus() {
            return taskstatus;
        }
        
        @SerializedName("gift_num")
        private String giftnum;

        public String getGiftnum(){
            return giftnum;
        }
        
        @SerializedName("task_pic_light_url")
        private String lpicture;

        public String getLpicture() {
            return lpicture;
        }
        @SerializedName("task_pic_gray_url")
        private String gpicture;

        public String getGpicture() {
            return gpicture;
        }
    }
    
    

    public static class Seven implements Serializable {
    	
    	@SerializedName("_id")
        private String id;

        public String getId(){
            return id;
        }
    	
    	@SerializedName("task_name")
        private String taskname;

        public String getTaskname() {
            return taskname;
        }
        
        @SerializedName("award_name")
        private String awardname;

        public String getAwardname() {
            return awardname;
        }
        
        @SerializedName("task_status")
        private int taskstatus;

        public int getTaskstatus() {
            return taskstatus;
        }
        
        @SerializedName("gift_num")
        private String giftnum;

        public String getGiftnum(){
            return giftnum;
        }
        
        @SerializedName("task_pic_light_url")
        private String lpicture;

        public String getLpicture() {
            return lpicture;
        }
        @SerializedName("task_pic_gray_url")
        private String gpicture;

        public String getGpicture() {
            return gpicture;
        }
    }
    
    /*
    @SerializedName("award")
    private List<Award> mAward;

    public List<Award> getAward() {
        return mAward;
    }
    public static class Award implements Serializable {
        @SerializedName("_id")
        private String mId;

        @SerializedName("type")
        private int mType;

        public String getId() {
            return mId;
        }

        public int getType() {
            return mType;
        }
    }*/
}
