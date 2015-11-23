package ppm.uqac.com.geekproject.geeklopedie;

/**
 * Created by Simon on 20/10/2015.
 * Classe pour gérer le contenu de la geeklopedie
 */
public class Content {
    private String _name;
    private String _description;
    private String _url;

    /**
     * Constructeur de la classe GeekActivity
     * @param n: nom de la GA
     * @param d: description de la GA
     * @param url: url du contenu a voir.
     */
    public Content(String n, String d, String url)
    {
        _name = n;
        _description=d;
        _url = url;

    }


    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public String get_url() {
        return _url;
    }

    public void set_url(String _url) {
        this._url = _url;
    }
}
