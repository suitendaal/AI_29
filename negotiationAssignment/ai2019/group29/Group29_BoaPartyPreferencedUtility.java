package group29;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import genius.core.Bid;
import genius.core.bidding.BidDetails;
import genius.core.boaframework.AcceptanceStrategy;
import genius.core.boaframework.BoaParty;
import genius.core.boaframework.OMStrategy;
import genius.core.boaframework.OfferingStrategy;
import genius.core.boaframework.OpponentModel;
import genius.core.issue.Issue;
import genius.core.issue.IssueDiscrete;
import genius.core.issue.Objective;
import genius.core.issue.Value;
import genius.core.issue.ValueDiscrete;
import genius.core.parties.NegotiationInfo;
import genius.core.uncertainty.AdditiveUtilitySpaceFactory;
import genius.core.utility.AbstractUtilitySpace;
import genius.core.utility.Evaluator;
import genius.core.utility.EvaluatorDiscrete;

/**
 * This example shows how BOA components can be made into an independent
 * negotiation party and which can handle preference uncertainty.
 * 
 * Note that this is equivalent to adding a BOA party via the GUI by selecting
 * the components and parameters. However, this method gives more control over
 * the implementation, as the agent designer can choose to override behavior
 * (such as handling preference uncertainty).
 * <p>
 * For more information, see: Baarslag T., Hindriks K.V., Hendrikx M.,
 * Dirkzwager A., Jonker C.M. Decoupling Negotiating Agents to Explore the Space
 * of Negotiation Strategies. Proceedings of The Fifth International Workshop on
 * Agent-based Complex Automated Negotiations (ACAN 2012), 2012.
 * https://homepages.cwi.nl/~baarslag/pub/Decoupling_Negotiating_Agents_to_Explore_the_Space_of_Negotiation_Strategies_ACAN_2012.pdf
 * 
 * @author Tim Baarslag
 */
@SuppressWarnings("serial")
public class Group29_BoaPartyPreferencedUtility extends BoaParty 
{
	
	private int learnValueAddition;
//	private int amountOfIssues;
	private double goldenValue;

	private double gamma;
	private double zeta = 0.5;
	
	@Override
	public void init(NegotiationInfo info) 
	{
		// The choice for each component is made here
		AcceptanceStrategy 	ac  = new AC_ai29();
		OfferingStrategy 	os  = new BiddingStrategy29();
		OpponentModel 		om  = new OpponentModelAI29();
		OMStrategy			oms = new OpponentStrategyModel29();
		
		// All component parameters can be set below.
		Map<String, Double> noparams = Collections.emptyMap();
		Map<String, Double> osParams = new HashMap<String, Double>();
		// Set the concession parameter "e" for the offering strategy to yield Boulware-like behavior
		osParams.put("e", 0.2);
		
		// Initialize all the components of this party to the choices defined above
		configure(ac, noparams, 
				os,	osParams, 
				om, noparams,
				oms, noparams);
		super.init(info);
	}

	/**
	 * Specific functionality, such as the estimate of the utility space in the
	 * face of preference uncertainty, can be specified by overriding the
	 * default behavior.
	 * 
	 * This example estimator sets all weights and all evaluator values randomly.
	 */
	@Override
	public AbstractUtilitySpace estimateUtilitySpace() 
	{
		AdditiveUtilitySpaceFactory additiveUtilitySpaceFactory = new AdditiveUtilitySpaceFactory(getDomain());
		List<IssueDiscrete> issues = additiveUtilitySpaceFactory.getIssues();
		
		List<Bid> bids = userModel.getBidRanking().getBidOrder();
		Collections.reverse(bids);
		HashMap<Issue, HashMap<Value, Double>> issueValues = new HashMap<>();
		HashMap<Issue, Double> weights = new HashMap<Issue, Double>();
		
		for (IssueDiscrete issue : issues) {
			weights.put(issue, 1.0/issues.size());
			issueValues.put(issue, new HashMap<>());
			for (Value value : issue.getValues()) {
				issueValues.get(issue).put(value, 0.0);
			}
		}
		
		double valueTerm;
		double weightTerm;
		zeta = 20;
		gamma = 4;
		for (int i = 0; i < bids.size(); i++) {
			Bid bid = bids.get(i);
			
			valueTerm = Math.pow((bids.size() + 1. - i) / (bids.size() + 1), zeta);
			weightTerm = Math.pow((bids.size() + 1. - i) / (bids.size() + 1), gamma);

			
			for(Issue issue : bid.getIssues()) {
				HashMap<Value, Double> hmv = issueValues.get(issue);
				Value value = bid.getValue(issue);
				Double j = hmv.get(value);
				hmv.put(value, (j == null) ? valueTerm : j + valueTerm );
				if (i != 0) {
					Bid previousBid = bids.get(i-1);

					if (previousBid.getValue(issue).equals(value)) {
						Double w = weights.get(issue);
						weights.put(issue, w + weightTerm);
					}
				}
			}
			normalize(weights);
		}
		for (Issue issue : weights.keySet()) {
			additiveUtilitySpaceFactory.setWeight(issue, weights.get(issue));
			
			HashMap<Value, Double> hmv = issueValues.get(issue);
			Double maxval = -1e99;
			for(Value val : hmv.keySet()) {
				Double testval = hmv.get(val);
				if(testval > maxval) {
					maxval = testval;
				}
			}
			
			for(Value val : hmv.keySet()) {
				Double currentval = hmv.get(val);
				additiveUtilitySpaceFactory.setUtility(issue, (ValueDiscrete) val, currentval / maxval);
			}
		}
		
		// Normalize the weights, since we picked them randomly in [0, 1]
		additiveUtilitySpaceFactory.normalizeWeights();
		
		// The factory is done with setting all parameters, now return the estimated utility space
		return additiveUtilitySpaceFactory.getUtilitySpace();
	}
	
	private void normalize(HashMap<Issue, Double> weights) {
		Double totalsum = weights.values().stream().mapToDouble(i -> i).sum();
		for (Issue i : weights.keySet()) {
			Double w = weights.get(i);
			weights.put(i, w/totalsum);
		}
	}

	@Override
	public String getDescription() 
	{
		return "Group 29 - boa party";
	}

	// All the rest of the agent functionality is defined by the components selected above, using the BOA framework
}

