package genius.core.bidding;

import java.io.Serializable;

import genius.core.Bid;

public class BidDetailsNash implements Serializable {
	
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
}
