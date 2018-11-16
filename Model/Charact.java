import javafx.scene.layout.Pane;
public class Charact extends Interactor
{
	private String name = "-";
	private String text = "-";
	public Charact()
	{
		setSprite("down-character.png");
	}
	public Charact makeCopy()
	{
		return new Charact();
	}
	public String getClassName()
	{
		return "Character";
	}
	public boolean isWalkableLeft(Interactor i)
	{
		return false;
	}
	public boolean isWalkableRight(Interactor i)
	{
		return false;
	}
	public boolean isWalkableUp(Interactor i)
	{
		return false;
	}
	public boolean isWalkableDown(Interactor i)
	{
		return false;
	}
	public void onInteract(Interactor t)
	{
		Action a = new Action((o)->{GameWorld.getWorld().getPlayer().disableMenu();o.start();});
		String i = getImageName().replaceFirst("[^-]*-","icon-");
		String n = getName();
		String[] s = getText().split(";");
		for(String s2 : s)
			a.then(o->{GameWorld.getWorld().showDialog(i, n, s2, o);});
		a.then(((o)->{GameWorld.getWorld().getPlayer().enableMenu();o.start();}));
		if(getName().matches(" *Dragon *"))
		{
			Decoration d = new Decoration("start.png");
			d.setX(0);
			d.setY(0);
			d.setIsTopLayer(true);
			a.then((o)->{GameWorld.getWorld().addDecoration(new Decoration("start.png"));});
		}
		a.start();
	}
	public String getName()
	{
		return name;
	}
	public void setName(String s)
	{
		name = s;
	}
	public String getText()
	{
		return text;
	}
	public void setText(String s)
	{
		text = s;
	}
	public void onInteractUp(Interactor i)
	{
		setSprite(getImageName().replaceFirst("[^-]*-","down-"));
		onInteract(i);
	}
	public void onInteractDown(Interactor i)
	{
		setSprite(getImageName().replaceFirst("[^-]*-","up-"));
		onInteract(i);
	}
	public void onInteractLeft(Interactor i)
	{
		setSprite(getImageName().replaceFirst("[^-]*-","right-"));
		onInteract(i);
	}
	public void onInteractRight(Interactor i)
	{
		setSprite(getImageName().replaceFirst("[^-]*-","left-"));
		onInteract(i);
	}
	public String parse(String saved)
	{
		saved = super.parse(saved);
		int index = saved.indexOf("\t\t");
		String[] p = saved.substring(0, index).split("\t");
		setText(p[0]);
		setName(p[1]);
		return saved.substring(index+2);
	}
	public String toString()
	{
		return super.toString() + getText() + "\t "+ getName() + "\t\t";
	}
	public Pane getEditor()
	{
		Pane p = super.getEditor();
		addTextField("Name", getName(), p, t->setName(t));
		addTextField("Text", getText(), p, t->setText(t));
		return p;
	}
}
