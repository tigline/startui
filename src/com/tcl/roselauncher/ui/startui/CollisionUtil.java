/**
 * 
 */
package com.tcl.roselauncher.ui.startui;


import static com.tcl.roselauncher.ui.startui.Constant.*;




/**
 * @Project ControlMenu	
 * @author houxb
 * @Date 2015-9-16
 */
public class CollisionUtil {
	
	
	public static float dotProduct(float[] vec1,float[] vec2)
	{
		return
			vec1[0]*vec2[0]+
			vec1[1]*vec2[1]+
			vec1[2]*vec2[2];
		
	}   
	
	//求向量的模
	public static float mould(float[] vec)
	{
		return (float)Math.sqrt(vec[0]*vec[0]+vec[1]*vec[1]+vec[2]*vec[2]);
	}
	
	//求两个向量的夹角
	public static float angle(float[] vec1,float[] vec2)
	{
		//先求点积
		float dp=dotProduct(vec1,vec2);
		//再求两个向量的模
		float m1=mould(vec1);
		float m2=mould(vec2);		
		float acos=dp/(m1*m2);
		
		//为了避免计算误差带来的问题
		if(acos>1)
		{
			acos=1;
		}
		else if(acos<-1)
		{
			acos=-1;
		}
			
		return (float)Math.acos(acos);
	}
	
