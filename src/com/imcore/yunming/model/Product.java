package com.imcore.yunming.model;

import java.io.Serializable;

public class Product implements Serializable{
	private static final long serialVersionUID = -8398180346532483137L;
	public long id;
	public int categoryId;
	public String productName;
	public String packing;
	public int price;
	public int saleTotal;
	public String imageUrl;
	public int favotieTotal;
	public String shortDesc;
	public String updatedTime;
}
