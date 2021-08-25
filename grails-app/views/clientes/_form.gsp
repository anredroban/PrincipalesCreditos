<%@ page import="callcenter.Clientes"%>

<div class="line">Datos Personales</div>
<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'nombre', 'error')} required">
	<label for="nombre"> <g:message code="clientes.nombre.label"
			default="Nombre" /> <span class="required-indicator">*</span>
	</label>
	<g:textField name="nombre" required=""
		value="${cliente?.nombre}" />
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'cedula', 'error')} required">
	<label for="cedula"> <g:message code="clientes.cedula.label"
			default="Cedula" /> <span class="required-indicator">*</span>
	</label>
	<g:textField name="cedula" required=""
		value="${cliente?.cedula}" maxlength="10" />
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'correo', 'error')} required">
	<label for="correo"> <g:message code="clientes.correo.label"
			default="Correo" /> <span class="required-indicator">*</span>
	</label>
	<g:textField name="correo" required=""
		value="${cliente?.correo}" />
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'nacionalidad', 'error')} required">
	<label for="nacionalidad"> <g:message
			code="clientes.nacionalidad.label" default="Nacionalidad" /> <span
		class="required-indicator">*</span>
	</label>
	<g:textField name="nacionalidad" required=""
		value="${cliente?.nacionalidad}" />
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'telefono', 'error')} required">
	<label for="telefono"> <g:message
			code="clientes.telefono.label" default="Telefono" /> <span
		class="required-indicator">*</span>
	</label>
	<g:textField name="telefono" required=""
		value="${cliente?.telefono}" maxlength="7" />
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'celular', 'error')} required">
	<label for="celular"><g:message code="clientes.celular.label"
			default="Celular" /> <span class="required-indicator">*</span> </label>
	<g:textField name="celular" required=""
		value="${cliente?.celular}" maxlength="10"/>
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'sexo', 'error')} required">
	<label for="sexo"> <g:message code="clientes.sexo.label"
			default="Sexo" /> <span class="required-indicator">*</span>
	</label>
	<g:select name="sexo"
		from="${cliente.constraints.sexo.inList}" required=""
		value="${cliente?.sexo}" valueMessagePrefix="clientes.sexo" />
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'estadoCivil', 'error')} required">
	<label for="estadoCivil"> <g:message
			code="clientes.estadoCivil.label" default="Estado Civil" /> <span
		class="required-indicator">*</span>
	</label>
	<g:select name="estadoCivil"
		from="${callcenter.Clientes$EstadoCivil?.values()}"
		keys="${callcenter.Clientes$EstadoCivil.values()*.name()}" required=""
		value="${cliente?.estadoCivil?.name()}" />
</div>

<br>
<div class="line">Dirección</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'tipoVivienda', 'error')} required">
	<label for="tipoVivienda"> <g:message
			code="clientes.tipoVivienda.label" default="Tipo Vivienda" /> <span
		class="required-indicator">*</span>
	</label>
	<g:select name="tipoVivienda"
		from="${callcenter.Clientes$TipoVivienda?.values()}"
		keys="${callcenter.Clientes$TipoVivienda.values()*.name()}"
		required="" value="${cliente?.tipoVivienda?.name()}" />
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'ciudad', 'error')} required">
	<label for="ciudad"> <g:message code="clientes.ciudad.label"
			default="Ciudad" /> <span class="required-indicator">*</span>
	</label>
	<g:select id="ciudad" name="ciudad" from="${['QUITO', 'GUAYAQUIL']}" noSelection="['':'-Seleccione-']"/>
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'canton', 'error')} required">
	<label for="canton"> <g:message code="clientes.canton.label"
			default="Canton" /> <span class="required-indicator">*</span>
	</label>
	<g:textField name="canton" required=""
		value="${cliente?.canton}" />
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'parroquia', 'error')} required">
	<label for="parroquia"> <g:message
			code="clientes.parroquia.label" default="Parroquiuia" /> <span
		class="required-indicator">*</span>
	</label>
	<g:textField name="parroquia" required=""
		value="${cliente?.parroquia}" />
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'sector', 'error')} required">
	<label for="sector"> <g:message code="clientes.sector.label"
			default="Sector" /> <span class="required-indicator">*</span>
	</label>
	<g:textField name="sector" required=""
		value="${cliente?.sector}" />
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'direccionEstadoCta', 'error')} required">
	<label for="direccionEstadoCta"> <g:message
			code="clientes.direccionEstadoCta.label"
			default="Direccion Estado Cta" /> <span class="required-indicator">*</span>
	</label>
	<g:textField name="direccionEstadoCta" required=""
		value="${cliente?.direccionEstadoCta}" />
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'referencia', 'error')} required">
	<label for="referencia"> <g:message
			code="clientes.referencia.label" default="Referencia" /> <span
		class="required-indicator">*</span>
	</label>
	<g:textField name="referencia" required=""
		value="${cliente?.referencia}" />
