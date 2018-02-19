package pfq.store.config;

public class StopState implements State {

	public void doAction(ContextStateApp context) {
		System.out.println("Stop application");
		System.exit(0);
	}

	public String toString() {
		return "Stop State";
	}
}