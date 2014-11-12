/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author wence
 */
public class ConectionDB_Linux {

//variables miembro
    private String usuario;
    private String clave;
    private String url;
    private String driverClassName;
    private Connection conn = null;
    private Statement estancia;
    public Object objeto;

    private ResultSet rset;
    public String id_medico;

//CONSTRUCTORES
    //Constructor que toma los datos de conexion por medio de parametros
    public ConectionDB_Linux(String usuario, String clave, String url, String driverClassName) {
        this.usuario = usuario;
        this.clave = clave;
        this.url = url;
        this.driverClassName = driverClassName;
    }

    //Constructor que crea la conexion sin parametros con unos definidos en la clase
    //(meter los datos correspondientes)
    public ConectionDB_Linux() {
        //poner los datos apropiados

        /* this.usuario = "isemusr";
         this.clave = "AfQaufdC7S";
         this.url = "jdbc:mysql://162.209.67.250:3306/isemcc";
         this.driverClassName = "org.gjt.mm.mysql.Driver";*/
        this.usuario = "root";
        this.clave = "eve9397";
        //this.url = "jdbc:mysql://localhost:3306/gnklmex_consolidada2";
        this.url = "jdbc:mysql://192.168.0.180:3306/gnklmex_consolidada2";
        this.driverClassName = "org.gjt.mm.mysql.Driver";
    }

    //metodos para recuperar los datos de conexion
    public String getClave() {
        return clave;
    }

    public String getUrl() {
        return url;
    }

    public String getUsuario() {
        return usuario;
    }

    public Connection getConn() {
        try {
            Class.forName(this.driverClassName).newInstance();
            this.conn = DriverManager.getConnection(this.url, this.usuario, this.clave);
//            System.out.println("Se conectó de FORMA EXITOSA ");

        } catch (Exception err) {
            System.out.println("Error " + err.getMessage());
        }
        return conn;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    //metodos para establecer los valores de conexion
    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsuario(String usuario) throws SQLException {
        this.usuario = usuario;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

//la conexion propiamente dicha
    public void conectar() throws SQLException {
        try {
            Class.forName(this.driverClassName).newInstance();
            this.conn = DriverManager.getConnection(this.url, this.usuario, this.clave);
//            System.out.println("Se conectó de FORMA EXITOSA ");

        } catch (Exception err) {
            System.out.println("Error " + err.getMessage());
        }
    }
    //Cerrar la conexion

    public void cierraConexion() throws SQLException {
        this.conn.close();
    }

//METODOS PARA TRABAJAR CON LA BASE DE DATOS
    public ResultSet consulta(String consulta) throws SQLException {
        this.estancia = (Statement) conn.createStatement();
        System.out.println(consulta);
        return this.estancia.executeQuery(consulta);
    }

    // -------------------
    public int ejecutar(String inserta) throws SQLException {
        System.out.println(inserta);
        Statement st = (Statement) this.conn.createStatement();
        return st.executeUpdate(inserta);
    }
}
/**/