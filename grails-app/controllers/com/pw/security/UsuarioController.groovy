package com.pw.security



import static org.springframework.http.HttpStatus.*

import com.mysql.jdbc.Util;
import com.pw.security.Permiso;
import com.pw.security.Rol;
import com.pw.security.Sesion;
import com.pw.security.Usuario;
import utilitarios.Util;
import callcenter.*;

import grails.transaction.Transactional

@Transactional(readOnly = false)
class UsuarioController {

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]


	static beforeInterceptor = {
		String accion = actionUri;
		if (!accion.equals("/usuario/login") && !accion.equals("/usuario/logout") && !accion.equals('/usuario/cargaConfiguracion')) {
			if (!session.user) {
				redirect(uri: "/usuario/login");
				return false;
			} else {
				boolean tienePermiso = utilitarios.Util.checkAccess(session.user.usuario, accion)
				if (!tienePermiso) {
					render "No tienes permiso para ingresar a este sitio-> " + accion;
				}
			}
		}
	}


	def login(){

		if(session.user){
			redirect(action:'dashboard')
		}

		if(params.usuario && params.password){
			def usuario = Usuario.findByUsuarioAndPasswordAndEstado(params.usuario.toString(), params.password.toString(), "ACTIVO");
			if(usuario){
				session.user = usuario
				def sesion = new Sesion()
				sesion.usuario = usuario
				sesion.idSesion = session.id
				sesion.fechaInicio = new Date()
				if(!sesion.save(flush: true))
					println sesion.errors
				redirect(action:'dashboard')
			}else{
				flash.errorMessage = "Error al iniciar sesion"
				usuario = new Usuario(params)
				[usuario: usuario]
			}

		}
	}

	def logout(){
		Sesion sesion = Sesion.findByIdSesion(session.id)
		sesion?.fechaFin = new Date()
		sesion.save(flush: true)
		session.invalidate()
		redirect(uri: "/usuario/login");
	}
	def dashboard(){

		Date fechaInicio = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 00:00:00"))
		Date fechaFin = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 23:59:59"))
		Subestado subestados = Subestado.findByType("Exitoso")
		Subestado subestadosCruzados = Subestado.findById(1,3)
		//Subestado subestados = Subestado.findById(1)
		Subestado cruzadas = Subestado.findByType("Cruzada")
		// Subestado cruzadas = Subestado.findById(3)
		Rol rol = Rol.findByNombre("OPERADOR")
		def ventasPorUsuario = Historial.executeQuery("select  nombreVendedor, count(*) from Historial where subestadoGestion in (:subestados) and subSubEstado = 'TDC' and fechaGestion between :fechaInicio and :fechaFin group by nombreVendedor order by count(*) desc", [subestados: subestados, fechaInicio: fechaInicio, fechaFin: fechaFin])
		def creditoPorUsuario = Historial.executeQuery("select nombreVendedor, count(*) from Historial where subestadoGestion in (:subestados) and subSubEstado = 'CREDITO' and fechaGestion between :fechaInicio and :fechaFin group by nombreVendedor order by count(*) desc", [subestados: subestados, fechaInicio: fechaInicio, fechaFin: fechaFin])
		def cruzadaPorUsuario = Historial.executeQuery("select nombreVendedor, count(*) from Historial where subestadoGestion in (:cruzadas) and fechaGestion between :fechaInicio and :fechaFin group by nombreVendedor order by count(*) desc", [cruzadas: cruzadas, fechaInicio: fechaInicio, fechaFin: fechaFin])
		def ventasPorHoraVendedor = Clientes.executeQuery("select hour(fechaGestion), nombreVendedor, count(*) from Clientes where subestadoGestion in (1, 3) and fechaGestion between :fechaInicio and :fechaFin group by hour(fechaGestion), nombreVendedor", [ fechaInicio: fechaInicio, fechaFin: fechaFin])
		//	String[][] consolidado = consolidarExitAdic(ventasPorUsuario, cruzadaPorUsuario)
		//ArrayList consolidado = [ventasPorUsuario, cruzadaPorUsuario]
		def inicioSesion = Sesion.executeQuery("select usuario.nombre, time(fechaInicio), time(fechaFin) from Sesion where fechaInicio between :fechaInicio and :fechaFin and usuario.rol = :rol order by fechaInicio desc", [fechaInicio: fechaInicio, fechaFin: fechaFin, rol: rol])

		//Para la tabla de gestionados contactados y no contactados
		int totalGestionados = 0
		int totalContactados = 0
		int totalNoContactados = 0
		int totalExitosos = 0
		int totalCreditos = 0
		int totalCruzadas = 0
		def gestionadosAgente = Historial.executeQuery("select nombreVendedor, count(*) from Historial where intentos != 0 and fechaGestion between :fechaInicio and :fechaFin group by nombreVendedor order by nombreVendedor", [fechaInicio: fechaInicio, fechaFin: fechaFin])
		def contactadosAgente = Historial.executeQuery("select nombreVendedor, count(*) from Historial where intentos != 0 and estadoGestion = 'CONTACTADO' and fechaGestion between :fechaInicio and :fechaFin group by nombreVendedor order by nombreVendedor", [fechaInicio: fechaInicio, fechaFin: fechaFin])
		def noContactadosAgente = Historial.executeQuery("select nombreVendedor, count(*) from Historial where intentos != 0 and estadoGestion = 'NO CONTACTADO' and fechaGestion between :fechaInicio and :fechaFin group by nombreVendedor order by nombreVendedor", [fechaInicio: fechaInicio, fechaFin: fechaFin])
		String[][] tablaResult = new String[gestionadosAgente.size()][7]
		//Lleno la matriz de resultados con los resultados de las onsultas anteriores
		for(int i = 0; i < tablaResult.size(); i++){
			tablaResult[i][0] = gestionadosAgente[i][0];
			tablaResult[i][1] = gestionadosAgente[i][1];
			for(int j = 0; j < contactadosAgente.size(); j++){
				if(contactadosAgente[j][0] == gestionadosAgente[i][0]){
					tablaResult[i][2] = contactadosAgente[j][1];
					break;
				}
			}
			//LLeno la información de las ventas TDC
			for(int j = 0; j < ventasPorUsuario.size(); j++){
				if(ventasPorUsuario[j][0] == gestionadosAgente[i][0]){
					tablaResult[i][4] = ventasPorUsuario[j][1]
					break
				}
			}
			//LLeno la información de las ventas CREDITO
			for(int j = 0; j < creditoPorUsuario.size(); j++){
				if(creditoPorUsuario[j][0] == gestionadosAgente[i][0]){
					tablaResult[i][5] = creditoPorUsuario[j][1]
					break
				}
			}

			//LLeno la información de las ventas CRUZADAS
			for(int j = 0; j < cruzadaPorUsuario.size(); j++){
				if(cruzadaPorUsuario[j][0] == gestionadosAgente[i][0]){
					tablaResult[i][6] = cruzadaPorUsuario[j][1]
					break
				}
			}

			for(int k = 0; k < noContactadosAgente.size(); k++){
				if(noContactadosAgente[k][0] == gestionadosAgente[i][0]){
					tablaResult[i][3] = noContactadosAgente[k][1];
					break;
				}
			}
		}
		//Recorro la matriz de resultados para obtener los totales
		for(int i = 0; i < tablaResult.size(); i++){
			totalGestionados = totalGestionados + tablaResult[i][1].toInteger();
			if(tablaResult[i][2] != null)
				totalContactados = totalContactados + tablaResult[i][2].toInteger();
			if(tablaResult[i][3] != null)
				totalNoContactados = totalNoContactados + tablaResult[i][3].toInteger();
			if(tablaResult[i][4] != null)
				totalExitosos = totalExitosos + tablaResult[i][4].toInteger()
			if(tablaResult[i][5] != null)
				totalCreditos = totalCreditos + tablaResult[i][5].toInteger()
			if(tablaResult[i][6] != null)
				totalCruzadas = totalCruzadas + tablaResult[i][6].toInteger()
		}

		//Consulta para las horas
		/*ArrayList<SubSubestado> mixtas = Subestado.executeQuery("from Subestado where id in (1, 3)")
		Date fechaInicio8 = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 08:00:00"))
		Date fechaFin9 = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 09:00:00"))
		Date fechaInicio9 = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 09:00:00"))
		Date fechaFin10 = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 10:00:00"))
		Date fechaInicio10 = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 10:00:00"))
		Date fechaFin11 = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 11:00:00"))
		Date fechaInicio11 = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 11:00:00"))
		Date fechaFin12 = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 12:00:00"))
		Date fechaInicio12 = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 12:00:00"))
		Date fechaFin13 = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 13:00:00"))
		Date fechaInicio13 = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 13:00:00"))
		Date fechaFin14 = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 14:00:00"))
		Date fechaInicio14 = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 14:00:00"))
		Date fechaFin15 = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 15:00:00"))
		Date fechaInicio15 = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 15:00:00"))
		Date fechaFin16 = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 16:00:00"))
		Date fechaInicio16 = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 16:00:00"))
		Date fechaFin17 = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 17:00:00"))
		Date fechaInicio17 = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 17:00:00"))
		Date fechaFin18 = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 18:00:00"))
		Date fechaInicio18 = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 18:00:00"))
		Date fechaFin19 = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 19:00:00"))
		Date fechaInicio19 = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 19:00:00"))
		Date fechaFin20 = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 20:00:00"))
		Date fechaInicio20 = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 20:00:00"))
		Date fechaFin21 = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 21:00:00"))
		def ventasPorHoraExitosas = Clientes.executeQuery("select nombreVendedor, count(*) from Clientes where subestadoGestion in (:subestados) and fechaGestion between :fechaInicio and :fechaFin group by nombreVendedor order by nombreVendedor", [subestados: subestados, fechaInicio: fechaInicio, fechaFin: fechaFin])
		def ventasPorHoraCruzadas = Clientes.executeQuery("select nombreVendedor, count(*) from Clientes where subestadoGestion in (:cruzadas) and fechaGestion between :fechaInicio and :fechaFin group by nombreVendedor order by nombreVendedor", [cruzadas: cruzadas, fechaInicio: fechaInicio, fechaFin: fechaFin])
		def ventasPorHoraGeneral = Clientes.executeQuery("select nombreVendedor, count(*) from Clientes where subestadoGestion in (:mixtas) and fechaGestion between :fechaInicio and :fechaFin group by nombreVendedor order by nombreVendedor", [mixtas: mixtas, fechaInicio: fechaInicio, fechaFin: fechaFin])
		def ventasHora8a9 = Clientes.executeQuery("select nombreVendedor, count(*) from Clientes where subestadoGestion in (:mixtas) and fechaGestion between :fechaInicio8 and :fechaFin9 group by nombreVendedor order by nombreVendedor", [mixtas: mixtas, fechaInicio8: fechaInicio8, fechaFin9: fechaFin9])
		def ventasHora9a10 = Clientes.executeQuery("select nombreVendedor, count(*) from Clientes where subestadoGestion in (:mixtas) and fechaGestion between :fechaInicio9 and :fechaFin10 group by nombreVendedor order by nombreVendedor", [mixtas: mixtas, fechaInicio9: fechaInicio9, fechaFin10: fechaFin10])
		def ventasHora10a11 = Clientes.executeQuery("select nombreVendedor, count(*) from Clientes where subestadoGestion in (:mixtas) and fechaGestion between :fechaInicio10 and :fechaFin11 group by nombreVendedor order by nombreVendedor", [mixtas: mixtas, fechaInicio10: fechaInicio10, fechaFin11: fechaFin11])
		def ventasHora11a12 = Clientes.executeQuery("select nombreVendedor, count(*) from Clientes where subestadoGestion in (:mixtas) and fechaGestion between :fechaInicio11 and :fechaFin12 group by nombreVendedor order by nombreVendedor", [mixtas: mixtas, fechaInicio11: fechaInicio11, fechaFin12: fechaFin12])
		def ventasHora12a13 = Clientes.executeQuery("select nombreVendedor, count(*) from Clientes where subestadoGestion in (:mixtas) and fechaGestion between :fechaInicio12 and :fechaFin13 group by nombreVendedor order by nombreVendedor", [mixtas: mixtas, fechaInicio12: fechaInicio12, fechaFin13: fechaFin13])
		def ventasHora13a14 = Clientes.executeQuery("select nombreVendedor, count(*) from Clientes where subestadoGestion in (:mixtas) and fechaGestion between :fechaInicio13 and :fechaFin14 group by nombreVendedor order by nombreVendedor", [mixtas: mixtas, fechaInicio13: fechaInicio13, fechaFin14: fechaFin14])
		def ventasHora14a15 = Clientes.executeQuery("select nombreVendedor, count(*) from Clientes where subestadoGestion in (:mixtas) and fechaGestion between :fechaInicio14 and :fechaFin15 group by nombreVendedor order by nombreVendedor", [mixtas: mixtas, fechaInicio14: fechaInicio14, fechaFin15: fechaFin15])
		def ventasHora15a16 = Clientes.executeQuery("select nombreVendedor, count(*) from Clientes where subestadoGestion in (:mixtas) and fechaGestion between :fechaInicio15 and :fechaFin16 group by nombreVendedor order by nombreVendedor", [mixtas: mixtas, fechaInicio15: fechaInicio15, fechaFin16: fechaFin16])
		def ventasHora16a17 = Clientes.executeQuery("select nombreVendedor, count(*) from Clientes where subestadoGestion in (:mixtas) and fechaGestion between :fechaInicio16 and :fechaFin17 group by nombreVendedor order by nombreVendedor", [mixtas: mixtas, fechaInicio16: fechaInicio16, fechaFin17: fechaFin17])
		def ventasHora17a18 = Clientes.executeQuery("select nombreVendedor, count(*) from Clientes where subestadoGestion in (:mixtas) and fechaGestion between :fechaInicio17 and :fechaFin18 group by nombreVendedor order by nombreVendedor", [mixtas: mixtas, fechaInicio17: fechaInicio17, fechaFin18: fechaFin18])
		def ventasHora18a19 = Clientes.executeQuery("select nombreVendedor, count(*) from Clientes where subestadoGestion in (:mixtas) and fechaGestion between :fechaInicio18 and :fechaFin19 group by nombreVendedor order by nombreVendedor", [mixtas: mixtas, fechaInicio18: fechaInicio18, fechaFin19: fechaFin19])
		def ventasHora19a20 = Clientes.executeQuery("select nombreVendedor, count(*) from Clientes where subestadoGestion in (:mixtas) and fechaGestion between :fechaInicio19 and :fechaFin20 group by nombreVendedor order by nombreVendedor", [mixtas: mixtas, fechaInicio19: fechaInicio19, fechaFin20: fechaFin20])
		def ventasHora20a21 = Clientes.executeQuery("select nombreVendedor, count(*) from Clientes where subestadoGestion in (:mixtas) and fechaGestion between :fechaInicio20 and :fechaFin21 group by nombreVendedor order by nombreVendedor", [mixtas: mixtas, fechaInicio20: fechaInicio20, fechaFin21: fechaFin21])
		int total8a9 = 0
		int total9a10 = 0
		int total10a11 = 0
		int total11a12 = 0
		int total12a13 = 0
		int total13a14 = 0
		int total14a15 = 0
		int total15a16 = 0
		int total16a17 = 0
		int total17a18 = 0
		int total18a19 = 0
		int total19a20 = 0
		int total20a21 = 0
		int totalExitosas = 0
		int totalCruzadasHora = 0
		String[][] tablaResultHorasAgente = new String[ventasPorHoraGeneral.size()][16]
		for(int a = 0; a < tablaResultHorasAgente.size(); a++){
			tablaResultHorasAgente[a][0] = ventasPorHoraGeneral[a][0];
			for(int b = 0; b < ventasHora8a9.size(); b++){
				if(ventasHora8a9[b][0] == ventasPorHoraGeneral[a][0]){
					tablaResultHorasAgente[a][1] = ventasHora8a9[b][1];
					total8a9 = total8a9 + tablaResultHorasAgente[a][1].toInteger()
				}
			}
			for(int c = 0; c < ventasHora9a10.size(); c++){
				if(ventasHora9a10[c][0] == ventasPorHoraGeneral[a][0]){
					tablaResultHorasAgente[a][2] = ventasHora9a10[c][1];
					total9a10 = total9a10 + tablaResultHorasAgente[a][2].toInteger()
				}
			}
			for(int d = 0; d < ventasHora10a11.size(); d++){
				if(ventasHora10a11[d][0] == ventasPorHoraGeneral[a][0]){
					tablaResultHorasAgente[a][3] = ventasHora10a11[d][1];
					total10a11 = total10a11 + tablaResultHorasAgente[a][3].toInteger()
				}
			}
			for(int e = 0; e < ventasHora11a12.size(); e++){
				if(ventasHora11a12[e][0] == ventasPorHoraGeneral[a][0]){
					tablaResultHorasAgente[a][4] = ventasHora11a12[e][1];
					total11a12 = total11a12 + tablaResultHorasAgente[a][4].toInteger()
				}
			}
			for(int f = 0; f < ventasHora12a13.size(); f++){
				if(ventasHora12a13[f][0] == ventasPorHoraGeneral[a][0]){
					tablaResultHorasAgente[a][5] = ventasHora12a13[f][1];
					total12a13 = total12a13 + tablaResultHorasAgente[a][5].toInteger()
				}
			}
			for(int g = 0; g < ventasHora13a14.size(); g++){
				if(ventasHora13a14[g][0] == ventasPorHoraGeneral[a][0]){
					tablaResultHorasAgente[a][6] = ventasHora13a14[g][1];
					total13a14 = total13a14 + tablaResultHorasAgente[a][6].toInteger()
				}
			}
			for(int h = 0; h < ventasHora14a15.size(); h++){
				if(ventasHora14a15[h][0] == ventasPorHoraGeneral[a][0]){
					tablaResultHorasAgente[a][7] = ventasHora14a15[h][1];
					total14a15 = total14a15 + tablaResultHorasAgente[a][7].toInteger()
				}
			}
			for(int i1 = 0; i1 < ventasHora15a16.size(); i1++){
				if(ventasHora15a16[i1][0] == ventasPorHoraGeneral[a][0]){
					tablaResultHorasAgente[a][8] = ventasHora15a16[i1][1];
					total15a16 = total15a16 + tablaResultHorasAgente[a][8].toInteger()
				}
			}
			for(int j1 = 0; j1 < ventasHora16a17.size(); j1++){
				if(ventasHora16a17[j1][0] == ventasPorHoraGeneral[a][0]){
					tablaResultHorasAgente[a][9] = ventasHora16a17[j1][1];
					total16a17 = total16a17 + tablaResultHorasAgente[a][9].toInteger()
				}
			}
			for(int k1 = 0; k1 < ventasHora17a18.size(); k1++){
				if(ventasHora17a18[k1][0] == ventasPorHoraGeneral[a][0]){
					tablaResultHorasAgente[a][10] = ventasHora17a18[k1][1];
					total17a18 = total17a18 + tablaResultHorasAgente[a][10].toInteger()
				}
			}
			for(int l1 = 0; l1 < ventasHora18a19.size(); l1++){
				if(ventasHora18a19[l1][0] == ventasPorHoraGeneral[a][0]){
					tablaResultHorasAgente[a][11] = ventasHora18a19[l1][1];
					total18a19 = total18a19 + tablaResultHorasAgente[a][11].toInteger()
				}
			}
			for(int m = 0; m < ventasHora19a20.size(); m++){
				if(ventasHora19a20[m][0] == ventasPorHoraGeneral[a][0]){
					tablaResultHorasAgente[a][12] = ventasHora19a20[m][1];
					total19a20 = total19a20 + tablaResultHorasAgente[a][12].toInteger()
				}
			}
			for(int n = 0; n < ventasHora20a21.size(); n++){
				if(ventasHora20a21[n][0] == ventasPorHoraGeneral[a][0]){
					tablaResultHorasAgente[a][13] = ventasHora20a21[n][1];
					total20a21 = total20a21 + tablaResultHorasAgente[a][13].toInteger()
				}
			}
			for(int o = 0; o < ventasPorHoraCruzadas.size(); o++){
				if(ventasPorHoraCruzadas[o][0] == ventasPorHoraGeneral[a][0]){
					tablaResultHorasAgente[a][14] = ventasPorHoraCruzadas[o][1];
					totalCruzadasHora = totalCruzadasHora + tablaResultHorasAgente[a][14].toInteger()
				}
			}
			for(int p = 0; p < ventasPorHoraExitosas.size(); p++){
				if(ventasPorHoraExitosas[p][0] == ventasPorHoraGeneral[a][0]){
					tablaResultHorasAgente[a][15] = ventasPorHoraExitosas[p][1];
					totalExitosas = totalExitosas + tablaResultHorasAgente[a][15].toInteger()
				}
			}
		}*/

		def queryTiemposAgentes = TiemposAgentes.executeQuery("select nombreAgente, efectivos, meta, faltantes, porcentajeMeta, promedioHora, inicioConexion, finConexion, tiempoConexion, tiempoBreak, tiempoMuerto, tiempoEfectivo, observacion, totalGrupoEfectivo, totalGrupoMeta, totalGrupoFaltantes, totalGrupoPorcentajesMeta, totalGrupoPorcentajesPromedioHora, fecha from TiemposAgentes")
		String[][] tableTiemposAgentes = new String[queryTiemposAgentes.size()][20]
		String totalEfectivosGrupo = "";
		String totalMetaGrupo = ""
		String totalFaltantesGrupo = ""
		String totalPorcentajeMetaGrupo = ""
		String totalPromedioHoraGrupo = ""
		String fechaHoraActualizacion = ""

		for(int o = 0; o < tableTiemposAgentes.size(); o++){
			tableTiemposAgentes[o][0] = queryTiemposAgentes[o][0];
			tableTiemposAgentes[o][1] = queryTiemposAgentes[o][1];
			tableTiemposAgentes[o][2] = queryTiemposAgentes[o][2];
			tableTiemposAgentes[o][3] = queryTiemposAgentes[o][3];
			tableTiemposAgentes[o][4] = queryTiemposAgentes[o][4];
			tableTiemposAgentes[o][5] = queryTiemposAgentes[o][5];
			tableTiemposAgentes[o][6] = queryTiemposAgentes[o][6];
			tableTiemposAgentes[o][7] = queryTiemposAgentes[o][7];
			tableTiemposAgentes[o][8] = queryTiemposAgentes[o][8];
			tableTiemposAgentes[o][9] = queryTiemposAgentes[o][9];
			tableTiemposAgentes[o][10] = queryTiemposAgentes[o][10];
			tableTiemposAgentes[o][11] = queryTiemposAgentes[o][11];
			tableTiemposAgentes[o][12] = queryTiemposAgentes[o][12];
			totalEfectivosGrupo = queryTiemposAgentes[o][13];
			totalMetaGrupo = queryTiemposAgentes[o][14];
			totalFaltantesGrupo =  queryTiemposAgentes[o][15];
			totalPorcentajeMetaGrupo =  queryTiemposAgentes[o][16];
			totalPromedioHoraGrupo =  queryTiemposAgentes[o][17];
			fechaHoraActualizacion =  queryTiemposAgentes[o][18];
		}

		//Para la tabla de gestionados, exitosos y contactados por base
		def contactadosPorBase = Clientes.executeQuery("select nombreCampania, count(*) from Clientes where intentos != 0 and estadoGestion = 'CONTACTADO' and fechaGestion between :fechaInicio and :fechaFin group by nombreCampania order by nombreCampania", [fechaInicio: fechaInicio, fechaFin: fechaFin])
		def exitososPorBase = Clientes.executeQuery("select nombreCampania, count(*) from Clientes where intentos != 0 and estadoGestion = 'CONTACTADO' and subestadoGestion in (:subestados) and fechaGestion between :fechaInicio and :fechaFin group by nombreCampania order by nombreCampania", [fechaInicio: fechaInicio, fechaFin: fechaFin, subestados: subestados])

		String[][] tablaResult1 = new String[contactadosPorBase.size()][4]
		//Lleno la matriz de resultados con los resultados de las consultas anteriores
		for(int i = 0; i < tablaResult1.size(); i++){
			tablaResult1[i][0] = contactadosPorBase[i][0]
			tablaResult1[i][1] = contactadosPorBase[i][1]
			for(int j = 0; j < exitososPorBase.size(); j++){
				if(exitososPorBase[j][0] == contactadosPorBase[i][0]){
					tablaResult1[i][2] = exitososPorBase[j][1]
					break
				}
			}
		}


		[inicioSesion: inicioSesion, tablaResult: tablaResult, totalGestionados: totalGestionados, totalContactados: totalContactados,
		 totalNoContactados: totalNoContactados, totalExitosos: totalExitosos, totalCreditos: totalCreditos, totalCruzadas: totalCruzadas,
		 ventasPorHoraVendedor:ventasPorHoraVendedor ,tableTiemposAgentes: tableTiemposAgentes, totalEfectivosGrupo: totalEfectivosGrupo, totalMetaGrupo: totalMetaGrupo, totalFaltantesGrupo: totalFaltantesGrupo,
		 totalPorcentajeMetaGrupo: totalPorcentajeMetaGrupo, totalPromedioHoraGrupo: totalPromedioHoraGrupo, fechaHoraActualizacion: fechaHoraActualizacion,
		 tablaResult1: tablaResult1];

	}



	private String[][] consolidarExitAdic(ArrayList exitosos, ArrayList cruzadas){
		String[][] consolidado = new String[exitosos.size()][3]
		//String[][] consolidado = new String[cruzadas.size()][exitosos.size()]
		for(int i = 0; i < exitosos.size(); i++){
			boolean encontrado = false
			for(int j = 0; j < cruzadas.size(); j++){
				if( exitosos[i][0] == cruzadas[j][0]){
					consolidado[i][0] = exitosos[i][0]
					consolidado[i][1] = exitosos[i][1]
					consolidado[i][2] = cruzadas[j][1]
					encontrado = true
					break
				}
			}
			if(!encontrado){
				consolidado[i][0] = exitosos[i][0]
				consolidado[i][1] = exitosos[i][1]
				consolidado[i][2] = "0"
			}
		}
		return consolidado
	}

	def dashboardAgente(){
		Date fechaInicio = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 00:00:00"));
		Date fechaFin = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 23:59:59"));
		Subestado subestado = Subestado.findByNombre("ACEPTA");
		Rol rol = Rol.findByNombre("OPERADOR");
		def ventasPorUsuario = Clientes.executeQuery("select nombreVendedor, count(*) from Clientes where subestadoGestion = :subestado and fechaGestion between :fechaInicio and :fechaFin group by nombreVendedor order by count(*) desc", [subestado: subestado, fechaInicio: fechaInicio, fechaFin: fechaFin]);
		def inicioSesion = Sesion.executeQuery("select usuario.nombre, time(min(dateCreated)) from Sesion where dateCreated between :fechaInicio and :fechaFin and usuario.rol = :rol group by usuario.nombre order by usuario.nombre", [fechaInicio: fechaInicio, fechaFin: fechaFin, rol: rol]);

		//Para la tabla de gestionados contactados y no contactados
		int totalGestionados = 0;
		int totalContactados = 0;
		int totalNoContactados = 0;
		def gestionadosAgente = Clientes.executeQuery("select nombreVendedor, count(*) from Clientes where intentos != 0 and fechaGestion between :fechaInicio and :fechaFin group by nombreVendedor order by nombreVendedor", [fechaInicio: fechaInicio, fechaFin: fechaFin]);
		def contactadosAgente = Clientes.executeQuery("select nombreVendedor, count(*) from Clientes where intentos != 0 and estadoGestion = 'CONTACTADO' and fechaGestion between :fechaInicio and :fechaFin group by nombreVendedor order by nombreVendedor", [fechaInicio: fechaInicio, fechaFin: fechaFin]);
		def noContactadosAgente = Clientes.executeQuery("select nombreVendedor, count(*) from Clientes where intentos != 0 and estadoGestion = 'NO CONTACTADO' and fechaGestion between :fechaInicio and :fechaFin group by nombreVendedor order by nombreVendedor", [fechaInicio: fechaInicio, fechaFin: fechaFin]);
		String[][] tablaResult = new String[gestionadosAgente.size()][4];
		//Lleno la matriz de resultados con los resultados de las onsultas anteriores
		for(int i = 0; i < tablaResult.size(); i++){
			tablaResult[i][0] = gestionadosAgente[i][0];
			tablaResult[i][1] = gestionadosAgente[i][1];
			for(int j = 0; j < contactadosAgente.size(); j++){
				if(contactadosAgente[j][0] == gestionadosAgente[i][0]){
					tablaResult[i][2] = contactadosAgente[j][1];
					break;
				}
			}
			for(int k = 0; k < noContactadosAgente.size(); k++){
				if(noContactadosAgente[k][0] == gestionadosAgente[i][0]){
					tablaResult[i][3] = noContactadosAgente[k][1];
					break;
				}
			}
		}
		//Recorro la matriz de resultados para obtener los totales
		for(int i = 0; i < tablaResult.size(); i++){
			totalGestionados = totalGestionados + tablaResult[i][1].toInteger();
			if(tablaResult[i][2] != null)
				totalContactados = totalContactados + tablaResult[i][2].toInteger();
			if(tablaResult[i][3] != null)
				totalNoContactados = totalNoContactados + tablaResult[i][3].toInteger();
		}

		[ventasPorUsuario: ventasPorUsuario, inicioSesion: inicioSesion, tablaResult: tablaResult, totalGestionados: totalGestionados, totalContactados: totalContactados, totalNoContactados: totalNoContactados];
	}

	def index(Integer max) {
//        params.max = Math.min(max ?: 10, 100)
		Usuario usuario = Usuario.findById(session.user.id);
		if(Util.isAdmin(usuario.usuario))
			respond Usuario.list(), model:[usuarioInstanceCount: Usuario.count()]
		else
			respond Usuario.executeQuery("from Usuario where rol.nombre != 'ADMINISTRADOR'"), model:[usuarioInstanceCount: Usuario.count()]
	}

	def show(Usuario usuarioInstance) {
		respond usuarioInstance
	}

	def create() {
		respond new Usuario(params)
	}

	@Transactional
	def save(Usuario usuarioInstance) {
		if (usuarioInstance == null) {
			notFound()
			return
		}

		if (usuarioInstance.hasErrors()) {
			respond usuarioInstance.errors, view:'create'
			return
		}

		usuarioInstance.save flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.created.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuarioInstance.id])
				redirect usuarioInstance
			}
			'*' { respond usuarioInstance, [status: CREATED] }
		}
	}

	def edit(Usuario usuarioInstance) {
		respond usuarioInstance
	}

	@Transactional
	def update(Usuario usuarioInstance) {
		if (usuarioInstance == null) {
			notFound()
			return
		}

		if (usuarioInstance.hasErrors()) {
			respond usuarioInstance.errors, view:'edit'
			return
		}

		usuarioInstance.save flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.updated.message', args: [message(code: 'Usuario.label', default: 'Usuario'), usuarioInstance.id])
				redirect usuarioInstance
			}
			'*'{ respond usuarioInstance, [status: OK] }
		}
	}

	@Transactional
	def delete(Usuario usuarioInstance) {

		if (usuarioInstance == null) {
			notFound()
			return
		}

		usuarioInstance.delete flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.deleted.message', args: [message(code: 'Usuario.label', default: 'Usuario'), usuarioInstance.id])
				redirect action:"index", method:"GET"
			}
			'*'{ render status: NO_CONTENT }
		}
	}

	protected void notFound() {
		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), params.id])
				redirect action: "index", method: "GET"
			}
			'*'{ render status: NOT_FOUND }
		}
	}
}
