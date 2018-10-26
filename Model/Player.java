import javafx.scene.input.KeyCode;
public class Player extends Interactor
{
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
	public void handleKey(KeyCode key)
	{
		if(key == KeyCode.BACK_SPACE)
			GameWorld.getWorld().showMenu();
		if(!movementEnabled())
		{
			if(key == KeyCode.UP)
				GameWorld.getWorld().executeUp();
			else if(key == KeyCode.DOWN)
				GameWorld.getWorld().executeDown();
			else if(key == KeyCode.LEFT)
				GameWorld.getWorld().executeLeft();
			else if(key == KeyCode.RIGHT)
				GameWorld.getWorld().executeRight();
			else if(key == KeyCode.W)
				GameWorld.getWorld().executeUp();
			else if(key == KeyCode.S)
				GameWorld.getWorld().executeDown();
			else if(key == KeyCode.A)
				GameWorld.getWorld().executeLeft();
			else if(key == KeyCode.D)
				GameWorld.getWorld().executeRight();
			else if(key == KeyCode.SPACE)
				GameWorld.getWorld().executeHandler();
			return;
		}
		if(key == KeyCode.UP)
			tryMoveUp(new Action());
		else if(key == KeyCode.DOWN)
			tryMoveDown(new Action());
		else if(key == KeyCode.LEFT)
			tryMoveLeft(new Action());
		else if(key == KeyCode.RIGHT)
			tryMoveRight(new Action());
		else if(key == KeyCode.W)
			tryMoveUp(new Action());
		else if(key == KeyCode.S)
			tryMoveDown(new Action());
		else if(key == KeyCode.A)
			tryMoveLeft(new Action());
		else if(key == KeyCode.D)
			tryMoveRight(new Action());
		else if(key == KeyCode.SPACE)
			interact();
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
