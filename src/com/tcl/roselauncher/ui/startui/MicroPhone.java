/**
 * 
 */
package com.tcl.roselauncher.ui.startui;


import org.cocos2d.nodes.CCSprite;
//import static com.tcl.roselauncher.ui.startui.Constant.*;
/**
 * @Project ControlMenu	
 * @author houxb
 * @Date 2015-9-18
 */
public class MicroPhone extends CCSprite {

	

	public CCSprite circle1; // circle2, circle3, circleBig, mic;
	public CCSprite circle2;
	public CCSprite circle3;
	public CCSprite mic;
	public float vx = 0;
	public float vy = 0;
	public static int countF = 0;
	public static int countS = 0;
	public static int countT = 0;
	public float BALL_R;
	public MicroPhone(){
		super("launcher/circle4.png");

		circle3 = new CCSprite("launcher/circle3.png");
		circle3.setPosition(this.getContentSize().width/2, this.getContentSize().height/2);
		addChild(circle3);
		
		circle2 = new CCSprite("launcher/circle2.png");
		circle2.setPosition(this.getContentSize().width/2, this.getContentSize().height/2);
		addChild(circle2);
		
		circle1 = new CCSprite("launcher/circle1.png");
		circle1.setPosition(this.getContentSize().width/2, this.getContentSize().height/2);		
		addChild(circle1);

		mic = new CCSprite("launcher/mic.png");
		mic.setPosition(this.getContentSize().width/2, this.getContentSize().height/2);
		addChild(mic);
		this.BALL_R = this.getContentSize().width/2 - 20.0f;
		
	}
	
	public void setVisibleValue(boolean value){
		this.setVisible(value);
		circle3.setVisible(value);
		circle2.setVisible(value);
		circle1.setVisible(value);
		mic.setVisible(value);
	}
	
	public void updateState(int level){

		if (1 == level) {
			//circle1.runAction()
			circle2.setVisible(false);
			circle3.setVisible(false);
			if (countF++ < 20) {
				circle1.setVisible(true);
			}else if(countF > 20 && countF < 40){
				circle1.setVisible(false);		
			}else{
				countF = 0;
			}
			
		}
		else if(2 == level) {
			circle3.setVisible(false);
			if (countS++ < 15) {
				circle1.setVisible(true);
				circle2.setVisible(false);
			}else if(countS > 15 && countS < 30){
				circle1.setVisible(false);
				circle2.setVisible(true);
			}else{
				countS = 0;
			}
		}
		else if(3 == level) {

			if (countT++ < 10) {
				circle1.setVisible(true);
				circle2.setVisible(false);
				circle3.setVisible(false);
			}else if(countT > 10 && countT < 30){
				if(countT > 20){
					circle3.setVisible(true);
					circle1.setVisible(false);
					circle2.setVisible(false);	
				}else{
					circle2.setVisible(true);
					circle1.setVisible(false);				
					circle3.setVisible(false);
				}	
			}else{
				countT = 0;
			}
		}
		else if(0 == level){
			circle1.setVisible(true);
			circle2.setVisible(true);
			circle3.setVisible(true);
		}
//		if (count++ < level) {
//			circle1.setVisible(false);
//			circle2.setVisible(true);
//			circle3.setVisible(false);
//		}else if(count > level && count < 2*level){
//			circle1.setVisible(true);
//			circle2.setVisible(false);
//			circle3.setVisible(true);			
//		}else{
//			count = 0;
//		}

	}
	
	
	
	
	
	
	
}
