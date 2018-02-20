package pfq.store.config;

public class ConfigState implements State {
	@Override
	public void doAction(ContextStateApp context) {
		System.out.println("Config application");
		context.getStageManger().showConfigScreen();
	}

	public String toString() {
		return "Config State";
	}
}