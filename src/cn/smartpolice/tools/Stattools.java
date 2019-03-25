package cn.smartpolice.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("statTool")
@Transactional(readOnly = false)
public class Stattools {

	/*
	 * ��ȡ�ض���ʽ�ĵ�ǰ��׼ʱ�䣬����ͼ�����ݴ���
	 * ���� int[3][3]
	 * 
	 * */
	public int[][] getNowtimeForChart(){
		//��ǰʱ���׼	
		//��ǰʱ��
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String[] nowtime1=df.format(new Date()).split(" ");//�ָ�ʱ��
		String[] nowtime0=nowtime1[0].split("-");//���������
		
		//�õ�����һ
		SimpleDateFormat df1=new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
		 day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 1);
		String[] monday=df1.format(c.getTime()).split("-");//����һ�����ڸ�ʽyyyy-MM-dd
		//��ñ����� 
		Calendar c1 = Calendar.getInstance();
		int day_of_week1 = c1.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week1 == 0){
			day_of_week1 = 7;
		}
		c1.add(Calendar.DATE, -day_of_week1 + 7);
		String[] sunday=df1.format(c1.getTime()).split("-");//�����գ����ڸ�ʽyyyy-MM-dd
		
		int[][] nowtime=new int[3][3];//���ڣ���һ�б��գ��ڶ��б���һ������������
		for(int ii=0;ii<3;ii++){
			nowtime[0][ii]=Integer.parseInt(nowtime0[ii]);
			nowtime[1][ii]=Integer.parseInt(monday[ii]);
			nowtime[2][ii]=Integer.parseInt(sunday[ii]);
		}	
		return nowtime;

	}
	
	/*
	 * ��ȡ�ض���ʽ�����ա����ܱ�׼ʱ�䣬����ͼ�����ݴ���
	 * 
	 * */
	public int[][] getLasttimeForChart(){
		
		//�õ�����
		SimpleDateFormat df= new SimpleDateFormat( "yyyy-MM-dd");
		Calendar  cal  =  Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday =df.format(cal.getTime());
		
		
		String[] yesterdaytime=yesterday.split("-");//���������
		
		//�õ����������ڼ�
		Calendar cal0 = Calendar.getInstance();
		cal0.setTime(new Date());
		int todayOfWeek=cal0.get(Calendar.DAY_OF_WEEK)-1;//���������ڼ�����������0������һ��1�� 
		
		
		//�������һ 
         Calendar cal1 = Calendar.getInstance();
         if(todayOfWeek==0){
        	 cal1.add(Calendar.DATE, -14);//�������ڽ��յĻ����ϵ���14��
         }else{
        	 cal1.add(Calendar.DATE, -7);//�������ڽ��յĻ����ϵ���7��
         }
         cal1.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
         String[] lastMonday= df.format(cal1.getTime()).split("-");
         
         
		//���������   
         Calendar cal2 = Calendar.getInstance();
         if(todayOfWeek==0){
        	 cal2.add(Calendar.DATE, -7);
         }else{
        	 cal2.add(Calendar.DATE, 0);
         }
        
         cal2.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
         String[] lastSunday=df.format(cal2.getTime()).split("-");    
         
         
         int[][] lasttime=new int[3][3];//���ڣ���һ���գ��ڶ�����һ����������
 		 for(int i=0;i<3;i++){
 			lasttime[0][i]=Integer.parseInt(yesterdaytime[i]);
 			lasttime[1][i]=Integer.parseInt(lastMonday[i]);
 			lasttime[2][i]=Integer.parseInt(lastSunday[i]);
 		 }	
 		return lasttime;
			
	}
	
	
	
	
	/*
	 * ����ע���ύʱ�䣬��ñ�׼��ʽ����
	 * ���� int[4]
	 * 
	 * */
	public int[] getSubtimeForChart(Date st){
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String[] st1=df.format(st).split(" ");
		String[] st2=st1[0].split("-");
		String[] st3=st1[1].split(":");
		int[] subtime=new int[5];//�ꡢ�¡��ա��ܼ���Сʱ
		SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
		String t=dateFm.format(st);//���ڼ�
		if(t.equals("����һ")){
			subtime[3]=1;
		}else if(t.equals("���ڶ�")){
			subtime[3]=2;
		}else if(t.equals("������")){
			subtime[3]=3;
		}else if(t.equals("������")){
			subtime[3]=4;
		}else if(t.equals("������")){
			subtime[3]=5;
		}else if(t.equals("������")){
			subtime[3]=6;
		}else if(t.equals("������")){
			subtime[3]=7;
		}			
		subtime[0]=Integer.parseInt(st2[0]);//��
		subtime[1]=Integer.parseInt(st2[1]);//��
		subtime[2]=Integer.parseInt(st2[2]);//��
		subtime[4]=Integer.parseInt(st3[0]);//Сʱ
		
		return subtime;	
	}
	
	/*
	 * ���������ͳ��
	 * 
	 * */
	public int getCheckingNum(int n,int state){
		
		if(state==0){
			n++;
		}
		return n;		
	}
	
	/*
	 * ͳ�Ʊ�������
	 * ������ǰͳ�����ݡ�����ʱ�䡢�ύʱ��
	 * ���ش�������ͳ������int[] data
	 * 
	 * */
	public int[] getDayChartData(int[] data,int[][] nowtime,int[] subtime){
		
		if (subtime[0] == nowtime[0][0] && subtime[1] == nowtime[0][1]
				&& subtime[2] == nowtime[0][2]) {// �Ǳ���
			// ��ʱ���
			// data[0][0]=0;
			if (subtime[4] <= 4) {
				data[1]++;
			} else if (subtime[4] <= 8) {
				data[2]++;
			} else if (subtime[4] <= 12) {
				data[3]++;
			} else if (subtime[4] <= 16) {
				data[4]++;
			} else if (subtime[4] <= 20) {
				data[5]++;
			} else if (subtime[4] <= 24) {
				data[6]++;
			} else {
				// �쳣����
				System.out.println("�쳣���ݣ�����");
			}
		} else {
			// System.out.println("�Ǳ���");
		}
		return data;
		
	}
	
	/*
	 * ͳ�Ʊ��ܵ�����
	 * 
	 * 
	 * */
	
	public int[] getWeekChartData(int[] data,int[][] nowtime,int[] subtime){
		
		if (subtime[0] >= nowtime[1][0] && subtime[1] >= nowtime[1][1]
				&& subtime[2] >= nowtime[1][2]) {
			if (subtime[0] <= nowtime[2][0]
					&& subtime[1] <= nowtime[2][1]
					&& subtime[2] <= nowtime[2][2]) {
				switch(subtime[3]){
					case 1:data[0]++;break;
					case 2:data[1]++;break;
					case 3:data[2]++;break;
					case 4:data[3]++;break;
					case 5:data[4]++;break;
					case 6:data[5]++;break;
					case 7:data[6]++;break;
				}		
			} else {
				// �Ǳ���
			}
		} else {
			// �Ǳ���
		}
		return data;	
	}
	
	/*
	 * ͳ�Ʊ�������
	 * 
	 * */
	
	public int[] getMonthChartData(int[] data,int[][] nowtime,int[] subtime){
		
		if (subtime[0] == nowtime[0][0] && subtime[1] == nowtime[0][1]) {
			if (subtime[2] <= 5) {
				data[0]++;
			} else if (subtime[2] <= 10) {
				data[1]++;
			} else if (subtime[2] <= 15) {
				data[2]++;
			} else if (subtime[2] <= 20) {
				data[3]++;
			} else if (subtime[2] <= 25) {
				data[4]++;
			} else if (subtime[2] <= 30) {
				data[5]++;
			} else if (subtime[2] <= 31) {
				data[6]++;
			} else {
				// �쳣����
			}
		} else {
			// no this month
		}
		return data;
	}
	
	/*
	 * ��ע����
	 * 
	 * */
	public int getTotalNum(List list){
	
		return list.size();
		
		
	}
	
	/*
	 * ����ע����
	 * 
	 * 
	 * */
	public int getLastmonthNum(int lastmonNum,int[][] nowtime,int[] subtime){
		
		 if (nowtime[0][1] == 1 && subtime[0] == nowtime[0][0] - 1
		 && subtime[1] == 12) {// ������һ�£��ύ�����ȥ�꣬�ύ�·���12��
		 lastmonNum++;
		 } else if (subtime[0] == nowtime[0][0]
		 && subtime[1] == nowtime[0][1] - 1) {// ���ڷ�һ�£��ύ����ǽ��꣬�ύ�·�������
		 lastmonNum++;
		 } else {
		 System.out.println("������Ҫ������");
		
		 }
		
		 return lastmonNum;
	}
	
	/*
	 * �������������
	 * 
	 * */
	public int[] getRelativeDayGrowth(int[] lastday,int[][] nowtime,int[] subtime){
		return null;
		
		
		
	}
	
}
