package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.sql.ResultSet;
import conn.*;

public final class cargarRequerimiento_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
java.text.DateFormat df = new java.text.SimpleDateFormat("yyyyMMddhhmmss"); 
      out.write('\n');
java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
      out.write('\n');
java.text.DateFormat df3 = new java.text.SimpleDateFormat("dd/MM/yyyy"); 
      out.write('\n');


    HttpSession sesion = request.getSession();
    String usua = "", tipo = "";
    if (sesion.getAttribute("F_NomCli") != null) {
        usua = (String) sesion.getAttribute("F_NomCli");
    } else {
        response.sendRedirect("index.jsp");
    }
    ConectionDB con = new ConectionDB();

      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n");
      out.write("        <!-- Estilos CSS -->\n");
      out.write("        <link href=\"css/bootstrap.css\" rel=\"stylesheet\">\n");
      out.write("        <link rel=\"stylesheet\" href=\"css/cupertino/jquery-ui-1.10.3.custom.css\" />\n");
      out.write("        <link href=\"css/navbar-fixed-top.css\" rel=\"stylesheet\">\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"css/dataTables.bootstrap.css\">\n");
      out.write("        <!---->\n");
      out.write("        <title>SIE Sistema de Ingreso de Entradas</title>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <div class=\"container\">\n");
      out.write("            <h1>SIALSS</h1>\n");
      out.write("            <h4>Módulo - Requerimiento de Distribuidor</h4>\n");
      out.write("            <div class=\"navbar navbar-default\">\n");
      out.write("                <div class=\"container\">\n");
      out.write("                    <div class=\"navbar-header\">\n");
      out.write("                        <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\".navbar-collapse\">\n");
      out.write("                            <span clss=\"icon-bar\"></span>\n");
      out.write("                            <span class=\"icon-bar\"></span>\n");
      out.write("                            <span class=\"icon-bar\"></span>\n");
      out.write("                            <span class=\"icon-bar\"></span>\n");
      out.write("                        </button>\n");
      out.write("                        <a class=\"navbar-brand\" href=\"main_menu.jsp\">Inicio</a>\n");
      out.write("                    </div>\n");
      out.write("                    <div class=\"navbar-collapse collapse\">\n");
      out.write("                        <ul class=\"nav navbar-nav\">\n");
      out.write("                            <li class=\"dropdown\">\n");
      out.write("                                <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">Requerimientos<b class=\"caret\"></b></a>\n");
      out.write("                                <ul class=\"dropdown-menu\">\n");
      out.write("                                    <li><a href=\"main_menu.jsp\"  onclick=\"\">Captura de Requerimientos</a></li>\n");
      out.write("                                    <li><a href=\"verRequerimientos.jsp\"  onclick=\"\">Ver Requerimientos</a></li>\n");
      out.write("                                    <!--li><a href=\"#\"  onclick=\"window.open('verDevolucionesEntrada.jsp', '', 'width=1200,height=800,left=50,top=50,toolbar=no')\">Imprimir Devoluciones</a></li>\n");
      out.write("                                    <li><a href=\"#\"  onclick=\"window.open('devolucionesInsumo.jsp', '', 'width=1200,height=800,left=50,top=50,toolbar=no')\">Devolver</a></li-->\n");
      out.write("                                </ul>\n");
      out.write("                            </li>\n");
      out.write("                        </ul>\n");
      out.write("                        <ul class=\"nav navbar-nav navbar-right\">\n");
      out.write("                            <li><a href=\"#\"><span class=\"glyphicon glyphicon-user\"></span> ");
      out.print(usua);
      out.write("</a></li>\n");
      out.write("                            <li class=\"active\"><a href=\"index.jsp\"><span class=\"glyphicon glyphicon-log-out\"></span></a></li>\n");
      out.write("                        </ul>\n");
      out.write("                    </div><!--/.nav-collapse -->\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("        <div class=\"container\">\n");
      out.write("            <div class=\"panel panel-primary\">\n");
      out.write("                <div class=\"panel-heading\">\n");
      out.write("                    <h3 class=\"panel-title\">Carga de Requerimientos</h3>\n");
      out.write("                </div>\n");
      out.write("                <div class=\"panel-body \">\n");
      out.write("                    <form method=\"post\" class=\"jumbotron\"  action=\"FileUploadServlet\" enctype=\"multipart/form-data\" name=\"form1\">\n");
      out.write("                        <div class=\"form-group\">\n");
      out.write("                            <div class=\"form-group\">\n");
      out.write("                                <div class=\"col-lg-3 text-success\">\n");
      out.write("                                    <h4>Seleccione el Excel a Cargar (XLSX)</h4>\n");
      out.write("                                </div>\n");
      out.write("                                <!--label for=\"Clave\" class=\"col-xs-2 control-label\">Clave*</label>\n");
      out.write("                                <div class=\"col-xs-2\">\n");
      out.write("                                    <input type=\"text\" class=\"form-control\" id=\"Clave\" name=\"Clave\" placeholder=\"Clave\" onKeyPress=\"return tabular(event, this)\" autofocus >\n");
      out.write("                                </div-->\n");
      out.write("                                <h4 for=\"Nombre\" class=\"col-sm-3\">Nombre Archivo*</h4>\n");
      out.write("                                <div class=\"col-sm-5\">\n");
      out.write("                                    <input class=\"form-control\" type=\"file\" name=\"file1\" id=\"file1\" accept=\".xlsx\"/>                                    \n");
      out.write("                                </div>\n");
      out.write("                            </div>\n");
      out.write("                        </div>\n");
      out.write("                        <button class=\"btn btn-block btn-primary\" type=\"submit\" name=\"accion\" value=\"guardar\" onclick=\"return valida_alta();\"> Cargar Archivo</button>\n");
      out.write("                    </form>\n");
      out.write("                    <div style=\"display: none;\" class=\"text-center\" id=\"Loader\">\n");
      out.write("                        <img src=\"imagenes/ajax-loader-1.gif\" height=\"150\" />\n");
      out.write("                    </div>\n");
      out.write("                    <div>\n");
      out.write("                        <h6>Los campos marcados con * son obligatorios</h6>\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("        <br><br><br>\n");
      out.write("        <div class=\"navbar navbar-fixed-bottom navbar-inverse\">\n");
      out.write("            <div class=\"text-center text-muted\">\n");
      out.write("                GNK Logística || Desarrollo de Aplicaciones 2009 - 2014 <span class=\"glyphicon glyphicon-registration-mark\"></span><br />\n");
      out.write("                Todos los Derechos Reservados\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("    </body>\n");
      out.write("</html>\n");
      out.write("\n");
      out.write("\n");
      out.write("<!-- \n");
      out.write("================================================== -->\n");
      out.write("<!-- Se coloca al final del documento para que cargue mas rapido -->\n");
      out.write("<!-- Se debe de seguir ese orden al momento de llamar los JS -->\n");
      out.write("<script src=\"js/jquery-1.9.1.js\"></script>\n");
      out.write("<script src=\"js/bootstrap.js\"></script>\n");
      out.write("<script src=\"js/jquery-ui-1.10.3.custom.js\"></script>\n");
      out.write("<script src=\"js/jquery.dataTables.js\"></script>\n");
      out.write("<script src=\"js/dataTables.bootstrap.js\"></script>\n");
      out.write("<script>\n");
      out.write("                            $(document).ready(function() {\n");
      out.write("                                $('#datosProv').dataTable();\n");
      out.write("                            });\n");
      out.write("</script>\n");
      out.write("<script>\n");
      out.write("\n");
      out.write("\n");
      out.write("    function isNumberKey(evt, obj)\n");
      out.write("    {\n");
      out.write("        var charCode = (evt.which) ? evt.which : event.keyCode;\n");
      out.write("        if (charCode === 13 || charCode > 31 && (charCode < 48 || charCode > 57)) {\n");
      out.write("            if (charCode === 13) {\n");
      out.write("                frm = obj.form;\n");
      out.write("                for (i = 0; i < frm.elements.length; i++)\n");
      out.write("                    if (frm.elements[i] === obj)\n");
      out.write("                    {\n");
      out.write("                        if (i === frm.elements.length - 1)\n");
      out.write("                            i = -1;\n");
      out.write("                        break\n");
      out.write("                    }\n");
      out.write("                /*ACA ESTA EL CAMBIO*/\n");
      out.write("                if (frm.elements[i + 1].disabled === true)\n");
      out.write("                    tabular(e, frm.elements[i + 1]);\n");
      out.write("                else\n");
      out.write("                    frm.elements[i + 1].focus();\n");
      out.write("                return false;\n");
      out.write("            }\n");
      out.write("            return false;\n");
      out.write("        }\n");
      out.write("        return true;\n");
      out.write("\n");
      out.write("    }\n");
      out.write("\n");
      out.write("\n");
      out.write("    function valida_alta() {\n");
      out.write("        /*var Clave = document.formulario1.Clave.value;*/\n");
      out.write("        var Nombre = document.getElementById('file1').value;\n");
      out.write("\n");
      out.write("        if (Nombre === \"\") {\n");
      out.write("            alert(\"Tiene campos vacíos, verifique.\");\n");
      out.write("            return false;\n");
      out.write("        }\n");
      out.write("        document.getElementById('Loader').style.display='block';\n");
      out.write("    }\n");
      out.write("</script>\n");
      out.write("<script language=\"javascript\">\n");
      out.write("    function justNumbers(e)\n");
      out.write("    {\n");
      out.write("        var keynum = window.event ? window.event.keyCode : e.which;\n");
      out.write("        if ((keynum == 8) || (keynum == 46))\n");
      out.write("            return true;\n");
      out.write("\n");
      out.write("        return /\\d/.test(String.fromCharCode(keynum));\n");
      out.write("    }\n");
      out.write("    otro = 0;\n");
      out.write("    function LP_data() {\n");
      out.write("        var key = window.event.keyCode;//codigo de tecla. \n");
      out.write("        if (key < 48 || key > 57) {//si no es numero \n");
      out.write("            window.event.keyCode = 0;//anula la entrada de texto. \n");
      out.write("        }\n");
      out.write("    }\n");
      out.write("    function anade(esto) {\n");
      out.write("        if (esto.value.length === 0) {\n");
      out.write("            if (esto.value.length == 0) {\n");
      out.write("                esto.value += \"(\";\n");
      out.write("            }\n");
      out.write("        }\n");
      out.write("        if (esto.value.length > otro) {\n");
      out.write("            if (esto.value.length == 4) {\n");
      out.write("                esto.value += \") \";\n");
      out.write("            }\n");
      out.write("        }\n");
      out.write("        if (esto.value.length > otro) {\n");
      out.write("            if (esto.value.length == 9) {\n");
      out.write("                esto.value += \"-\";\n");
      out.write("            }\n");
      out.write("        }\n");
      out.write("        if (esto.value.length < otro) {\n");
      out.write("            if (esto.value.length == 4 || esto.value.length == 9) {\n");
      out.write("                esto.value = esto.value.substring(0, esto.value.length - 1);\n");
      out.write("            }\n");
      out.write("        }\n");
      out.write("        otro = esto.value.length\n");
      out.write("    }\n");
      out.write("\n");
      out.write("\n");
      out.write("    function tabular(e, obj)\n");
      out.write("    {\n");
      out.write("        tecla = (document.all) ? e.keyCode : e.which;\n");
      out.write("        if (tecla != 13)\n");
      out.write("            return;\n");
      out.write("        frm = obj.form;\n");
      out.write("        for (i = 0; i < frm.elements.length; i++)\n");
      out.write("            if (frm.elements[i] == obj)\n");
      out.write("            {\n");
      out.write("                if (i == frm.elements.length - 1)\n");
      out.write("                    i = -1;\n");
      out.write("                break\n");
      out.write("            }\n");
      out.write("        /*ACA ESTA EL CAMBIO*/\n");
      out.write("        if (frm.elements[i + 1].disabled == true)\n");
      out.write("            tabular(e, frm.elements[i + 1]);\n");
      out.write("        else\n");
      out.write("            frm.elements[i + 1].focus();\n");
      out.write("        return false;\n");
      out.write("    }\n");
      out.write("\n");
      out.write("</script> \n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
