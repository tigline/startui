package com.tcl.roselauncher.iflytek;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.VoiceWakeuper;
import com.iflytek.cloud.WakeuperListener;
import com.iflytek.cloud.WakeuperResult;




/**
 * @author chgang
 * @create time��2015-8-21 ����10:19:53
 * ����ʶ����
 */
public class VoiceIdentifyListener {


	private static final String TAG = "VoiceIdentifyListener";

	private static final int TIME_VOICE_BAR_LAST = 20 * 1000;

//	private RecognizerTalk mEngineTalk = null;
//	private IWakeupOperate mWakeupOperate = null;
//	public static boolean isSpeaking = false;
//	private ITTSControl mTTSControl = null;

	private IVoiceListenerCallback mIVoiceListenerCallback = null;
	
	SharedPreferences mSharedPreferences;
	
	public interface IVoiceListenerCallback {
		/**
		 * 唤醒
		 */
		void onWakeup();
		/**
		 * 监听说话开始
		 */
		void onTalkStart();
		/**
		 * 监听说话结束
		 */
		void onTalkEnd();
		/**
		 * 识别结果出现
		 */
		void onTalkResult(String result);
		/**
		 * 识别结果转换语音命令
		 */
		void onTalkVoiceResult(Object response);
		
		/**
		 * 唤醒状态结束，语音输入状态结束
		 */
		void onSleep();
		/**
		 * 出现错误
		 * @param error
		 */
		void onError(String error);
		/**
		 * 回调录音的音量大小
		 * @param volume
		 */
		void onVolumeUpdate(int volume);
		/**
		 * 回调网络请求
		 * @param question
		 */
		void onRequestCommand(String question);
	}
	
	public IVoiceListenerCallback getVoiceListenerCallback() {
		return mIVoiceListenerCallback;
	}

	public void setVoiceListenerCallback(IVoiceListenerCallback mIVoiceListenerCallback) {
		this.mIVoiceListenerCallback = mIVoiceListenerCallback;
	}
	
