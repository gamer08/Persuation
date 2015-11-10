package ppm.uqac.com.geekproject;
/**
 * Classe choice qui représente un choix d'une question.
 */
public class Choice
{
    private  String _description;
    private  int _weight;

    public Choice()
    {
        _description ="";
        _weight =-1;
    }

    public String description()
    {
        return _description;
    }

    public void setDescription(String value)
    {
        _description = value;
    }

    public int weight()
    {
        return _weight;
    }

    public void setWeight(int value)
    {
        _weight = value;
    }
}
