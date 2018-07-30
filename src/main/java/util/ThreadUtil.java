package util;

public class ThreadUtil {

    public static void makeDelay(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
