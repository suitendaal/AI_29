package group29;

import java.util.List;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import genius.core.Bid;
import genius.core.boaframework.AcceptanceStrategy;
import genius.core.boaframework.BoaParty;
import genius.core.boaframework.OMStrategy;
import genius.core.boaframework.OfferingStrategy;
import genius.core.boaframework.OpponentModel;
import genius.core.issue.Issue;
import genius.core.issue.IssueDiscrete;
import genius.core.issue.Value;
import genius.core.issue.ValueDiscrete;
import genius.core.parties.NegotiationInfo;
import genius.core.uncertainty.AdditiveUtilitySpaceFactory;
import genius.core.uncertainty.UserModel;
import genius.core.utility.AbstractUtilitySpace;

/**
 * For more information, see: Baarslag T., Hindriks K.V., Hendrikx M.,
 * Dirkzwager A., Jonker C.M. Decoupling Negotiating Agents to Explore the Space
 * of Negotiation Strategies. Proceedings of The Fifth International Workshop on
 * Agent-based Complex Automated Negotiations (ACAN 2012), 2012.
 * https://homepages.cwi.nl/~baarslag/pub/Decoupling_Negotiating_Agents_to_Explore_the_Space_of_Negotiation_Strategies_ACAN_2012.pdf
 * 
 * @author Tim Baarslag
 * 
 * extended by
 * J. Dumont, L. van der Knaap, W. Kok, R. Luijendijk, S. Uitendaal
 * Group 29
 */
@SuppressWarnings("serial")
public class Group29_BoaParty extends BoaParty 
{
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
	 * extended by
	 * J. Dumont, L. van der Knaap, W. Kok, R. Luijendijk, S. Uitendaal
	 * Group 29
	 * 
	 * Implementation is described fully in the report under 2g.
	 * 
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
		double zeta_valueTerm = 14;
		double zeta_weightTerm = 4;
		
		// iterate over bids and updated scores if their issues have been seen before or hasn't been changed.
		for (int i = 0; i < bids.size(); i++) {
			Bid bid = bids.get(i);
			
			valueTerm = Math.pow((bids.size() + 1. - i) / (bids.size() + 1), zeta_valueTerm);
			weightTerm = Math.pow((bids.size() + 1. - i) / (bids.size() + 1), zeta_weightTerm);

			
			for(Issue issue : bid.getIssues()) {
				HashMap<Value, Double> hmv = issueValues.get(issue);
				Value value = bid.getValue(issue);
				Double j = hmv.get(value);
				hmv.put(value, (j == null) ? valueTerm : j + valueTerm );
				
				if (i != 0) { // don't check previous value if only first bid is looked at.
					Bid previousBid = bids.get(i-1);

					if (previousBid.getValue(issue).equals(value)) {
						Double w = weights.get(issue);
						weights.put(issue, w + weightTerm);
					}
				}
			}
			normalize(weights);
		}
		
		// set weights and find highest score of value per issue -> likely very important
		// assign it the highest preference while normalizing the rest
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
		
		// Normalize the weights
		additiveUtilitySpaceFactory.normalizeWeights();
		
		// The factory is done with setting all parameters, now return the estimated utility space
		return additiveUtilitySpaceFactory.getUtilitySpace();
	}
	
	// normalization only for weights.
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

