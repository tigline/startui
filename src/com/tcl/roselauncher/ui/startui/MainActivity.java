package com.tcl.roselauncher.ui.startui;


import java.util.ArrayList;
import java.util.Random;

import org.cocos2d.config.ccMacros;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemImage;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	public static final String LOG_TAG = "StartUIActivity";
	private CCGLSurfaceView mSurfaceView;
	private static ArrayList<Circle> circleList ;
	private static String [] infoList = {"碟中谍5", "最新的电影","控制空调","购物","听歌", "关灯","新闻","电视剧"};
	private static StartLayer startLayer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mSurfaceView = new CCGLSurfaceView(this);
		
		setContentView(mSurfaceView);
		//setContentView(R.layout.activity_main);
		//applicationDidFinishLaunching(this, mSurfaceView);
		
	}

	public class HelloLayer extends CCLayer {
		public MicroPhone microPhone;
		public CCSprite scanState;
		public CCSprite backGroud;
		public CCSprite errorState;
		public CCSprite starSky;
		CGSize winSize = CCDirector.sharedDirector().winSize();
		Context curContext;
        public HelloLayer(Context context) {
        	  super();
        	  curContext = context;
        	  setIsTouchEnabled(true);
        	  circleList = new ArrayList<Circle>();
              CGPoint SCREEN_CENTER = CGPoint.ccp(winSize.width/2, winSize.height/2);
              
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
        	  //错误状态
        	  errorState = CCSprite.sprite("launcher/error.png");
        	  errorState.setPosition(SCREEN_CENTER);
        	  errorState.setVisible(false);
        	  addChild(errorState);
        	  
        	  CCMenuItem item = CCMenuItemImage.item("SendScoreButton.png", "SendScoreButtonPressed.png", this, "menuCallback");
        	  CCMenu menu = CCMenu.menu(item);
        	  menu.setAnchorPoint(1.0f, 0.0f);
        	  menu.setPosition(winSize.width-100, 20);
        	  addChild(menu);
        	  scheduleUpdate();
        	  
        	  
//            CCMenuItemFont item1 = CCMenuItemFont.item("Test pushScene", this, "onPushScene");
//            CCMenuItemFont item2 = CCMenuItemFont.item("Test pushScene w/transition", this, "onPushSceneTran");
//            CCMenuItemFont item3 = CCMenuItemFont.item("Quit", this, "onQuit");
//
//            CCMenu menu = CCMenu.menu(item1, item2, item3);
//            menu.alignItemsVertically();
//
//            addChild(menu);
        	  

        }
        public void menuCallback(Object sender) {
        	Intent intent = new Intent(curContext, PlayerActivity.class);
			startActivity(intent);
			//finish();
        }
        @SuppressLint("DefaultLocale")
		public void update(float dt) {
        	if (circleList.size() > 0) {
    			for (int i = 0; i < circleList.size(); i++) {
    				circleList.get(i).CheckCollision(circleList, dt);
    			}
    		}
		}
        @Override
        public boolean ccTouchesBegan(MotionEvent event) {
        	ccMacros.CCLOG(LOG_TAG, "touchBegan");
//            CGPoint convertedLocation = CCDirector.sharedDirector()
//            	.convertToGL(CGPoint.make(event.getX(), event.getY()));
            
            
            return true;
        }
        @Override
        public boolean ccTouchesEnded(MotionEvent event) {
//        	ccMacros.CCLOG(LOG_TAG, "touchEnded");
//        	CGPoint convertedLocation = CCDirector.sharedDirector()
//                	.convertToGL(CGPoint.make(event.getX(), event.getY()));
        	Random r = new Random();


        	int num =  r.nextInt(6) + 2;
			int index = r.nextInt(7);
			float scale = num/10.0f;
			Circle circle = new Circle(infoList[index],scale);
			
			circle.setPosition(winSize.width/2, winSize.height/2);
			
			circleList.add(circle);
			addChild(circle);
			ccMacros.CCLOG(LOG_TAG, "touchEnded");
			return true;
        	
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



    // CLASS IMPLEMENTATIONS
    public void applicationDidFinishLaunching(Context context, View view) {

        // attach the OpenGL view to a window
        CCDirector.sharedDirector().attachInView(view);

        // set landscape mode
        CCDirector.sharedDirector().setLandscape(true);

        // show FPS
        CCDirector.sharedDirector().setDisplayFPS(true);

        // frames per second
        CCDirector.sharedDirector().setAnimationInterval(1.0f / 60);

        CCScene scene = CCScene.node();
        
        scene.addChild(new HelloLayer(this), 0);

        // Make the Scene active
        CCDirector.sharedDirector().runWithScene(scene);

    }
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
        CCDirector.sharedDirector().setLandscape(true);
    }

    public void onDestroy() {
    	super.onDestroy();
    	
    	CCDirector.sharedDirector().end();
    }
	
}
