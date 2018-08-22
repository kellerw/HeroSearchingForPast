import java.util.LinkedList;
public class Action
{
	private LinkedList<Step> actions = new LinkedList<>();
	public Action(Step s)
	{
		actions.add(s);
	}
	public void start()
	{
		if(actions.size()>0)
			actions.remove(0).act(this);
	}
	public Action then(Step s)
	{
		actions.add(s);
		return this;
	}
	public static interface Step
	{
		public void act(Action next);
	}
}
