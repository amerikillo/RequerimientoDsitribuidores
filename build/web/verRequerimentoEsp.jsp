<%-- 
    Document   : verRequerimientos
    Created on : 11/11/2014, 08:15:58 AM
    Author     : Americo
--%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="conn.*" %>
<!DOCTYPE html>
<%java.text.DateFormat df = new java.text.SimpleDateFormat("yyyyMMddhhmmss"); %>
<%java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd"); %>
<%java.text.DateFormat df3 = new java.text.SimpleDateFormat("dd/MM/yyyy"); %>
<%

    HttpSession sesion = request.getSession();
    String usua = "", F_ClaCli = "";
    if (sesion.getAttribute("F_NomCli") != null) {
        usua = (String) sesion.getAttribute("F_NomCli");
        F_ClaCli = (String) sesion.getAttribute("F_ClaCli");
    } else {
        response.sendRedirect("index.jsp");
    }
    ConectionDB con = new ConectionDB();

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Estilos CSS -->
        <link href="css/bootstrap.css" rel="stylesheet">
        <link rel="stylesheet" href="css/cupertino/jquery-ui-1.10.3.custom.css" />
        <link href="css/navbar-fixed-top.css" rel="stylesheet">
        <!---->
        <title>SIALSS</title>
    </head>
    <body>
        <div class="container">
            <h1>SIALSS</h1>
            <h4>Módulo - Requerimiento de Distribuidor</h4>
            <div class="navbar navbar-default">
                <div class="container">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                            <span clss="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="main_menu.jsp">Inicio</a>
                    </div>
                    <div class="navbar-collapse collapse">
                        <ul class="nav navbar-nav">
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Requerimientos<b class="caret"></b></a>
                                <ul class="dropdown-menu">
                                    <li><a href="main_menu.jsp"  onclick="">Captura de Requerimientos</a></li>
                                    <li><a href="verRequerimientos.jsp"  onclick="">Ver Requerimientos</a></li>
                                    <!--li><a href="#"  onclick="window.open('verDevolucionesEntrada.jsp', '', 'width=1200,height=800,left=50,top=50,toolbar=no')">Imprimir Devoluciones</a></li>
                                    <li><a href="#"  onclick="window.open('devolucionesInsumo.jsp', '', 'width=1200,height=800,left=50,top=50,toolbar=no')">Devolver</a></li-->
                                </ul>
                            </li>
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <li><a href="#"><span class="glyphicon glyphicon-user"></span> <%=usua%></a></li>
                            <li class="active"><a href="index.jsp"><span class="glyphicon glyphicon-log-out"></span></a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>

            <h3>Ver Requerimientos</h3>
            <a class="btn btn-default" href="verRequerimientos.jsp">Regresar</a>
            <table class="table table-condensed table-bordered table-striped">
                <tr>
                    <td>Clave</td>
                    <td>Cantidad</td>
                    <td>Observaciones</td>
                    <td width="100px"></td>
                </tr>
                <%
                    try {
                        con.conectar();
                        ResultSet rset = con.consulta("select F_ClaPro, F_Cant, F_Obs, F_Id from tb_detpedido where F_IdPed = '" + request.getParameter("F_IdPed") + "'");
                        while (rset.next()) {
                            String color = "";
                            if (!rset.getString("F_Obs").equals("")) {
                                color = "danger";
                            }
                %>
                <tr class="<%=color%>">
                    <td><%=rset.getString("F_ClaPro")%></td>
                    <td><%=rset.getString("F_Cant")%></td>
                    <td><%=rset.getString("F_Obs")%></td>
                    <td>
                        <!--div class="row">
                            <form action="Capturar?F_Id=<%=rset.getString("F_Id")%>" method="post" class="col-sm-6">
                                <button class="btn btn-primary btn-sm" name="accion" onclick="return confirm('Seguro que desea eliminarla?')" value="EliminarInsumo"><span class="glyphicon glyphicon-edit"></span></button>
                            </form>
                            <form action="Capturar" method="post" class="col-sm-6">
                                <input name="F_Id" value="<%=rset.getString("F_Id")%>" class="hidden" />
                                <input name="F_IdPed" value="<%=request.getParameter("F_IdPed")%>" class="hidden" />
                                <button class="btn btn-danger btn-sm" name="accion" onclick="return confirm('Seguro que desea eliminar el pedido?')" value="EliminarInsumoEdi">X</button>
                            </form>
                        </div-->
                    </td>
                </tr>
                <%
                        }
                        con.cierraConexion();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                %>
                </tr>
            </table>
        </div>
    </body>
    <!-- 
    ================================================== -->
    <!-- Se coloca al final del documento para que cargue mas rapido -->
    <!-- Se debe de seguir ese orden al momento de llamar los JS -->
    <script src="js/jquery-1.9.1.js"></script>
    <script src="js/bootstrap.js"></script>
    <script src="js/funcInvCiclico.js"></script>
    <script src="js/jquery-ui.js"></script>
</html>
</html>
