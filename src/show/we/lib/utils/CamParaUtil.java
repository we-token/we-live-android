package com.iavstream.utility;

import android.annotation.SuppressLint;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CamParaUtil {
	
	private static final String TAG = "BLife_CamParaUtil";
	private CameraSizeComparator sizeComparator = new CameraSizeComparator();
	private static CamParaUtil myCamPara = null;
	
	private CamParaUtil(){

	}
	
	public static CamParaUtil getInstance(){
		if(myCamPara == null){
			myCamPara = new CamParaUtil();
			return myCamPara;
		}
		else{
			return myCamPara;
		}
	}

	public  Size getPropPreviewSize(List<Camera.Size> list, float th, int minWidth){
		Collections.sort(list, sizeComparator);
		int i = 0;
		for(Size s:list){
			if((s.width >= minWidth) && equalRate(s, th)){
				Log.i(TAG, "PreviewSize:w = " + s.width + "h = " + s.height);
				break;
			}
			i++;
		}
		if(i == list.size()){
		i = 0;
	}
		return list.get(i);
	}
	public Size getPropPictureSize(List<Camera.Size> list, float th, int minWidth){
		Collections.sort(list, sizeComparator);
		int i = 0;
		for(Size s:list){
			if((s.width >= minWidth) && equalRate(s, th)){
				Log.i(TAG, "PictureSize : w = " + s.width + "h = " + s.height);
				break;
			}
			i++;
		}
		if(i == list.size()){
			i = 0;
		}
		return list.get(i);
	}

	public boolean equalRate(Size s, float rate){
		float r = (float)(s.width)/(float)(s.height);
		if(Math.abs(r - rate) <= 0.03)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public  class CameraSizeComparator implements Comparator<Camera.Size>{
		public int compare(Size lhs, Size rhs) {
			// TODO Auto-generated method stub
			if(lhs.width == rhs.width){
				return 0;
			}
			else if(lhs.width > rhs.width){
				return 1;
			}
			else{
				return -1;
			}
		}

	}
	public  void printSupportPreviewSize(Camera.Parameters params){
		List<Size> previewSizes = params.getSupportedPreviewSizes();
		for(int i=0; i< previewSizes.size(); i++){
			Size size = previewSizes.get(i);
			Log.i(TAG, "previewSizes:width = "+size.width+" height = "+size.height);
		}
	
	}
	public  void printSupportPictureSize(Camera.Parameters params){
		List<Size> pictureSizes = params.getSupportedPictureSizes();
		for(int i=0; i< pictureSizes.size(); i++){
			Size size = pictureSizes.get(i);
			Log.i(TAG, "pictureSizes:width = "+ size.width
					+" height = " + size.height);
		}
	}
	public void printSupportFocusMode(Camera.Parameters params){
		List<String> focusModes = params.getSupportedFocusModes();
		for(String mode : focusModes){
			Log.i(TAG, "focusModes--" + mode);
		}
	}
	@SuppressLint("NewApi")
	public int getFrontCamIndex()
	{
		 int cameraCount = 0;    	 
    	 Camera.CameraInfo cameraInfo = new Camera.CameraInfo();  
    	 cameraCount = Camera.getNumberOfCameras(); // get cameras number  
    	         
    	 for ( int camIdx = 0; camIdx < cameraCount;camIdx++ )
    	 {
    	        Camera.getCameraInfo( camIdx, cameraInfo ); // get camerainfo
    	        if ( cameraInfo.facing ==Camera.CameraInfo.CAMERA_FACING_FRONT )
    	        {  // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置  
    	           return camIdx;
    	        }
    	 }		
		return -1;
	 }
	
	@SuppressLint("NewApi")
	public int getBackCamIndex()
	{
		 int cameraCount = 0;    	 
    	 Camera.CameraInfo cameraInfo = new Camera.CameraInfo();  
    	 cameraCount = Camera.getNumberOfCameras(); // get cameras number  
    	         
    	 for ( int camIdx = 0; camIdx < cameraCount;camIdx++ )
    	 {  
    	        Camera.getCameraInfo( camIdx, cameraInfo ); // get camerainfo
    	        if ( cameraInfo.facing ==Camera.CameraInfo.CAMERA_FACING_BACK )
    	        {  // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置  
    	           return camIdx;
    	        }
    	 }		
		return -1;
	 }
	
}
