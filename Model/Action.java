public class Action
{
	private Action next = null;
	private Step step;
	public Action(Step s)
	{
		this.step = s;
		this.next = new Action();
	}
	private Action()
	{}
	public void start()
	{
		if(step!=null)
			step.act(next);
	}
	public Action then(Step s)
	{
		this.next = new Action(s);
		return this.next;
	}
	public static interface Step
	{
		public void act(Action next);
	}
}
