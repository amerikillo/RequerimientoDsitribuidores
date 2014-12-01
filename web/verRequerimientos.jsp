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

            <%@include file="jspf/MenuPrincipal.jspf" %>

            <h3>Ver Requerimientos</h3>

            <table class="table table-condensed table-bordered table-striped">
                <tr>
                    <td>ID Pedido</td>
                    <td>Status</td>
                    <td>Fecha de Captura</td>
                    <td width="180px"></td>
                </tr>
                <%                    try {
                        con.conectar();
                        ResultSet rset = con.consulta("select F_IdPed, F_StsPed, DATE_FORMAT(F_FecCap,'%d/%m/%Y %H:%i:%s')as F_FecCap from tb_pedidos where F_ClaCli = '" + F_ClaCli + "' and F_StsPed!=500");
                        while (rset.next()) {
                            int banLibISEM = 0;
                            String color = "warning";
                            String status = "Inconcluso";
                            if (rset.getString("F_StsPed").equals("1")) {
                                status = "Eliminado";
                                color = "danger";
                            }
                            if (rset.getString("F_StsPed").equals("2")) {
                                status = "Capturado";
                                color = "success";
                            }
                            
                            if (rset.getString("F_StsPed").equals("3")) {
                                status = "En Revisión por ISEM";
                                color = "info";
                            }

                            ResultSet rset2 = con.consulta("select F_ClaPro, F_Cant, F_Obs, F_Id from tb_detpedido where F_IdPed = '" + rset.getString("F_IdPed") + "' order by F_ClaPro+0");
                            while (rset2.next()) {
                                int cantMax = 0;
                                int cantSolPrev = 0;
                                int banCantExedida = 0, CantExe = 0;
                                ResultSet rset3 = con.consulta("select F_Cant from tb_maxdist where F_ClaPro='" + rset2.getString("F_ClaPro") + "' and F_ClaCli = '" + F_ClaCli + "'");
                                while (rset3.next()) {
                                    cantMax = rset3.getInt("F_Cant");
                                }

                                rset3 = con.consulta("select SUM(F_Cant) as TotalSol from tb_detpedido d, tb_pedidos p where d.F_IdPed = p.F_IdPed and p.F_ClaCli = '" + F_ClaCli + "' and d.F_ClaPro = '" + rset2.getString("F_ClaPro") + "' ");
                                while (rset3.next()) {
                                    cantSolPrev = rset3.getInt("TotalSol");
                                }

                                if (rset2.getInt("F_Cant") > cantMax && rset2.getString("F_Obs").equals("")) {
                                    banLibISEM = 1;
                                }

                                cantMax = cantMax - cantSolPrev;

                                if (cantMax < 0) {
                                    banCantExedida = 1;
                                    CantExe = (cantMax * -1);
                                    cantMax = 0;
                                }
                            }
                %>
                <tr class="<%=color%>">
                    <td><%=rset.getString("F_IdPed")%></td>
                    <td><%=status%></td>
                    <td><%=rset.getString("F_FecCap")%></td>
                    <td>
                        <div class="row">
                            <form action="verRequerimentoEsp.jsp" method="post" class="col-sm-4">
                                <input value="<%=rset.getString("F_IdPed")%>" name="F_IdPed"  class="hidden" />
                                <button class="btn btn-primary btn-sm" name="accion" value="EliminarInsumo"><span class="glyphicon glyphicon-search"></span></button>
                            </form>
                            <form action="Capturar?F_IdPed=<%=rset.getString("F_IdPed")%>" method="post" class="col-sm-4">
                                <button class="btn btn-danger btn-sm" name="accion" onclick="return confirm('Seguro que desea eliminar el pedido?')" value="EliminarPedido">X</button>
                            </form>
                            <%
                                if (banLibISEM == 0 && color.equals("success")) {
                            %>
                            <form action="Capturar?F_IdPed=<%=rset.getString("F_IdPed")%>" method="post" class="col-sm-4">
                                <button class="btn btn-success btn-sm" name="accion" title="Liberar para ISEM" onclick="return confirm('Seguro que desea Confirmar el pedido?')" value="ValidaDist"><span class="glyphicon glyphicon-thumbs-up"></span></button>
                            </form>
                            <%
                                }
                            %>
                        </div>
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
