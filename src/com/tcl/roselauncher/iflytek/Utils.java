package com.tcl.roselauncher.iflytek;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import android.content.Context;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.InputEvent;
import android.view.KeyEvent;


public class Utils {
	public static final String TAG = "Utils";

	public static final String NULL = "null";

	public static void setSystemVolume(Context context) {

	}

	public static int getSystemVolume(Context context) {
		return 1;
	}

	/**
	 * 鍒ゆ柇褰撳墠瀛楃涓叉槸鍚︿负绌�
	 */
	public static boolean isNotEmpty(String str) {
		if (null != str && str.trim().length() > 0 && !NULL.equalsIgnoreCase(str)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 鍘绘爣鐐圭鍙�
	 * 
	 * @param str
	 * @return
	 */
	public static String punctuation(String str) {
		if (!isNotEmpty(str)) {
			return null;
		} else {
			Pattern p = Pattern.compile("[.,銆傦紝锛侊紵鈥溾��*锛氾紱\"\\?!:']");// 澧炲姞瀵瑰簲鐨勬爣鐐�

			Matcher m = p.matcher(str);

			return m.replaceAll(""); // 鎶婃爣鐐圭鍙锋浛鎹㈡垚绌猴紝鍗冲幓鎺夎嫳鏂囨爣鐐圭鍙�
		}
	}

	public static boolean copy(String srcPath, String dstPath) {
		Log.i(TAG, "copy: " + srcPath + " ---> " + dstPath);
		try {
			File oldfile = new File(srcPath);
			if (oldfile.exists()) {
				Log.i(TAG, "oldfile exists");
				InputStream inStream = new FileInputStream(srcPath);
				FileOutputStream fs = new FileOutputStream(dstPath);
				copyFileUsingStreams(inStream, fs);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static void copyFileUsingStreams(InputStream input, OutputStream output)
			throws IOException {
		try {
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);
			}
		} finally {
			input.close();
			output.close();
		}
	}

	// public static void startApp(Context mContext, String mPackageName_temp,
	// String mActivityName_temp, Intent mIntent) {
	//
	// String mPackageName = mPackageName_temp;
	// String mActivityName = mActivityName_temp;
	//
	// // 浠巌ntent涓彇鍑哄寘鍚嶅拰activity鍚�
	// if (TextUtils.isEmpty(mPackageName) && mIntent != null) {
	// if (mIntent.getPackage() != null) {
	// mPackageName = mIntent.getPackage();
	// }
	// if (mIntent.getComponent() != null) {
	// mPackageName = mIntent.getComponent().getPackageName();
	// }
	// }
	//
	// if (TextUtils.isEmpty(mActivityName) && mIntent != null) {
	// if (mIntent.getClass().getName() != null) {
	// mActivityName = mIntent.getClass().getName();
	// }
	// if (mIntent.getComponent() != null) {
	// mActivityName = mIntent.getComponent().getClassName();
	// }
	// }
	//
	// // 鏋勯�犳渶缁堢殑intent
	// Intent intent_final = null;
	// if (mIntent == null) {
	// intent_final = getIntentFromPackage(mContext, mPackageName,
	// mActivityName);
	//
	// } else {
	// intent_final = mIntent;
	// }
	//
	// Log.i(TAG, "startApp pack =" + mPackageName + "; mactivity ="
	// + mActivityName);
	//
	// if (isNeedStorageSource(mPackageName, mActivityName)) { // 璁剧疆淇℃簮锛�
	// TVWinManager.getInstance(mContext).setStorageSource();
	//
	// }
	//
	// if (!isActivityExit(mContext, intent_final)) {// 搴旂敤涓嶅瓨鍦紝鐩存帴鍘诲埌鍟嗗簵椤甸潰
	// goToMartketDetailPage(mContext, mPackageName);
	// return;
	// }
	// if (!TextUtils.isEmpty(mPackageName) &&
	// !TextUtils.isEmpty(mActivityName)) {
	// if (ShopUtil.getInstance(mContext).clickApp(mPackageName,
	// mActivityName)) { // 鍒ゆ柇鏄惁闇�瑕佸崌绾ф彁绀猴紵
	// return;
	// }
	//
	// /*
	// * if (MessageUtil.startActivity(mContext, // 鏄惁闇�瑕佹秷鎭洅瀛愬惎鍔紵
	// * mPackageName, mActivityName)) { Log.d(TAG, "娑堟伅鐩掑瓙鍚姩Activity");
	// * return; }
	// */
	// }
	//
	// if (!TextUtils.isEmpty(mPackageName)) {
	// updateStartFrequency(mContext, mPackageName);
	// }
	//
	// try {
	// mContext.startActivity(intent_final);
	// Log.i(TAG, intent_final.toString());
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	/**
	 * 鎵цshell鎸囦护
	 * 
	 * @param command
	 *            鎸囦护
	 */
	public static void excuteShell(String command) {
		InputStream is = null;
		BufferedReader br = null;
		String line = null;
		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec(command);
			int result = process.waitFor();
			Log.i(TAG, "result : " + result);
			is = process.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			while (true) {
				line = br.readLine();
				if (TextUtils.isEmpty(line)) {
					break;
				} else {
					Log.i(TAG, "read line : " + line);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 鎵цshell鎸囦护
	 * 
	 * @param cmd
	 *            鎸囦护
	 */
	public static void exec(String cmd, Context context) {

	}

//	public static String getParsValue(String key, Pars[] pars) {
//		if (pars != null) {
//			for (int i = 0; i < pars.length; i++) {
//				if (key.equals(pars[i].getKey())) {
//					String value = pars[i].getValue();
//					return value;
//				}
//			}
//		}
//
//		return null;
//	}

	/**
	 * 灏嗘帶鍒跺懡浠や互骞挎挱鐨勫舰寮忓彂閫佸埌瀹跺涵浜戠浉鍐屽簲鐢ㄤ腑鍘�
	 * @param action
	 * @param cmd
	 * @param context
	 */
	public static void sendCmdToHomeCloud(String action, String cmd, Context context) {
		Intent intent = new Intent();
		intent.setAction(action);
		intent.putExtra("cmd", cmd);
		context.sendBroadcast(intent);
	}

	public static void simulateKeystroke(final int KeyCode) {
		Log.i(TAG, "simulateKeystroke" + KeyCode);
		Thread sendKeyEvent = new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(100); // TouchEvent
					long now = SystemClock.uptimeMillis();
					KeyEvent down = new KeyEvent(now, now, KeyEvent.ACTION_DOWN, KeyCode, 0);
					KeyEvent up = new KeyEvent(now, now, KeyEvent.ACTION_UP, KeyCode, 0);
					Class clazz = Class.forName("android.hardware.input.InputManager");
					Method method = clazz.getDeclaredMethod("getInstance");
					Object realOb = method.invoke(clazz);
					if (realOb != null)
						;
					InputManager im = (InputManager) realOb;
					Method method2 = clazz.getDeclaredMethod("injectInputEvent", InputEvent.class, Integer.class);
					Object ob2 = method2.invoke(realOb, down, 0);
					Object ob3 = method2.invoke(realOb, up, 0);
					Log.i(TAG, "inputevent : " + KeyCode);
				} catch (InterruptedException e) {
					e.printStackTrace();
					Log.i(TAG, "simulateKeystroke + InterruptedException" + KeyCode);
				} catch (ClassNotFoundException e) {
					Log.i(TAG, "simulateKeystroke + ClassNotFoundException" + KeyCode);
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					Log.i(TAG, "simulateKeystroke + IllegalAccessException" + KeyCode);
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					Log.i(TAG, "simulateKeystroke + NoSuchMethodException" + KeyCode);
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					Log.i(TAG, "simulateKeystroke + IllegalArgumentException" + KeyCode);
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					Log.i(TAG, "simulateKeystroke + InvocationTargetException" + KeyCode);
					e.printStackTrace();
				}
			}
		};

		sendKeyEvent.start();
	}

	// public static long mCurrentTime;
	public static void recordCurrentTime(String method) {
		long currentTime = System.currentTimeMillis() / 1000;
		// mCurrentTime += currentTime;
//		Log.i(TAG, method + " : " + (currentTime));
	}

//	public static int getHistoryIndex(String question) {
//		String regex = "(绗�)(涓�|浜寍涓墊鍥泑浜攟鍏瓅涓億鍏珅涔潀鍗�)(涓獆閮�)";
//		Matcher m = Pattern.compile(regex, Pattern.DOTALL).matcher(question);
//		if (m.find()) {
//			String group = m.group(2);
//			int index = NumberUtil.numberCN2Arab(group);
//			return index;
//		}
//		return -1;
//	}
}
