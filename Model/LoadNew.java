import javafx.scene.layout.Pane;
public class LoadNew extends Base
{
	private String destination = "-";
	public LoadNew()
	{
		setSprite("brickportal.png");
	}
	public LoadNew makeCopy()
	{
		return new LoadNew();
	}
	public String getClassName()
	{
		return "LoadNew";
	}
	public Action onWalk(Interactor i)
	{
		if("Player".equals(i.getClassName()))
			return new Action(o->{GameWorld.getWorld().load(getDestination()); o.start();});
		return new Action(o->{GameWorld.getWorld().remove(i); o.start();});
	}
	public void setDestination(String dest)
	{
		this.destination = dest.equals("")?"-":dest;
	}
	public String getDestination()
	{
		return this.destination;
	}
	public String parse(String saved)
	{
		saved = super.parse(saved);
		int index = saved.indexOf("\t\t");
		setDestination(saved.substring(0, index));
		return saved.substring(index+2);
	}
	public String toString()
	{
		return super.toString() + getDestination() + "\t\t";
	}
	public Pane getEditor()
	{
		Pane p = super.getEditor();
		addTextField("Destination", getDestination(), p, t->setDestination(t));
		return p;
	}
}
