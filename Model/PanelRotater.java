public class PanelRotater extends Interactable
{
	public PanelRotater()
	{
		setSprite("rotatebutton.png");
	}
	public PanelRotater makeCopy()
	{
		return new PanelRotater();
	}
	public String getClassName()
	{
		return "PanelRotater";
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
	public void onInteract(Interactor i)
	{
		((MovePad)(GameWorld.getWorld().getBase((";"+getNames()+";").replaceAll(".*;Rotate-","").replaceAll(";.*","")))).rotate();
	}
}