</div>

<br>
<div class="line">Persona Juridica</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'opciones', 'error')} required">
	<label for="optJuridica">SI</label>
	<g:radio name="optJuridica" value="SI" id="juridica1" />
	<label for="optJuridica">NO</label>
	<g:radio name="optJuridica" value="NO" id="juridica2" />
</div>

<div id="juridica">
<div class="line"></div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'razonSocial', 'error')} ">
	<label for="ruc"> <g:message code="clientes.razonSocial.label"
			default="Razon Social" />
	</label>
	<g:textField name="razonSocial" value="${cliente?.ruc}" />
</div>


<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'ruc', 'error')} ">
	<label for="ruc"> <g:message code="clientes.ruc.label"
			default="Ruc" />

	</label>
	<g:textField name="ruc" value="${cliente?.ruc}" />
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'actividadEconomica', 'error')} ">
	<label for="actividadEconomica"> <g:message
			code="clientes.actividadEconomica.label"
			default="Actividad Economica" />
	</label>
	<g:textField name="actividadEconomica"
		value="${cliente?.actividadEconomica}" />
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'representanteLegal', 'error')} ">
	<label for="representanteLegal"> <g:message
			code="clientes.representanteLegal.label"
			default="Representante Legal" />
	</label>
	<g:textField name="representanteLegal"
		value="${cliente?.representanteLegal}" />
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'cedRepLegal', 'error')} ">
	<label for="cedRepLegal"> <g:message
			code="clientes.cedRepLegal.label" default="Cedula" />
	</label>
	<g:textField name="cedRepLegal"
		value="${cliente?.cedRepLegal}" />
</div>
</div>

<br>
<div class="line">Referencias Personales</div>

<div
	class="col-lg-6 ${hasErrors(bean: cliente, field: 'referencia1', 'error')} required">
	<label for="referencia1"> <g:message
			code="clientes.referencia1.label" default="Referencia1" /> <span
		class="required-indicator">*</span>
	</label>
	<g:textField name="referencia1" required=""
		value="${cliente?.referencia1}" />
</div>

<div
	class="col-lg-6 ${hasErrors(bean: cliente, field: 'tlfRef1', 'error')} required">
	<label for="tlfRef1"> <g:message code="clientes.tlfRef1.label"
			default="Telefono" /> <span class="required-indicator">*</span>
	</label>
	<g:textField name="tlfRef1" required=""
		value="${cliente?.tlfRef1}" maxlength="10"/>
</div>

<div
	class="col-lg-6 ${hasErrors(bean: cliente, field: 'referencia2', 'error')} required">
	<label for="referencia2"> <g:message
			code="clientes.referencia2.label" default="Referencia2" /> <span
		class="required-indicator">*</span>
	</label>
	<g:textField name="referencia2" required=""
		value="${cliente?.referencia2}" />
</div>

<div
	class="col-lg-6 ${hasErrors(bean: cliente, field: 'tlfRef2', 'error')} required">
	<label for="tlfRef2"> <g:message code="clientes.tlfRef2.label"
			default="Telefono" /> <span class="required-indicator">*</span>
	</label>
	<g:textField name="tlfRef2" required=""
		value="${cliente?.tlfRef2}" maxlength="10"/>
</div>

