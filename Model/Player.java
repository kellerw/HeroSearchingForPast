import javafx.scene.input.KeyCode;
public class Player extends Interactor
{
	public Player()
	{
		setSprite("sample.png");
	}
	public String getClassName()
	{
		return "Player";
	}
	public Player makeCopy()
	{
		return new Player();
	}
	private boolean movementEnabled = true;
	public boolean movementEnabled()
	{
		return movementEnabled;
	}
	public void disableMovement(Action next)
	{
		movementEnabled = false;
		next.start();
	}
	public void enableMovement(Action next)
	{
		movementEnabled = true;
		next.start();
	}
	public void handleKey(KeyCode key)
	{
		if(!movementEnabled())
			return;
		new Action(o->disableMovement(o)).start();
		if(key == KeyCode.UP)
			tryMoveUp(new Action(o->enableMovement(o)));
		else if(key == KeyCode.DOWN)
			tryMoveDown(new Action(o->enableMovement(o)));
		else if(key == KeyCode.LEFT)
			tryMoveLeft(new Action(o->enableMovement(o)));
		else if(key == KeyCode.RIGHT)
			tryMoveRight(new Action(o->enableMovement(o)));
		else
			new Action(o->enableMovement(o)).start();
	}
}
