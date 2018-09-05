/**
 * @(#)InteractionInfoUtils.java		2012-3-2
 * Copyright (c) 2007-2012 Shanghai ShuiDuShi Co.Ltd. All right reserved.
 * 
 */
package show.we.lib.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用于打印跟服务器端交互的地址信息，便于交互地址审核
 * @author ll
 * @version 2.0.0
 * @since 2012-3-2
 */
public class PrintInfoUtils {
	
    /**文件名称 */
    private static final String REPORT_ABSOLUTE_PATH = android.os.Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/show_log.txt";

	/**
	 * print
	 * @param message message
	 */
	public static void print(String message) {
		try {
			FileWriter fileWriter = new FileWriter(REPORT_ABSOLUTE_PATH, true);
			fileWriter.write("\n===============  start  ================\n");
			fileWriter.write(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "   " + message);
			fileWriter.write("\n===============  end  ================\n");

			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