<div class="line">¿Los datos de instalación son los mismos que los
	datos del cliente?</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'opciones', 'error')} required">
	<label for="opciones">SI</label>
	<g:radio name="opciones" value="SI" id="opcion1" />
	<label for="opciones">NO</label>
	<g:radio name="opciones" value="NO" id="opcion2" />
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'personaContacto', 'error')} required">
	<label for="personaContacto"> <g:message
			code="clientes.personaContacto.label" default="Persona Contacto" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="personaContacto" required=""
		value="${cliente?.personaContacto}" />
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'horario', 'error')} required">
	<label for="horario"> <g:message code="clientes.horario.label"
			default="Hora Visita" /> <span class="required-indicator">*</span>
	</label>
	<g:datePicker name="horario" value="${new Date()}" />
</div>

<div id="instalacion">
	<div class="line">Datos Instalacion</div>

	<div
		class="col-lg-4 ${hasErrors(bean: cliente, field: 'direccionInstalacion', 'error')} required">
		<label for="direccionInstalacion"> <g:message
				code="clientes.direccionInstalacion.label"
				default="Direccion Instalacion" /> <span class="required-indicator">*</span>
		</label>
		<g:textField name="direccionInstalacion" required=""
			value="${cliente?.direccionInstalacion}" />
	</div>

	<div
		class="col-lg-4 ${hasErrors(bean: cliente, field: 'refInstalacion', 'error')} required">
		<label for="refInstalacion"> <g:message
				code="clientes.refInstalacion.label" default="Ref Instalacion" /> <span
			class="required-indicator">*</span>
		</label>
		<g:textField name="refInstalacion" required=""
			value="${cliente?.refInstalacion}" />
	</div>

	<div
		class="col-lg-4 ${hasErrors(bean: cliente, field: 'sectorInstalacion', 'error')} required">
		<label for="sectorInstalacion"> <g:message
				code="clientes.sectorInstalacion.label" default="Sector Instalacion" />
			<span class="required-indicator">*</span>
		</label>
		<g:textField name="sectorInstalacion" required=""
			value="${cliente?.sectorInstalacion}" />
	</div>

	<div
		class="col-lg-4 ${hasErrors(bean: cliente, field: 'tipoViviendaInstalacion', 'error')} required">
		<label for="tipoViviendaInstalacion"> <g:message
				code="clientes.tipoViviendaInstalacion.label"
				default="Tipo Vivienda Instalacion" /> <span
			class="required-indicator">*</span>
		</label>
		<g:select name="tipoViviendaInstalacion"
			from="${callcenter.Clientes$TipoViviendaInstalacion?.values()}"
			keys="${callcenter.Clientes$TipoViviendaInstalacion.values()*.name()}"
			required=""
			value="${cliente?.tipoViviendaInstalacion?.name()}" />

	</div>

	<div
		class="col-lg-4 ${hasErrors(bean: cliente, field: 'ciudadInstalacion', 'error')} required">
		<label for="ciudadInstalacion"> <g:message
				code="clientes.ciudadInstalacion.label" default="Ciudad Instalacion" />
			<span class="required-indicator">*</span>
		</label>
		<g:select id="ciudadInstalacion" name="ciudadInstalacion" from="${['QUITO', 'GUAYAQUIL']}" noSelection="['':'-Seleccione-']"/>
	</div>

	<div
		class="col-lg-4 ${hasErrors(bean: cliente, field: 'parroquiaInstalacion', 'error')} required">
		<label for="parroquiaInstalacion"> <g:message
				code="clientes.parroquiaInstalacion.label"
				default="Parroquia Instalacion" /> <span class="required-indicator">*</span>
		</label>
		<g:textField name="parroquiaInstalacion" required=""
			value="${cliente?.parroquiaInstalacion}" />
	</div>

	<div
		class="col-lg-4 ${hasErrors(bean: cliente, field: 'cantonInstalacion', 'error')} required">
		<label for="cantonInstalacion"> <g:message
				code="clientes.cantonInstalacion.label" default="Canton Instalacion" />
			<span class="required-indicator">*</span>
		</label>
		<g:textField name="cantonInstalacion" required=""
			value="${cliente?.cantonInstalacion}" />
	</div>

	<div
		class="col-lg-4 ${hasErrors(bean: cliente, field: 'correoInstalacion', 'error')} required">
		<label for="correoInstalacion"> <g:message
				code="clientes.correoInstalacion.label" default="Correo Instalacion" />
			<span class="required-indicator">*</span>
		</label>
		<g:textField name="correoInstalacion" required=""
			value="${cliente?.correoInstalacion}" />
	</div>

	<div
		class="col-lg-4 ${hasErrors(bean: cliente, field: 'telfSitio', 'error')} required">
		<label for="telfSitio"> <g:message
				code="clientes.telfSitio.label" default="Telf Sitio" /> <span
			class="required-indicator">*</span>
		</label>
		<g:textField name="telfSitio" required=""
			value="${cliente?.telfSitio}" maxlength="7"/>
	</div>

	<div
		class="col-lg-4 ${hasErrors(bean: cliente, field: 'celularSitio', 'error')} required">
		<label for="celularSitio"> <g:message
				code="clientes.celularSitio.label" default="Celular Sitio" /> <span
			class="required-indicator">*</span>
		</label>
		<g:textField name="celularSitio" required=""
			value="${cliente?.celularSitio}" maxlength="10"/>
	</div>
