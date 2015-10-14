/**
 * 
 */
package com.tcl.roselauncher.ui.startui;



import java.util.ArrayList;
import java.util.Random;

import org.cocos2d.config.ccMacros;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemImage;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import static com.tcl.roselauncher.ui.startui.Constant.*;
/**
 * @Project StartUI	
 * @author houxb
 * @Date 2015-10-12
 */
public class StartLayer extends CCLayer {
	
	/**
	 * 
	 */
	public static int dbLevel = 0;
	public static int  stateFlag = 0;
	private static float angle;

	public static String resultText;
	private boolean eninit = false;
	public MicroPhone microPhone;
	public CCSprite scanState;
	public CCSprite backGroud;
	public CCSprite errorState;
	public CCSprite starSky;
	public static CCLabel scanNotice;
	public static CCLabel errorNoticeU;
	public static CCLabel errorNoticeD;
	public static Circle colliCircle;
	private static int count;
	private static int sum;
	private static int accum;
	CGSize winSize = CCDirector.sharedDirector().winSize();
	Activity curContext;
	public static final String LOG_TAG = "StartUIActivity";
	private static ArrayList<Circle> circleList ;
	private static String [] infoList = {"碟中谍5", "最新的电影","控制空调","购物","听歌", "关灯","新闻","电视剧"};
	public CGPoint SCREEN_CENTER = CGPoint.ccp(winSize.width/2, winSize.height/2);
	
	public void setResultText(String text){
    	StartLayer.resultText = text;
    }
    
    public String setResultText(){
		return StartLayer.resultText;
    }
	public void setStateFlag(int value){
    	StartLayer.stateFlag = value;
    }
    
    public int getStateFlag(){
		return StartLayer.stateFlag;
    }
    public StartLayer(Activity context) {
    	  super();
    	  eninit = true;
    	  curContext = context;
    	  setIsTouchEnabled(true);
    	  circleList = new ArrayList<Circle>();
          
          
          //背景
          backGroud = CCSprite.sprite("launcher/background.jpg");
          backGroud.setPosition(SCREEN_CENTER);
          //backGroud.setVisible(true);
          addChild(backGroud);
          starSky = CCSprite.sprite("launcher/starsky.png");
          starSky.setPosition(SCREEN_CENTER);
          //starSky.setVisible(true);
          addChild(starSky);
          
          
          //初始化麦克风
          microPhone = new MicroPhone();
          microPhone.setPosition(SCREEN_CENTER); 
          microPhone.setVisible(true);
    	  addChild(microPhone);
    	  //初始化搜索状态
    	  scanState = CCSprite.sprite("launcher/analysis.png");
    	  scanState.setPosition(SCREEN_CENTER); 
    	  scanState.setVisible(false);
    	  addChild(scanState);
    	  scanNotice = CCLabel.makeLabel("识别中。。。", "fangzheng.ttf", 36);
    	  scanNotice.setPosition(SCREEN_CENTER);
    	  scanNotice.setVisible(false);
    	  addChild(scanNotice);
    	  
    	  
    	  //错误状态
    	  errorState = CCSprite.sprite("launcher/error.png");
    	  errorState.setPosition(SCREEN_CENTER);
    	  errorState.setVisible(false);
    	  addChild(errorState);
    	  errorNoticeU = CCLabel.makeLabel("识别失败", "fangzheng.ttf", 36);
    	  errorNoticeU.setPosition(errorState.getContentSize().width/2, 
    			  errorState.getContentSize().height/2 + 20);
    	  errorState.addChild(errorNoticeU);
    	  errorNoticeD = CCLabel.makeLabel("请重新输入", "fangzheng.ttf", 36);
    	  errorNoticeD.setPosition(errorState.getContentSize().width/2, 
    			  errorState.getContentSize().height/2 - 20);
    	  errorState.addChild(errorNoticeD);
    	  
    	  
    	  CCMenuItem item = CCMenuItemImage.item("SendScoreButton.png", "SendScoreButtonPressed.png", this, "menuCallback");
    	  CCMenu menu = CCMenu.menu(item);
    	  menu.setAnchorPoint(1.0f, 0.0f);
    	  menu.setPosition(winSize.width-100, 20);
    	  addChild(menu);
    	  
    	  
    	  colliCircle = new Circle(" ",1.0f,0.0f,0.0f);
    	  colliCircle.setPosition(SCREEN_CENTER);
    	  colliCircle.setVisible(false);
  		  circleList.add(colliCircle);
  		  addChild(colliCircle);
  		  scheduleUpdate();
  //		  schedule("autoremove", 0.5f);
  		  
//        CCMenuItemFont item1 = CCMenuItemFont.item("Test pushScene", this, "onPushScene");
//        CCMenuItemFont item2 = CCMenuItemFont.item("Test pushScene w/transition", this, "onPushSceneTran");
//        CCMenuItemFont item3 = CCMenuItemFont.item("Quit", this, "onQuit");
//
//        CCMenu menu = CCMenu.menu(item1, item2, item3);
//        menu.alignItemsVertically();
//
//        addChild(menu);

    }
//    public void autoremove(float dt) {
//		accum += dt;
//		//String s = String.format("Time: %f", accum);
//		ccMacros.CCLOG(LOG_TAG, "autoremove");
//
//		if( accum > 5 ) {
//			create = true;
//			unschedule("autoremove");
//			ccMacros.CCLOG(LOG_TAG, "scheduler removed");
//			
//		}
//	}
    