	public VoiceIdentifyListener(Context context, IVoiceListenerCallback voiceListenerCallback) {
		this.mContext = context;
		this.setVoiceListenerCallback(voiceListenerCallback);
		init();
	}

//	public void init() {
//		// TODO 创建语音识别对象
//		mEngineTalk = new RecognizerTalk(LauncherApp.getInstance());
//		// TODO 为语音识别设置监听回调
//		mEngineTalk.setListener(mRecognizerTalkListener);
//		mEngineTalk.setFarFeild(true);
//		// TODO 需主动调用初始化
//		mEngineTalk.init();
//		// TODO 设置个性化数据
//		// 注意，若没有个性化数据，该方法也需要调用一次，参数可以设置为null
//		mEngineTalk.setUserData(null);
//
//		mEngineTalk.setVadEnable(true);
//		mEngineTalk.setVADTimeout(20000, 2000);
////		mEngineTalk.setRecordingTimeout(20000);
//		
//		mWakeupOperate = (IWakeupOperate) mEngineTalk.getOperate("OPERATE_WAKEUP");
//		//加入唤醒词
//		List<String> command = new ArrayList<String>();
//		command.add(LauncherApp.getContext().getString(R.string.wake_up_word_1));
//		command.add(LauncherApp.getContext().getString(R.string.wake_up_word_2));
//
//		if (mWakeupOperate != null) {
//			mWakeupOperate.setCommandData(command);
//			mWakeupOperate.setWakeupListener(mWakeupListener);
//			mWakeupOperate.setFarFeild(true);
//		}
//		
//		mTTSControl = TTSFactory.createTTSControl(LauncherApp.getContext(), "");
//		mTTSControl.setStreamType(AudioManager.STREAM_MUSIC);
//		mTTSControl.initTTSEngine(LauncherApp.getContext());
//	}
//
//	/**
//	 * @Description : TODO 语音识别的类型，可以指定返回的协议是命令类还是自由文本类的
//	 * @Author : Dancindream
//	 * @CreateDate : 2013-9-29
//	 */
//	private String mTalkType = RecognizerTalk.TYPE_FREETEXT;
//
//	/**
//	 * @Description : TODO 语音识别的监听回调
//	 * @Author : Dancindream
//	 * @CreateDate : 2013-9-29
//	 */
//	private IRecognizerTalkListener mRecognizerTalkListener = new IRecognizerTalkListener() {
//
//		@Override
//		public void onDataDone() {
//			Log.d(TAG, "onDataDone");
//		}
//
//		@Override
//		public void onInitDone() {
//			Log.d(TAG, "onInitDone+模块初始化完成");
//		}
//
//		@Override
//		public void onTalkStart() {
//			Log.d(TAG, "onTalkStart+语音识别流程开始");
//			mIVoiceListenerCallback.onTalkStart();
//			Utils.recordCurrentTime("onTalkStart");
//			//当调用start方法之后，会快速回调，通知上层，语音识别的流程已经开始了
//			cancelVoiceTimer();
//			createVoiceTimer();
//		}
//
//		@Override
//		public void onTalkRecordingStart() {
//			Log.d(TAG, "onTalkRecordingStart+录音正式开始");
//		}
//		
//		@Override
//		public void onTalkStop() {
//			Utils.recordCurrentTime("onTalkStop");
//			Log.d(TAG, "onTalkStop+录音已结束");
//			mIVoiceListenerCallback.onTalkEnd();
//		}
//		
//		@Override
//		public void onTalkRecordingStop() {
//			Log.d(TAG, "onTalkRecordingStop+麦克风关闭后的回调");
//		}
//		
//		@Override
//		public void onTalkParticalResult(String arg0) {
//			Log.d(TAG, "onTalkParticalResult");
//		}
//		
//		@Override
//		public void onTalkResult(String arg0) {
//			Utils.recordCurrentTime("onTalkResult");
//			mIVoiceListenerCallback.onTalkResult(arg0);
//			Log.d(TAG, "onTalkResult识别的结果: " + arg0);
//			String temp_question = Utils.punctuation(arg0);
//			Log.d(TAG, "user_question result：" + temp_question);
//			mTTSControl.play(temp_question);
//			if (Utils.isNotEmpty(temp_question) && temp_question.length() > 1) {
//				// 获取本地命令列表， 列表由 VoiceCommandCallback实现各个界面的动态命令
//				List<Command> localCommandList = null;
//				//获取当前界面可执行的动态命令
//				VoiceCommandCallback callback = LauncherApp.getInstance().getmVoiceCommandCallback();
//				if (callback != null && callback.getLocalCommand() != null) {
//					localCommandList = callback.getLocalCommand();
//				}
//				//根据语音识别结果获取本地的命令，如果没有，则向网络服务器请求。
//				VoiceResponse response = LocalDynamicCommand.getInstance().getLocalCommand(localCommandList, temp_question);
//				
//				if (response != null) {
//					//本地识别成功
//					Log.d(TAG, "本地识别ok:" + response.getCommand());
//					Log.d(TAG, "本地识别ok:" + response.toString());
//					if(CommandConstants.ROSE_TV_WAKE_UP.equals(response.getCommand())){
//						//回到待唤醒状态
//						isSpeaking = false;
//						judgeHandler.sendEmptyMessage(Integer.MAX_VALUE);
//						return;
//					}
//					mIVoiceListenerCallback.onTalkVoiceResult(response);
//				} else {
//					Log.d(TAG, "本地识别no:");
//					mIVoiceListenerCallback.onRequestCommand(temp_question);
//				}
//				
//////				voice_controller();
//			} else {
//				//未获取到语音识别结果
//				mTTSControl.play(LauncherApp.getContext().getString(R.string.tips_no_result));
//				mIVoiceListenerCallback.onError(LauncherApp.getContext().getString(R.string.tips_no_result));
//			}
//			judgeHandler.sendEmptyMessageDelayed(Integer.MAX_VALUE, 1500);
//		}
//		
//		@Override
//		public void onTalkError(ErrorUtil arg0) {
//			Log.d(TAG, "onTalkError+异常反馈");
//			mIVoiceListenerCallback.onError(arg0.message);
//			mIVoiceListenerCallback.onSleep();
//			judgeHandler.sendEmptyMessage(Integer.MAX_VALUE);
//		}
//		
//		@Override
//		public void onTalkCancel() {
//			Log.d(TAG, "onTalkCancel+语音识别流程已取消");
//			judgeHandler.sendEmptyMessage(Integer.MAX_VALUE);
//		}
//
//		@Override
//		public void onTalkProtocal(String arg0) {
//			Log.d(TAG, "onTalkProtocal+语义协议回调"+arg0);
//			judgeHandler.sendEmptyMessage(Integer.MAX_VALUE);
//		}
//
//		@Override
//		public void onUserDataCompile() {
//			Log.d(TAG, "onUserDataCompile+个性化数据开始编译");
//		}
//
//		@Override
//		public void onUserDataCompileDone() {
//			Log.d(TAG, "onUserDataCompileDone+个性化数据编译结束");
//		}
//
//		@Override
//		public void onVolumeUpdate(int arg0) {
//			mIVoiceListenerCallback.onVolumeUpdate(arg0);
////			Log.d(TAG, "onVolumeUpdate录音时音量数据的回调");
//		}
//	};
//	
//	/**
//	 * 唤醒回调接口
//	 */
//	IWakeupListener mWakeupListener = new IWakeupListener() {
//
//		@Override
//		public void onSuccess(String arg0) {
//			Log.d(TAG, "mWakeupListener onSuccess 唤醒成功时回调: " + arg0);
//			isSpeaking = true;
//			cancelVoiceTimer();
//			createVoiceTimer();
////			TipsVoicePlayer.getInstance(LauncherApp.getContext()).play();
//			mTTSControl.play("主人你好");
//			mIVoiceListenerCallback.onWakeup();
//			mEngineTalk.start(mTalkType);
//		}
//
//		@Override
//		public void onStop() {
//			Log.d(TAG, "mWakeupListener : onStop唤醒服务停止");
//		}
//
//		@Override
//		public void onStart() {
//			Log.d(TAG, "mWakeupListener : onStart启动唤醒服务");
//		}
//
//		@Override
//		public void onInitDone() {
//			Log.d(TAG, "mWakeupListener onInitDone唤醒初始化结束");
//			mWakeupOperate.startWakeup();
//		}
//
//		@Override
//		public void onError(ErrorUtil arg0) {
//			Log.d(TAG, "mWakeupListener onError唤醒模块异常");
//		}
//	};
//	
//
//	/**
//	 * 需要延迟1.5秒执行
//	 */
//	private Handler judgeHandler = new Handler(){
//
//		@Override
//		public void handleMessage(Message msg) {
//			judgeVoiceOrWackup();
//		}
//		
//	};
//	
//	/**
//	 * 判断语音还是唤醒启动
//	 */
//	public void judgeVoiceOrWackup() {
//		Log.d(TAG, "judge_voice_wackup :" + isSpeaking);
//		if (isSpeaking) {
//			mEngineTalk.start(mTalkType);
//		} else {
//			mIVoiceListenerCallback.onSleep();
//			mWakeupOperate.startWakeup();
//		}
//	}
//
//	/**
//	 * 记录唤醒时间，当一定时间没有语音输入之后，回到唤醒状态
//	 */
//	private Timer voice_timer = new Timer();
//	private TimerTask voice_timer_task = null;
//	/**
//	 * 语音录入定时器
//	 */
//	private void createVoiceTimer() {
//		if (voice_timer_task == null) {
//			voice_timer_task = new TimerTask(){
//
//				@Override
//				public void run() {
//					Log.d(TAG, "VoiceTimerTask:" + isSpeaking);
//					isSpeaking = false;
//				}
//				
//			};
//		}
//		
//		if(voice_timer != null){
//			voice_timer.schedule(voice_timer_task, TIME_VOICE_BAR_LAST);
//		}
//	}
//	
//	/**
//	 * 取消定时操作
//	 */
//	private void cancelVoiceTimer(){
//		if (null != voice_timer_task) {
//			voice_timer_task.cancel();
//			voice_timer_task = null;
//		}
//	}
//	
//
//	/**
//	 * 播放语音
//	 * @param text 待播放的文字
//	 */
//	public void playTTS(String text) {
//		if (TextUtils.isEmpty(text)) {
//			LogUtil.w(TAG, "playTTS:text empty!");
//			return;
//		}
//		if (mTTSControl == null) {
//			Log.d(TAG, "playTTS:mTTSControl null!");
//			return;
//		}
//		LogUtil.d(TAG, "playTTS:text " + text);
//		cancelTTS();
//		mTTSControl.play(text);
//	}
//
//	private void cancelTTS() {
//		if (mTTSControl == null) {
//			Log.d(TAG, "cancelTTS:mTTSControl null!");
//			return;
//		}
//		mTTSControl.cancel();
//	}
//
//	TTSPlayerListener mTTSPlayerListener = new TTSPlayerListener() {
//
//		@Override
//		public void onTtsData(byte[] arg0) {
//		}
//
//		@Override
//		public void onPlayEnd() {
//			Log.d(TAG, "onPlayEnd");
//		}
//
//		@Override
//		public void onPlayBegin() {
//		}
//
//		@Override
//		public void onInitFinish() {
//		}
//
//		@Override
//		public void onError(USCError arg0) {
//		}
//
//		@Override
//		public void onCancel() {
//		}
//
//		@Override
//		public void onBuffer() {
//		}
//	};
	
	
	/*****************************讯飞语音****************************************/
	private Context mContext;
	// 语音听写对象
	private SpeechRecognizer mIat;
	// 语音唤醒对象
	private VoiceWakeuper mIvw;
//	private SpeechSynthesizer mTts;
	// 唤醒结果内容
	private String resultString;
	// 设置门限值 ： 门限值越低越容易被唤醒
	private final static int MIN = -20;
	private int curThresh = MIN;	
	private String user_request = "";
	
