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
		if(!movementEnabled())
			return;
		if(key == KeyCode.UP)
			tryMoveUp(new Action());
		else if(key == KeyCode.DOWN)
			tryMoveDown(new Action());
		else if(key == KeyCode.LEFT)
			tryMoveLeft(new Action());
		else if(key == KeyCode.RIGHT)
			tryMoveRight(new Action());
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
