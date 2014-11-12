<%-- 
    Document   : index
    Created on : 17/02/2014, 03:34:46 PM
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
    String usua = "", tipo = "";
    if (sesion.getAttribute("F_NomCli") != null) {
        usua = (String) sesion.getAttribute("F_NomCli");
    } else {
        response.sendRedirect("index.jsp");
    }
    ConectionDB con = new ConectionDB();
    String F_ClaPro = "", F_DesPro = "";
    int cantMax = 0;
    int banCaptura = 0, banFinalizar = 0;
    try {
        con.conectar();
        if (request.getParameter("accion").equals("BuscarInsumo")) {
            ResultSet rset = null;
            if (request.getParameter("F_ClaPro") != null && !request.getParameter("F_ClaPro").equals("")) {
                rset = con.consulta("select F_ClaPro, F_DesPro from tb_medica where F_ClaPro = '" + request.getParameter("F_ClaPro") + "' ");
            }
            if (request.getParameter("F_DesPro") != null && !request.getParameter("F_DesPro").equals("")) {
                rset = con.consulta("select F_ClaPro, F_DesPro from tb_medica where F_DesPro = '" + request.getParameter("F_DesPro") + "' ");
            }
            while (rset.next()) {
                F_ClaPro = rset.getString("F_ClaPro");
                F_DesPro = rset.getString("F_DesPro");
                ResultSet rset2 = con.consulta("select F_Cant from tb_maxdist where F_ClaPro = '" + rset.getString("F_ClaPro") + "' and F_ClaCli = '" + sesion.getAttribute("F_ClaCli") + "' ");
                while (rset2.next()) {
                    cantMax = rset2.getInt("F_Cant");
                }
                banCaptura = 1;
            }
        }
        con.cierraConexion();
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
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

            <h3>Crear Requerimiento</h3>

            <div class="row">
                <div class="col-lg-12">
                    <form action="Capturar">
                        <button class="btn btn-primary btn-block" type="submit" name="accion" value="NuevoRequerimento" 
                                <%

                                    if (sesion.getAttribute(
                                            "F_IdPed") != null) {
                                        out.println("disabled");
                                    }
                                %>
                                >Crear Nuevo Pedido</button>
                    </form>
                </div>
            </div>
            <hr/>
            <%
                if (sesion.getAttribute(
                        "F_IdPed") != null) {
            %>
            <div class="row">
                <h4 class="col-sm-1 col-lg-offset-9 text-right" >Folio:</h4>
                <div class="col-sm-2">
                    <input class="form-control" readonly="" value="<%=sesion.getAttribute("F_IdPed")%>" />
                </div>
            </div>
            <div class="row">
                <form action="main_menu.jsp" method="post">
                    <h4 class="col-sm-1">Clave:</h4>
                    <div class="col-sm-2">
                        <input class="form-control" placeholder="Por Clave" name="F_ClaPro" value="<%=F_ClaPro%>" />
                    </div>
                    <h4 class="col-sm-2">Descripción:</h4>
                    <div class="col-sm-6">
                        <input class="form-control" placeholder="Por Descripción" id="buscarDescrip" name="F_DesPro" value="<%=F_DesPro%>" />
                    </div>
                    <div class="col-lg-1">
                        <button class="btn btn-primary btn-block" name="accion" value="BuscarInsumo">Buscar</button>
                    </div>
                </form>
            </div>
            <form action="Capturar" method="post">
                <input class="hidden" placeholder="Por Clave" name="F_ClaPro" value="<%=F_ClaPro%>" />
                <div class="row">
                    <h4 class="col-sm-2">Cantidad a Solicitar</h4>
                    <div class="col-sm-2">
                        <input type="number" class="form-control" placeholder="Piezas" id="CantSol" name="CantSol" />
                    </div>
                    <h4 class="col-sm-2">Cantidad Máxima</h4>
                    <div class="col-sm-2">
                        <input class="form-control" placeholder="Máximo" value="<%=cantMax%>" id="CantMax" name="MaxCant" readonly />
                    </div>
                </div>
                <div class="row">
                    <h4 class="col-sm-2">Observaciones</h4>
                    <div class="col-sm-10">
                        <textarea class="form-control" id="Observaciones" name="F_Observaciones"></textarea>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-lg-12">
                        <button type="submit" name="accion" value="Capturar" id="btnCapturar" onclick="return validaMax();" class="btn btn-success btn-block" <%
                            if (banCaptura == 0) {
                                out.println("disabled");
                            }
                                %> >Capturar</button>
                    </div>
                </div>
            </form>
            <hr>
            <table class="table table-bordered table-condensed table-striped">
                <tr>
                    <td>Clave</td>
                    <td>Cantidad</td>
                    <td>Observaciones</td>
                    <td width="20px"></td>
                </tr>
                <%
                    try {
                        con.conectar();
                        ResultSet rset = con.consulta("select d.F_ClaPro, m.F_DesPro, d.F_Cant as F_Cant, d.F_Obs, d.F_Id from tb_detpedido d, tb_medica m where d.F_ClaPro = m.F_ClaPro and d.F_IdPed='" + sesion.getAttribute("F_IdPed") + "'");
                        while (rset.next()) {
                            banFinalizar = 1;
                %>

                <tr>
                    <td><a href="#" title="<%=rset.getString("F_DesPro")%>"><%=rset.getString("F_ClaPro")%></a></td>
                    <td><%=rset.getString("F_Cant")%></td>
                    <td><%=rset.getString("F_Obs")%></td>
                    <td>
                        <form action="Capturar?F_Id=<%=rset.getString("F_Id")%>" method="post">
                            <button class="btn btn-danger" name="accion" onclick="return confirm('Seguro que desea eliminarla?')" value="EliminarInsumo">X</button>
                        </form>
                    </td>
                </tr>
                <%
                        }
                        con.cierraConexion();
                    } catch (Exception e) {

                    }
                %>
            </table>
            <hr/>
            <div class="row">
                <%
                    if (banFinalizar == 1) {
                %>
                <form action="Capturar">
                    <input class="hidden" readonly="" name="F_IdPed" value="<%=sesion.getAttribute("F_IdPed")%>" />
                    <div class="col-lg-6">                        
                        <button class="btn btn-warning btn-block" type="submit" name="accion" value="CancelarRequerimento" onclick="return confirm('Seguro que desea CANCELAR este pedido?')">Cancelar Pedido</button>
                    </div>
                    <div class="col-lg-6">
                        <button class="btn btn-danger btn-block" name="accion" value="FinalizarRequerimento" onclick="return confirm('Seguro que desea CONFIRMAR este pedido?')">Finalizar Pedido</button>
                    </div>
                </form>
                <%
                    }
                %>
            </div>

            <%
                }
            %>
        </div>
        <br><br><br>
        <div class="navbar navbar-fixed-bottom navbar-inverse">
            <div class="text-center text-muted">
                GNK Logística || Desarrollo de Aplicaciones 2009 - 2014 <span class="glyphicon glyphicon-registration-mark"></span><br />
                Todos los Derechos Reservados
            </div>
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

