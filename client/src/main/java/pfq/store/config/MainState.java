package pfq.store.config;

public class MainState implements State{

	@Override
	public void doAction(ContextStateApp context) {
		System.out.println("Main  application");
		context.getStageManger().showMainScreen();
	}

}
