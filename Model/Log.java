import javafx.application.Platform;
public abstract class Log extends Interactor
{
	private boolean onFire = false;
	public void move(int dx, int dy, Action a, Walkable w)
	{
		super.move(dx, dy, a, w);
		GameWorld.getWorld().playSound("push.mp3", new Action());
	}
	public boolean canPushUpL(Interactor i)
	{
		return check(0, -1) && ((Log)(GameWorld.getWorld().getInteractable(getX()-1, getY()))).canPushUpL(i);
	}
	public boolean canPushUpR(Interactor i)
	{
		return check(0, -1) && ((Log)(GameWorld.getWorld().getInteractable(getX()+1, getY()))).canPushUpR(i);
	}
	public boolean isWalkableUp(Interactor i)
	{
		if(canPushUpL(i) && canPushUpR(i))
		{
			((Log)(GameWorld.getWorld().getInteractable(getX()-1, getY()))).isWalkableUpL(i);
			((Log)(GameWorld.getWorld().getInteractable(getX()+1, getY()))).isWalkableUpR(i);
			tryMoveUp(new Action());
		}
		return false;
	}
	public boolean isWalkableUpL(Interactor i)
	{
		((Log)(GameWorld.getWorld().getInteractable(getX()-1, getY()))).isWalkableUpL(i);
		tryMoveUp(new Action());
		return false;
	}
	public boolean isWalkableUpR(Interactor i)
	{
		((Log)(GameWorld.getWorld().getInteractable(getX()+1, getY()))).isWalkableUpR(i);
		tryMoveUp(new Action());
		return false;
	}
	
	public boolean canPushDownL(Interactor i)
	{
		return check(0, 1) && ((Log)(GameWorld.getWorld().getInteractable(getX()-1, getY()))).canPushDownL(i);
	}
	public boolean canPushDownR(Interactor i)
	{
		return check(0, 1) && ((Log)(GameWorld.getWorld().getInteractable(getX()+1, getY()))).canPushDownR(i);
	}
	public boolean isWalkableDown(Interactor i)
	{
		if(canPushDownL(i) && canPushDownR(i))
		{
			((Log)(GameWorld.getWorld().getInteractable(getX()-1, getY()))).isWalkableDownL(i);
			((Log)(GameWorld.getWorld().getInteractable(getX()+1, getY()))).isWalkableDownR(i);
			tryMoveDown(new Action());
		}
		return false;
	}
	public boolean isWalkableDownL(Interactor i)
	{
		((Log)(GameWorld.getWorld().getInteractable(getX()-1, getY()))).isWalkableDownL(i);
		tryMoveDown(new Action());
		return false;
	}
	public boolean isWalkableDownR(Interactor i)
	{
		((Log)(GameWorld.getWorld().getInteractable(getX()+1, getY()))).isWalkableDownR(i);
		tryMoveDown(new Action());
		return false;
	}
	
	
	
	public boolean canPushLeftU(Interactor i)
	{
		return check(-1, 0) && ((Log)(GameWorld.getWorld().getInteractable(getX(), getY()-1))).canPushLeftU(i);
	}
	public boolean canPushLeftD(Interactor i)
	{
		return check(-1, 0) && ((Log)(GameWorld.getWorld().getInteractable(getX(), getY()+1))).canPushLeftD(i);
	}
	public boolean isWalkableLeft(Interactor i)
	{
		if(canPushLeftU(i) && canPushLeftD(i))
		{
			((Log)(GameWorld.getWorld().getInteractable(getX(), getY()-1))).isWalkableLeftU(i);
			((Log)(GameWorld.getWorld().getInteractable(getX(), getY()+1))).isWalkableLeftD(i);
			tryMoveLeft(new Action());
		}
		return false;
	}
	public boolean isWalkableLeftU(Interactor i)
	{
		((Log)(GameWorld.getWorld().getInteractable(getX(), getY()-1))).isWalkableLeftU(i);
		tryMoveLeft(new Action());
		return false;
	}
	public boolean isWalkableLeftD(Interactor i)
	{
		((Log)(GameWorld.getWorld().getInteractable(getX(), getY()+1))).isWalkableLeftD(i);
		tryMoveLeft(new Action());
		return false;
	}
	
	
	public boolean canPushRightU(Interactor i)
	{
		return check(1, 0) && ((Log)(GameWorld.getWorld().getInteractable(getX(), getY()-1))).canPushRightU(i);
	}
	public boolean canPushRightD(Interactor i)
	{
		return check(1, 0) && ((Log)(GameWorld.getWorld().getInteractable(getX(), getY()+1))).canPushRightD(i);
	}
	public boolean isWalkableRight(Interactor i)
	{
		if(canPushRightU(i) && canPushRightD(i))
		{
			((Log)(GameWorld.getWorld().getInteractable(getX(), getY()-1))).isWalkableRightU(i);
			((Log)(GameWorld.getWorld().getInteractable(getX(), getY()+1))).isWalkableRightD(i);
			tryMoveRight(new Action());
		}
		return false;
	}
	public boolean isWalkableRightU(Interactor i)
	{
		((Log)(GameWorld.getWorld().getInteractable(getX(), getY()-1))).isWalkableRightU(i);
		tryMoveRight(new Action());
		return false;
	}
	public boolean isWalkableRightD(Interactor i)
	{
		((Log)(GameWorld.getWorld().getInteractable(getX(), getY()+1))).isWalkableRightD(i);
		tryMoveRight(new Action());
		return false;
	}
	
	boolean check(int dx, int dy)
	{
		if(onFire)
			return false;
		return GameWorld.getWorld().getInteractable(getX()+dx, getY()+dy) == null && GameWorld.getWorld().getWalkable(getX()+dx, getY()+dy) != null && GameWorld.getWorld().getWalkable(getX()+dx, getY()+dy).isWalkable(this);
	}
	public void setOnFire()
	{
		setOnFire(new Action());
	}
	private Decoration flame = null;
	public Decoration getFlame()
	{
		return flame;
	}
	public void setOnFire(Action a)
	{
		if(onFire)
			return;
		flame = new Decoration("Fire.gif");
		flame.setX(getX());
		flame.setY(getY());
		flame.setIsTopLayer(true);
		Log l = this;
		GameWorld.getWorld().addDecoration(flame);
		onFire = true;
		GameWorld.getWorld().playSound("flame.mp3", new Action());
		new Thread() { public void run() {
				try {
					Thread.sleep((int)(600));
					Platform.runLater(()->
					{
						if(GameWorld.getWorld().getInteractable(getX(),getY())==l)
						{
							for(int i = -1; i <= 1; i++)
								for(int j = -1; j <= 1; j++)
									if(Math.abs(i+j)== 1)
									{
										Interactable o = GameWorld.getWorld().getInteractable(getX()+i, getY()+j);
										if(o != null && o.getClassName().startsWith("Log-"))
											((Log)o).setOnFire();
										
									}
							a.start();
						}
					});
				} catch(InterruptedException v) {
					System.out.println(v);
				}
			}  
		}.start();
	}
}
