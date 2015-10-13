/**
 * 
 */
package com.tcl.roselauncher.ui.startui;



import java.util.ArrayList;

import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import android.util.Log;





import static com.tcl.roselauncher.ui.startui.Constant.*;
/**
 * @Project ControlMenu	
 * @author houxb
 * @Date 2015-9-10
 */
public class Circle extends CCSprite {
	
	/**
	 * @param string
	 */
	
	public float ACC = 0.5f; //���ٶ�
	public float SPEED;		//
	public float MASS = 10;
	public float COF = 0.05f;
	public float vx = 0 ;  //X �����ٶ�
	public float vy = 0 ;
	public float BALL_R;
	public float xOffset;
	public float yOffset;
	
	//private static  Circle circle;
	public Circle(String text, float scale) {
		super("launcher/circle4.png");
		
		CGRect rect = getBoundingBox();
		CCLabel content = CCLabel.makeLabel( text, "fangzheng.ttf", 36);

		setAnchorPoint(CGPoint.ccp(0.5f, 0.5f));		
		//content.setPosition(getContentSiye().width*scale/2, getContentSiye().height*scale/2);		
		content.setPosition(getContentSize().width/2, getContentSize().height/2);
		Log.e("Circle point", CGRect.width(rect)/2 +" and "+CGRect.height(rect)/2);
		addChild(content);
		setScale(scale);
		this.setBallR(STANDA_BAll_R * scale/2);
		
//		if ((MathUtils.random(Integer.MAX_VALUE -1) % 100 - 1)%2 == 0) {
//			vx = 450.0f;
//		}else{
//			vx = -450.0f;
//		}
//		if ((MathUtils.random(Integer.MAX_VALUE -1) % 200 - 101)%2 == 0) {
//			vy = -300.0f;
//		}
		vx =  (float) (Math.random()*(Integer.MAX_VALUE -1) % 1820.0f - 910.0f);
		vy =  (float) (Math.random()*(Integer.MAX_VALUE -1) % 980.0f - 490.0f);
		//setTouchEnabled(true);
		//setTouchMode(CCTouchMode.OneByOne);
		//registerWithTouchDispatcher();

	}
	
	public void setBallR(float ballR){
		this.BALL_R = ballR;
	}
	
	public float getBallR(){
		return this.BALL_R;
	}
	
//	public CCRect getBoundingBox(CCSprite sprite, float scale){
//		
//		float spriteW = sprite.getContentSize().width;
//		float spriteH = sprite.getContentSize().height;
//		float scaleW = sprite.getContentSize().width * scale;
//		float scaleH = sprite.getContentSize().height * scale;
//		
//		CCRect m_obRect = new CCRect(
//				sprite.getPosition().x - spriteW + (spriteW - scaleW)/2,
//				sprite.getPosition().y - spriteH + (spriteH - scaleH)/2,
//				sprite.getContentSize().width*scale,
//				sprite.getContentSize().height*scale);
//		
//		return m_obRect;
//	}
	
	public void TranslateTo(float dt){		
		xOffset = getPosition().x + vx*dt;
		yOffset = getPosition().y + vy*dt;
		this.setPosition(xOffset, yOffset);
		vx = vx * V_TENUATION;
		vy = vy * V_TENUATION;
	}
	
	public void CheckCollision(ArrayList<Circle> circleList, float dt){

		for (int i = 0; i < circleList.size(); i++) {
			if (this != circleList.get(i)) {					
				CollisionUtil.collision(this,circleList.get(i));
				
			}
		}

		if(this.yOffset< (BALL_R + DIS_OFFSET)||this.yOffset>(SCREEN_HEIGHT - BALL_R - DIS_OFFSET))//��Χ
		{
			//���󵲰���ҵ��壬y���ٶ��÷�
			this.vy=-this.vy;
			//flag=true;
//			Log.d("coll 1"+texId,"x="+this.xOffset+",y="+this.yOffset+", vx="+this.vx+",vy="+this.vy);
		}
		if(this.xOffset< (DIS_OFFSET + BALL_R)||this.xOffset>(SCREEN_WIDTH - BALL_R - DIS_OFFSET))//��Χ
		{
			//��ǰ�����󵲰壬X���ٶ��÷�
			this.vx=-this.vx;
			//flag=true;
//			Log.d("coll 2"+texId,"x="+this.xOffset+",y="+this.yOffset+", vx="+this.vx+",vy="+this.vy);
		}
		
		this.TranslateTo(dt);
	
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
