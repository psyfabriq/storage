
package pfq.store.config;

public class AuthorizationState implements State{

	@Override
	public void doAction(ContextStateApp context) {
		System.out.println("Auth application");
		context.getStageManger().showLoginScreen();
	}

}
