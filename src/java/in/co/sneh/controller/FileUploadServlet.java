/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package in.co.sneh.controller;

import LeeExcel.LeeExcel;
import in.co.sneh.model.FileUpload;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import java.io.InputStream;
import java.io.PrintWriter;
import org.apache.commons.fileupload.FileItemIterator;
import javax.servlet.http.HttpSession;
import conn.*;

/**
 *
 * @author CEDIS TOLUCA3
 */
public class FileUploadServlet extends HttpServlet {

    LeeExcel lee = new LeeExcel();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ConectionDB con = new ConectionDB();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession sesion = request.getSession(true);
        String Unidad = (String) sesion.getAttribute("F_ClaCli");

        boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
        if (isMultiPart) {
            ServletFileUpload upload = new ServletFileUpload();
            try {
                FileItemIterator itr = upload.getItemIterator(request);
                while (itr.hasNext()) {
                    FileItemStream item = itr.next();
                    if (item.isFormField()) {
                        String fielName = item.getFieldName();
                        InputStream is = item.openStream();
                        byte[] b = new byte[is.available()];
                        is.read(b);
                        String value = new String(b);
                        response.getWriter().println(fielName + ":" + value + "<br/>");
                    } else {
                        String path = getServletContext().getRealPath("/");
                        if (FileUpload.processFile(path, item)) {
                            //response.getWriter().println("file uploaded successfully");
                            if (lee.obtieneArchivo(path, item.getName(), Unidad)) {
                                out.println("<script>alert('Se cargó el Requerimiento Correctamente')</script>");
                                out.println("<script>window.location='cargarRequerimiento.jsp'</script>");
                            }
                            //response.sendRedirect("cargaFotosCensos.jsp");
                        } else {
                            //response.getWriter().println("file uploading falied");
                            //response.sendRedirect("cargaFotosCensos.jsp");
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                out.println("<script>alert('No se pudo cargar el Requerimiento, " + e.getMessage() + "')</script>");
                out.println("<script>window.location='cargarRequerimiento.jsp'</script>");
            } catch (FileUploadException e) {
                System.out.println(e.getMessage());
                out.println("<script>alert('No se pudo cargar el Requerimiento, " + e.getMessage() + "')</script>");
                out.println("<script>window.location='cargarRequerimiento.jsp'</script>");
            }
            out.println("<script>alert('Se cargó el Requerimiento Correctamente')</script>");
            out.println("<script>window.location='cargarRequerimiento.jsp'</script>");
            //response.sendRedirect("carga.jsp");
        }

        /*
         * Para insertar el excel en tablas
         */
    }
}
