package cn.edu.ouc.util;


public class StepDetectionUtil {
	
	/**
	 * ����ϼ��ٶ�
	 * @param accel ������ٶ�
	 * @return
	 */
	public static float getMagnitudeOfAccel(float[] accel) {
		return (float) Math.sqrt(accel[0] * accel[0] + 
				accel[1] * accel[1] + 
				accel[2] * accel[2]);
	}
	
	/**
	 * ����ֲ�ƽ�����ٶ�
	 * @param magAccel �ϼ��ٶ�
	 * @param w �ֲ����ڴ�С
	 * @return
	 */
	public static float[] getLocalMeanAccel(float[] magAccel, int w) {
		int size = magAccel.length;
		float[] localMeanAccel = new float[size];
		float sum = 0;
		for(int i = 0; i < size; i++) {
			sum = magAccel[i];
			for(int j = 1; j <= w; j++) {
				int right = i + j; // ��ǰλ��i�Ҳ���±�
				int left = i - j; // ��ǰλ��i�����±�
				if(right >= size) { // ����Ҳ���±곬�����ڴ�С����������ڴ�С���ص�������ʼλ��
					right = right - size;
				}
				if(left < 0) { // ��������±�С�ڴ�����С�±��㣬����ϴ��ڴ�С���ص�����ĩβλ��
					left = size + left;
				}
				sum += magAccel[left] + magAccel[right];
			}
			localMeanAccel[i] = sum / ( 2 * w + 1);
		}
		return localMeanAccel;
	}
	
	/**
	 * ����ֲ�ƽ�����ٶȵ�ƽ��ֵ������ȷ���Ų��ж���������ֵ
	 * @param localMeanAccel �ֲ�ƽ�����ٶ�
	 * @return
	 */
	public static float getAverageLocalMeanAccel(float[] localMeanAccel) {
		float sum = 0;
		int size = localMeanAccel.length;
		for(int i = 0; i < size; i++) {
			sum += localMeanAccel[i];
		}
		return sum / size;
	}
	
	/**
	 * ����ֲ����ٶȷ���
	 * @param magAccel �ϼ��ٶ�
	 * @param localMeanAccel �ֲ�ƽ�����ٶ�
	 * @param w �ֲ����ڴ�С
	 * @return
	 */
	public static float[] getAccelVariance(float[] magAccel, float[] localMeanAccel, int w) {
		int size = magAccel.length;
		float[] accelVariance = new float[size];
		float sum = 0;
		for(int i = 0; i < size; i++) {
			sum = (float) Math.pow(magAccel[i] - localMeanAccel[i], 2);
			for(int j = 1; j <= w; j++) {
				int right = i + j; // ��ǰλ��i�Ҳ���±�
				int left = i - j; // ��ǰλ��i�����±�
				if(right >= size) { // ����Ҳ���±곬�����ڴ�С����������ڴ�С���ص�������ʼλ��
					right = right - size;
				}
				if(left < 0) { // ��������±�С�ڴ�����С�±��㣬����ϴ��ڴ�С���ص�����ĩβλ��
					left = size + left;
				}
				sum +=  Math.pow(magAccel[left] - localMeanAccel[left], 2)
						+ Math.pow(magAccel[right] - localMeanAccel[right], 2);
			}
			accelVariance[i] = sum / ( 2 * w + 1);
		}
		return accelVariance;
	}
	
	/**
	 * ����Ų��ж�����
	 * ����������ָ����ֵ���ж�������1��������0
	 * @param localMeanAccel �ֲ�ƽ�����ٶ�
	 * @param w �ֲ����ڴ�С
	 * @return
	 */
	public static int[] getCondition(float[] localMeanAccel, float threshold) {
		int size = localMeanAccel.length;
		int[] condition = new int[size];
		for(int i = 0; i < size; i++) {
			if(localMeanAccel[i] > threshold)
				condition[i] = 1;
			else condition[i] = 0;
		}
		return condition;
	}
	
	/**
	 * �ж�data�Ƿ�Ϊ1�����Ϊ1������true�����򷵻�false
	 */
	public static boolean isOne(int data) {
		if(data == 1)
			return true;
		else return false;
	}
}