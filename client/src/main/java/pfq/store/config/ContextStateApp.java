package pfq.store.config;

import java.util.HashMap;

import javafx.scene.Scene;
import javafx.stage.Stage;
import pfq.store.StateManager;

public class ContextStateApp {
	private State state;
	StateManager stateManager;


	private HashMap<String,State> list = new HashMap<String,State>();

	public ContextStateApp(StateManager stateManager) {
		list.put("start", new StartState());
		list.put("stop",  new StopState());
		list.put("auth",  new AuthorizationState());
		list.put("conf",  new ConfigState());
		state = list.get("start");
		this.stateManager = stateManager;
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
	
	public StateManager getStageManger() {
		return stateManager;
	}
	
    public void pull() {
    	state.doAction(this);
    }
	

}
