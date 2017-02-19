package com.fay.domain;

import java.util.ArrayList;
import java.util.List;

import com.fay.rule.TimeWindowRule.ITimeWindowRule;
import com.fay.rule.TimeWindowRule.TimeWindow_0;
 

public class Vehicle {
	private int id;
	private String name;
	private Cell cell;
   private int capacity;
   private boolean isIdle;

   private int backTime;
   
   private List<Integer> arriveDesTime ;       //锟斤拷锟斤拷锟斤拷锟皆拷锟绞憋拷锟�
   private List<Integer> arriveDesCell ;       //锟斤拷锟斤拷锟斤拷牡锟皆�
   private List<Operation>  transingOperation;		//小锟斤拷锟斤拷装锟截的癸拷锟斤拷
   
   private int timeWindow;
   private boolean timeWindowIdle;
   
   private ITimeWindowRule trule;
   
   private int useTime;
   
   private List<Double> useRate;
   
/***************************锟斤拷锟斤拷锟斤拷***********************************************************************/

   public List<Integer> getDesTime(){
	   return this.arriveDesTime;
   }
   
   public List<Integer> getDesCell(){
	   return this.arriveDesCell;
   }
   /**
	 * @Description construction of Vehicle
	 * @param size
	 * @exception:
	 */
   public Vehicle(int id, String name,int size)
   {
	this.id = id;
	this.name = name;
	this.capacity=size;
	this.isIdle=true; //默锟较刚匡拷始锟斤拷为锟斤拷锟斤拷
	this.backTime = 0;
	this.transingOperation = new ArrayList<Operation>();            //锟斤拷锟斤拷装锟斤拷锟叫的癸拷锟斤拷
	this.timeWindowIdle = false;
	this.arriveDesCell = new ArrayList<Integer>();
	this.arriveDesTime = new ArrayList<Integer>();
	this.trule = new TimeWindow_0();
   }
   
   public Vehicle() {
	// TODO Auto-generated constructor stub
	   this.transingOperation = new ArrayList<Operation>(); 
   }

/**
    * @Description 锟斤拷锟斤拷锟铰次回碉拷锟斤拷元锟斤拷时锟斤拷
    * @param 要锟斤拷锟矫碉拷时锟斤拷
    */
   public void SetBackTime(int t)
   {
	   backTime=t;
   }
   
   /***
    * 锟斤拷锟矫碉拷锟斤拷目锟侥地碉拷时锟斤拷
    * @param t--要锟斤拷锟矫碉拷时锟斤拷
    */
   public void SetArriveDesTime(List<Integer> t)
   {
	   arriveDesTime=t;
   }
   
   /**
    * 小锟斤拷锟截碉拷锟斤拷元时锟斤拷锟角凤拷锟斤拷锟絚urrentTime
    * @param currentTime
    * @return
    */
   public boolean IsVehicleBack(int currentTime) {
	// TODO Auto-generated method stub
	if(this.backTime <= currentTime) return true;
	else return false;
   }

   /** return the cell it should arrive at at current time */
   public int IsVehicleArrival(int currentTime){
	   for(int i : arriveDesTime){
		   if(i == currentTime)
			   return this.getDesCell().get(this.getDesTime().indexOf(i));
	   }
	   return 0;
   }
   
   public void setVehicleAvailable(){
	   this.isIdle = true;
   }
   
   public void setVehicleBusy(){
	   this.isIdle = false;
   }
   
   public void setTimeWindow(int time){
	   this.timeWindow = time;
   }
   
   public int getTimeWindow(){
	   return this.timeWindow;
   }
   
   public void reduceTimeWindow(){
	   if(this.timeWindow > 0)
		   this.timeWindow --;
   }
   
   /**
    * 锟斤拷取锟斤拷前小锟斤拷锟斤拷状态
    */
   public boolean getIdle() {
	// TODO Auto-generated method stub
	return this.isIdle;
   }
   
   /**
    * 锟斤拷取小锟斤拷锟斤拷锟斤拷锟斤拷
    */
   public int getCapacity() {
	  return this.capacity;
   }
   
   public void addOperation(Operation o){
	   this.transingOperation.add(o);
   }
   
   public void removeOperation(Operation o){
	   this.transingOperation.remove(o);
   }
   
   public List<Operation> getTransingOperation(){
	   return this.transingOperation;
   }
   
   public boolean getTimeWindowIdle(){
	   return this.timeWindowIdle;
   }
   
   public void setTimeWindowIdle(boolean b){
	   this.timeWindowIdle = b;
   }
   
   public void setCell(Cell cell) {
		this.cell = cell;
	}
   
   public int getVehicleId(){
	   return this.id;
   }
   
   public void setDesCell(List<Integer> desCell){
	   this.arriveDesCell = desCell;
   }
   
   public void transferOperationInit(){
	   this.transingOperation = new ArrayList<Operation>();
   }
   
   public int getBackTime(){
	   return this.backTime;
   }
   
   public void setCapacity(int c){
	   this.capacity = c;
   }
   
   public void setCapacityPlus(){
	   this.capacity++;
   }
   
   public void setTimeWindowRule(ITimeWindowRule rule){
	   this.trule = rule;
   }
   
   public ITimeWindowRule getTimeWindowRule(){
	   return this.trule;
   }

   public void rest() {
	// TODO Auto-generated method stub
	   for(int i : this.arriveDesTime){
		   i = 0;
	   }
	   this.backTime = 0;
	   this.transingOperation = new ArrayList<Operation>();
	   this.timeWindowIdle = false;
	   
   }
   
   public void restForVehicle(){
	   this.useRate = new ArrayList<Double>();
	   this.useTime = 0;
   }
   
   public void rest_1() {
		   this.backTime = 0;
		   this.trule = null;
		   this.transingOperation = new ArrayList<Operation>();            //锟斤拷锟斤拷装锟斤拷锟叫的癸拷锟斤拷
		   this.timeWindowIdle = false;
		   this.arriveDesCell = new ArrayList<Integer>();
		   this.arriveDesTime = new ArrayList<Integer>();
		   
	   }
   
   
   
}
