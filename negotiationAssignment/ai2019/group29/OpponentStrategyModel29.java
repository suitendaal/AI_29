package group29;

import java.util.List;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import genius.core.bidding.BidDetails;
import genius.core.boaframework.BOAparameter;
import genius.core.boaframework.NegotiationSession;
import genius.core.boaframework.OMStrategy;
import genius.core.boaframework.OpponentModel;

/**
 * This class uses an opponent model to determine the next bid for the opponent,
 * while taking the opponent's preferences into account. The opponent model is
 * used to select the best bid.
 * 
 */
public class OpponentStrategyModel29 extends OMStrategy {

	/**
	 * when to stop updating the opponentmodel. Note that this value is not
	 * exactly one as a match sometimes lasts slightly longer.
	 */
	double updateThreshold = 1.1;

	/**
	 * Initializes the opponent model strategy. If a value for the parameter t
	 * is given, then it is set to this value. Otherwise, the default value is
	 * used.
	 * 
	 * @param negotiationSession
	 *            state of the negotiation.
	 * @param model
	 *            opponent model used in conjunction with this opponent modeling
	 *            strategy.
	 * @param parameters
	 *            set of parameters for this opponent model strategy.
	 */
	@Override
	public void init(NegotiationSession negotiationSession, OpponentModel model, Map<String, Double> parameters) {
		super.init(negotiationSession, model, parameters);
		if (parameters.get("t") != null) {
			updateThreshold = parameters.get("t").doubleValue();
		} else {
			System.out.println("OMStrategy assumed t = 1.1");
		}
	}

	/**
	 * Returns the best bid for the opponent given a set of similarly preferred
	 * bids.
	 * 
	 * @param list
	 *            of the bids considered for offering.
	 * @return bid to be offered to opponent.
	 */
	@Override
	public BidDetails getBid(List<BidDetails> allBids) {
		
		// Sorteer de bids op volgorde van laag naar hoog
		// Kies je bid afhankelijk van de tijd, verdere tijd = hogere bid

		// 1. If there is only a single bid, return this bid
		if (allBids.size() == 1) {
			return allBids.get(0);
		}
		double bestUtil = -1;
		BidDetails bestBid = allBids.get(0);
		
		// 2. Check that not all bids are assigned at utility of 0
		// to ensure that the opponent model works. If the opponent model
		// does not work, offer a random bid.
		boolean allWereZero = true;
		
		// 3. Determine the best bid // << is the magic sauce
		double combiUtil_eval=0;
		double combiUtil_best=0;
		double ownBest =0;
		BidDetails sendBid = bestBid;
//		double bestBid =0;
//		List<BidDetails> allBidsNash;
//		Deque<String> deque = new LinkedList<>();
		Deque<BidDetails> allBidsNash = new LinkedList<>();
//		ArrayList<BidDetails> allBidsNash = ArrayList<BidDetails> ();
		double time=negotiationSession.getTime();
//		double ownBest= evaluation;
		for (BidDetails bid : allBids) {
			double evaluation = model.getBidEvaluation(bid.getBid());//bid utility opponent

			if (evaluation > 0.0001) {
				allWereZero = false; //opponent model works
			}
			
			double ownEval= model.getBidEvaluation(bid.getBid());
			combiUtil_eval=evaluation*ownEval;
			combiUtil_best=bestUtil*ownBest;
			
			if(combiUtil_eval>=combiUtil_best) { //pick best/high utility for both parties
			//if (evaluation > bestUtil) {
				bestBid = bid; //best meaning highest nash product value
				bestUtil = evaluation;
				ownBest= model.getBidEvaluation(bid.getBid());
//				System.out.println(ownBest);
				allBidsNash.addFirst(bid);
			}else { //bid is not better, append to list sorted on Nash product
				allBidsNash.add(bid);
			}
		}
		
		Random r = new Random();
		if (canUpdateOM()) { //checks if still updatable opponent model
			Random r1 = new Random();
//			End_time = negotiationSession. 
//			double time = negotiationSession.getTime(); //is normalized time
			double threshold=r1.nextInt(101)/100;
			ArrayList<BidDetails> allBidsNashConvert = new ArrayList<BidDetails>(allBidsNash);
			int index=r.nextInt(allBidsNash.size());
			if (threshold > time) {
				sendBid =allBidsNashConvert.get(index);
//				sendBid= allBidsNash.element();	
			}else {
				sendBid= bestBid;
			}
		}//else sendBid stays the same
		
		// 4. Send bid
		
		//test
		System.out.println(allBids);
		System.out.println(allBidsNash);
		//
		
		if (allWereZero) {//The opponent model did not work, therefore, offer a random bid.
			return allBids.get(r.nextInt(allBids.size()));
		}else { //opponentmodel does work, send bid		
			return 	sendBid;		
		}
//		return (BidDetails) allBids;
	}
	/**
	 * The opponent model may be updated, unless the time is higher than a given
	 * constant.
	 * 
	 * @return true if model may be updated.
	 */
	@Override
	public boolean canUpdateOM() {
		return negotiationSession.getTime() < updateThreshold;
	}

	@Override
	public Set<BOAparameter> getParameterSpec() {
		Set<BOAparameter> set = new HashSet<BOAparameter>();
		set.add(new BOAparameter("t", 1.1, "Time after which the OM should not be updated"));
		return set;
	}

	@Override
	public String getName() {
		return "Group 29 opponent strategy model";
	}
}