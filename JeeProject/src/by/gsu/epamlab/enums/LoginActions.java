package by.gsu.epamlab.enums;

import by.gsu.epamlab.controllers.actions.LoginAction;
import by.gsu.epamlab.controllers.actions.LogoutAction;
import by.gsu.epamlab.controllers.actions.RegistrationAction;
import by.gsu.epamlab.interfaces.IAction;

public enum LoginActions {
	LOGIN {
		public IAction getAction() {
			return new LoginAction();
		}
	},
	LOGOUT {
		public IAction getAction() {
			return new LogoutAction();
		}
	},
	REGISTRATION {
		public IAction getAction() {
			return new RegistrationAction();
		}
	};
	abstract public IAction getAction();
}
