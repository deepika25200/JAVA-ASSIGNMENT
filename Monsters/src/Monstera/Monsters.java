package Monstera;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.DrbgParameters.Instantiation;
import java.util.*;
class Monster
{
	//this class represents monster and its different properties
	private String Name,ColorOfEye,FeatherColor,MagicalAbility,Weakness;
	private int SizeOfMonster,Strength,Durability,AgressionLevel;
	public Monster(String name,String colorOfEye, String featherColor, String magicalAbility, String weakness,
			int sizeOfMonster, int strength, int durability, int agressionLevel) {
		Name=name;
		ColorOfEye = colorOfEye;
		FeatherColor = featherColor;
		MagicalAbility = magicalAbility;
		Weakness = weakness;
		SizeOfMonster = sizeOfMonster;
		Strength = strength;
		Durability = durability;
		AgressionLevel = agressionLevel;
	}
	public String getColorOfEye() {
		return ColorOfEye;
	}
	public void setColorOfEye(String colorOfEye) {
		ColorOfEye = colorOfEye;
	}
	public String getFeatherColor() {
		return FeatherColor;
	}
	public void setFeatherColor(String featherColor) {
		FeatherColor = featherColor;
	}
	public String getMagicalAbility() {
		return MagicalAbility;
	}
	public void setMagicalAbility(String magicalAbility) {
		MagicalAbility = magicalAbility;
	}
	public String getWeakness() {
		return Weakness;
	}
	public void setWeakness(String weakness) {
		Weakness = weakness;
	}
	public int getSizeOfMonster() {
		return SizeOfMonster;
	}
	public void setSizeOfMonster(int sizeOfMonster) {
		SizeOfMonster = sizeOfMonster;
	}
	public int getStrength() {
		return Strength;
	}
	public void setStrength(int strength) {
		Strength = strength;
	}
	public int getDurability() {
		return Durability;
	}
	public void setDurability(int durability) {
		Durability = durability;
	}
	public int getAgressionLevel() {
		return AgressionLevel;
	}
	public void setAgressionLevel(int agressionLevel) {
		AgressionLevel = agressionLevel;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	@Override
	public String toString() {
		return "Name=" + Name + ", ColorOfEye=" + ColorOfEye + ", FeatherColor=" + FeatherColor
				+ ", MagicalAbility=" + MagicalAbility + ", Weakness=" + Weakness + ", SizeOfMonster=" + SizeOfMonster
				+ ", Strength=" + Strength + ", Durability=" + Durability + ", AgressionLevel=" + AgressionLevel + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(AgressionLevel, ColorOfEye, Durability, FeatherColor, MagicalAbility, Name, SizeOfMonster,
				Strength, Weakness);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Monster other = (Monster) obj;
		return AgressionLevel == other.AgressionLevel && Objects.equals(ColorOfEye, other.ColorOfEye)
				&& Durability == other.Durability && Objects.equals(FeatherColor, other.FeatherColor)
				&& Objects.equals(MagicalAbility, other.MagicalAbility) && Objects.equals(Name, other.Name)
				&& SizeOfMonster == other.SizeOfMonster && Strength == other.Strength
				&& Objects.equals(Weakness, other.Weakness);
	}
	
	
}
class BabyMonster
{
	// this is baby mosnster which has same properties as Monster and it inherits properties of it's 2 parents with reflection
	
	private String Name,ColorOfEye,FeatherColor,MagicalAbility,Weakness;
	private int SizeOfMonster,Strength,Durability,AgressionLevel;
	//this method will get properties from parents of monster and assign them randomly to baby and return the baby object
	BabyMonster createBaby(Monster parent1,Monster parent2) throws IllegalArgumentException, IllegalAccessException
	{
		Random r=new Random();
		BabyMonster c=new BabyMonster();
		Field[]  parent1Fields=parent1.getClass().getDeclaredFields();//fields of parent1
	    Field[]  parent2Fields=parent2.getClass().getDeclaredFields();//fields of parent2
	    Field[] childFields=c.getClass().getDeclaredFields();//fields of baby(child)
	    List<String> stringValues= new ArrayList<>();//arraylist to store string properties of 
	    List<Integer> integerValues=new ArrayList<>();//integerlist to store integer properties
	    //setting all fields accessibility as true as they are all private
	    for(Field f:parent1Fields){
	        f.setAccessible(true);
	    }
	    for(Field f:parent2Fields){
	        f.setAccessible(true);
	    }
	    for(Field f:childFields){
	        f.setAccessible(true);
	    }
	    /*looping through fields and assigning a random value from one of parent through random
	     * and storing the values in respective lists */
	    for(Field f: parent1Fields)
	    {
	    	if(f.getType().isAssignableFrom(String.class))
	        {
	            String s1=String.valueOf(f.get(parent1));
	            String s2=String.valueOf(f.get(parent2));
	            stringValues.add(r.nextBoolean()?s1:s2);
	        }
	        else{
	            int i1=(Integer) f.get(parent1);
	            int i2=(Integer) f.get(parent2);
	            int i3=r.nextBoolean()?i1:i2;
	            integerValues.add(i3);
	        }
	     }
	    //assigning stored values to baby
	    for(int i=0;i<childFields.length;i++)
	    {
	    	Field f=childFields[i];
	    	if(i<stringValues.size())
	    		f.set(c, stringValues.get(i));
	    	else
	    		f.set(c,integerValues.get(i-stringValues.size()));		
	    }
	    return c;
	}
	
