package LeeExcel;

import conn.ConectionDB;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Vector;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Indra Hidayatulloh
 */
public class LeeExcel {

    private Vector vectorDataExcelXLSX = new Vector();
    ConectionDB con = new ConectionDB();

    public boolean obtieneArchivo(String path, String file, String claCli) {
        String excelXLSXFileName = path + "/exceles/" + file;
        vectorDataExcelXLSX = readDataExcelXLSX(excelXLSXFileName);
        displayDataExcelXLSX(vectorDataExcelXLSX, claCli);
        return true;
    }

    public Vector readDataExcelXLSX(String fileName) {
        Vector vectorData = new Vector();

        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);

            XSSFWorkbook xssfWorkBook = new XSSFWorkbook(fileInputStream);

            // Read data at sheet 0
            XSSFSheet xssfSheet = xssfWorkBook.getSheetAt(0);

            Iterator rowIteration = xssfSheet.rowIterator();

            // Looping every row at sheet 0
            while (rowIteration.hasNext()) {
                XSSFRow xssfRow = (XSSFRow) rowIteration.next();
                Iterator cellIteration = xssfRow.cellIterator();

                Vector vectorCellEachRowData = new Vector();

                // Looping every cell in each row at sheet 0
                while (cellIteration.hasNext()) {
                    XSSFCell xssfCell = (XSSFCell) cellIteration.next();
                    vectorCellEachRowData.addElement(xssfCell);
                }

                vectorData.addElement(vectorCellEachRowData);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return vectorData;
    }

    public void displayDataExcelXLSX(Vector vectorData, String claCli) {
        // Looping every row data in vector
        int idPed = 0;
        try {
            con.conectar();
            con.ejecutar("insert into tb_pedidos values('" + claCli + "','0','0',CURDATE(),NOW())");
            ResultSet rset = con.consulta("select MAX(F_IdPed) as F_IdPed from tb_pedidos where F_ClaCli = '" + claCli + "' and F_FecEnt = CURDATE() ");
            while (rset.next()) {
                idPed = rset.getInt("F_IdPed");
            }
            con.cierraConexion();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < vectorData.size(); i++) {
            Vector vectorCellEachRowData = (Vector) vectorData.get(i);
            String qry = "insert into tb_detpedido values (0, '" + idPed + "', ";
            // looping every cell in each row
            for (int j = 0; j < 2; j++) {

                if (j == 0) {
                    System.out.println("algo");
                    try {
                        String ClaPro = ((vectorCellEachRowData.get(j).toString()) + "");
                        NumberFormat formatter = new DecimalFormat("0000.00");
                        ClaPro = formatter.format(Double.parseDouble(ClaPro));
                        String[] punto = ClaPro.split("\\.");
                        System.out.println(punto.length);
                        if (punto.length > 1) {
                            System.out.println(ClaPro + "***" + punto[0] + "////" + punto[1]);
                            if (punto[1].equals("01")) {
                                ClaPro = agrega(punto[0]) + ".01";
                            } else if (punto[1].equals("02")) {
                                ClaPro = agrega(punto[0]) + ".02";
                            } else if (punto[1].equals("00")) {
                                ClaPro = agrega(punto[0]);
                            } else {
                                ClaPro = agrega(punto[0]);
                            }
                            System.out.println(ClaPro);
                        }
                        qry = qry + "'" + agrega(ClaPro) + "' , ";

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    try {
                        String Clave = ((int) Double.parseDouble(vectorCellEachRowData.get(j).toString()) + "");
                        qry = qry + "'" + Clave + "' , ";
                    } catch (Exception e) {
                    }
                }
            }
            qry = qry + " '', curdate(),'0')"; // agregar campos fuera del excel
            try {
                con.conectar();
                try {
                    System.out.println(qry);
                    con.ejecutar(qry);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                con.cierraConexion();
            } catch (Exception e) {
            }
        }

        try {
            con.conectar();
            con.ejecutar("update tb_pedidos set F_StsPed= '2' where F_IdPed='"+idPed+"' ");
            con.cierraConexion();
        } catch (Exception e) {
        }
    }

    public String agrega(String clave) {
        String clave2 = "";
        if (clave.length() < 4) {

            if (!clave.substring(0, 1).equals("0")) {
                //System.out.println(clave);
                if (clave.length() == 1) {
                    clave2 = ("000" + clave);
                }
                if (clave.length() == 2) {
                    clave2 = ("00" + clave);
                }
                if (clave.length() >= 3) {
                    clave2 = ("0" + clave);
                }

            }
        } else {
            clave2 = clave;
        }
        return clave2;
    }

}