	//两个球进行碰撞物理计算的方法
	public static  boolean collisionS(Circle balla,MicroPhone ballb)
	{
		
		//求碰撞直线向量B->A （也就是两个参与碰撞的桌球球心连线的向量）
		float BAx=balla.getPosition().x-ballb.getPosition().x;
		float BAy=balla.getPosition().y-ballb.getPosition().y;		
		
		//求上述向量的模
		float mvBA=mould(new float[]{BAx,0,BAy});	
		float BallA_R = balla.BALL_R;
		float BallB_R = ballb.BALL_R;
		//若两球距离大于球半径的两倍则没有碰撞
		if(mvBA>(BallA_R + BallB_R))
		{

			return false;
		}		

		/*
		 * 两球进行碰撞的算法为：
		 * 1、计算两球球心连线的向量
		 * 2、将每个球的速度分解为平行与垂直此向量的两部分
		 * 3、根据完全弹性碰撞的知识，碰撞后平行于向量的速度两球交换，垂直于向量的速度不变
		 * 4、重新组装两球的新速度即：
		 * vax=vax垂直+vbx平行    vay=vay垂直+vby平行    
		 * vbx=vbx垂直+vax平行    vby=vby垂直+vay平行    
		 */
		
	    //分解B球速度========================================begin=============	
//		
//		//求b球的速度大小
//		float vB=(float)Math.sqrt(ballb.vx*ballb.vx+ballb.vy*ballb.vy);
//		//平行方向的Xy分速度
//		float vbCollx=0;
//		float vbColly=0;
//		//垂直方向的Xy分速度
//		float vbVerticalX=0;
//		float vbVerticaly=0;
//		
//		//若球速度小于阈值则认为速度为零，不用进行分解计算了
//		if(V_THRESHOLD<vB)
//		{
//			//求B球的速度方向向量与碰撞直线向量B->A的夹角(弧度)
//			float bAngle=angle
//			(
//				new float[]{ballb.vx,0,ballb.vy},
//			    new float[]{BAx,0,BAy}
//			);
//			
//			//求B球在碰撞方向的速度大小
//			float vbColl=vB*(float)Math.cos(bAngle);
//			
//			//求B球在碰撞方向的速度向量
//			vbCollx=(vbColl/mvBA)*BAx;
//			vbColly=(vbColl/mvBA)*BAy;
//			
//			//求B球在碰撞垂直方向的速度向量
//			vbVerticalX=ballb.vx-vbCollx;
//			vbVerticaly=ballb.vy-vbColly;
//		}
//		//分解B球速度========================================end===============
//		
		//分解A球速度========================================begin=============	
		
		//求a球的速度大小
		float vA=(float)Math.sqrt(balla.vx*balla.vx+balla.vy*balla.vy);
		//平行方向的Xy分速度
		float vaCollx=0;
		float vaColly=0;
		//垂直方向的Xy分速度
		float vaVerticalX=0;
		float vaVerticaly=0;
		 
		//若球速度小于阈值则认为速度为零，不用进行分解计算了
		if(V_THRESHOLD<vA)
		{
			//求A球的速度方向向量与碰撞直线向量B->A的夹角(弧度)
			float aAngle=angle
			(
				new float[]{balla.vx,0,balla.vy},
			    new float[]{BAx,0,BAy}
			);			
			
			//求A球在碰撞方向的速度大小
			float vaColl=vA*(float)Math.cos(aAngle);
			
			//求A球在碰撞方向的速度向量
			vaCollx=(vaColl/mvBA)*BAx;
			vaColly=(vaColl/mvBA)*BAy;
			
			//求A球在碰撞垂直方向的速度向量
//			vaVerticalX=-balla.vx-vaCollx;
//			vaVerticaly=-balla.vy-vaColly;
			vaVerticalX=-balla.vx;
			vaVerticaly=-balla.vy;
		}		
		//分解A球速度========================================end===============
		
		//求碰撞后AB球的速度
		//基本思想为垂直方向速度不变，碰撞方向两球速度交换，垂直方向速度不变
//		balla.vx=vaVerticalX+vbCollx;
//		balla.vy=vaVerticaly+vbColly;
		balla.vx= vaVerticalX;
		balla.vy= vaVerticaly;
		
//		ballb.vx=vbVerticalX+vaCollx;
//		ballb.vy=vbVerticaly+vaColly;	
		
		
		return true;
	}
	public static  boolean collision(Circle balla,Circle ballb)
	{
		
		//求碰撞直线向量B->A （也就是两个参与碰撞的桌球球心连线的向量）
		float BAx=balla.getPosition().x-ballb.getPosition().x;
		float BAy=balla.getPosition().y-ballb.getPosition().y;		
		
		//求上述向量的模
		float mvBA=mould(new float[]{BAx,0,BAy});	
		float BallA_R = balla.BALL_R;
		float BallB_R = ballb.BALL_R;
		//若两球距离大于球半径的两倍则没有碰撞
		if(mvBA>(BallA_R + BallB_R))
		{

			return false;
		}		

		/*
		 * 两球进行碰撞的算法为：
		 * 1、计算两球球心连线的向量
		 * 2、将每个球的速度分解为平行与垂直此向量的两部分
		 * 3、根据完全弹性碰撞的知识，碰撞后平行于向量的速度两球交换，垂直于向量的速度不变
		 * 4、重新组装两球的新速度即：
		 * vax=vax垂直+vbx平行    vay=vay垂直+vby平行    
		 * vbx=vbx垂直+vax平行    vby=vby垂直+vay平行    
		 */
		
	    //分解B球速度========================================begin=============	
		
		//求b球的速度大小
		float vB=(float)Math.sqrt(ballb.vx*ballb.vx+ballb.vy*ballb.vy);
		//平行方向的Xy分速度
		float vbCollx=0;
		float vbColly=0;
		//垂直方向的Xy分速度
		float vbVerticalX=0;
		float vbVerticaly=0;
		
		//若球速度小于阈值则认为速度为零，不用进行分解计算了
		if(V_THRESHOLD<vB)
		{
			//求B球的速度方向向量与碰撞直线向量B->A的夹角(弧度)
			float bAngle=angle
			(
				new float[]{ballb.vx,0,ballb.vy},
			    new float[]{BAx,0,BAy}
			);
			
			//求B球在碰撞方向的速度大小
			float vbColl=vB*(float)Math.cos(bAngle);
			
			//求B球在碰撞方向的速度向量
			vbCollx=(vbColl/mvBA)*BAx;
			vbColly=(vbColl/mvBA)*BAy;
			
			//求B球在碰撞垂直方向的速度向量
			vbVerticalX=ballb.vx-vbCollx;
			vbVerticaly=ballb.vy-vbColly;
		}
		//分解B球速度========================================end===============
		
		//分解A球速度========================================begin=============	
		
		//求a球的速度大小
		float vA=(float)Math.sqrt(balla.vx*balla.vx+balla.vy*balla.vy);
		//平行方向的Xy分速度
		float vaCollx=0;
		float vaColly=0;
		//垂直方向的Xy分速度
		float vaVerticalX=0;
		float vaVerticaly=0;
		 
		//若球速度小于阈值则认为速度为零，不用进行分解计算了
		if(V_THRESHOLD<vA)
		{
			//求A球的速度方向向量与碰撞直线向量B->A的夹角(弧度)
			float aAngle=angle
			(
				new float[]{balla.vx,0,balla.vy},
			    new float[]{BAx,0,BAy}
			);			
			
			//求A球在碰撞方向的速度大小
			float vaColl=vA*(float)Math.cos(aAngle);
			
			//求A球在碰撞方向的速度向量
			vaCollx=(vaColl/mvBA)*BAx;
			vaColly=(vaColl/mvBA)*BAy;
			
			//求A球在碰撞垂直方向的速度向量
			vaVerticalX=balla.vx-vaCollx;
			vaVerticaly=balla.vy-vaColly;
		}		
		//分解A球速度========================================end===============
		
		//求碰撞后AB球的速度
		//基本思想为垂直方向速度不变，碰撞方向两球速度交换，垂直方向速度不变
		balla.vx=vaVerticalX+vbCollx;
		balla.vy=vaVerticaly+vbColly;
		
		ballb.vx=vbVerticalX+vaCollx;
		ballb.vy=vbVerticaly+vaColly;	
		
		
		return true;
	}
}
