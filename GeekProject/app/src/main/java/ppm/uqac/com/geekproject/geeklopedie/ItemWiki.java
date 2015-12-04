package ppm.uqac.com.geekproject.geeklopedie;

/**
 * Created by Valentin on 02/12/2015.
 */
public class ItemWiki
{
    private String  _name;
    private String _definition;

    public ItemWiki(String n, String d)
    {
        _name = n;
        _definition = d;
    }

    public String getName()
    {
        return _name;
    }

    public String getDefinition()
    {
        return _definition;
    }

}
