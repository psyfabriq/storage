package pfq.store;

import org.controlsfx.control.Notifications;

import javafx.geometry.Pos;
import javafx.util.Duration;

public class Notification {
	private static Notification SINGLETON;
	private static Duration duration;
	private static Pos      position;
	private Notification() {
		this.duration = Duration.seconds(5);
		this.position = Pos.TOP_RIGHT;
	}
	
	static {
		SINGLETON = new Notification();
	}
	
	public static void error(String text) {
		Notifications notificationBuilder = Notifications.create()
                .title("ERROR")
                .text(text)
                .graphic(null)
                .hideAfter(duration)
                .position(position);
       notificationBuilder.showError();
	}
	
	public static void success(String text) {
		Notifications notificationBuilder = Notifications.create()
                .title("SUCCESS")
                .text(text)
                .graphic(null)
                .hideAfter(duration)
                .position(position);
       notificationBuilder.showConfirm();
	}
	
	public static void info(String text) {
		
	}
	
	public static void warning(String text) {
		
	}
}
