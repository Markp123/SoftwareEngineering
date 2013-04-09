package SEngine;


import javax.swing.JButton;
import java.awt.Dimension;

public class Cell extends JButton {
	private String image = ".";
	private boolean isFood = false;
	private boolean isRock = false;
	private boolean isRAntHill = false; 
	private boolean isBAntHill = false; 
	private boolean isEmpty = false;
	private boolean isAntHill = false;
	private int foodAmount = 0;
	public static int preferredWidth = 45;

	public Cell() {
		super();
		this.setPreferredSize(new Dimension(preferredWidth, preferredWidth));
		this.setText(toString());
	}        

	public void updateTile()
	{
		this.setText(toString());
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

	public static int getPreferredWidth() 
	{
		return preferredWidth;
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

	public boolean getIsAntHill()
	{
		return isAntHill;
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

	public void setIsAntHill(boolean isAntHill)
	{
		this.isAntHill = isAntHill;
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
			image = "B";
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
