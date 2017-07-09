package es.uv.etse.bdweb.hotel.init;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class InitBD {

	static String nameFileJSON = "datebase/initBD.json";

	public static void main(String[] args) throws SQLException  {

		String JDBCQuery = "";
		ArrayList<String> listaFilas;

		ConnectionBD conn = new ConnectionBD();
		conn.connect();

		listaFilas = new ArrayList<String>(Arrays.asList("tip_hab_tipo", "tip_hab_precio"));
		JDBCQuery = fromJsonToJDBCQuery("tipos_habitaciones", listaFilas);
		System.out.println("Tabla \"tipos_habitaciones\"");
		conn.insertToBD(JDBCQuery.toString());
		listaFilas.clear();

		/*
		 * Insert Tabla "habitaciones"
		 */
		listaFilas = new ArrayList<String>(Arrays.asList("hab_numero", "hab_ocupada", "tipos_habitaciones_tip_hab_id"));
		JDBCQuery = fromJsonToJDBCQuery("habitaciones", listaFilas);
		System.out.println("Tabla \"habitaciones\"");
		conn.insertToBD(JDBCQuery.toString());
		listaFilas.clear();

		/*
		 * Insert Tabla "promociones"
		 */
		listaFilas = new ArrayList<String>(Arrays.asList("prom_fecha_inicio", "prom_fecha_final", "prom_descripcion",
				"prom_precio", "tipos_habitaciones_tip_hab_id"));
		JDBCQuery = fromJsonToJDBCQuery("promociones", listaFilas);
		System.out.println("Tabla \"promociones\"");
		conn.insertToBD(JDBCQuery.toString());
		listaFilas.clear();

		/*
		 * Insert Tabla "clientes"
		 */
		listaFilas = new ArrayList<String>(Arrays.asList("cli_id", "cli_nombre", "cli_apellidos", "cli_password",
				"cli_direccion", "cli_dni", "cli_email", "cli_numero_movil", "cli_numero_tarjeta"));
		JDBCQuery = fromJsonToJDBCQuery("clientes", listaFilas);
		System.out.println("Tabla \"clientes\"");
		conn.insertToBD(JDBCQuery.toString());
		listaFilas.clear();

		/*
		 * Insert Tabla "clientes_habituales"
		 */
		listaFilas = new ArrayList<String>(Arrays.asList("cli_id", "cli_newsletter", "cli_descuento"));
		JDBCQuery = fromJsonToJDBCQuery("clientes_habituales", listaFilas);
		System.out.println("Tabla \"clientes_habituales\"");
		conn.insertToBD(JDBCQuery.toString());
		listaFilas.clear();

		/*
		 * Insert Tabla "reservas"
		 */
		listaFilas = new ArrayList<String>(Arrays.asList("res_fecha_inicio", "res_fecha_final", "res_estado",
				"res_importe", "res_cobrada", "habitaciones_hab_id", "clientes_cli_id", "promociones_prom_id"));
		JDBCQuery = fromJsonToJDBCQuery("reservas", listaFilas);
		System.out.println("Tabla \"reservas\"");
		conn.insertToBD(JDBCQuery.toString());
		listaFilas.clear();
		
		/*
		 * Insert Tabla "recepcionistas"
		 */
		listaFilas = new ArrayList<String>(Arrays.asList("recep_id", "recep_nombre", "recep_apellidos",
				"recep_password", "recep_email"));
		JDBCQuery = fromJsonToJDBCQuery("recepcionistas", listaFilas);
		System.out.println("Tabla \"recepcionistas\"");
		conn.insertToBD(JDBCQuery.toString());
		listaFilas.clear();

		conn.connectClose();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String fromJsonToJDBCQuery(String nombreTabla, ArrayList<String> nombresFilasArray) {
		String JDBCQuery = "";
		JSONParser parser = new JSONParser();

		try {

			Object obj = parser.parse(new FileReader(nameFileJSON));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray jsonArray = (JSONArray) jsonObject.get(nombreTabla);

			StringBuilder sbNombresFilas = new StringBuilder();
			for (String s : nombresFilasArray) {
				sbNombresFilas.append(s);
				sbNombresFilas.append(",");
			}
			sbNombresFilas.delete(sbNombresFilas.length() - 1, sbNombresFilas.length());

			StringBuilder sbQuery = new StringBuilder(
					"INSERT INTO " + nombreTabla + " (" + sbNombresFilas.toString() + ") VALUES ");
			ArrayList<HashMap<String, String>> lista = new ArrayList<>();

			Iterator<List> iterator = jsonArray.iterator();
			while (iterator.hasNext()) {
				lista.add((HashMap<String, String>) iterator.next());
			}

			for (HashMap<String, String> mapa : lista) {
				sbQuery.append("(");
				for (String s : nombresFilasArray) {
					if (mapa.get(s) != null)
						sbQuery.append("'" + mapa.get(s) + "',");
					else
						sbQuery.append("NULL,");
				}
				sbQuery.delete(sbQuery.length() - 1, sbQuery.length());
				sbQuery.append("),");
			}
			sbQuery.delete(sbQuery.length() - 1, sbQuery.length());
			sbQuery.append(";");
			JDBCQuery = sbQuery.toString();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return JDBCQuery;
	}
}