    public void menuCallback(Object sender) {

    	Bundle bundle = new Bundle();

		openActivity(curContext, PlayerActivity.class, bundle);
		//finish();
    }
	public void openActivity(Activity activity, Class<?> pClass, Bundle bundle) {
		Intent intent = new Intent(activity, pClass);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//Gdx.app.exit();
		activity.startActivity(intent);
		//Gdx.app.exit();
	}
	
	//主循环
	@SuppressLint("DefaultLocale")
	public void update(float dt) {
		
		if (eninit) {
			if (accum++ > 5.0f) {
				accum = 0;
				initCreate();
			}
		}
		
    	if (circleList.size() > 0) {
			for (int i = 0; i < circleList.size(); i++) {
				circleList.get(i).CheckCollision(circleList, dt);
			}
		}
    	
    	if (INIT == stateFlag) {
			setInitState();
		} 
    	else if (SPEAK == stateFlag) {
			setSpeakState();
		}
    	else if (SCAN == stateFlag) {
			setScanState();
		}
    	else if (ERROR == stateFlag) {
			setErrorState();
		}
	}
	
	public void initCreate(){
		//for (int i = 0; i < 8; i++) {
		
		if (sum < 6) {
			if ( count++ > 15) { 
				count = 0;
				createCircle();
				sum++;
			}
		}else{
			sum = 0;
			eninit = false;
		}
		//}
	}

    @Override
    public boolean ccTouchesBegan(MotionEvent event) {
    	ccMacros.CCLOG(LOG_TAG, "touchBegan");
//        CGPoint convertedLocation = CCDirector.sharedDirector()
//        	.convertToGL(CGPoint.make(event.getX(), event.getY()));
 //   	stateFlag = 2;
    	//create = true;
    	
        return true;
    }
    @Override
    public boolean ccTouchesEnded(MotionEvent event) {
//    	ccMacros.CCLOG(LOG_TAG, "touchEnded");
//    	CGPoint convertedLocation = CCDirector.sharedDirector()
//            	.convertToGL(CGPoint.make(event.getX(), event.getY()));
    	//microPhone.updateState(0);
//    	stateFlag = 1;
//    	dbLevel = 1;
    	setOnResult(" ");
    	//createCircle();
		ccMacros.CCLOG(LOG_TAG, "touchEnded");
		//stateFlag = 0;
		return true;
    }

    public void createCircle() {
    	Random r = new Random();
    	int angle = r.nextInt(360);
    	int radius = 212;
    	int index = r.nextInt(7);
    	int num =  r.nextInt(6) + 2;
		float scale = num/10.0f;
		float posX = (float)(radius*Math.cos(angle));
		float posY = (float)(radius*Math.sin(angle));
		Circle circle = new Circle(infoList[index],scale,posX,posY);
		circle.setPosition(posX + winSize.width/2, posY + winSize.height/2);
		circleList.add(circle);
		addChild(circle);
    }
    public void createCircleFromVoice(String text) {
    	Random r = new Random();
    	int angle = r.nextInt(360);
    	int radius = 212;

    	int num =  r.nextInt(6) + 2;
		float scale = num/10.0f;
		float posX = (float)(radius*Math.cos(angle));
		float posY = (float)(radius*Math.sin(angle));
		Circle circle = new Circle(text,scale,posX,posY);
		circle.setPosition(posX + winSize.width/2, posY + winSize.height/2);
		circleList.add(circle);
		addChild(circle);
    }
    
    public void setDbLevel(int db) {
    	if (db > 0 && db < 10) {
			dbLevel = 1;
		}
    	else if (db > 10 && db < 20) {
			dbLevel = 2;
		}
    	else if (db > 20) {
			dbLevel = 3;
		}else{
			dbLevel = 0;
		}
    }
    
    public void setInitState() {
    	microPhone.setVisible(true);
    	scanState.setVisible(false);
    	scanNotice.setVisible(false);
    	errorState.setVisible(false);
    	
    }
    
    public void setSpeakState() {
    	microPhone.setVisible(true);
    	scanState.setVisible(false);
    	scanNotice.setVisible(false);
    	errorState.setVisible(false);
    	microPhone.updateState(dbLevel);
    	
    }
    
    public void setScanState() {
    	microPhone.setVisible(false);
    	scanState.setVisible(true);
    	scanNotice.setVisible(true);
    	errorState.setVisible(false);
    	angle += 2;
    	scanState.setRotation(angle);
    }
    
    public void setOnResult(String text) {
    	ccMacros.CCLOG(LOG_TAG, text);
    	//setResultText(text);
    	createCircleFromVoice(text);
    }
    
    public void setErrorState() {
    	microPhone.setVisible(false);
    	scanState.setVisible(false);
    	scanNotice.setVisible(false);
    	errorState.setVisible(true);
    }
    
    
    //public void touchEnded()
    

    /*
    public void onPushScene(Object sender) {
        CCScene scene = CCScene.node();
        scene.addChild(new Layer2(), 0);
        CCDirector.sharedDirector().pushScene(scene);
    }

    public void onPushSceneTran(Object sender) {
        CCScene scene = CCScene.node();
        scene.addChild(new Layer2(), 0);
        CCDirector.sharedDirector().pushScene(CCSlideInTTransition.transition(1, scene));
    }
	*/
    public void onQuit(Object sender) {
        CCDirector.sharedDirector().popScene();
    }

    public void onVoid() {
    }
}
