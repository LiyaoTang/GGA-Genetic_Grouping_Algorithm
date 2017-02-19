package com.fay.util;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public final class Timer {
	/** ��ǰʱ��**/
    static int time = 0;

 //------------------------------֮ǰ�汾------------------
    
    static Set<Integer> triggers = new TreeSet<Integer>();
    public static void addTrigger(int t) {
        if (t > 0) triggers.add(t);
    }
    
    public static void resetTimer() {//����ʱ��
        time = 0;
        triggers.clear();
    }

    public static void stepTimer() {//ʱ�Ӳ���
        if (triggers.size() > 0) {
            int nextTime = Collections.min(triggers);
            time = nextTime;
            triggers.remove(new Integer(nextTime));
        }
        else {
            time++;
        }
    }

    /** ��ȡ��ǰʱ�� */
    public static int currentTime() {
        return time;
    }
    
    /** ������ʱ�� */
    public static void startTimer() {
        time = 0;
    }
    
    /**
     * ʱ�Ӳ���
     * 
     * @param step
     *            ʱ�䲽��
     */
    public static void stepTimer(int step) {
        time += step;
    }

	public static void printTrigger() {
		// TODO Auto-generated method stub
		/**transform the Set To Array **/
		Integer[] TimeResult=(Integer[])triggers.toArray(new Integer[triggers.size()]);
		StringBuilder  infoBuilder = new StringBuilder();
		for (int i = 0; i < TimeResult.length; i++) {
			infoBuilder.append(TimeResult[i]);
			infoBuilder.append(",");
		}
		System.out.println(infoBuilder.toString());
	}
}