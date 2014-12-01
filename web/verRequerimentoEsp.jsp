<%-- 
    Document   : verRequerimientos
    Created on : 11/11/2014, 08:15:58 AM
    Author     : Americo
--%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="conn.*" %>
<!DOCTYPE html>
<%java.text.DateFormat df = new java.text.SimpleDateFormat("yyyyMMddhhmmss"); %>
<%java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd"); %>
<%java.text.DateFormat df3 = new java.text.SimpleDateFormat("dd/MM/yyyy"); %>
<%
    DecimalFormat formatter = new DecimalFormat("#,###,###");
    DecimalFormat formatterDecimal = new DecimalFormat("#,###,##0.00");
    DecimalFormatSymbols custom = new DecimalFormatSymbols();
    custom.setDecimalSeparator('.');
    custom.setGroupingSeparator(',');
    formatter.setDecimalFormatSymbols(custom);
    formatterDecimal.setDecimalFormatSymbols(custom);
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

            <%@include file="jspf/MenuPrincipal.jspf" %>

            <h3>Ver Requerimiento</h3>
            <h4>Requerimiento: <%=request.getParameter("F_IdPed")%></h4>
            <%
                try {
                    con.conectar();
                    ResultSet rset = con.consulta("select SUM(F_Cant) as F_Cant from tb_detpedido where F_IdPed = '" + request.getParameter("F_IdPed") + "'");
                    while (rset.next()) {
            %>
            Total Solicitado: <%=formatter.format(rset.getInt("F_Cant"))%> piezas
            <%
                    }
                    con.cierraConexion();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            %>
            <form action="ModificarPedido?F_IdPed=<%=request.getParameter("F_IdPed")%>" method="post">
                <div class="row">
                    <div class="col-sm-2 col-sm-offset-8">
                        <button type="submit" name="accion" value="ActualizarDetPedido" class="btn btn-primary btn-block">Actualizar</button>
                    </div>
                    <div class="col-sm-2">
                        <a class="btn btn-default btn-block" href="verRequerimientos.jsp">Regresar</a>
                    </div>
                </div>
                <table class="table table-condensed table-bordered table-striped">
                    <tr>
                        <td class="text-center">Clave</td>
                        <td class="text-center" width="150px">Cant Sol</td>
                        <td class="text-center" width="150px">Cant Max.</td>
                        <td class="text-center">Observaciones</td>
                        <td width="100px"></td>
                    </tr>
                    <%
                        try {
                            con.conectar();
                            ResultSet rset = con.consulta("select F_ClaPro, F_Cant, F_Obs, F_Id from tb_detpedido where F_IdPed = '" + request.getParameter("F_IdPed") + "' order by F_ClaPro+0");
                            while (rset.next()) {
                                String color = "";
                                int cantMax = 0;
                                int cantSolPrev = 0;
                                int banCantExedida = 0, CantExe = 0;
                                ResultSet rset2 = con.consulta("select F_Cant from tb_maxdist where F_ClaPro='" + rset.getString("F_ClaPro") + "' and F_ClaCli = '" + F_ClaCli + "'");
                                while (rset2.next()) {
                                    cantMax = rset2.getInt("F_Cant");
                                }

                                rset2 = con.consulta("select SUM(F_Cant) as TotalSol from tb_detpedido d, tb_pedidos p where d.F_IdPed = p.F_IdPed and p.F_ClaCli = '" + F_ClaCli + "' and d.F_ClaPro = '" + rset.getString("F_ClaPro") + "'  and d.F_StsPed!=500");
                                while (rset2.next()) {
                                    cantSolPrev = rset2.getInt("TotalSol");
                                }

                                if (rset.getInt("F_Cant") > cantMax) {
                                    color = "danger";
                                }

                                cantMax = cantMax - cantSolPrev;

                                if (cantMax < 0) {
                                    banCantExedida = 1;
                                    CantExe = (cantMax * -1);
                                    cantMax = 0;
                                }
                    %>
                    <tr class="<%=color%>">
                        <td class="text-center"><%=rset.getString("F_ClaPro")%></td>
                        <td>
                            <input type="number" class="form-control text-right" name="F_CantSol<%=rset.getString("F_Id")%>" value="<%=rset.getString("F_Cant")%>" />
                        </td>
                        <%
                            if (banCantExedida == 0) {
                        %>
                        <td class="text-right"><%=formatter.format(cantMax)%></td>
                        <%
                        } else {
                        %>
                        <td>Cantidad Excedida con <%=CantExe%></td>
                        <%
                            }
                        %>
                        <td>
                            <textarea class="form-control" name="F_Obs<%=rset.getString("F_Id")%>"><%=rset.getString("F_Obs")%></textarea>
                        </td>
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
            </form>
        </div>
        <%@include file="jspf/piePagina.jspf" %>
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
