import java.io.File;
import java.util.function.Consumer;
import java.util.Arrays;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class DirectoryWatcher extends Pane
{
	private int rows;
	private String directory;
	private Pane buttons;
	private String top = "";
	private TextField name;
	private int height;
	Consumer<String> action;
	public DirectoryWatcher(String directory,int h, String def, String description, Consumer<String> action)
	{
		this(directory, h, def, description, 8, action);
	}
	public DirectoryWatcher(String directory, int h, String def, String description, int rows, Consumer<String> action)
	{
		rows++;
		height = h;
		this.rows = rows;
		this.action = action;
		this.directory = directory;
		try
		{
			setMinHeight(h);
			setMaxHeight(h);
			setPrefHeight(h);
			
			name = new TextField(def);
			name.setOnKeyPressed((a)->
			{
				updateButtons();
			});
			name.setOnKeyReleased((a)->
			{
				updateButtons();
			});
			name.setLayoutX(0);
			name.setLayoutY(0);
			name.setMinHeight(h/rows);
			name.setMaxHeight(h/rows);
			name.setPrefHeight(h/rows);
			name.minWidthProperty().bind(this.widthProperty());
			name.maxWidthProperty().bind(this.widthProperty());
			name.prefWidthProperty().bind(this.widthProperty());
			name.setPromptText(description);
			name.setOnAction(a->
			{
				if(top!="")
					action.accept(top);
			});
			
			buttons = new Pane();
			buttons.setLayoutX(0);
			buttons.setLayoutY(h/rows);
			buttons.setMinHeight(h*(rows-1)/rows);
			buttons.setMaxHeight(h*(rows-1)/rows);
			buttons.setPrefHeight(h*(rows-1)/rows);
			buttons.minWidthProperty().bind(this.widthProperty());
			buttons.maxWidthProperty().bind(this.widthProperty());
			buttons.prefWidthProperty().bind(this.widthProperty());
			getChildren().addAll(name, buttons);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e);
		}
	}
	public void setText(String s)
	{
		name.setText(s);
		updateButtons();
	}
	public void updateButtons()
	{
		buttons.getChildren().clear();
		String s = name.getText();
		if(s.equals(""))
		{
			top = "";
			return;
		}
		File[] files = new File(directory).listFiles();
		if(files.length == 0)
			return;
		String[] names = new String[files.length];
		for(int i = 0; i < names.length; i++)
			names[i] = files[i].getName();
		Arrays.sort(names, (a, b) -> {
			if(a.toLowerCase().startsWith(s.toLowerCase()))
			{
				if(b.toLowerCase().startsWith(s.toLowerCase()))
					return a.compareTo(b);
				return -1;
			}
			if(b.toLowerCase().startsWith(s.toLowerCase()))
				return 1;
			if(a.toLowerCase().contains(s.toLowerCase()))
			{
				if(b.toLowerCase().contains(s.toLowerCase()))
					return a.compareTo(b);
				return -1;
			}
			if(b.toLowerCase().contains(s.toLowerCase()))
				return 1;
			return score(b.toLowerCase(), s.toLowerCase()) - score(a.toLowerCase(), s.toLowerCase());
		});
		top = names[0];
		for(int i = 0; i < names.length && i < rows-1; i++)
		{
			Button b = new Button(names[i]);
			b.setLayoutX(0);
			b.setLayoutY(height * i / rows);
			b.setMinHeight(height/rows);
			b.setMaxHeight(height/rows);
			b.setPrefHeight(height/rows);
			b.minWidthProperty().bind(this.widthProperty());
			b.maxWidthProperty().bind(this.widthProperty());
			b.prefWidthProperty().bind(this.widthProperty());
			String str = names[i];
			b.setOnAction(a->
			{
				name.setText(str);
				action.accept(str);
			});
			buttons.getChildren().add(b);
		}
	}
	public int score(String a, String b)
	{
		 int[][] scores = new int[a.length()+1][b.length()+1];
		 for(int i = 0; i < a.length(); i++)
			for(int j = 0; j < b.length(); j++)
				if(a.charAt(i) == b.charAt(j))
					scores[i+1][j+1] = scores[i][j] + 1;
				else
					scores[i+1][j+1] = Math.max(scores[i][j+1], scores[i+1][j]);
		return scores[a.length()][b.length()];
	}
}
