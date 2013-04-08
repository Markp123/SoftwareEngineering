package SEngine;
public class Cell {
	
	private String image;
	private boolean isFood, isRock, isRAntHill, isBAntHill, isEmpty;
	private int foodAmount;
	
	public Cell()
	{
		image = "."; //Default Cell image (Empty Cell)
		isFood = false;
		isRock = false;
		isRAntHill = false;
		isBAntHill = false;
		isEmpty = false;
		foodAmount = 0;
		
		BMarker = new boolean[6];
		RMarker = new boolean[6];
		ant = null;
	}
	
	public void setFood(boolean isFood) 
	{
		this.isFood = isFood;
	}

	public boolean getIsFood() {
		return isFood;
	}

	public boolean getIsRock() {
		return isRock;
	}

	public boolean getIsRAntHill() {
		return isRAntHill;
	}

	public boolean getIsBAntHill() {
		return isBAntHill;
	}

	public boolean getIsEmpty() {
		return isEmpty;
	}

	public int getFoodAmount() {
		return foodAmount;
	}

	public void setRock(boolean isRock) 
	{
		this.isRock = isRock;
	}

	public void setRAntHill(boolean isRAntHill) 
	{
		this.isRAntHill = isRAntHill;
	}

	public void setBAntHill(boolean isBAntHill) 
	{
		this.isBAntHill = isBAntHill;
	}

	public void setEmpty(boolean isEmpty) 
	{
		this.isEmpty = isEmpty;
	}

	public void setFoodAmount(int foodAmount)
	{
		this.foodAmount = foodAmount;
	}

	public String getImage() 
	{
		return image;
	}

	public void setImage(String newImage)
	{
		this.image = newImage;
	}
	
	public String toString()
	{
		if(isRock)
		{
			image = "#";
		}
		else if (isFood)
		{
			image = String.valueOf(foodAmount);
		}
		else if (isRAntHill)
		{
			image = "+";
		}
		else if (isBAntHill)
		{
			image = "-";
		}
		else if (isEmpty)
		{
			image = ".";
		}
		return image;
		
	}
	private Ant ant;
	private boolean[] BMarker;
	private boolean[] RMarker;

	public Ant getAnt() {
		return ant;
	}
	public boolean isAnt() {
		return ant!=null;
	}
	public void setAnt(Ant ant) {
		this.ant = ant;
	}
	public void removeAnt() {
		this.ant = null;
	}
	public void setBMarker(int bMarker) {
		BMarker[bMarker] = true;
	}
	public void removeBMarker(int bMarker) {
		BMarker[bMarker] = false;
	}

	public void setRMarker(int rMarker) {
		RMarker[rMarker] = true;
	}
	public void removeRMarker(int rMarker) {
		RMarker[rMarker] = false;
	}
	
}