	@Override
	public String toString() {
		return "BabyMonster [Name=" + Name + ", ColorOfEye=" + ColorOfEye + ", FeatherColor=" + FeatherColor
				+ ", MagicalAbility=" + MagicalAbility + ", Weakness=" + Weakness + ", SizeOfMonster=" + SizeOfMonster
				+ ", Strength=" + Strength + ", Durability=" + Durability + ", AgressionLevel=" + AgressionLevel + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(AgressionLevel, ColorOfEye, Durability, FeatherColor, MagicalAbility, Name, SizeOfMonster,
				Strength, Weakness);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BabyMonster other = (BabyMonster) obj;
		return AgressionLevel == other.AgressionLevel && Objects.equals(ColorOfEye, other.ColorOfEye)
				&& Durability == other.Durability && Objects.equals(FeatherColor, other.FeatherColor)
				&& Objects.equals(MagicalAbility, other.MagicalAbility) && Objects.equals(Name, other.Name)
				&& SizeOfMonster == other.SizeOfMonster && Strength == other.Strength
				&& Objects.equals(Weakness, other.Weakness);
	}
}
public class Monsters {
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		String Name,ColorOfEye,FeatherColor,MagicalAbility,Weakness;
		int SizeOfMonster,Strength,Durability,AgressionLevel;
		List<Monster> monsters=new ArrayList<>();//stores monster objects
		List<BabyMonster> babies=new ArrayList<>();//stores babies objects
		while(true)
		{
			System.out.println("You want to create a monster if yes press 1 if you don't want to press 2");
			int choose=sc.nextInt();
			if(choose==1)
			{
				System.out.println("Note : enter without any spaces in middle of string values");
				System.out.println("enter name of your monster");
				Name=sc.next();
				System.out.println("enter colorOfEye of your monster");
				ColorOfEye=sc.next();
				System.out.println("enter feather color of your monster");
				FeatherColor=sc.next();
				System.out.println("enter magical abilitiy (1) of your monster");
				MagicalAbility=sc.next();
				System.out.println("enter Weakness of your monster");
				Weakness=sc.next();
				System.out.println("enter size of your monster");
				SizeOfMonster=sc.nextInt();
				System.out.println("enter strength of your monster");
				Strength=sc.nextInt();
				System.out.println("enter durability of your monster");
				Durability=sc.nextInt();
				System.out.println("enter agression level of your monster");
				AgressionLevel=sc.nextInt();
				//creating monsters from user input and storing it in monsters list
				monsters.add(new Monster(Name,ColorOfEye,FeatherColor,MagicalAbility,Weakness,SizeOfMonster,Strength,Durability,AgressionLevel));
			}
			else
			{
				//creating babies if there are more than 1 parent
				if(monsters.size()>=2)
				{
					System.out.println("you have created "+monsters.size()+" monsters");
					for(int i=0;i<monsters.size();i++)
					{
						for(int j=i+1;j<monsters.size();j++)
						{
							//getting all permutations
							BabyMonster b=new BabyMonster();
							try
							{
								//creating babies with all combinations and storing it in babies list
							babies.add(b.createBaby(monsters.get(i),monsters.get(j)));
							}
							catch(IllegalArgumentException e)
							{
								e.printStackTrace();
							}
							catch(IllegalAccessException e)
							{
								e.printStackTrace();
							}
						}
					}
					//printing ouput
					System.out.println("you have created "+babies.size()+" baby monsters");
					for(Monster m:monsters)
						System.out.println("Monster"+m.toString());
					for(BabyMonster b:babies)
						System.out.println(b.toString());
				}
				return;
			}
		}
	}

}
