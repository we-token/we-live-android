package show.we.lib.utils;

import java.io.File;

import android.os.Environment;

/**
 * 常量标识类
 * 
 * @author wang
 * 
 */
public class ConstantUtil {

	public static String getChannel() {
//		return "400033"; //分配乐逗版本渠道号		
		return "100007"; //分配官方版本渠道号
	}
	//所有版本标识编译控制
	public static final String getLeDouSDKVer() {
		if (getChannel().equals("400033")){
			return "ledou"; //乐逗版本
		} else if(getChannel().equals("100007")){
			return "guanfang"; //官方版本
		}else{
			return "guanfang"; //官方版本
		}
	}	
	
}