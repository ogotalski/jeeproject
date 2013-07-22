package by.gsu.epamlab.enums;

import by.gsu.epamlab.controllers.actions.CourierCancelOrderCAction;
import by.gsu.epamlab.controllers.actions.OrdersReportAction;
import by.gsu.epamlab.controllers.actions.PayOrderAction;
import by.gsu.epamlab.controllers.actions.PlayGoMainAction;
import by.gsu.epamlab.controllers.actions.ViewTicketOrdersAction;
import by.gsu.epamlab.interfaces.IAction;

public enum PlayCourierActions {
	VIEW_TICKETS {
		public IAction getAction() {
			return new ViewTicketOrdersAction();
		}
	},
	GO_MAIN {
		public IAction getAction() {
			return new PlayGoMainAction();
		}
		
	},
	PAY {
		public IAction getAction() {
			return new PayOrderAction();
		}
	},
	REPORT {
		public IAction getAction() {
			return new OrdersReportAction();
		}
	},
	CANCEL_ORDER {
		public IAction getAction() {
			return new CourierCancelOrderCAction();
		}
	};
	abstract public IAction getAction();

}
