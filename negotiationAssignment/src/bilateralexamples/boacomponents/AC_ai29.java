package bilateralexamples.boacomponents;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import genius.core.Bid;
import genius.core.boaframework.AcceptanceStrategy;
import genius.core.boaframework.Actions;
import genius.core.boaframework.BOAparameter;
import genius.core.boaframework.NegotiationSession;
import genius.core.boaframework.OfferingStrategy;
import genius.core.boaframework.OpponentModel;

/**
 * This Acceptance Condition will accept an opponent bid if the utility is
 * higher than the bid the agent is ready to present.
 * 
 * Decoupling Negotiating Agents to Explore the Space of Negotiation Strategies
 * T. Baarslag, K. Hindriks, M. Hendrikx, A. Dirkzwager, C.M. Jonker
 * 
 */
public class AC_ai29 extends AcceptanceStrategy {

	private double a;
	private double b;
	private double d;

	private int OurOpeningBid = -1; // start uninitialized

	/**
	 * Empty constructor for the BOA framework.
	 */
	public AC_ai29() {
	}

	public AC_ai29(NegotiationSession negoSession, OfferingStrategy strat,
			double alpha, double beta) {
		this.negotiationSession = negoSession;
		this.offeringStrategy = strat;
		this.a = alpha;
		this.b = beta;
	}

	@Override
	public void init(NegotiationSession negoSession, OfferingStrategy strat,
			OpponentModel opponentModel, Map<String, Double> parameters)
			throws Exception {
		this.negotiationSession = negoSession;
		this.offeringStrategy = strat;

		if (parameters.get("a") != null || parameters.get("b") != null || parameters.get("d") != null) {
			a = parameters.get("a");
			b = parameters.get("b");
			d = parameters.get("d");
		} else {
			a = 1;
			b = 0;
			d = 0;
		}
	}

	@Override
	public String printParameters() {
		String str = "[a: " + a + " b: " + b + "]";
		return str;
	}

	@Override
	public Actions determineAcceptability() {
		double nextMyBidUtil = offeringStrategy.getNextBid()
				.getMyUndiscountedUtil();
		double lastOpponentBidUtil = negotiationSession.getOpponentBidHistory()
				.getLastBidDetails().getMyUndiscountedUtil();
		
		if (OurOpeningBid < 0) { // unknown if we started or they started
			Bid lastBid = negotiationSession.getOpponentBidHistory().getFirstBidDetails().getBid();
			OurOpeningBid = lastBid == null ? 1 : 0;
		}
		
		double f_t = -b * negotiationSession.getTime() + a + d * OurOpeningBid;
		
		if (lastOpponentBidUtil >= f_t) {
			return Actions.Accept;
		}
		else if (lastOpponentBidUtil > nextMyBidUtil) {
			return Actions.Accept;
		}
		return Actions.Reject;
	}

	@Override
	public Set<BOAparameter> getParameterSpec() {

		Set<BOAparameter> set = new HashSet<BOAparameter>();
		set.add(new BOAparameter("a", 1.0,
				"Accept when the opponent's utility * a + b is greater than the utility of our current bid"));
		set.add(new BOAparameter("b", 0.0,
				"Accept when the opponent's utility * a + b is greater than the utility of our current bid"));

		return set;
	}

	@Override
	public String getName() {
		return "AC of ai 29";
	}
}