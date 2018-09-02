import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
public abstract class Movable extends Walkable
{
	private Walkable base;
	public void move(int x, int y, Action then, Walkable newtile)
	{
		base = newtile;
		Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(0), (e)->{}, new KeyValue(getSprite().layoutXProperty(), getSprite().getLayoutX()), new KeyValue(getSprite().layoutYProperty(), getSprite().getLayoutY())));
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(300), (e)->{}, new KeyValue(getSprite().layoutXProperty(), getSprite().getLayoutX()+x*GameWorld.TILEWIDTH), new KeyValue(getSprite().layoutYProperty(), getSprite().getLayoutY()+y*GameWorld.TILEHEIGHT)));
		setX(getX()+x, false);
		setY(getY()+y, false);
		timeline.play();
		timeline.setOnFinished(e->then.start());
	}
	public Walkable getFloor()
	{
		return base;
	}
}
