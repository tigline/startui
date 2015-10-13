/**
 * 
 */
package com.tcl.roselauncher.ui.startui;



import java.util.ArrayList;

import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import static com.tcl.roselauncher.ui.startui.Constant.*;
/**
 * @Project ControlMenu	
 * @author houxb
 * @Date 2015-9-10
 * 内容泡泡类
 */
public class Circle extends CCSprite {
	
	/**
	 * @param string
	 */
	public boolean runFlag = true;
	public float ACC = 0.5f; //
	public float SPEED;		//
	public float MASS = 10;
	public float COF = 0.05f;
	public float vx = 0 ;  //
	public float vy = 0 ;
	public float BALL_R;
	public float xOffset;
	public float yOffset;
	public float toScale;
	public float crScale;
	public MicroPhone micPhone;
	//private static  Circle circle;
	public Circle(String text, float scale ,float posX, float posY ) {
		super("launcher/circle4.png");
		toScale = scale;
//		CGRect rect = getBoundingBox();
		CCLabel content = CCLabel.makeLabel( text, "fangzheng.ttf", 36);
		setAnchorPoint(CGPoint.ccp(0.5f, 0.5f));		
//		content.setPosition(getContentSiye().width*scale/2, getContentSiye().height*scale/2);		
		content.setPosition(getContentSize().width/2, getContentSize().height/2);
//		Log.e("Circle point", CGRect.width(rect)/2 +" and "+CGRect.height(rect)/2);
		addChild(content);
		if (1.0f == scale) {
			runFlag = false;
			setScale(scale);
			this.setBallR(STANDA_BAll_R/2);
		}else{
			setScale(0.001f);
			this.setBallR(STANDA_BAll_R * 0.001f/2);
		}
		vx = - posX*1.5f;
		vy = - posY*1.5f;		

	}
	
	public void setBallR(float ballR){
		this.BALL_R = ballR;
	}
	
	public float getBallR(){
		return this.BALL_R;
	}
	
	
	public void TranslateTo(float dt){	
		
		xOffset = getPosition().x + vx*dt;
		yOffset = getPosition().y + vy*dt;
		this.setPosition(xOffset, yOffset);
		vx = vx * V_TENUATION;
		vy = vy * V_TENUATION;
		crScale += dt;
		if (crScale < toScale) {
			setScale(crScale);
			this.setBallR(STANDA_BAll_R * crScale/2);
		}
	}
	
	public void CheckCollision(ArrayList<Circle> circleList, float dt){

		for (int i = 0; i < circleList.size(); i++) {
			if (this != circleList.get(i)) {					
				CollisionUtil.collision(circleList.get(i),this);
				
				//CollisionUtil.collisionS(this, micPhone);
			}
		}

		//麦克风动画    初始化8个球    逻辑 只出现 几个球 如何消失   30秒内 随机出现一个球     对外的接口   切换返回问题
		if(this.yOffset< (BALL_R )||this.yOffset>(SCREEN_HEIGHT - BALL_R))//外围
		{
			//碰左挡板或右挡板，y向速度置反
			this.vy=-this.vy;
			//flag=true;
//			Log.d("coll 1"+texId,"x="+this.xOffset+",y="+this.yOffset+", vx="+this.vx+",vy="+this.vy);
		}else if(this.yOffset== BALL_R ||this.yOffset==(SCREEN_HEIGHT - BALL_R)){
			circleList.remove(this);
			this.removeSelf();
			this.removeFromParentAndCleanup(true);
		}

		if(this.xOffset< (DIS_OFFSET + BALL_R)||this.xOffset>(SCREEN_WIDTH - BALL_R - DIS_OFFSET))//外围
		{
			//碰前挡板或后挡板，X向速度置反
			this.vx=-this.vx;
			//flag=true;
//			Log.d("coll 2"+texId,"x="+this.xOffset+",y="+this.yOffset+", vx="+this.vx+",vy="+this.vy);
		}else if(this.xOffset==BALL_R||this.xOffset==(SCREEN_WIDTH - BALL_R)){
			circleList.remove(this);
			this.removeSelf();
			this.removeFromParentAndCleanup(true);
		}
		if (runFlag) {
			this.TranslateTo(dt);
		}
		
	
	}
	
    /*
	public static Circle getInstance(String text, float scale)
	{
		if (null == circle)
		{
			circle = new Circle(text,scale);
		}
		return circle;
	} 
	
	@Override
	public Boolean touchBegan(CCTouch pTouch)
	{	
		Log.e("Circle", "touchBegan" );
		//float x = pTouch.getLocation().x;
		//float y = pTouch.getLocation().y;

		//setPosition(x, y);
		return true;
	}

	@Override
	public void touchMoved(CCTouch pTouch)
	{
		float x = pTouch.getLocation().x;
		float y = pTouch.getLocation().y;
		setPosition(x, y);
	}

	@Override
	public void touchEnded(CCTouch pTouch)
	{
		float x = pTouch.getLocation().x;
		float y = pTouch.getLocation().y;
		//setPosition(x, y);
	}
	*/
//	public Circle(String image){
//
//		//sprite = new CCSprite(image);
//		//CCLabelTTF content = new CCLabelTTF(textTure, "fangyheng.ttf", 24);
//		//content.setPosition(halo.getContentSize().width/2.5f, halo.getContentSize().height/2.5f);
//		//halo.addChild(content);
//		//sprite.setPosition(x, y);
//
//	}
	/*
	public static Circle getInstance(String image)
	{
		if (null == circle)
		{
			circle = new Circle(image);
		}
		return circle;
	}
	

	/*
	public void setLocation(float x, float y){
		this.x = x;
		this.y = y;
		circle.setPosition(x, y);
	}
	
	public static Circle getInstance()
	{
		if (null == parse)
		{
			parse = new Circle();
		}

		return parse;
	}
	
	
	
	
	public CCPoint getPositon(){
		CCPoint point = new CCPoint(x, y);
		return point;
	}
	*/
	
}
