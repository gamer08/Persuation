package ppm.uqac.com.geekproject.profile;

/**
 * Cette classe gère tout ce qui est en lien avec l'expéreience accumulée par l'utilisateur
 * et son niveau
 * Created by Valentin on 14/11/2015.
 */
public class Level
{
    double _experience;
    int _level;

    public Level()
    {
        _experience = 0;
        _level = 1; // au début de l'utilisation de l'application, le niveau de l'utilisateur est de niveau 1
    }

    public int getLevel() { return _level; }

    public double getExperience() { return _experience; }

    public boolean isLevelUp()
    {
        // Algorithme de

        if (_experience>getLevelLimit())
        {
            return true;
        }

        else
        {
            return false;
        }
    }

    public void setLevel()
    {
        if (isLevelUp())
        {
            _level +=1;
        }
    }

    public double getLevelLimit()
    {
        return _level * 50 + Math.exp(_level - 1) * 5;
    }

}
