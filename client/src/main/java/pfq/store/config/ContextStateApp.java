package pfq.store.config;

import java.util.HashMap;

import pfq.store.DisplayManager;
import pfq.store.service.ConnettionService;

public class ContextStateApp {
	private State state;
	private DisplayManager stateManager;
	public ConnettionService connettionService;


	private HashMap<String,State> list = new HashMap<String,State>();

	public ContextStateApp(DisplayManager stateManager) {
		list.put("start", new StartState());
		list.put("stop",  new StopState());
		list.put("auth",  new AuthorizationState());
		list.put("conf",  new ConfigState());
		list.put("main",  new MainState());
		state = list.get("start");
		this.stateManager = stateManager;
		this.stateManager.initContext(this);
	}

	public void setState(State state) {
		this.state = state;
	}

	public State getCurrentState() {
		return state;
	}
	
	public State  getState(String key) {
		return list.get(key);
	}
	
	public DisplayManager getStageManger() {
		return stateManager;
	}
	
	
    public void pull() {
    	stateManager.closeScreen();
    	state.doAction(this);
    }
	

}
