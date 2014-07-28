
package com.example.pipedrivetest.model.auth;

import java.util.List;

public class Info{
   	private boolean account_is_not_paying;
   	private boolean account_is_open;
   	private String add_time;
   	private String billing_currency;
   	private String cancel_time;
   	private boolean cancelled_flag;
   	private String country;
   	private String creator_company_id;
   	private Number id;
   	private String identifier;
   	private String name;
   	private Number plan_id;
   	private String promo_code;
   	private String status;
   	private String trial_ends;
   	private String used_promo_code_key;

 	public boolean getAccount_is_not_paying(){
		return this.account_is_not_paying;
	}
	public void setAccount_is_not_paying(boolean account_is_not_paying){
		this.account_is_not_paying = account_is_not_paying;
	}
 	public boolean getAccount_is_open(){
		return this.account_is_open;
	}
	public void setAccount_is_open(boolean account_is_open){
		this.account_is_open = account_is_open;
	}
 	public String getAdd_time(){
		return this.add_time;
	}
	public void setAdd_time(String add_time){
		this.add_time = add_time;
	}
 	public String getBilling_currency(){
		return this.billing_currency;
	}
	public void setBilling_currency(String billing_currency){
		this.billing_currency = billing_currency;
	}
 	public String getCancel_time(){
		return this.cancel_time;
	}
	public void setCancel_time(String cancel_time){
		this.cancel_time = cancel_time;
	}
 	public boolean getCancelled_flag(){
		return this.cancelled_flag;
	}
	public void setCancelled_flag(boolean cancelled_flag){
		this.cancelled_flag = cancelled_flag;
	}
 	public String getCountry(){
		return this.country;
	}
	public void setCountry(String country){
		this.country = country;
	}
 	public String getCreator_company_id(){
		return this.creator_company_id;
	}
	public void setCreator_company_id(String creator_company_id){
		this.creator_company_id = creator_company_id;
	}
 	public Number getId(){
		return this.id;
	}
	public void setId(Number id){
		this.id = id;
	}
 	public String getIdentifier(){
		return this.identifier;
	}
	public void setIdentifier(String identifier){
		this.identifier = identifier;
	}
 	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
 	public Number getPlan_id(){
		return this.plan_id;
	}
	public void setPlan_id(Number plan_id){
		this.plan_id = plan_id;
	}
 	public String getPromo_code(){
		return this.promo_code;
	}
	public void setPromo_code(String promo_code){
		this.promo_code = promo_code;
	}
 	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status = status;
	}
 	public String getTrial_ends(){
		return this.trial_ends;
	}
	public void setTrial_ends(String trial_ends){
		this.trial_ends = trial_ends;
	}
 	public String getUsed_promo_code_key(){
		return this.used_promo_code_key;
	}
	public void setUsed_promo_code_key(String used_promo_code_key){
		this.used_promo_code_key = used_promo_code_key;
	}
}
