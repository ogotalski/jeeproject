package by.gsu.epamlab.enums;

import by.gsu.epamlab.controllers.actions.CancelOrderAction;
import by.gsu.epamlab.controllers.actions.OrderAction;
import by.gsu.epamlab.controllers.actions.PlayGoMainAction;
import by.gsu.epamlab.controllers.actions.ViewTicketsAction;
import by.gsu.epamlab.interfaces.IAction;

public enum PlaysActions {
	VIEW_TICKETS {
		public IAction getAction() {
			return new ViewTicketsAction();
		}
	},
	GO_MAIN {
		public IAction getAction() {
			return new PlayGoMainAction();
		}
		
	},
	ORDER {
		public IAction getAction() {
			return new OrderAction();
		}
	},
	CANCEL_ORDER {
		public IAction getAction() {
			return new CancelOrderAction();
		}
	};
	abstract public IAction getAction();

}
