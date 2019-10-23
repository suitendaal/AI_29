package group29;

import java.io.Serializable;

import genius.core.Bid;
import genius.core.bidding.BidDetails;

public class BidDetailsNash implements Serializable, Comparable<BidDetailsNash> {
	
	public double nashProduct = 0;
	public BidDetails bidDetails;

	public BidDetailsNash(BidDetails bidDetails) {
		this.bidDetails = bidDetails;
	}

	public void setNashProduct(double nash) {
		this.nashProduct = nash;
	}
	
	public double getNashProduct() {
		return nashProduct;
	}
	
	public BidDetails getBidDetails() {
		return bidDetails;
	}
	
	public Bid getBid() {
		return bidDetails.getBid();
	}
	
	@Override
	public String toString() {
		return "(" + nashProduct + ")";
	}
	
	@Override
	public int compareTo(BidDetailsNash other) {
		double otherNash = other.getNashProduct();
		
		int value = 0;
		if (this.nashProduct < otherNash) {
			value = 1;
		} else if (this.nashProduct > otherNash) {
			value = -1;
		}
		return value;
	}
}
