import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.IOException;
public abstract class GameObject
{
	public static boolean LIVE = false;
	//Semicolon separated list of names this element has
	private String names = "";
	//Image of the object
	private ImageView sprite = new ImageView();
	//x coordinate in tiles to display on screen
	private double x = 0;
	//y coordinate in tiles to display on screen
	private double y = 0;
	//name of image
	private String imagename;
	
	//call the constructor - used in the editor
	public abstract GameObject makeCopy();
	
	public String getNames()
	{
		return names;
	}
	public void setNames(String names)
	{
		this.names = names;
	}
	
	public ImageView getSprite()
	{
		return sprite;
	}
	public String getImageName()
	{
		return imagename;
	}
	public void setSprite(String image)
	{
		try
		{
			this.imagename = image;
			if(LIVE)
				//special method for the editor so don't have to rebuild to get new Images
				sprite.setImage(new Image("Images/"+image));
			else
				sprite.setImage(new Image(getClass().getResource(image).openStream()));
		}
		catch(IOException ioe) {ioe.printStackTrace();}
	}
	
	public double getX()
	{
		return this.x;
	}
	public void setX(double x)
	{
		this.x = x;
		sprite.setLayoutX(x*GameWorld.TILEWIDTH);
	}
	
	public double getY()
	{
		return this.y;
	}
	public void setY(double y)
	{
		this.y = y;
		sprite.setLayoutY(y*GameWorld.TILEHEIGHT);
	}
	public abstract String getClassName();
	public String parse(String saved)
	{
		int index = saved.indexOf("\t\t");
		String s = saved.substring(0, index);
		String[] parts = s.split("\t");
		setNames(parts[1]);
		setSprite(parts[2]);
		setX(Double.parseDouble(parts[3]));
		setY(Double.parseDouble(parts[4]));
		return saved.substring(index+2);
	}
	public String toString()
	{
		return getClassName() + "\t" + (getNames().equals("")?"-":getNames())+"\t"+getImageName()+"\t"+getX()+"\t"+getY() + "\t\t";
	}
}