	private void init() {
		if (mSharedPreferences == null){
    		mSharedPreferences = mContext.getSharedPreferences("ControlMenu", Context.MODE_PRIVATE);
    	}
		
		// 初始化唤醒对象
		VoiceWakeuper.createWakeuper(mContext, null);
		// 初始化识别对象
		mIat = SpeechRecognizer.createRecognizer(mContext, mInitListener);
		mIvw = VoiceWakeuper.getWakeuper();
		if(mIvw != null){
			// 清空参数
			mIvw.setParameter(SpeechConstant.PARAMS, null);
			// 设置识别引擎
			mIvw.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
			// 唤醒门限值，根据资源携带的唤醒词个数按照“id:门限;id:门限”的格式传入
			mIvw.setParameter(SpeechConstant.IVW_THRESHOLD, "0:" + curThresh);
			// wakeup 设置唤醒模式
			mIvw.setParameter(SpeechConstant.IVW_SST, "oneshot");//oneshot 设置唤醒+识别模式
			// 设置返回结果格式
			mIvw.setParameter(SpeechConstant.RESULT_TYPE, "json");
			// 设置持续进行唤醒
//			mIvw.setParameter(SpeechConstant.KEEP_ALIVE, "1");
//			mIvw.setParameter(SpeechConstant.CLOUD_GRAMMAR, mCloudGrammarID);// 设置云端识别使用的语法id
		}
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				start_xf_wackup();
			}
		}, 5000);