</div>

<br><br>
<div class="line">Coordenadas</div>

<div class="col-lg-6 ${hasErrors(bean: cliente, field: 'latitud', 'error')} required">
	<label for="latitud"> <g:message
			code="clientes.latitud.label" default="Latitud" /> <span
		class="required-indicator">*</span>
	</label>
	<g:textField name="latitud" required="" value="${cliente?.latitud}" />
</div>

<div class="col-lg-6 ${hasErrors(bean: cliente, field: 'longitud', 'error')} required">
	<label for="longitud"> <g:message
			code="clientes.longitud.label" default="Longitud" /> <span
		class="required-indicator">*</span>
	</label>
	<g:textField name="longitud" required="" value="${cliente?.longitud}" />
</div>

<div class="col-lg-6 ${hasErrors(bean: cliente, field: 'latitudInstlacion', 'error')} required">
	<label id="lbLatitud" for="latitud"> <g:message
			code="clientes.latitudInstlacion.label" default="Latitud Instalación" /> <span
		class="required-indicator">*</span>
	</label>
	<g:textField name="latitudInstlacion" required="" value="${cliente?.latitudInstlacion}" />
</div>

<div class="col-lg-6 ${hasErrors(bean: cliente, field: 'longitudInstlacion', 'error')} required">
	<label id="lbLongitud" for="longitud"> <g:message
			code="clientes.longitud.label" default="Longitud Instalación" /> <span
		class="required-indicator">*</span>
	</label>
	<g:textField name="longitudInstlacion" required="" value="${cliente?.longitudInstlacion}" />
</div>

<div class="line">Servicio Contratado</div>

<div
	class="col-lg-6 ${hasErrors(bean: cliente, field: 'tipoInternet', 'error')} required">
	<label for="tipoInternet"> <g:message
			code="clientes.tipoInternet.label" default="Tipo Internet" /> <span
		class="required-indicator">*</span>
	</label>
	<g:select name="tipoInternet"
		from="${callcenter.Clientes$TipoInternet?.values()}"
		keys="${callcenter.Clientes$TipoInternet.values()*.name()}"
		required="" value="${cliente?.tipoInternet?.name()}" />
</div>

<div
	class="col-lg-6 ${hasErrors(bean: cliente, field: 'velocidadInternet', 'error')} required">
	<label for="velocidadInternet"> <g:message
			code="clientes.velocidadInternet.label" default="Velocidad Internet" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="velocidadInternet"
		from="${cliente.constraints.velocidadInternet.inList}"
		required="" value="${cliente?.velocidadInternet}"
		valueMessagePrefix="clientes.velocidadInternet" />
</div>

