package WayneEnerprises;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
class Order {
    private static final Random r= new Random();
    private int cargoWeight;
    private String destination;
    long timestamp;
    public Order() {
        this.cargoWeight = r.nextInt(41) + 10; // Random cargo weight between 10 and 50 tons
        this.destination = r.nextBoolean() ? "Gotham" : "Atlanta";
    }
    public Order(String destinstion)
    {
    	this.cargoWeight = r.nextInt(41) + 10;
    	this.destination=destinstion;
    }
    public Order(int cargoWeight, String destination) {
		super();
		this.cargoWeight = cargoWeight;
		this.destination = destination;
	}
	public int getCargoWeight() {
        return cargoWeight;
    }

    public String getDestination() {
        return destination;
    }
    public boolean isExpired()
    {
    	
    	return (System.currentTimeMillis()-this.timestamp)>(600000*100);
    }

	@Override
	public String toString() {
		return "Order [cargoWeight=" + cargoWeight + ", destination=" + destination + "]";
	}
    
}
class Ship {
    private int currentCargo;
    private String currentplace;
    int trips=0,id;
    public Ship(int id) {
        this.currentCargo = 0;
        this.currentplace="Gotham";
        this.id=id;
    }
    public String getCurrentplace() {
		return currentplace;
	}

	public void setCurrentplace() {
		if(currentplace=="Gotham")
			currentplace="Atlanta";
		else
			currentplace="Gotham";
		trips+=1;
		if(trips==10)
			sendToMaintenance();
	}
    public void loadCargo(Order order) {
        currentCargo += order.getCargoWeight();
    }
    public void sendToMaintenance() {
    	System.out.println("ship in maintanance");
    	try
    	{
    		Thread.sleep(60*60);
    	}
    	catch(InterruptedException e)
    	{
    		System.out.println(e);
    	}
    }

    public int getCurrentCargo() {
        return currentCargo;
    }
}
public class WayneEnterPrises {
    private static final int cost = 1000;
    private static final int penality = 250;
    private static final int goal = 10000;
    private static int totalprofit = 0;
    private static int OrdersDelivered = 0;
    private static int OrdersCanceled = 0;
    private static int mincargo=50;
    private static int maxcargo=300;
    static Random r=new Random();
    private static BlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();
    private static BlockingQueue<Ship> availableShips = new LinkedBlockingQueue<>();
    private static final Object lock = new Object();

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            availableShips.add(new Ship(i));
        }
        ExecutorService e=Executors.newFixedThreadPool(12);
        for(int i=0;i<7;i++)
        	e.submit(new ConsumerTask());
        for(int i=0;i<5;i++)
        	e.submit(new ShippingTask()); 
        Runtime.getRuntime().addShutdownHook(new Thread(e:: shutdown));
    }
    static class ShippingTask implements Runnable
    {
		Order o=null;
		int cancelles=0;
		int trips=0;
		public void run()
		{
			while (totalprofit<goal) {
				int loadvalue=0;
            	//System.out.println(availableShips);
            	ArrayList<Order> order=new ArrayList<Order>();
            	try
            	{
            		Ship ship = availableShips.take();
				for(Order o:orderQueue)
				{
					if(loadvalue+o.getCargoWeight()<300 &&  !o.getDestination().equals(ship.getCurrentplace()))
            		{
            				loadvalue+=o.getCargoWeight();
            				order.add(o);
            		}
				}
						for(Order o:order)
						{
							o=orderQueue.poll(1,TimeUnit.MILLISECONDS);
						}
						order.clear();
						if(loadvalue>50)
						{
						Thread.sleep(100);
						System.out.println("carrying weight :"+loadvalue+" to "+ship.getCurrentplace());
						ship.setCurrentplace();
						totalprofit+=1000;
						OrdersDelivered+=1;
						}
		            	cancelles=0;
		            	availableShips.add(ship);
		            /*for(Order o:orderQueue)
		            {
		            	if(o.isExpired() && totalprofit>0)
		            	{
		            		totalprofit-=250;
		            	  cancelles+=1;
		            	}
		            	  if(cancelles>2)
		            	  {
		            		  System.out.println("customer left");
		            		  orderQueue.remove(o);
		            	  }
		            	  
		            }*/
	              }
	             catch(InterruptedException e)
            	{
	            	 e.printStackTrace();
            	}
	     }
			if(totalprofit>goal)
			{
				printResults();
				System.exit(0);
			}
		}
    }
    static class ConsumerTask implements Runnable
    {
    	public void run()
    	{
    		 while (true) {
    			 Order order = new Order();
                 try {
                     orderQueue.put(order);
                 }catch(InterruptedException e) {
                 e.printStackTrace();
    		 }
                     try
                     {
                    	 Thread.sleep(1);
                     }
                     catch(InterruptedException e)
                     {
                    	 e.printStackTrace();
                     }
                 }
    	}
                     
    }
    private static void printResults() {
        System.out.println("Total orders delivered: " + OrdersDelivered);
        //System.out.println("Total orders canceled: " + OrdersCanceled);
        System.out.println("Total earnings: $" + totalprofit);
    }
}

