import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
public abstract class Movable extends Walkable
{
	private Base base;
	public void tryMoveUp(Action then)
	{
		tryMove(0, -1, then);
	}
	public void tryMoveDown(Action then)
	{
		tryMove(0, 1, then);
	}
	public void tryMoveLeft(Action then)
	{
		tryMove(-1, 0, then);
	}
	public void tryMoveRight(Action then)
	{
		tryMove(1, 0, then);
	}
	public void tryMove(int x, int y, Action then)
	{
		move(x, y, then);
	}
	public void move(int x, int y, Action then)
	{
		Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(0), (e)->{}, new KeyValue(getSprite().layoutXProperty(), getSprite().getLayoutX()), new KeyValue(getSprite().layoutYProperty(), getSprite().getLayoutY())));
		setX(getX()+x);
		setY(getY()+y);
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(300), (e)->{}, new KeyValue(getSprite().layoutXProperty(), getSprite().getLayoutX()), new KeyValue(getSprite().layoutYProperty(), getSprite().getLayoutY())));
		timeline.play();
		timeline.setOnFinished(e->then.start());
	}
}