//		mTts = SpeechSynthesizer.createSynthesizer(mContext, mTtsInitListener);
	}
	
	private InitListener mTtsInitListener = new InitListener() {
		@Override
		public void onInit(int code) {
			Log.d(TAG, "InitListener init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
				Log.d(TAG, "onInit error : " + code);
			}
		}
	};
	
	private WakeuperListener mWakeuperListener = new WakeuperListener() {

		@Override
		public void onResult(WakeuperResult result) {
			try {
				if(mIVoiceListenerCallback != null){
					isSpeaking = true;
//					TipsVoicePlayer.getInstance(mContext).play();
					String text = result.getResultString();
					Log.d(TAG, "onResult！"+text);
					voice_controller();
					stop_xf_wackup();
					start_xf_voice();
					mIVoiceListenerCallback.onWakeup();
				}
			} catch (Exception e) {
				Log.d(TAG, "onResult Error");
				e.printStackTrace();
			}
			
		}

		@Override
		public void onError(SpeechError error) {
			Log.d(TAG, "onError！"+error.getPlainDescription(true));
		}

		@Override
		public void onBeginOfSpeech() {
			Log.d(TAG, "onBeginOfSpeech！"+"开始说话");
			if(mIVoiceListenerCallback != null){
				Log.d(TAG, "onBeginOfSpeech！"+"onSleep()");
				mIVoiceListenerCallback.onSleep();
			}
		}

		@Override
		public void onEvent(int eventType, int isLast, int arg2, Bundle obj) {

		}
	};
	
	/**
	 * 初始化监听器。
	 */
	private InitListener mInitListener = new InitListener() {

		@Override
		public void onInit(int code) {
			Log.d(TAG, "SpeechRecognizer init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
				Log.d(TAG, "初始化失败,错误码："+code);
        	}
		}
	};
	
	/**
	 * 参数设置
	 * @param param
	 * @return 
	 */
	private void setParam(){
		// 清空参数
		mIat.setParameter(SpeechConstant.PARAMS, null);
		String lag = mSharedPreferences.getString("iat_language_preference", "mandarin");
		// 设置引擎
		mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);

		if (lag.equals("en_us")) {
			// 设置语言
			mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
		}else {
			// 设置语言
			mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
			// 设置语言区域
			mIat.setParameter(SpeechConstant.ACCENT, lag);
		}

		// 设置语音前端点
		mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "4000"));
		// 设置语音后端点
		mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));
		// 设置标点符号
		mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "1"));
		// 设置音频保存路径
		mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/iflytek/wavaudio.pcm");// 设置音频保存路径
	}
	
	/**
	 * 听写监听器。
	 */
	private RecognizerListener recognizerListener=new RecognizerListener(){

		@Override
		public void onBeginOfSpeech() {	
			Log.d(TAG, "听写监听器---开始说话");
			if(mIVoiceListenerCallback != null){
				mIVoiceListenerCallback.onTalkStart();
			}
		}


		@Override
		public void onError(SpeechError error) {
			if(mIVoiceListenerCallback != null){
				mIVoiceListenerCallback.onError("您好像没有说话哦.");
			}
//			error.getErrorCode();
			Log.d(TAG, "听写监听器---onError" + error.getPlainDescription(true));
			judge_voice_wackup();
		}

		@Override
		public void onEndOfSpeech() {
			if(mIVoiceListenerCallback != null){
				mIVoiceListenerCallback.onTalkEnd();
			}
			Log.d(TAG, "听写监听器---结束说话");
		}

		@Override
		public void onResult(RecognizerResult results, boolean isLast) {	
			String text = JsonParser.parseIatResult(results.getResultString());
			Log.d(TAG, "听写监听器---text"+text+isLast);
			user_request = user_request + text;
			if(mIVoiceListenerCallback != null){
//				mIVoiceListenerCallback.onTalkVoiceResult(response)
			}
			if(isLast) {
				String temp_question = Utils.punctuation(user_request);
				Log.d(TAG, "听写监听器---temp_question:"+temp_question);
				if (Utils.isNotEmpty(temp_question) && temp_question.length() > 1) {
					mIVoiceListenerCallback.onRequestCommand(temp_question);
					mIVoiceListenerCallback.onTalkVoiceResult(temp_question);
//					// 获取本地命令列表， 列表由 VoiceCommandCallback实现各个界面的动态命令
//					List<Command> localCommandList = null;
//					//获取当前界面可执行的动态命令
//					VoiceCommandCallback callback = LauncherApp.getInstance().getVoiceManagerService().getmVoiceCommandCallback();
//					if (callback != null && callback.getLocalCommand() != null) {
//						localCommandList = callback.getLocalCommand();
//					}
//					//根据语音识别结果获取本地的命令，如果没有，则向网络服务器请求。
//					VoiceResponse response = LocalDynamicCommand.getInstance().getLocalCommand(localCommandList, temp_question);
//					
//					if (response != null) {
//						//本地识别成功
//						Log.d(TAG, "本地识别ok:" + response.toString());
//						if(Constants.ROSE_TV_WAKE_UP.equals(response.getCommand())){
//							//回到待唤醒状态
//							isSpeaking = false;
//							judge_voice_wackup();
//							return;
//						}
//						mIVoiceListenerCallback.onTalkVoiceResult(response);
//					} else {
//						Log.d(TAG, "本地识别no:");
//						mIVoiceListenerCallback.onRequestCommand(temp_question);
//					}
//					voice_controller();
				}
				
				judge_voice_wackup();
				user_request = "";
			}
		}

		@Override
		public void onVolumeChanged(int volume) {
			if(mIVoiceListenerCallback != null){
				mIVoiceListenerCallback.onVolumeUpdate(volume);
			}
			//Log.d(TAG, "当前正在说话，音量大小：" + volume);
		}


		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
			
		}
	};
	
	/**
	 * 开始说话录音
	 */
	private void start_xf_voice() {
		user_request = "";
		setParam();
		int retSpeech = mIat.startListening(recognizerListener);
		if(retSpeech != ErrorCode.SUCCESS){
			Log.d(TAG, "听写失败,错误码：" + retSpeech);
		}else {
			Log.d(TAG, "开始说话录音!");
		}
	}
	
	/**
	 * 启动讯飞语音唤醒
	 */
	private void start_xf_wackup() {
		Log.d(TAG, "启动讯飞语音唤醒！");
		mIvw = VoiceWakeuper.getWakeuper();
		// 非空判断，防止因空指针使程序崩溃
		if (mIvw != null) {
			mIvw.startListening(mWakeuperListener);
		} else {
			Log.d(TAG, "唤醒未初始化！");
		}
	}
	
	/**
	 * 停止讯飞语音唤醒
	 */
	private void stop_xf_wackup() {
		mIvw = VoiceWakeuper.getWakeuper();
		if (mIvw != null) {
			mIvw.stopListening();
		} else {
			Log.d(TAG, "wakeup uninit");
		}
	}
	
	/**
	 * 语音还是唤醒启动判断
	 */
	public void judge_voice_wackup() {
		Log.d(TAG, "judge_voice_wackup:"+isSpeaking);
		if (isSpeaking) {
			start_xf_voice();
		} else {
			start_xf_wackup();
		}
	}
	
	public boolean isSpeaking() {
		return isSpeaking;
	}
	
	/**
	 * 录音控制器
	 */
	private boolean isSpeaking = true;
	private Timer voice_timer = new Timer();
	private VoiceTimerTask voice_timerTask = new VoiceTimerTask();

	class VoiceTimerTask extends TimerTask {
		@Override
		public void run() {
			Log.d(TAG, "VoiceTimerTask" + isSpeaking);
			isSpeaking = false;
		}
	}
	/**
	 * 语音录入定时器
	 */
	private void voice_controller() {
		if (null != voice_timerTask) {
			voice_timerTask.cancel();
		}

		voice_timerTask = new VoiceTimerTask();
		voice_timer.schedule(voice_timerTask, TIME_VOICE_BAR_LAST);
	}
	
}
