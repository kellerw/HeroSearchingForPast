import javafx.scene.layout.Pane;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import javafx.scene.Group;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.transform.Scale; 
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.shape.Rectangle;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
public class GameWorld extends Pane
{
	//width and height of each tile
	public static final int TILEWIDTH = 100;
	public static final int TILEHEIGHT = 100;
	//number of tiles wide/tall world is
	public static final int TILESWIDE = 25;
	public static final int TILESHIGH = 19;
	private static GameWorld world;
	private Player hero;
	private Group group;
	String lastlevel = "Start";
	private Pane pane;
	private Pane decorationPaneBottom;
	private Pane decorationPaneTop;
	private Pane interactablePane;
	private Pane tilePane;
	private Pane basePane;
	private Interactable[][] interactables = new Interactable[1][1];
	private Tile[][] tiles = new Tile[1][1];
	private Base[][] bases = new Base[1][1];
	private ArrayList<Decoration> decorations = new ArrayList<>();
	private SimpleIntegerProperty startx = new SimpleIntegerProperty(0);
	private SimpleIntegerProperty starty = new SimpleIntegerProperty(0);
	private Scale scale;
	private boolean loading = false;
	private HashMap<String, Base> basemap = new HashMap<>();
	private ImageView menu;
	public static GameWorld getWorld()
	{
		if(world == null)
			world = new GameWorld();
		return world;
	}
	public double scaleX()
	{
		return scale.getX();
	}
	public double scaleY()
	{
		return scale.getY();
	}
	public int getWide()
	{
		return tiles.length + (int)getLeft();
	}
	public int getHigh()
	{
		return tiles[0].length + (int)getTop();
	}
	private GameWorld()
	{
		scale = new Scale();
		pane = new Pane();
		menu = new ImageView();
		menu.layoutXProperty().bind(this.layoutXProperty());
		menu.layoutYProperty().bind(this.layoutYProperty());
		menu.fitWidthProperty().bind(this.widthProperty());
		menu.fitHeightProperty().bind(this.heightProperty());
		pane.minWidthProperty().bind(this.widthProperty().divide(scale.xProperty()));
		pane.maxWidthProperty().bind(this.widthProperty().divide(scale.xProperty()));
		pane.prefWidthProperty().bind(this.widthProperty().divide(scale.xProperty()));
		pane.minHeightProperty().bind(this.heightProperty().divide(scale.yProperty()));
		pane.maxHeightProperty().bind(this.heightProperty().divide(scale.yProperty()));
		pane.prefHeightProperty().bind(this.heightProperty().divide(scale.yProperty()));
		decorationPaneBottom = getPane();
		decorationPaneTop = getPane();
		interactablePane = getPane();
		tilePane = getPane();
		basePane = getPane();
		this.getChildren().add(pane);
		if(!GameObject.LIVE)
		{
			this.scaleXProperty().bind(this.widthProperty().divide(TILEWIDTH*TILESWIDE));
			this.scaleYProperty().bind(this.heightProperty().divide(TILEHEIGHT*TILESHIGH));
			hero = new Player();
		}
		pane.getChildren().add(basePane);
		pane.getChildren().add(decorationPaneBottom);
		pane.getChildren().add(tilePane);
		pane.getChildren().add(interactablePane);
		if(hero != null)
			interactablePane.getChildren().add(hero.getSprite());
		pane.getChildren().add(decorationPaneTop);
		if(hero != null)
		{
			pane.layoutXProperty().bind(hero.getSprite().layoutXProperty().multiply(-1).add(new SimpleDoubleProperty(TILEWIDTH*(TILESWIDE-1)/2.0).multiply(this.scaleXProperty())));
			pane.layoutYProperty().bind(hero.getSprite().layoutYProperty().multiply(-1).add(new SimpleDoubleProperty(TILEHEIGHT*(TILESHIGH-1)/2.0).multiply(this.scaleYProperty())));
		}
		pane.getTransforms().addAll(scale);
	}
	public void setWidth(int w1, int w2)
	{
		int v = Math.max(1, w2 - w1);
		if(hero == null)
		{
			scale.xProperty().bind(this.widthProperty().divide(v*TILEWIDTH));
			//this.scaleXProperty().bind(this.widthProperty().divide(v*TILEWIDTH));
			pane.layoutXProperty().bind(new SimpleDoubleProperty(-TILEWIDTH*w1).multiply(scale.xProperty()));//.multiply(this.scaleXProperty()));
		}
		Interactable[][] ints = new Interactable[v][interactables[0].length];
		for(int i = Math.max(startx.getValue(), w1); i < Math.min(w2, startx.getValue() - interactables.length); i++)
			for(int j = 0; j < ints[i].length; j++)
				ints[i-w1][j] = interactables[i-startx.getValue()][j];
		Tile[][] ts = new Tile[v][interactables[0].length];
		for(int i = Math.max(startx.getValue(), w1); i < Math.min(w2, startx.getValue() - interactables.length); i++)
			for(int j = 0; j < ints[i].length; j++)
				ts[i-w1][j] = tiles[i-startx.getValue()][j];
		Base[][] bs = new Base[v][interactables[0].length];
		for(int i = Math.max(startx.getValue(), w1); i < Math.min(w2, startx.getValue() - interactables.length); i++)
			for(int j = 0; j < ints[i].length; j++)
				bs[i-w1][j] = bases[i-startx.getValue()][j];
		interactables = ints;
		tiles = ts;
		bases = bs;
		startx.setValue(w1);
	}
	public double getLeft()
	{
		return startx.getValue();
	}
	public double getTop()
	{
		return starty.getValue();
	}
	public Pane getPane()
	{
		Pane p = new Pane();
		p.minWidthProperty().bind(pane.widthProperty());
		p.maxWidthProperty().bind(pane.widthProperty());
		p.prefWidthProperty().bind(pane.widthProperty());
		p.minHeightProperty().bind(pane.heightProperty());
		p.maxHeightProperty().bind(pane.heightProperty());
		p.prefHeightProperty().bind(pane.heightProperty());
		/*Rectangle r = new Rectangle(0, 0, 0, 0);
		r.layoutXProperty().bind(startx.multiply(TILEWIDTH));
		r.layoutYProperty().bind(starty.multiply(TILEHEIGHT));
		r.widthProperty().bind(p.widthProperty().subtract(r.layoutXProperty()));
		r.heightProperty().bind(p.heightProperty().subtract(r.layoutYProperty()));
		r.setFill(Color.TRANSPARENT);
		p.getChildren().add(r);*/
		return p;
	}
	public void remove(GameObject o)
	{
		if(o.isBase())
		{
			for(int i = 0; i < tiles.length; i++)
				for(int j = 0; j < tiles[i].length; j++)
					if(bases[i][j] == o)
						bases[i][j] = null;
			basePane.getChildren().remove(o.getSprite());
		}
		if(o.isTile())
		{
			for(int i = 0; i < tiles.length; i++)
				for(int j = 0; j < tiles[i].length; j++)
					if(tiles[i][j] == o)
						tiles[i][j] = null;
			tilePane.getChildren().remove(o.getSprite());
		}
		if(o.isInteractable())
		{
			for(int i = 0; i < tiles.length; i++)
				for(int j = 0; j < tiles[i].length; j++)
					if(interactables[i][j] == o)
						interactables[i][j] = null;
			interactablePane.getChildren().remove(o.getSprite());
		}
		if(o.isDecoration())
		{
			decorations.remove(o);
			decorationPaneTop.getChildren().remove(o.getSprite());
			decorationPaneBottom.getChildren().remove(o.getSprite());
		}
	}
	public void setHeight(int h1, int h2)
	{
		int v = Math.max(1, h2 - h1);
		if(hero == null)
		{
			scale.yProperty().bind(this.heightProperty().divide(v*TILEHEIGHT));
			//this.scaleYProperty().bind(this.heightProperty().divide(v*TILEHEIGHT));
			pane.layoutYProperty().bind(new SimpleDoubleProperty(-TILEHEIGHT*h1).multiply(scale.yProperty()));//.multiply(this.scaleYProperty()));
		}
		Interactable[][] ints = new Interactable[interactables.length][v];
		for(int i = 0; i < ints.length; i++)
			for(int j = Math.max(starty.getValue(), h1); j < Math.min(h2, starty.getValue() - interactables[i].length); j++)
			{
// 				System.out.println("ints[i][j-h1] = interactables[i][j-starty.getValue()]");
// 				System.out.println("ints["+i+"]["+j+"-"+h1+"] = interactables["+i+"]["+j+"-"+starty.getValue()+"]");
				ints[i][j-h1] = interactables[i][j-starty.getValue()];
			}
		Tile[][] ts = new Tile[interactables.length][v];
		for(int i = 0; i < ints.length; i++)
			for(int j = Math.max(starty.getValue(), h1); j < Math.min(h2, starty.getValue() - interactables[i].length); j++)
				ts[i][j-h1] = tiles[i][j-starty.getValue()];
		Base[][] bs = new Base[interactables.length][v];
		for(int i = 0; i < ints.length; i++)
			for(int j = Math.max(starty.getValue(), h1); j < Math.min(h2, starty.getValue() - interactables[i].length); j++)
				bs[i][j-h1] = bases[i][j-starty.getValue()];
		interactables = ints;
		tiles = ts;
		bases = bs;
		starty.setValue(h1);
	}
	public void expandTop()
	{
		setHeight(starty.getValue() - 1, starty.getValue() + tiles[0].length);
	}
	public void expandBottom()
	{
		setHeight(starty.getValue(), starty.getValue() + tiles[0].length + 1);
	}
	public void expandLeft()
	{
		setWidth(startx.getValue() - 1, startx.getValue() + tiles.length);
	}
	public void expandRight()
	{
		setWidth(startx.getValue(), startx.getValue() + tiles.length + 1);
	}
	public void addBase(Base b)
	{
		if(bases[(int)b.getX() - startx.getValue()][(int)b.getY() - starty.getValue()] != null)
			remove(bases[(int)b.getX() - startx.getValue()][(int)b.getY() - starty.getValue()]);
		bases[(int)b.getX() - startx.getValue()][(int)b.getY() - starty.getValue()] = b;
		basePane.getChildren().add(b.getSprite());
		for(String s: b.getNames().split(";"))
			basemap.put(s, b);
		if(hero != null && (";"+b.getNames()+";").toLowerCase().contains((";From-"+lastlevel+";").toLowerCase()))
		{
			hero.setX(b.getX());
			hero.setY(b.getY());
		}
	}
	public void addTile(Tile t)
	{
		if(tiles[(int)t.getX() - startx.getValue()][(int)t.getY() - starty.getValue()] != null)
			remove(tiles[(int)t.getX() - startx.getValue()][(int)t.getY() - starty.getValue()]);
		tiles[(int)t.getX() - startx.getValue()][(int)t.getY() - starty.getValue()] = t;
		tilePane.getChildren().add(t.getSprite());
	}
	public void addInteractable(Interactable i)
	{
		if(interactables[(int)i.getX() - startx.getValue()][(int)i.getY() - starty.getValue()] != null)
			remove(interactables[(int)i.getX() - startx.getValue()][(int)i.getY() - starty.getValue()]);
		interactables[(int)i.getX() - startx.getValue()][(int)i.getY() - starty.getValue()] = i;
		interactablePane.getChildren().add(interactablePane.getChildren().size()-(hero==null?0:1), i.getSprite());
	}
	public Player getPlayer()
	{
		return hero;
	}
	public void addDecoration(Decoration decoration)
	{
		decorations.add(decoration);
		(decoration.isTopLayer()?decorationPaneTop:decorationPaneBottom).getChildren().add(decoration.getSprite());
	}
	private static HashMap<String, MediaPlayer> mediamap = new HashMap<>();
	private MediaPlayer getMedia(String s)
	{
		if(mediamap.containsKey(s))
		{
			MediaPlayer m = mediamap.get(s);
			m.seek(javafx.util.Duration.ZERO);
			return m;
		}
		MediaPlayer m = new MediaPlayer(new Media(getClass().getResource(s).toString()));
		mediamap.put(s, m);
		return m;
	}
	public void playSound(String sound, Action then)
	{
		MediaPlayer a =getMedia(sound);
			a.setOnEndOfMedia(()->
			{
				then.start();
			}
		);
		a.setOnError(()->
		{
			System.out.println(a.getError());
		});
		a.play();
	}
	private MediaPlayer music = null;
	private String last = "";
	public void setMusic(String sound)
	{
		if("null".equals(sound) || "".equals(sound))
			sound = null;
		if(hero == null)
		{
			last = sound;
			return;
		}
		if(sound != null)
		{
			if(sound.equals(last))
				return;
			if(music != null)
				music.stop();
			if(hero != null)
				music = getMedia(sound);
			if(music != null)
				music.setOnEndOfMedia(()->
				{
					music.seek(javafx.util.Duration.ZERO);
				}
			);
			if(hero != null)
				music.play();
		}
		else
		{
			if(music != null)
				music.stop();
			music = null;
		}
		last = sound;
	}
	public String getSong()
	{
		return last;
	}
	public void load(String file)
	{
		
		if(hero != null)
		{
			hero = new Player();
			pane.layoutXProperty().bind(hero.getSprite().layoutXProperty().multiply(-1).add(new SimpleDoubleProperty(TILEWIDTH*(TILESWIDE-1)/2.0).multiply(this.scaleXProperty())));
			pane.layoutYProperty().bind(hero.getSprite().layoutYProperty().multiply(-1).add(new SimpleDoubleProperty(TILEHEIGHT*(TILESHIGH-1)/2.0).multiply(this.scaleYProperty())));
		}
		loading = true;
		Scanner scan = null;
		setWidth(0, 1);
		setHeight(0, 1);
		bases = new Base[bases.length][bases[0].length];
		tiles = new Tile[bases.length][bases[0].length];
		interactables = new Interactable[bases.length][bases[0].length];
		basemap = new HashMap<>();
		decorations.clear();
		decorationPaneBottom.getChildren().clear();
		decorationPaneTop.getChildren().clear();
		tilePane.getChildren().clear();
		interactablePane.getChildren().clear();
		basePane.getChildren().clear();
		if(hero != null)
			interactablePane.getChildren().add(hero.getSprite());
		try
		{
			if(GameObject.LIVE)
				scan = new Scanner(new File("Datafiles/"+file+".data"));
			else
				scan = new Scanner(getClass().getResource(file+".data").openStream());
			setWidth(Integer.parseInt(scan.nextLine()),Integer.parseInt(scan.nextLine()));
			setHeight(Integer.parseInt(scan.nextLine()),Integer.parseInt(scan.nextLine()));
			setMusic(scan.nextLine());
			int count = 2;
			String s = null;
			try
			{
				while(!"".equals(s = scan.nextLine()))
				{
					count++;
					try
					{
						addDecoration((Decoration)parseObject(s));
					}
					catch(DoNotCreateException dnce){}
				}
				count++;
				bases = new Base[bases.length][bases[0].length];
				while(!"".equals(s = scan.nextLine()))
				{
					count++;
					try
					{
						addBase((Base)parseObject(s));
					}
					catch(DoNotCreateException dnce){}
				}
				count++;
				tiles = new Tile[bases.length][bases[0].length];
				while(!"".equals(s = scan.nextLine()))
				{
					count++;
					try
					{
						addTile((Tile)parseObject(s));
					}
					catch(DoNotCreateException dnce){}
				}
				count++;
				interactables = new Interactable[bases.length][bases[0].length];
				while(!"".equals(s = scan.nextLine()))
				{
					count++;
					try
					{
						addInteractable((Interactable)parseObject(s));
					}
					catch(DoNotCreateException dnce){}
				}
				count++;
			}
			catch(RuntimeException e)
			{
				System.out.println("On line "+count);
				System.out.println(s);
				throw e;
			}
		} catch(IOException e){if(hero != null) e.printStackTrace();}
		finally {if(scan != null) scan.close();}
		lastlevel = file;
		loading = false;
		if(Main.enablesaves)
			savesave();
	}
	public void loadsave()
	{
		File f = new File("SearchingForAPast.savefile");
		Scanner scan = null;
		try
		{
			scan = new Scanner(f);
			String level = scan.nextLine();
			Memory.found = new java.util.HashSet<String>();
			while(scan.hasNextLine())
				Memory.found.add(scan.nextLine());
			lastlevel = "Start";
			load(level);
		}
		catch(Exception e)
		{}
		finally
		{
			if(scan != null)
				scan.close();
		}
	}
	public void savesave()
	{
		try
		{
			PrintWriter writer = new PrintWriter("SearchingForAPast.savefile", "UTF-8");
			writer.println(lastlevel);
			for(String s : Memory.found)
				writer.println(s);
			writer.close();
			}
		catch(Exception e){}
	}
	public void fadeIn(String destination)
	{
		hero.disableMovement(new Action());
		Rectangle r = new Rectangle(0, 0, TILESWIDE*TILEWIDTH,TILESHIGH*TILEHEIGHT);
		r.layoutXProperty().bind(hero.getSprite().layoutXProperty().multiply(-1).multiply(this.scaleXProperty()));
		r.layoutYProperty().bind(hero.getSprite().layoutYProperty().multiply(-1).multiply(this.scaleYProperty()));
		r.setFill(javafx.scene.paint.Color.BLACK);
		r.setOpacity(0);
		getChildren().add(r);
		Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(0), (e)->{}, new KeyValue(r.opacityProperty(), 0.0)));
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(250), (e)->{}, new KeyValue(r.opacityProperty(), 1.0)));
		timeline.setOnFinished(e->
		{
			load(destination);
			hero.disableMovement(new Action());
			Timeline timeline2 = new Timeline();
			timeline2.setCycleCount(1);
			timeline2.getKeyFrames().add(new KeyFrame(Duration.millis(0), (e2)->{}, new KeyValue(r.opacityProperty(), 1.0)));
			timeline2.getKeyFrames().add(new KeyFrame(Duration.millis(250), (e2)->{}, new KeyValue(r.opacityProperty(), 0.0)));
			timeline2.setOnFinished(e2->
			{
				getChildren().remove(r);
				hero.enableMovement(new Action());
			});
			timeline2.play();
		});
		timeline.play();
	}
	public void showCutscene(String filename)
	{
		showCutscene(filename, 5, new Action());
	}
	public void showCutscene(String filename, int layer, Action then)
	{
		hero.disableMovement(new Action());
		hero.disableMenu();
		try
		{
			Media media = new Media(getClass().getResource(filename).toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(media);
			mediaPlayer.setAutoPlay(true);
			MediaView mediaView = new MediaView(mediaPlayer);
			mediaView.layoutXProperty().bind(this.layoutXProperty());
			mediaView.layoutYProperty().bind(this.layoutYProperty());
			mediaView.fitWidthProperty().bind(this.widthProperty());
			mediaView.fitHeightProperty().bind(this.heightProperty());
			getChildren().add(mediaView);
			setLayer(-1);
			mediaPlayer.setOnReady(()->
			{
				double l = media.getDuration().toSeconds();
				Action h = new Action(o->
				{
					setLayer(layer);
					hero.enableMovement(new Action());
					getChildren().remove(mediaView);
					o.start();
				}).then(o->{then.start();o.start();});
				handler = h;
				new Thread() { public void run() {
						try {
							Thread.sleep((int)(1000*l));
							Platform.runLater(()->
							{
								if(handler == h)
								{
									executeHandler();
									hero.enableMenu();
								}
							});
						} catch(InterruptedException v) {
							System.out.println(v);
						}
					}  
				}.start();
			});
		}
		catch(Exception e)
		{
			System.out.println(e);
			hero.enableMovement(new Action());
		}
	}
	private interface Handle
	{
		public void handle();
	}
	private Handle up = ()->{};
	private Handle down = ()->{};
	private Handle left = ()->{};
	private Handle right = ()->{};
	private Action handler = new Action();
	public void executeHandler()
	{
		handler.start();
	}
	public void executeUp()
	{
		up.handle();
	}
	public void executeRight()
	{
		right.handle();
	}
	public void executeLeft()
	{
		left.handle();
	}
	public void executeDown()
	{
		down.handle();
	}
	private int menuitem = 0;
	public void showMenu()
	{
		hero.disableMenu();
		setLayer(-1);
		menuitem = -1;
		hero.disableMovement(new Action());
		down = ()->{menuitem = (menuitem+1)%5;try{menu.setImage(new Image(getClass().getResource("menu_"+menuitem+".png").openStream()));}catch(Exception e){}};
		up = ()->{menuitem = (menuitem+4)%5;try{menu.setImage(new Image(getClass().getResource("menu_"+menuitem+".png").openStream()));}catch(Exception e){}};
		this.getChildren().add(menu);
		executeDown();
		handler = new Action(o->{
			if(menuitem==3)
			{
				showMainMenu();
			}
			else if(menuitem == 4)
			{
				showMemories();
			}
			else
			{
				hero.enableMenu();
				this.getChildren().remove(menu);
				setLayer(5);
				hero.enableMovement(new Action());
				if(menuitem == 1)
				{
					restartLevel();
				}
				else if(menuitem == 2)
				{
					lastlevel = "end";
					load("overworld");
				}
			}
		});
	}
	public void showMainMenu()
	{
		menuitem = -1;
		down = ()->{menuitem = (menuitem+1)%4;try{menu.setImage(new Image(getClass().getResource("mainmenu_"+menuitem+".png").openStream()));}catch(Exception e){}};
		up = ()->{menuitem = (menuitem+3)%4;try{menu.setImage(new Image(getClass().getResource("mainmenu_"+menuitem+".png").openStream()));}catch(Exception e){}};
		executeDown();
		handler = new Action(o->{
			if(menuitem==0)
			{
				Memory.found = new java.util.HashSet<>();
				world = new GameWorld();
				Main.setWorld();
			}
			else if(menuitem == 1)
			{
				hero.enableMenu();
				this.getChildren().remove(menu);
				setLayer(5);
				hero.enableMovement(new Action());
			}
			else if(menuitem == 2)
			{
				try{menu.setImage(new Image(getClass().getResource("Credits.png").openStream()));}catch(Exception e){}
				down = ()->{};
				up = ()->{};
				handler = new Action(o2->showMainMenu());
			}
			else if(menuitem == 3)
			{
				System.exit(0);
			}
		});
	}
	public static final String[] cutscenes = new String[]{"EndCutscene.mp4","ice.mp4", "CastleCutscene.mp4", "ForestCutscene.mp4", "", ""};
	public void setMemoryHandler()
	{
		handler = new Action(o->
		{
			if(menuitem==6)
			{
				hero.enableMenu();
				hero.enableMovement(new Action());
				this.getChildren().remove(menu);
				showMenu();
			}
			else
			{
				getChildren().remove(menu);
				showCutscene(cutscenes[menuitem], -1, new Action((o2)->{getChildren().add(menu); hero.disableMenu();setMemoryHandler();o2.start();}));
			}
		});
	}
	public void showMemories()
	{
		menuitem = -1;
		down = ()->{do {menuitem = (menuitem+1)%7;} while(menuitem != 6 && !Memory.found.contains(cutscenes[menuitem]));try{menu.setImage(new Image(getClass().getResource("Cutscenes_"+menuitem+".png").openStream()));}catch(Exception e){}};
		up = ()->{do {menuitem = (menuitem+6)%7;} while(menuitem != 6 && !Memory.found.contains(cutscenes[menuitem]));try{menu.setImage(new Image(getClass().getResource("Cutscenes_"+menuitem+".png").openStream()));}catch(Exception e){}};
		executeDown();
		setMemoryHandler();
	}
	public void showDialog(String speaker, String name, String text, Action then)
	{
		hero.disableMovement(new Action());
		Pane t = new Pane();
		int H = 200;
		int SIZE = 100;
		t.layoutYProperty().bind(this.heightProperty().subtract(H));
		t.minWidthProperty().bind(this.widthProperty().divide(scale.xProperty()));
		t.maxWidthProperty().bind(this.widthProperty().divide(scale.xProperty()));
		t.prefWidthProperty().bind(this.widthProperty().divide(scale.xProperty()));
		t.setMinHeight(H);
		t.setMaxHeight(H);
		t.setPrefHeight(H); 
		Rectangle r = new Rectangle();
		r.widthProperty().bind(this.widthProperty());
		r.setLayoutY(-SIZE/2);
		r.setHeight(H+SIZE);
		r.setArcWidth(20);
		r.setArcHeight(20);
		r.setFill(javafx.scene.paint.Color.web("0xCCCCCC"));
		t.getChildren().add(r);
		ImageView img = new ImageView();
		img.setLayoutX(0);
		img.setLayoutY(-SIZE);
		img.setFitWidth(SIZE);
		img.setFitHeight(SIZE);
		try{img.setImage(new Image(getClass().getResource(speaker).openStream()));}catch(Exception e){}
		t.getChildren().add(img);
		r = new Rectangle();
		r.widthProperty().bind(t.widthProperty().divide(2).subtract(SIZE));
		r.setLayoutY(-SIZE/2);
		r.setLayoutX(SIZE);
		r.setHeight(SIZE/2);
		r.setArcWidth(20);
		r.setArcHeight(20);
		r.setFill(javafx.scene.paint.Color.web("0x999999"));
		t.getChildren().add(r);
		Text l = new Text(name);
		l.setTextAlignment(TextAlignment.JUSTIFY);
		l.wrappingWidthProperty().bind(t.widthProperty().divide(2).subtract(SIZE + 20));
		l.setLayoutY(-SIZE/2 + 35);
		l.setLayoutX(SIZE + 10);
		l.setStyle("-fx-font: 30 arial;");
		t.getChildren().add(l);
		l = new Text(text);
		l.setTextAlignment(TextAlignment.JUSTIFY);
		l.wrappingWidthProperty().bind(this.widthProperty().subtract(20));
		l.setLayoutX(10);
		l.setLayoutY(60);
		l.setStyle("-fx-font: 30 arial;");
		t.getChildren().add(l);
		this.getChildren().add(t);
		handler = new Action(o->{hero.enableMovement(new Action()); this.getChildren().remove(t); setLayer(5); then.start(); o.start();});
	}
	public void save(String file)
	{
		PrintWriter out = null;
		try
		{
			out = new PrintWriter(new File("Datafiles/"+file+".data"));
			out.println((int)getLeft());
			out.println((int)getWide());
			out.println((int)getTop());
			out.println((int)getHigh());
			out.println(last);
			for(Decoration d : decorations)
				out.println(d);
			out.println();
			for(int i = 0; i < bases.length; i++)
				for(int j = 0; j < bases[i].length; j++)
					if(bases[i][j] != null)
						out.println(bases[i][j]);
			out.println();
			for(int i = 0; i < bases.length; i++)
				for(int j = 0; j < bases[i].length; j++)
					if(tiles[i][j] != null)
						out.println(tiles[i][j]);
			out.println();
			for(int i = 0; i < bases.length; i++)
				for(int j = 0; j < bases[i].length; j++)
					if(interactables[i][j] != null)
						out.println(interactables[i][j]);
			out.println();
		} catch(IOException e){e.printStackTrace();}
		finally {if(out != null) out.close();}
	}
	public GameObject parseObject(String object)
	{
		String name = object.substring(0, object.indexOf('\t'));
		for(GameObject go : ObjectList.LIST)
			if(name.equals(go.getClassName()))
			{
				GameObject res = go.makeCopy();
				res.parse(object);
				return res;
			}
		throw new IllegalArgumentException("Could not parse type: "+name);
	}
	public Tile getTile(double x, double y)
	{
		if(loading)
			return null;
		return fetch(tiles, x, y);
	}
	public Base getBase(String n)
	{
		if(basemap.containsKey(n))
			return basemap.get(n);
		return null;
	}
	public Base getBase(double x, double y)
	{
		if(loading)
			return null;
		return fetch(bases, x, y);
	}
	public Walkable getWalkable(double x, double y)
	{
		if(loading)
			return null;
		Tile tile = getTile(x, y);
		return tile==null?getBase(x,y):tile;
	}
	public Interactable getInteractable(double x, double y)
	{
		if(loading)
			return null;
		return fetch(interactables, x, y);
	}
	public void setLayer(int layer)
	{
		pane.getChildren().clear();
		if(layer > -1) pane.getChildren().add(basePane);
		if(layer > 0) pane.getChildren().add(decorationPaneBottom);
		if(layer > 1) pane.getChildren().add(tilePane);
		if(layer > 2) pane.getChildren().add(interactablePane);
		if(layer > 3) pane.getChildren().add(decorationPaneTop);
	}
	public <T> T fetch(T[][] arr, double xv, double yv)
	{
		int x = (int) xv - startx.getValue();
		int y = (int) yv - starty.getValue();
		if( x < 0 || y < 0 || x >= arr.length || y >= arr[x].length)
			return null;
		return arr[x][y];
	}
	public void moveInteractable(Interactable i, int ox, int oy, int nx, int ny)
	{
		if(!interactablePane.getChildren().contains(i.getSprite()))
			return; //hopefully fixes a bug where invisible things from before we restarted the level are still floating around
		ox -= startx.getValue();
		nx -= startx.getValue();
		oy -= starty.getValue();
		ny -= starty.getValue();
		if(ox >= 0 && ox < interactables.length && oy >= 0 && oy < interactables[ox].length && interactables[ox][oy] == i)
			interactables[ox][oy] = null;
		if(nx >= 0 && nx < interactables.length && ny >= 0 && ny < interactables[nx].length)
			interactables[nx][ny] = i;
	}
	public void restartLevel()
	{
		String t = lastlevel;
		lastlevel = "Start";
		load(t);
	}
}
