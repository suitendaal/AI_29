package group29;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
 * extended by
 * J. Dumont, L. van der Knaap, W. Kok, R. Luijendijk, S. Uitendaal
 * Group 29
 */
public class OpponentStrategyModel29 extends OMStrategy {

	/**
	 * when to stop updating the opponentmodel. Note that this value is not
	 * exactly one as a match sometimes lasts slightly longer.
	 */
	double updateThreshold = 1.1;

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
		
		// Sort the bids from highest to lowest utility
		// Choose bids dependent of time, further in time = higher bid

		// 1. If there is only a single bid, return this bid
		if (allBids.size() == 1) {
			return allBids.get(0);
		}
		double bestUtil = -1;
		BidDetailsNash bestBidNash = new BidDetailsNash(allBids.get(0));
		
		// 2. Check that not all bids are assigned at utility of 0
		// to ensure that the opponent model works. If the opponent model
		// does not work, offer a random bid.
		boolean allWereZero = true;
		
		// 3. Determine the best bid // << is the magic sauce
		double combiUtil_eval = 0;
		double combiUtil_best = 0;
		double ownBest = 0;
		BidDetailsNash sendBidNash = bestBidNash;

		Deque<BidDetailsNash> allBidsNash = new LinkedList<>();
		double time=negotiationSession.getTime();
		
		for (BidDetails bid : allBids) {
			BidDetailsNash bidNash = new BidDetailsNash(bid);
			
			double evaluation = model.getBidEvaluation(bidNash.getBid());//bid utility opponent

			if (evaluation > 0.0001) {
				allWereZero = false; //opponent model works
			}
			
			double ownEval= negotiationSession.getUtilitySpace().getUtility(bidNash.getBid()) ;
			combiUtil_eval = evaluation*ownEval;
			combiUtil_best = bestUtil*ownBest;
			
			bidNash.setNashProduct(combiUtil_eval);

			if(combiUtil_eval>=combiUtil_best) { //pick best/high utility for both parties
				bestBidNash = bidNash; //best meaning highest nash product value
				bestUtil = evaluation;
				ownBest= model.getBidEvaluation(bidNash.getBid());
				allBidsNash.addFirst(bestBidNash);
			}else { //bid is not better, append to list sorted on Nash product
				allBidsNash.add(bidNash);
			}
		}
		
		Random r = new Random();
		if (canUpdateOM()) { //checks if still update-able opponent model >>note: bidding should do last bid
			Random r1 = new Random();
			ArrayList<BidDetailsNash> allBidsNashConvert = new ArrayList<BidDetailsNash>(allBidsNash);
			Collections.sort(allBidsNashConvert);
			int method=2; //<<<set the method
			
			if (method == 1) {	//Send random bid first, later send highest Nash product 
				double threshold=r1.nextInt(101)/100; //values between 0 and 100
				if (threshold > time) { //random choice between bestbid or nash, time progresses more bestbids
					int sizeBids = allBidsNashConvert.size();
					int lenChoice=(int)Math.round(1-time)*sizeBids;
					if (lenChoice == 0) {
						sendBidNash = allBidsNashConvert.get(0);
					}
					else {
						ArrayList<BidDetailsNash> pickOne = new ArrayList<BidDetailsNash>(allBidsNashConvert.subList(0,lenChoice));//New array with actual available bids
						int index=r.nextInt(pickOne.size()); //index used in both methods
						sendBidNash=pickOne.get(index);
					}
				}else {
					sendBidNash = bestBidNash;
				}
			}else if(method == 2){
				int sizeBids = allBidsNashConvert.size();
				int lenChoice=(int)Math.round(1-time)*sizeBids; //without round it would never be sizebids only floored
				if (lenChoice == 0) {
					sendBidNash = allBidsNashConvert.get(0);
				}
				else {
					ArrayList<BidDetailsNash> pickOne = new ArrayList<BidDetailsNash>(allBidsNashConvert.subList(0,lenChoice));//New array with actual available bids
					int index=r.nextInt(pickOne.size()); //index used in both methods
					sendBidNash=pickOne.get(index);
				}
			}else {
				sendBidNash=bestBidNash; //just send something
			}
		}//else sendBid stays the same
		
		// 4. Send bid
		
		if (allWereZero) {//The opponent model did not work, therefore, offer next bid
			return allBids.get(r.nextInt(allBids.size()));
		}else { //opponentmodel does work, send bid		
			return sendBidNash.getBidDetails();		
		}
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