package ppm.uqac.com.geekproject;

/**
 * Created by Arnaud on 12/10/2015.
 * Cette classe rassemble les caractéristiques d'une GeekActivity
 */
public class GeekActivity {
    private String _name;
    private String _desscription;
    private int _level;
    private int _experience;
    private boolean _isDone;


    /**
     * Constructeur de la classe GekkActivity
     * @param _n: nom de la GeekActivity
     * @param _d: description de la GeekActivity
     * @param _lvl: niveau de la GeekActivity
     * @param _xp : expérience obtenue lorsque l'utilisateur a fait la GeekActivity
     * @param _done: indique si l'utilisateur a déjà effectué l'acitivté ou non
     */
    public GeekActivity(String _n, String _d,int _lvl, int _xp, boolean _done)
    {
        _name = _n;
        _desscription=_d;
        _level=_lvl;
        _experience= _xp;
        _isDone = _done;
    }

    /**
     * Cette méthode permet de modifier la valeur du boolean _isDone lorsque l'utilisateur a fait l'activité
     * @param _done : boolean indiquant l'état de l'activité.
     */
    public void SetIsDone(boolean _done)
    {
        _isDone=_done;
    }
}
