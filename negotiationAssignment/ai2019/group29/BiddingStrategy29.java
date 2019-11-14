package group29;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.List;

import genius.core.BidHistory;
import genius.core.bidding.BidDetails;
import genius.core.boaframework.BOAparameter;
import genius.core.boaframework.NegotiationSession;
import genius.core.boaframework.NoModel;
import genius.core.boaframework.OMStrategy;
import genius.core.boaframework.OfferingStrategy;
import genius.core.boaframework.OpponentModel;
import genius.core.boaframework.SortedOutcomeSpace;
import genius.core.misc.Range;

import java.io.*;
import java.util.ArrayList;
//import com.opencsv.CSVReader;
//import com.opencsv.CSVWriter;

public class BiddingStrategy29 extends OfferingStrategy {

	private double e;
	private SortedOutcomeSpace outcomespace;
	
	private int OurOpeningBid = -1; 
	//* dBid + target < 1 *//
	private double dBid = 0.2;
	private double target = 0.7; // target gets updated if turn 1
	private double targetDecrease = 0.01;

	/**
	 * Method which initializes the agent by setting all parameters. The
	 * parameter "e" is the only parameter which is required.
	 */
	@Override
	public void init(NegotiationSession negoSession, OpponentModel model, OMStrategy oms,
	//public void init(NegotiationSession negoSession, OpponentModel om, OMStrategy oms,
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
//	public List<BidDetails> determineOpeningBid() {
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
//	public List<BidDetails> determineNextBid() {
		
		if (OurOpeningBid < 0) { // unknown if we started or they started
			OurOpeningBid = negotiationSession.getOpponentBidHistory().size() == 0 ? 1 : 0;
		}
		//Won't reach if agreement is reached before time is up (60 == time)
		else if (negotiationSession.getOpponentBidHistory().size() == negotiationSession.getTimeline().getTotalTime()-1) {
			try {
				CsvMaker(negotiationSession.getOwnBidHistory().getHistory(),negotiationSession.getOpponentBidHistory().getHistory());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			target = 1; //end with a high bid
		}
//		System.out.println(negotiationSession.getOpponentBidHistory().size());
//		System.out.println(negotiationSession.getTimeline().getTotalTime()-1);
		
		
		// TODO place this in init
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
		try {
			CsvMaker(negotiationSession.getOwnBidHistory().getHistory(),negotiationSession.getOpponentBidHistory().getHistory());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nextBid;//bidsAboveUtilitygoal.get(rand.nextInt(bidsAboveUtilitygoal.size()));
	}

	public NegotiationSession getNegotiationSession() {
		return negotiationSession;
	}

	// TODO: what to do?
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
	
	public void CsvMaker(List<BidDetails> list, List<BidDetails> list2) throws IOException {
		
		String name= "bid.csv";
		File newFile= new File(name);
		//boolean exists name.exist;
		//boolean test = name.isFile();
		boolean test = newFile.exists();
		int i=1;
		String nameNew=name;
		while(test) { //test if file exists and create a new name 
			nameNew= "bid"+i+".csv";
			newFile= new File(nameNew);
			test = newFile.exists();
			i++;
		}
		try ( FileWriter writer = new FileWriter(nameNew)) {
		 //try (PrintWriter writer = new PrintWriter(new File(nameNew))) {
			 
		      //StringBuilder sb = new StringBuilder();
			 //CSVWriter sb = new CSVWriter(new FileWriter(csv));
//			 FileWriter writer = new FileWriter(nameNew);
			 writer.append("Own Bid");
			 writer.append(',');
			 writer.append(',');
			 writer.append("Bid opponent");
			 writer.append(',');
			 writer.append('\n');
			//			 for (rows : list) {
//				writer.append(list);
//			}
//			 writer.writeAll(list);
			 StringBuilder sb = new StringBuilder();
			 System.out.println(list.size());
			 System.out.println(list2.size());
			 for (int k = 0; k < list.size(); k++) {
				String str1 = list.get(k).toString();
				String str2 = list2.get(k).toString();
				sb.append(str1);
				sb.append(',');
				sb.append(str2);
				sb.append('\n');
			}
			 //for (int j = 0; j < list2.size(); j++) {

				//String str2 = list2.get(j).toString();
//				System.out.println(str);
				
//				System.out.println("hier");
//				writer.write(list.get(j));
//				sb.append(str1);
				//sb.append(',');
				//sb.append(str2);
				//sb.append('\n');
//				writer.append(str);
//				//writer.write(str);
//				writer.append('\n');
//				writer.write(str);
			//}
			 writer.write(sb.toString());
//		      writer.write(sb.toString());
			 writer.flush();
			 writer.close();
		      System.out.println("Logging bids done!");

		    } catch (FileNotFoundException e) {
		      System.out.println(e.getMessage());
		    }
	}
	
}


