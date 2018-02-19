package pfq.store.config;

import pfq.store.AppSettings;
import pfq.store.AppUtil;

public class StartState implements State {

	public void doAction(ContextStateApp context) {
		System.out.println("Start application");
		String host = AppSettings.get("host");
		int    port = AppSettings.getInt("port", 5);
		if(AppUtil.pingHost(host, port, 5)) {
			context.setState(context.getState("auth"));
		}else {
			//System.out.println("Host not found");
			context.setState(context.getState("conf"));
			}
		context.pull();
	}

	public String toString() {
		return "Start State";
	}
}
