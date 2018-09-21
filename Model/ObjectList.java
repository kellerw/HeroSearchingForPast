public class ObjectList
{
	//Provide a static list of all objects
	//add objects here to allow serialization
	public static final GameObject[] LIST = {
		new Decoration("sample.png"),
		new Base(),
		new Wall(),
		new Ice(),
		new LoadNew(),
		new Boulder(),
		new Memory(),
		new Platform(),
		new Lava(),
		new LogV(),
		new LogH(),
		new LogTop(),
		new LogBottom(),
		new LogLeft(),
		new LogRight()
	};
}
