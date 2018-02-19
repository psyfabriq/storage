package pfq.store.config;

public class ConfigState implements State {

	public void doAction(ContextStateApp context) {
		context.setState(context.getState("start"));
	}

	public String toString() {
		return "Config State";
	}
}