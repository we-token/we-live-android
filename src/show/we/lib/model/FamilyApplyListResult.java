package show.we.lib.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2014/5/19.
 */
public class FamilyApplyListResult extends BaseListResult<FamilyApplyListResult.ApplyData> {

     public static class ApplyData implements Serializable {
         @SerializedName("_id")
         private String applyId;

         @SerializedName("pic")
         private String applyerPic;

         @SerializedName("family_id")
         private long familyId;

         @SerializedName("lastmodif")
         private long lastModifTime;

         @SerializedName("nick_name")
         private String nickName;

         @SerializedName("status")
         private int status;

         @SerializedName("timestamp")
         private long timeStamp;

         @SerializedName("xy_user_id")
         private long xyUserId;

         @SerializedName("finance")
         private Finance finance;

         public String getApplyerPic() {
             return applyerPic;
         }

         public void setApplyerPic(String applyerPic) {
             this.applyerPic = applyerPic;
         }

         public String getApplyId() {
             return applyId;
         }

         public void setApplyId(String applyId) {
             this.applyId = applyId;
         }

         public long getFamilyId() {
             return familyId;
         }

         public void setFamilyId(long familyId) {
             this.familyId = familyId;
         }

         public long getLastModifTime() {
             return lastModifTime;
         }

         public void setLastModifTime(long lastModifTime) {
             this.lastModifTime = lastModifTime;
         }

         public String getNickName() {
             return nickName;
         }

         public void setNickName(String nickName) {
             this.nickName = nickName;
         }

         public int getStatus() {
             return status;
         }

         public void setStatus(int status) {
             this.status = status;
         }

         public long getTimeStamp() {
             return timeStamp;
         }

         public void setTimeStamp(long timeStamp) {
             this.timeStamp = timeStamp;
         }

         public long getXyUserId() {
             return xyUserId;
         }

         public void setXyUserId(long xyUserId) {
             this.xyUserId = xyUserId;
         }

         public Finance getFinance() {
             return finance;
         }

         public void setFinance(Finance finance) {
             this.finance = finance;
         }
     }
}
