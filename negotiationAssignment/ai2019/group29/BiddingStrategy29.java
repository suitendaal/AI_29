package group29;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.List;

import genius.core.bidding.BidDetails;
import genius.core.boaframework.BOAparameter;
import genius.core.boaframework.NegotiationSession;
import genius.core.boaframework.NoModel;
import genius.core.boaframework.OMStrategy;
import genius.core.boaframework.OfferingStrategy;
import genius.core.boaframework.OpponentModel;
import genius.core.boaframework.SortedOutcomeSpace;
import genius.core.misc.Range;

/**
 * implemented by
 * J. Dumont, L. van der Knaap, W. Kok, R. Luijendijk, S. Uitendaal
 * Group 29
 */
public class BiddingStrategy29 extends OfferingStrategy {

	private double e;
	private SortedOutcomeSpace outcomespace;
	
	private int OurOpeningBid = -1; 
	private double dBid = 0.2;
	private double target = 0.7; // target gets updated if turn 1
	private double targetDecrease = 0.01;

	/**
	 * Method which initializes the agent by setting all parameters. The
	 * parameter "e" is the only parameter which is required.
	 */
	@Override
	public void init(NegotiationSession negoSession, OpponentModel model, OMStrategy oms,
			Map<String, Double> parameters) throws Exception {
		super.init(negoSession, parameters);
		if (parameters.get("e") != null) {
			this.negotiationSession = negoSession;

			outcomespace = new SortedOutcomeSpace(negotiationSession.getUtilitySpace());
			negotiationSession.setOutcomeSpace(outcomespace);
			
			this.e = parameters.get("e");
			
			if (parameters.get("dBid") != null) {
				this.dBid = parameters.get("dBid");
			}
			
			if (parameters.get("target") != null) {
				this.target = parameters.get("target");
			}
			
			if (parameters.get("targetDecrease") != null) {
				this.targetDecrease = parameters.get("targetDecrease");
			}

			this.opponentModel = model;
			
			this.omStrategy = oms;
		} else {
			throw new Exception("Constant \"e\" for the concession speed was not set.");
		}
	}

	@Override
	public BidDetails determineOpeningBid() {
		return determineNextBid();
	}

	/**
	 * Simple offering strategy which retrieves the target utility and looks for
	 * the nearest bid if no opponent model is specified. If an opponent model
	 * is specified, then the agent return a bid according to the opponent model
	 * strategy.
	 */
	@Override
	public BidDetails determineNextBid() {
		
		if (OurOpeningBid < 0) { // Starts at -1 and is set to 0 or 1 depening on who has the opening bid
			OurOpeningBid = negotiationSession.getOpponentBidHistory().size() == 0 ? 1 : 0;
		}
		else if (negotiationSession.getOpponentBidHistory().size() == negotiationSession.getTimeline().getTotalTime()-1) {
			target = 1; //end with a high bid
		}
		
		
		double utilityGoal = target + OurOpeningBid*dBid;
		Range utilRange = new Range(utilityGoal, 1);
		List<BidDetails> bidsAboveUtilitygoal = negotiationSession.getOutcomeSpace().getBidsinRange(utilRange);
		
		while (bidsAboveUtilitygoal.isEmpty()) {
			target -= targetDecrease;
			return determineNextBid();
		}
		if (opponentModel instanceof NoModel) {
			Random rand = new Random();
			nextBid = bidsAboveUtilitygoal.get(rand.nextInt(bidsAboveUtilitygoal.size()));
		} else {
			nextBid = omStrategy.getBid(bidsAboveUtilitygoal);
		}
		
		return nextBid;//bidsAboveUtilitygoal.get(rand.nextInt(bidsAboveUtilitygoal.size()));
	}

	public NegotiationSession getNegotiationSession() {
		return negotiationSession;
	}

	@Override
	public Set<BOAparameter> getParameterSpec() {
		Set<BOAparameter> set = new HashSet<BOAparameter>();
		set.add(new BOAparameter("e", 1.0, "Concession rate"));
		set.add(new BOAparameter("dBid", dBid, "Target increment when opening bidder"));
		set.add(new BOAparameter("target", target, "Base target"));
		set.add(new BOAparameter("targetDecrease", targetDecrease, "Target decrease"));
		return set;
	}
	
	@Override
	public String getName() {
		return "Bidding Strategy group 29";
	}
}
