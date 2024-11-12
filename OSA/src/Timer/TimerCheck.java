package Timer;
import java.util.TimerTask;

import application.OnlineShopApp;

import java.util.Timer;

//Class to check at specific times if ther are products with quantity = 0 and not deleted yet. 
public class TimerCheck {

    public static void main(String[] args) {
        Timer timer = new Timer();

        // Schedule the task to run every specific time interval (e.g., every 5 seconds)
        timer.schedule(new checkDeleteApps(), 0, 1000);
    }

    static class checkDeleteApps extends TimerTask {
    	
        @Override
        public void run() {
        	// calling the noStock function
        	OnlineShopApp.getInstance().noStock();
        }
    }

    
}
