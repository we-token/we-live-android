package show.we.lib.union;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import show.we.sdk.util.EnvironmentUtils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

/**
 * 添加统计信息
 * @author Administrator
 *
 */
public class PostUnion{
	private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
        "6", "7", "8", "9", "a", "b", "c", "d", "e", "f","g","h", "i", "j",
        "k", "l", "m", "n", "o", "p", "q","r","s", "t", "u", "v", "w", "x", "y", "z"};
	private static final String UNION_URL="http://union.51weibo.tv/api/receive";
	 HttpPost httpPost = new HttpPost(UNION_URL);
	 public String result = null;
	 List<NameValuePair> params = new ArrayList<NameValuePair>();
	 /**
	  * 向服务器发送统计请求
	 * @param context
	 * @param uid
	 * @param event_id
	 * @param event_value
	 * @throws SocketException
	 */ 
	 public void unionRequest(Context context,int uid,String event_id,String event_value) throws SocketException{
		 String CHANNEL_ID = (String) EnvironmentUtils.GeneralParameters.parameters().get("f");
		 SimpleDateFormat    sDateFormat    =   new    SimpleDateFormat("yyyy/MM/dd    HH:mm:ss");       
		 String date = sDateFormat.format(new    java.util.Date());  
		 TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
		 ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 
		 String net_type = getCurrentNetType(context);
		 String os_module = android.os.Build.MODEL; // 手机型号 
	       String os_brand= android.os.Build.BRAND;//手机品牌
	       String os_version = android.os.Build.VERSION.RELEASE;//手机操作系统版本
	       String os_ratio = getOs_ratio(context);//手机分辨率
		 PackageManager manager = context.getPackageManager();
		 PackageInfo info = null;
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 String app_version = info.versionName;
		 if(event_id=="logout"){
			 SharedPreferences sharedPreferences= context.getSharedPreferences("timeforlogin",
					 Activity.MODE_PRIVATE); 
			 String time =sharedPreferences.getString("logintime", "");
			 Date date1 = new Date(date);
		      Date date2 = new Date(time);
		     long temp = date1.getTime() - date2.getTime();
			 event_value = temp/1000+"";
		 }
		 
		 String md5 = getMD5(context);
		 
		 System.out.println(event_id+"："+event_value+"用户ID："+uid+"IMEI码："+md5+"品牌："+os_brand+"手机型号："+	os_module+"操作系统版本："+os_version+"手机分辨率："+os_ratio+"网络类型："+net_type);
		 params.add(new BasicNameValuePair("pkg_version",1+"")); 
	     params.add(new BasicNameValuePair("app_id", "2001")); 
	     params.add(new BasicNameValuePair("platform", "android")); 
	     params.add(new BasicNameValuePair("app_version", app_version));	
	     params.add(new BasicNameValuePair("uid", uid+"")); 
	     params.add(new BasicNameValuePair("channel_id", CHANNEL_ID));
	     params.add(new BasicNameValuePair("client_id", md5)); 
	     params.add(new BasicNameValuePair("date", date)); 
	     params.add(new BasicNameValuePair("event_id", event_id));
	     params.add(new BasicNameValuePair("event_value", event_value));
	     params.add(new BasicNameValuePair("os_brand", os_brand));
	     params.add(new BasicNameValuePair("os_module", os_module));
	     params.add(new BasicNameValuePair("os_version", os_version));
	     params.add(new BasicNameValuePair("os_ratio", os_ratio ));
	     //params.add(new BasicNameValuePair("ip", ip));
	     params.add(new BasicNameValuePair("net_type", net_type));
	     params.add(new BasicNameValuePair("imei", md5));
	     new Thread(r).start(); 
	 }
		Runnable r = new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 HttpResponse httpResponse = null; 
			     try { 
			            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); 
			            httpResponse = new DefaultHttpClient().execute(httpPost); 
			            if (httpResponse.getStatusLine().getStatusCode() == 200) { 
			                String result = EntityUtils.toString(httpResponse.getEntity()); 
			                System.out.println("result:" + result);
			            } 
			        } catch (ClientProtocolException e) { 
			            e.printStackTrace(); 
			        } catch (IOException e) { 
			            e.printStackTrace(); 
			        }
			}
		};
		/**
		 * 获取用户网络类型
		 * @param context
		 * @return
		 */
		public static String getCurrentNetType(Context context) { 
		    String type = ""; 
		    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 
		    NetworkInfo info = cm.getActiveNetworkInfo();
		    if(isNetworkConnected(context)){
		     if (info.getType() == ConnectivityManager.TYPE_WIFI) { 
		        type = "WIFI"; 
		    } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) { 
		        int subType = info.getSubtype(); 
		        if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS 
		                || subType == TelephonyManager.NETWORK_TYPE_EDGE) { 
		            type = "2G"; 
		        } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS || subType == TelephonyManager.NETWORK_TYPE_HSDPA 
		                || subType == TelephonyManager.NETWORK_TYPE_EVDO_A || subType == TelephonyManager.NETWORK_TYPE_EVDO_0 
		                || subType == TelephonyManager.NETWORK_TYPE_EVDO_B) { 
		            type = "3G"; 
		        } else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {
		            type = "4G"; 
		        } 
		    } else { 
			        type = "UNKNOW"; 
			    }
		    }
		    return type; 
		}
		public static String getOs_ratio(Context context){
			SharedPreferences sharedPreferences= context.getSharedPreferences("timeforlogin",
					 Activity.MODE_PRIVATE); 
			 String widthPixels =sharedPreferences.getString("widthPixels", "");
			 String heightPixels =sharedPreferences.getString("heightPixels", "");
			return widthPixels+"x"+heightPixels;
		}
		public static boolean isNetworkConnected(Context context) {  
		      if (context != null) {  
		          ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
		                  .getSystemService(Context.CONNECTIVITY_SERVICE);  
		          NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
		          if (mNetworkInfo != null) {  
		              return mNetworkInfo.isAvailable();  
		          	}  
		      }  
		     return false;  
	}  
		private static String byteToString(byte[] bByte) {
	        StringBuffer sBuffer = new StringBuffer();
	        for (int i = 0; i < bByte.length; i++) {
	            sBuffer.append(byteToArrayString(bByte[i]));
	        }
	        return sBuffer.toString();
	    }

	    public static String GetMD5Code(String strObj) {
	        String resultString = null;
	        try {
	            resultString = new String(strObj);
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            // md.digest() 该函数返回值为存放哈希值结果的byte数组
	            resultString = byteToString(md.digest(strObj.getBytes()));
	        } catch (NoSuchAlgorithmException ex) {
	            ex.printStackTrace();
	        }
	        return resultString;
	    }
	    private static String byteToArrayString(byte bByte) {
	        int iRet = bByte;
	        // System.out.println("iRet="+iRet);
	        if (iRet < 0) {
	            iRet += 256;
	        }
	        int iD1 = iRet / 16;
	        int iD2 = iRet % 16;
	        return strDigits[iD1] + strDigits[iD2];
	    }
	    private String getMD5(Context context){
	    	TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
	    	String Imei = tm.getDeviceId();//获取Imei号
	    	String imsi = tm.getSubscriberId();//获取Imsi号
	    	WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);  
	        WifiInfo info = wifi.getConnectionInfo();  
	        String WIFIMac = info.getMacAddress();//获取wifiMac地址
	        String PrivateId = Installation.id(context);
	        String md5 = GetMD5Code(imsi+Imei+WIFIMac+PrivateId);
			return md5;  
	    }
}
