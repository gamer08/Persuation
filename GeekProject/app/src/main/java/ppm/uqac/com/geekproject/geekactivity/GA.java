package ppm.uqac.com.geekproject.geekactivity;

/**
 * Created by Arnaud on 12/10/2015.
 * Cette classe rassemble les caractéristiques d'une GA
 */
public class GA {
    private String _name;
    private String _description;
    private int _level;
    private int _experience;
    private boolean _isDone;



    /**
     * Constructeur de la classe GeekActivity
     * @param _n: nom de la GA
     * @param _d: description de la GA
     * @param _lvl: niveau de la GA
     * @param _xp : expérience obtenue lorsque l'utilisateur a fait la GA
     * @param _done: indique si l'utilisateur a déjà effectué l'acitivté ou non
     */
    public GA(String _n, String _d, int _lvl, int _xp, boolean _done)
    {
        _name = _n;
        _description=_d;
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

    /**
     * Methode qui permet le retour du nom de l'activité
     * @return
     */
    public String get_name() {
        return _name;
    }

    public String get_description() {
        return _description;
    }

    public int get_level() {
        return _level;
    }

    public int get_experience() {
        return _experience;
    }

    public boolean get_isDone() {
        return _isDone;
    }

}
