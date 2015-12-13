package ppm.uqac.com.geekproject.profile;

/**
 * Created by Valentin on 22/11/2015.
 */
public class Badge
{
    private String _name;
    private String _description;
    private int _image;
    private boolean _got;

    public Badge(String n, String d, int i, boolean g)
    {
        _name = n;
        _description = d;
        _image = i;
        _got = g;

        System.out.println("Nouveau badge avec comme id = " + _image);
    }


    public void setDescription(String d)
    {
        _description = d;
    }

    public void setName(String n)
    {
        _name = n;
    }

    public String getDescription()
    {
        return _description;
    }

    public String getName()
    {
        return _name;
    }

    public int getImage() { return _image; }

    public boolean isGot()
    {
        return _got;
    }

    public void setGet(boolean g)
    {
        _got = g;
    }


}
