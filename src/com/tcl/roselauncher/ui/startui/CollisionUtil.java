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
	
	//��������ģ
	public static float mould(float[] vec)
	{
		return (float)Math.sqrt(vec[0]*vec[0]+vec[1]*vec[1]+vec[2]*vec[2]);
	}
	
	//�����������ļн�
	public static float angle(float[] vec1,float[] vec2)
	{
		//������
		float dp=dotProduct(vec1,vec2);
		//��������������ģ
		float m1=mould(vec1);
		float m2=mould(vec2);		
		float acos=dp/(m1*m2);
		
		//Ϊ�˱������������������
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
	
	//�����������ײ�������ķ���
	public static  boolean collision(Circle balla,Circle ballb)
	{
		
		//����ײֱ������B->A ��Ҳ��������������ײ�������������ߵ�������
		float BAx=balla.getPosition().x-ballb.getPosition().x;
		float BAy=balla.getPosition().y-ballb.getPosition().y;		
		
		//������������ģ
		float mvBA=mould(new float[]{BAx,0,BAy});	
		float BallA_R = balla.BALL_R;
		float BallB_R = balla.BALL_R;
		//��������������뾶��������û����ײ
		if(mvBA>(BallA_R + BallB_R))
		{

			return false;
		}		

		/*
		 * ���������ײ���㷨Ϊ��
		 * 1�����������������ߵ�����
		 * 2����ÿ������ٶȷֽ�Ϊƽ���봹ֱ��������������
		 * 3��������ȫ������ײ��֪ʶ����ײ��ƽ�����������ٶ����򽻻�����ֱ���������ٶȲ���
		 * 4��������װ��������ٶȼ���
		 * vax=vax��ֱ+vbxƽ��    vay=vay��ֱ+vbyƽ��    
		 * vbx=vbx��ֱ+vaxƽ��    vby=vby��ֱ+vayƽ��    
		 */
		
	    //�ֽ�B���ٶ�========================================begin=============	
		
		//��b����ٶȴ�С
		float vB=(float)Math.sqrt(ballb.vx*ballb.vx+ballb.vy*ballb.vy);
		//ƽ�з����Xy���ٶ�
		float vbCollx=0;
		float vbColly=0;
		//��ֱ�����Xy���ٶ�
		float vbVerticalX=0;
		float vbVerticaly=0;
		
		//�����ٶ�С����ֵ����Ϊ�ٶ�Ϊ�㣬���ý��зֽ������
		if(V_THRESHOLD<vB)
		{
			//��B����ٶȷ�����������ײֱ������B->A�ļн�(����)
			float bAngle=angle
			(
				new float[]{ballb.vx,0,ballb.vy},
			    new float[]{BAx,0,BAy}
			);
			
			//��B������ײ������ٶȴ�С
			float vbColl=vB*(float)Math.cos(bAngle);
			
			//��B������ײ������ٶ�����
			vbCollx=(vbColl/mvBA)*BAx;
			vbColly=(vbColl/mvBA)*BAy;
			
			//��B������ײ��ֱ������ٶ�����
			vbVerticalX=ballb.vx-vbCollx;
			vbVerticaly=ballb.vy-vbColly;
		}
		//�ֽ�B���ٶ�========================================end===============
		
		//�ֽ�A���ٶ�========================================begin=============	
		
		//��a����ٶȴ�С
		float vA=(float)Math.sqrt(balla.vx*balla.vx+balla.vy*balla.vy);
		//ƽ�з����Xy���ٶ�
		float vaCollx=0;
		float vaColly=0;
		//��ֱ�����Xy���ٶ�
		float vaVerticalX=0;
		float vaVerticaly=0;
		 
		//�����ٶ�С����ֵ����Ϊ�ٶ�Ϊ�㣬���ý��зֽ������
		if(V_THRESHOLD<vA)
		{
			//��A����ٶȷ�����������ײֱ������B->A�ļн�(����)
			float aAngle=angle
			(
				new float[]{balla.vx,0,balla.vy},
			    new float[]{BAx,0,BAy}
			);			
			
			//��A������ײ������ٶȴ�С
			float vaColl=vA*(float)Math.cos(aAngle);
			
			//��A������ײ������ٶ�����
			vaCollx=(vaColl/mvBA)*BAx;
			vaColly=(vaColl/mvBA)*BAy;
			
			//��A������ײ��ֱ������ٶ�����
			vaVerticalX=balla.vx-vaCollx;
			vaVerticaly=balla.vy-vaColly;
		}		
		//�ֽ�A���ٶ�========================================end===============
		
		//����ײ��AB����ٶ�
		//����˼��Ϊ��ֱ�����ٶȲ��䣬��ײ���������ٶȽ�������ֱ�����ٶȲ���
		balla.vx=vaVerticalX+vbCollx;
		balla.vy=vaVerticaly+vbColly;
		
		ballb.vx=vbVerticalX+vaCollx;
		ballb.vy=vbVerticaly+vaColly;		
		
		
		//��������������ײ������˶���������ת�ǶȲ���

		
		
		//========================================
		//�˴����ò���������ײ�����Ĵ���
		//�˴����ò���������ײ�����Ĵ���
		//�˴����ò���������ײ�����Ĵ���
		//========================================
		
		return true;
	}
}
