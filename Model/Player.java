import javafx.scene.input.KeyCode;
import javafx.application.Platform;
public class Player extends Boulder
{
	private boolean openmenu = true;
	public Player()
	{
		setSprite("hero.png");
	}
	public String getClassName()
	{
		return "Player";
	}
	public Player makeCopy()
	{
		return new Player();
	}
	private int movementEnabled = 0;
	public boolean movementEnabled()
	{
		return movementEnabled == 0;
	}
	public void disableMovement(Action next)
	{
		movementEnabled++;
		next.start();
	}
	public void enableMovement(Action next)
	{
		movementEnabled--;
		next.start();
	}
	public void enableMenu()
	{
		openmenu = true;
	}
	public void disableMenu()
	{
		openmenu = false;
	}
	private boolean goNorth;
	private boolean goSouth;
	private boolean goEast;
	private boolean goWest;
	
	public void checkUpdate(Action then)
	{
		Platform.runLater(()->
		{
			if(!movementEnabled())
			{
				if(goNorth)
					GameWorld.getWorld().executeUp();
				else if(goSouth)
					GameWorld.getWorld().executeDown();
				else if(goWest)
					GameWorld.getWorld().executeLeft();
				else if(goEast)
					GameWorld.getWorld().executeRight();
				return;
			}
			else
			{
				if(goNorth)
					tryMoveUp(new Action(o->checkUpdate(o)));
				else if(goSouth)
					tryMoveDown(new Action(o->checkUpdate(o)));
				else if(goWest)
					tryMoveLeft(new Action(o->checkUpdate(o)));
				else if(goEast)
					tryMoveRight(new Action(o->checkUpdate(o)));
			}
			then.start();
		});
	}
	public void handleKey(KeyCode key)
	{
		if(openmenu && key == KeyCode.BACK_SPACE)
			GameWorld.getWorld().showMenu();
		if(key == KeyCode.UP)
			goNorth = true;
		else if(key == KeyCode.DOWN)
			goSouth = true;
		else if(key == KeyCode.LEFT)
			goWest = true;
		else if(key == KeyCode.RIGHT)
			goEast = true;
		else if(key == KeyCode.W)
			goNorth = true;
		else if(key == KeyCode.S)
			goSouth = true;
		else if(key == KeyCode.A)
			goWest = true;
		else if(key == KeyCode.D)
			goEast = true;
		if(!movementEnabled())
		{
			if(key == KeyCode.SPACE)
				GameWorld.getWorld().executeHandler();
			else if(key == KeyCode.ENTER)
				GameWorld.getWorld().executeHandler();
		}
		else
		{
			if(key == KeyCode.SPACE)
				interact();
			else if(key == KeyCode.ENTER)
				interact();
		}
		checkUpdate(new Action());
	}
	public void handleKeyRelease(KeyCode key)
	{
		if(key == KeyCode.UP)
			goNorth = false;
		else if(key == KeyCode.DOWN)
			goSouth = false;
		else if(key == KeyCode.LEFT)
			goWest = false;
		else if(key == KeyCode.RIGHT)
			goEast = false;
		else if(key == KeyCode.W)
			goNorth = false;
		else if(key == KeyCode.S)
			goSouth = false;
		else if(key == KeyCode.A)
			goWest = false;
		else if(key == KeyCode.D)
			goEast = false;
	}
	public void tryMoveUp(Action then)
	{
		new Action(o->disableMovement(o)).then(o->super.tryMoveUp(o)).then(o->enableMovement(o)).then(then).start();
	}
	public void tryMoveDown(Action then)
	{
		new Action(o->disableMovement(o)).then(o->super.tryMoveDown(o)).then(o->enableMovement(o)).then(then).start();
	}
	public void tryMoveLeft(Action then)
	{
		new Action(o->disableMovement(o)).then(o->super.tryMoveLeft(o)).then(o->enableMovement(o)).then(then).start();
	}
	public void tryMoveRight(Action then)
	{
		new Action(o->disableMovement(o)).then(o->super.tryMoveRight(o)).then(o->enableMovement(o)).then(then).start();
	}
}