<div class="line">Forma de pago e información de crédito</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'formaPago', 'error')} required">
	<label for="formaPago"> <g:message
			code="clientes.formaPago.label" default="Forma Pago" /> <span
		class="required-indicator">*</span>
	</label>
	<g:select name="formaPago"
		from="${callcenter.Clientes$FormaPago?.values()}"
		keys="${callcenter.Clientes$FormaPago.values()*.name()}" required=""
		value="${cliente?.formaPago?.name()}" />
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'banco', 'error')} required">
	<label id="lbBanco" for="banco"> <g:message
			code="clientes.banco.label" default="Banco" /> <span
		class="required-indicator">*</span>
	</label>
	<g:select name="banco" from="${callcenter.Clientes$Banco?.values()}"
		keys="${callcenter.Clientes$Banco.values()*.name()}" required=""
		value="${cliente?.banco?.name()}" />
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'tipoTarjeta', 'error')}">
	<label id="lbTipoTarjeta" for="tipoTarjeta"> <g:message
			code="clientes.tipoTarjeta.label" default="Tipo Tarjeta" /> <span
		class="required-indicator">*</span>
	</label>
	<g:select name="tipoTarjeta"
		from="${callcenter.Clientes$TipoTarjeta?.values()}"
		keys="${callcenter.Clientes$TipoTarjeta.values()*.name()}" required=""
		value="${cliente?.tipoTarjeta?.name()}" />
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'numeroTarjeta', 'error')}">
	<label id="lbNumCard" for="numeroTarjeta"> <g:message
			code="clientes.numeroTarjeta.label" default="Numero" /> <span
		class="required-indicator">*</span>
	</label>
	<g:textField name="numeroTarjeta"
		value="${cliente?.numeroTarjeta}" maxlength="16	"/>
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'codSeguridad', 'error')}">
	<label id="lbCodSegurity" for="codSeguridad"> <g:message
			code="clientes.codSeguridad.label" default="Cod Seguridad" /> <span
		class="required-indicator">*</span>
	</label>
	<g:textField name="codSeguridad"
		value="${cliente?.codSeguridad}" maxlength="4"/>
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'fechaExpiracion', 'error')}">
	<label id="lbFechaExp" for="fechaExpiracion"> <g:message
			code="clientes.fechaExpiracion.label" default="Fecha Expiración" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="fechaExpiracion"
		value="${cliente?.fechaExpiracion}" />
</div>

<div class="line">Observaciones</div>

<div
	class="col-lg-6 ${hasErrors(bean: cliente, field: 'observaciones', 'error')}">
	<label for="chObservacioens"> <g:message
			code="clientes.observaciones.label" default="Observaciones" /> <span
		class="required-indicator">*</span>
	</label>
	<g:select id="chObservacioens" name="type" from="${['100% DSC DE INSTALACION APLICA CONDICIONES', '20% DSC DE INSTALACION APLICA CONDICIONES', 'OTROS']}" noSelection="['':'-Seleccione-']"/>
</div>


<div
	class="col-lg-6 ${hasErrors(bean: cliente, field: 'observaciones', 'error')}">
	<label for="observaciones"> <g:message
			code="clientes.observaciones.label" default="Observaciones" /> <span
		class="required-indicator">*</span>
	</label>
	<g:textArea id="observaciones" name="observaciones" value="${cliente?.observaciones}" />
</div>

<br><br><br><br>
<div class="line">Referidos (Dejar en blanco en caso de no existir.)</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'referido1', 'error')}">
	<label for="fechaExpiracion"> <g:message
			code="clientes.referido1.label" default="Nombre Referido 1" />
	</label>
	<g:textField name="referido1" />
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'tlfReferido1', 'error')}">
	<label for="tlfReferido1"> <g:message
			code="clientes.referido1.label" default="Telefono" />
	</label>
	<g:textField name="tlfReferido1" maxlength="10"/>
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'celReferido1', 'error')}">
	<label for="celReferido1"> <g:message
			code="clientes.referido1.label" default="Celular" />
	</label>
	<g:textField name="celReferido1" maxlength="10"/>
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'referido2', 'error')}">
	<label for="referido2"> <g:message
			code="clientes.referido1.label" default="Nombre Referido 2" />
	</label>
	<g:textField name="referido2" />
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'tlfReferido2', 'error')}">
	<label for="tlfReferido2"> <g:message
			code="clientes.tlfReferido2.label" default="Telefono" />
	</label>
	<g:textField name="tlfReferido2" maxlength="10"/>
</div>

<div
	class="col-lg-4 ${hasErrors(bean: cliente, field: 'celReferido1', 'error')}">
	<label for="celReferido2"> <g:message
			code="clientes.celReferido2.label" default="Celular" />
	</label>
	<g:textField name="celReferido2" maxlength="10"/>
</div>
<br>
<br>
<br>
<br>