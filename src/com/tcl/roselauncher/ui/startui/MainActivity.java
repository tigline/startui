package com.tcl.roselauncher.ui.startui;




import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.util.ResourceUtil;
import com.iflytek.cloud.util.ResourceUtil.RESOURCE_TYPE;
import com.tcl.roselauncher.iflytek.VoiceIdentifyListener;
import com.tcl.roselauncher.iflytek.VoiceIdentifyListener.IVoiceListenerCallback;

import static com.tcl.roselauncher.ui.startui.Constant.*;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	public static final String LOG_TAG = "StartUIActivity";
	private CCGLSurfaceView mSurfaceView;
	private static StartLayer startLayer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		initMsc();
		VoiceIdentifyListener listener = new VoiceIdentifyListener(this, voiceListenerCallback);
		
		mSurfaceView = new CCGLSurfaceView(this);
		
		setContentView(mSurfaceView);
		//setContentView(R.layout.activity_main);
		//applicationDidFinishLaunching(this, mSurfaceView);
		
	}

	private void initMsc() {
		// 设置你申请的应用appid
		StringBuffer paramInit = new StringBuffer();
		paramInit.append("appid=").append(getString(R.string.app_id)).append(",");
		// 设置使用v5+
		paramInit.append(SpeechConstant.ENGINE_MODE).append("=").append(SpeechConstant.MODE_MSC);
		SpeechUtility.createUtility(this, paramInit.toString());
		
		// 加载识唤醒地资源，resPath为本地识别资源路径
		StringBuffer param = new StringBuffer();
		String resPath = ResourceUtil.generateResourcePath(this, RESOURCE_TYPE.assets, "ivw/" + getString(R.string.app_id)	+ ".jet");
		param.append(ResourceUtil.IVW_RES_PATH + "=" + resPath);
		param.append("," + ResourceUtil.ENGINE_START + "=" + SpeechConstant.ENG_IVW);
		boolean ret = SpeechUtility.getUtility().setParameter(ResourceUtil.ENGINE_START, param.toString());
		if (!ret) {
			Log.d("LauncherApp", "启动本地引擎失败");
		}
	}

private VoiceIdentifyListener.IVoiceListenerCallback voiceListenerCallback = new IVoiceListenerCallback() {
		
		@Override
		public void onWakeup() {
			// TODO Auto-generated method stub
			startLayer.setStateFlag(INIT);
		}
		
		@Override
		public void onVolumeUpdate(int volume) {
			// TODO Auto-generated method stub
			startLayer.setDbLevel(volume);
		}
		
		@Override
		public void onTalkVoiceResult(Object response) {
			// TODO Auto-generated method stub
			startLayer.setOnResult(response.toString());
			//startLayer.setStateFlag(INIT);
		}
		
		@Override
		public void onTalkStart() {
			// TODO Auto-generated method stub
			startLayer.setStateFlag(SPEAK);
		}
		
		@Override
		public void onTalkResult(String result) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onTalkEnd() {
			// TODO Auto-generated method stub
			startLayer.setStateFlag(SCAN);
		}
		
		@Override
		public void onSleep() {
			// TODO Auto-generated method stub
			startLayer.setStateFlag(INIT);
		}
		
		@Override
		public void onRequestCommand(String question) {
			// TODO Auto-generated method stub
			
		}
		 
		@Override
		public void onError(String error) {
			// TODO Auto-generated method stub
			//talkEnd = false;
			startLayer.setStateFlag(ERROR);
		}
	};
		

	
	@Override
    public void onStart() {
    	super.onStart();
    	CCDirector.sharedDirector().attachInView(mSurfaceView);

        // set landscape mode
        CCDirector.sharedDirector().setLandscape(true);

        // show FPS
        CCDirector.sharedDirector().setDisplayFPS(true);

        // frames per second
        CCDirector.sharedDirector().setAnimationInterval(1.0f / 60);

        CCScene scene = CCScene.node();
        startLayer = new StartLayer(this);
        scene.addChild(startLayer, 0);

        // Make the Scene active
        CCDirector.sharedDirector().runWithScene(scene);
    }
    @Override
	public void onPause() {
		super.onPause();
		//CCDirector.sharedDirector().setLandscape(true);
		CCDirector.sharedDirector().onPause();
	}
    @Override
	public void onResume() {
    	super.onResume();
    	
        CCDirector.sharedDirector().onResume();
    }

    public void onDestroy() {
    	super.onDestroy();
    	
    	CCDirector.sharedDirector().end();
    }
	
}
