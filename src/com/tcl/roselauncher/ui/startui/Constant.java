/**
 * 
 */
package com.tcl.roselauncher.ui.startui;

//import org.cocos2d.types.CGPoint;



/**
 * @Project ControlMenu	
 * @author houxb
 * @Date 2015-9-16
 */
public class Constant {
	public static final float V_THRESHOLD=0.1f;//速度阈值，小于此阈值的速度认为为0
	
	public final static float SCREEN_WIDTH=1920.0f;//桌子底长度
	public final static float SCREEN_HEIGHT=1080.0f;//桌子底宽度
	public final static float DIS_OFFSET=20.0f;//速度衰减系数
	public static final float V_TENUATION=0.970f;//速度衰减系数
	public static final float STANDA_BAll_R = 414.0f;
	//public static final CGPoint SCREEN_CENTER = new CGPoint(SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
	public static boolean weakup;
	public static boolean talk;
	public static boolean talkEnd;
	public static boolean onResult;
	public static boolean onError;
	public static String resultText;
	public final static int INIT = 0;
	public final static int SPEAK = 1;
	public final static int SCAN = 2;
	public final static int ERROR = 3;
	public final static int SLEEP = 4;
	public final static int MAXSIZE = 12;
}
