<meta name="layout" content="main">
<asset:stylesheet src="usogeneral/bootstrap-datepicker.min.css"></asset:stylesheet>

<div class="container-fluid">
	<title>Gestionar Cliente</title>

<asset:stylesheet src="gestion/gestionCliente.css" />


<%--<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">--%>
<asset:stylesheet src="cloudflare/bootstrap.min.css" />
<%--<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min.css">--%>
<asset:stylesheet src="cloudflare/bootstrap-datetimepicker.min.css" />


<%--<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/2.14.1/moment.min.js"></script>--%>
<asset:javascript src="cloudflare/moment.min.js" />
<%--<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>--%>
<asset:javascript src="cloudflare/bootstrap.min.js" />
<%--<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>--%>
<asset:javascript src="cloudflare/bootstrap-datetimepicker.min.js" />
<script type='text/javascript'>
    $(function () {
        $('#datetimepicker3').datetimepicker({
            format: 'LT'
        });
    });
</script>

<script type="text/javascript">
    $(function () {
        $('#datetimepicker1').datetimepicker({
            format: 'YYYY/MM/DD hh:mm A'
        });
    });
</script>


<script type="text/javascript">
    $(function () {
        $('#datetimepicker2').datetimepicker({
            format: 'YYYY/MM/DD hh:mm A'
        });
    });
</script>
<script>
    window.setInterval (BlinkIt, 500);
    var color = "red";
    function BlinkIt () {
        var blink = document.getElementById ("blink");
        color = (color == "#e4e4e4")? "red" : "#e4e4e4";
        blink.style.color = color;
        blink.style.fontSize='36px';}
</script>



<script type="text/javascript">
    $(function () {
        $('#datetimepicker4').datetimepicker({
            format: 'YYYY/MM/DD hh:mm A'
        });
    });
</script>

<g:if test="${cliente.registroExitoso == 'SI'}">
	<div class="col-lg-12 col-md-12 col-xs-12">
		<label id="blink" style="font-size: 28px; font-weight: bold; color: red" >¡AVISO! </label><span id="priodidadTc" style="font-size: 28px; font-weight: bold; color: red">CLIENTE EXITOSO NO GESTIONAR</span>
	</div>
</g:if>

<div class="col-lg-12 col-md-12 col-xs-12">
	<h1><span class="fa fa-phone"></span> Gestionar Cliente</h1>
</div>
<g:form action="guardarCliente">
<div style="border-radius: 30px" class="panel panel-default col-lg-12 col-md-12 col-xs-12">
	<div style="color: red; font-size: 18px; font-weight: bold" class="form-group col-lg-12 col-md-12 col-xs-12">
		<label>TIPO PRODUCTO: </label>${cliente.tipoProducto}
	</div>
	<div class="form-group col-lg-4 col-md-6 col-xs-12">
		<label>Nombres: </label>
		${cliente.nombre}
	</div>
	<div class="form-group col-lg-4 col-md-6 col-xs-12">
		<label>Identificación: </label>
		${cliente.identificacion}
	</div>
	<g:if test="${cliente.segmento && cliente.segmento.trim() != ''}">
		<div class="form-group col-lg-4 col-md-6 col-xs-12">
			<label>Segmento: </label>
			${cliente.segmento}
		</div>
	</g:if>
	<g:if test="${cliente.subsegmento && cliente.subsegmento.trim() != ''}">
		<div class="form-group col-lg-4 col-md-6 col-xs-12">
			<label>Sub segmento: </label>
			${cliente.subsegmento}
		</div>
	</g:if>
	<g:if test="${cliente.tipoCuenta && cliente.tipoCuenta.trim() != ''}">
		<div class="form-group col-lg-4 col-md-6 col-xs-12">
			<label>Tipo Cuenta: </label>
			${cliente.tipoCuenta}
		</div>
	</g:if>
	<g:if test="${cliente.numeroCuenta && cliente.numeroCuenta.trim() != ''}">
		<div class="form-group col-lg-4 col-md-6 col-xs-12">
			<label>Num Cuenta: </label>
			${utilitarios.Util.hideCardNumber(cliente.numeroCuenta)}
		</div>
	</g:if>
<%--	<g:if test="${cliente.edad && cliente.edad.trim() != ''}">
		<div class="form-group col-lg-4 col-md-6 col-xs-12">
			<label>Edad: </label>
			${cliente.edad}
		</div>
	</g:if>--%>
	<g:if test="${cliente.estadoCivil && cliente.estadoCivil.trim() != ''}">
		<div class="form-group col-lg-4 col-md-6 col-xs-12">
			<label>Estado Civil: </label>
			${cliente.estadoCivil}
		</div>
	</g:if>
	<g:if test="${cliente.genero && cliente.genero.trim() != ''}">
		<div class="form-group col-lg-4 col-md-6 col-xs-12">
			<label>Sexo: </label>
			${cliente.genero}
		</div>
	</g:if>
	<g:if test="${cliente.fechaNacimiento && cliente.fechaNacimiento.trim() != ''}">
		<div class="form-group col-lg-4 col-md-6 col-xs-12">
			<label>Fecha Nacimiento: </label>
			${cliente.fechaNacimiento}
		</div>
	</g:if>

	<g:if test="${cliente.provinciaDomicilio && cliente.provinciaDomicilio.trim() != ''}">
		<div class="form-group col-lg-4 col-md-6 col-xs-12">
			<label>Provincia Domicilio: </label>
			${cliente.provinciaDomicilio}
		</div>
	</g:if>

	<g:if test="${cliente.ciudadDomicilio && cliente.ciudadDomicilio.trim() != ''}">
		<div class="form-group col-lg-4 col-md-6 col-xs-12">
			<label>Ciudad Domicilio: </label>
			${cliente.ciudadDomicilio}
		</div>
	</g:if>

	<g:if test="${cliente.direccionDomicilio && cliente.direccionDomicilio.trim() != ''}">
		<div class="form-group col-lg-12 col-md-12 col-xs-12">
			<label>Direccion Domicilio: </label>
			${cliente.direccionDomicilio}
		</div>
	</g:if>

	<g:if test="${cliente.provinciaTrabajo && cliente.provinciaTrabajo.trim() != ''}">
		<div class="form-group col-lg-4 col-md-6 col-xs-12">
			<label>Provincia Trabajo: </label>
			${cliente.provinciaTrabajo}
		</div>
	</g:if>

	<g:if test="${cliente.ciudadTrabajo && cliente.ciudadTrabajo.trim() != ''}">
		<div class="form-group col-lg-4 col-md-6 col-xs-12">
			<label>Ciudad Trabajo: </label>
			${cliente.ciudadTrabajo}
		</div>
	</g:if>

	<g:if test="${cliente.direccionTrabajo && cliente.direccionTrabajo.trim() != ''}">
		<div class="form-group col-lg-12 col-md-12 col-xs-12">
			<label>Direccion Trabajo: </label>
			${cliente.direccionTrabajo}
		</div>
	</g:if>

	<g:if test="${cliente.perfilRiesgoEndeud && cliente.perfilRiesgoEndeud.trim() != ''}">
		<div class="form-group col-lg-4 col-md-6 col-xs-12">
			<label>Perfil Riesgo Endeudamiento: </label>
			${cliente.perfilRiesgoEndeud}
		</div>
	</g:if>
	<g:if test="${cliente.deudaProtegida && cliente.deudaProtegida.trim() != ''}">
		<div class="form-group col-lg-4 col-md-6 col-xs-12">
			<label>Ofertar PDP: </label>
			${cliente.deudaProtegida}
		</div>
	</g:if>
	<g:if test="${cliente.codigoMarca && cliente.codigoMarca.trim() != ''}">
		<div class="form-group col-lg-4 col-md-6 col-xs-12">
			<label>Codigo Marca: </label>
			${cliente.codigoMarca}
		</div>
	</g:if>
	<g:if test="${cliente.planRecompensas && cliente.planRecompensas.trim() != ''}">
		<div class="form-group col-lg-4 col-md-6 col-xs-12">
			<label>Plan Recompensas: </label>
			${cliente.planRecompensas}
		</div>
	</g:if>
</div>

<g:if test="${cliente.prioridadCampania == 'PRI TC'}">
	<div class="col-lg-12 col-md-12 col-xs-12">
		<label id="blink" style="font-size: 28px; font-weight: bold; color: red" >¡AVISO! </label><span id="priodidadTc" style="font-size: 28px; font-weight: bold; color: red">DAR PRIORIDAD TARJETA DE CRÉDITO TC</span>
	</div>
</g:if>
<g:if test="${cliente.prioridadCampania == 'PRI CRD'}">
	<div class="col-lg-12 col-md-12 col-xs-12">
		<label id="blink" style="font-size: 28px; font-weight: bold; color: red">¡AVISO! </label><span id="prioridadCredito" style="font-size: 28px; font-weight: bold; color: red">DAR PRIORIDAD CRÉDITO</span>
	</div>
</g:if>

<div class="col-lg-12 col-md-12 col-xs-12">
	<h5> <b>Datos de Tarjeta</b></h5>
</div>
<g:if test="${cliente.credConsumEsc1 && cliente.credConsumEsc1.trim() != ''}">
	<div style="border-radius: 30px; /*background-color: #58d68d*/ background-image: radial-gradient(circle,  #82e0aa, #58d68d,  #2ecc71 );" class="panel panel-default col-lg-12 col-md-12 col-xs-12">
		<g:if test="${cliente.credConsumEsc1 && cliente.credConsumEsc1.trim() != ''}">
			<div class="form-group col-lg-4 col-md-6 col-xs-12">
				<label id="labelConsumoEscenario1">Créd Consumo Esc 1: </label>
				${cliente.credConsumEsc1}
			</div>
		</g:if>
		<g:if test="${cliente.cuotaConsumEsc1 && cliente.cuotaConsumEsc1.trim() != ''}">
			<div class="form-group col-lg-4 col-md-6 col-xs-12">
				<label>Cuota Consumo Esc 1: </label>
				${cliente.cuotaConsumEsc1}
			</div>
		</g:if>
		<g:if test="${cliente.plazoConsumEsc1 && cliente.plazoConsumEsc1.trim() != ''}">
			<div class="form-group col-lg-4 col-md-6 col-xs-12">
				<label>Plazo Consumo Esc 1: </label>
				${cliente.plazoConsumEsc1}
			</div>
		</g:if>
		<g:if test="${cliente.prodConsumEsc1 && cliente.prodConsumEsc1.trim() != ''}">
			<div class="form-group col-lg-4 col-md-6 col-xs-12">
				<label>Producto Consumo Esc 1: </label>
				${cliente.prodConsumEsc1}
			</div>
		</g:if>
		<g:if test="${cliente.garanteConsumEsc1 && cliente.garanteConsumEsc1.trim() != ''}">
			<div class="form-group col-lg-4 col-md-6 col-xs-12">
				<label>Garante Consumo Esc 1: </label>
				${cliente.garanteConsumEsc1}
			</div>
		</g:if>
		<g:if test="${cliente.tarjetaEsc1 && cliente.tarjetaEsc1.trim() != ''}">
			<div class="form-group col-lg-4 col-md-6 col-xs-12">
				<label>Tarjeta Consumo Esc 1: </label>
				${cliente.tarjetaEsc1}
			</div>
		</g:if>
		<g:if test="${cliente.marcaTarjEsc1 && cliente.marcaTarjEsc1.trim() != ''}">
			<div class="form-group col-lg-4 col-md-6 col-xs-12">
				<label>Marca Consumo Esc 1: </label>
				${cliente.marcaTarjEsc1}
			</div>
		</g:if>
		<g:if test="${cliente.fliaTarjetaEsc1 && cliente.fliaTarjetaEsc1.trim() != ''}">
			<div class="form-group col-lg-4 col-md-6 col-xs-12">
				<label>Familia Consumo Esc 1: </label>
				${cliente.fliaTarjetaEsc1}
			</div>
		</g:if>
		<g:if test="${cliente.plast1TarjEsc1 && cliente.plast1TarjEsc1.trim() != ''}">
			<div class="form-group col-lg-4 col-md-6 col-xs-12">
				<label>Plástico Consumo Esc 1: </label>
				${cliente.plast1TarjEsc1}
			</div>
		</g:if>
	</div>
</g:if>

<g:if test="${cliente.credConsumExclusivo && cliente.credConsumExclusivo.trim() != ''}">
	<div style="border-radius: 30px; /*background-color: #f7dc6f;*/background-image: radial-gradient(circle, #f7dc6f, #f7dc6f, #f1c40f );" class="panel panel-default col-lg-12 col-md-12 col-xs-12">
		<g:if test="${cliente.credConsumExclusivo && cliente.credConsumExclusivo.trim() != ''}">
			<div class="form-group col-lg-4 col-md-6 col-xs-12">
				<label id="labelConsumoExclusivo">Créd Consumo Exclusivo: </label>
				${cliente.credConsumExclusivo}
			</div>
		</g:if>
		<g:if test="${cliente.cuotaConsumExclusivo && cliente.cuotaConsumExclusivo.trim() != ''}">
			<div class="form-group col-lg-4 col-md-6 col-xs-12">
				<label>Cuota Consumo Exclusivo: </label>
				${cliente.cuotaConsumExclusivo}
			</div>
		</g:if>
		<g:if test="${cliente.plazoConsumExclusivo && cliente.plazoConsumExclusivo.trim() != ''}">
			<div class="form-group col-lg-4 col-md-6 col-xs-12">
				<label>Plazo Consumo Exclusivo: </label>
				${cliente.plazoConsumExclusivo}
			</div>
		</g:if>
		<g:if test="${cliente.prodConsumExclusivo && cliente.prodConsumExclusivo.trim() != ''}">
			<div class="form-group col-lg-4 col-md-6 col-xs-12">
				<label>Producto Consumo Exclusivo: </label>
				${cliente.prodConsumExclusivo}
			</div>
		</g:if>
		<g:if test="${cliente.garanteConsumExclusivo && cliente.garanteConsumExclusivo.trim() != ''}">
			<div class="form-group col-lg-4 col-md-6 col-xs-12">
				<label>Garante Consumo Exclusivo: </label>
				${cliente.garanteConsumExclusivo}
			</div>
		</g:if>
	</div>
</g:if>


<%-- TARJETA EXCLUSIVA --%>
<g:if test="${cliente.tarjetaExclusiva && cliente.tarjetaExclusiva.trim() != ''}">
	<div style="border-radius: 30px; /*background-color: #f1948a;*/ background-image: radial-gradient(circle, #f1948a, #ec7063, #e74c3c );" class="panel panel-default col-lg-12 col-md-12 col-xs-12">
		<g:if test="${cliente.tarjetaExclusiva && cliente.tarjetaExclusiva.trim() != ''}">
			<div class="form-group col-lg-4 col-md-6 col-xs-12">
				<label>Tarjeta Exclusiva: </label>
				${cliente.tarjetaExclusiva}
			</div>
		</g:if>
		<g:if test="${cliente.marcaTarjetaExclusiva && cliente.marcaTarjetaExclusiva.trim() != ''}">
			<div class="form-group col-lg-4 col-md-6 col-xs-12">
				<label>Marca Tarjeta Exclusiva: </label>
				${cliente.marcaTarjetaExclusiva}
			</div>
		</g:if>
		<g:if test="${cliente.fliaTarjetaExclusiva && cliente.fliaTarjetaExclusiva.trim() != ''}">
			<div class="form-group col-lg-4 col-md-6 col-xs-12">
				<label>Familia Tarjeta Exclusiva: </label>
				${cliente.fliaTarjetaExclusiva}
			</div>
		</g:if>
		<g:if test="${cliente.plast1TarjExclusiva && cliente.plast1TarjExclusiva.trim() != ''}">
			<div class="form-group col-lg-4 col-md-6 col-xs-12">
				<label>Plástico Tarjeta Exclusiva: </label>
				${cliente.plast1TarjExclusiva}
			</div>
		</g:if>
		<g:if test="${cliente.prodTarjetaExclusiva && cliente.prodTarjetaExclusiva.trim() != ''}">
			<div class="form-group col-lg-4 col-md-6 col-xs-12">
				<label>Producto Tarjeta Exclusiva: </label>
				${cliente.prodTarjetaExclusiva}
			</div>
		</g:if>
	</div>
</g:if>

<%-- EASY CODE --%>
<div style="border-radius: 30px" class="panel panel-default col-lg-12 col-md-12 col-xs-12">
	<g:if test="${cliente.codigoAsignacion && cliente.codigoAsignacion.trim() != ''}">
		<div class="form-group col-lg-4 col-md-6 col-xs-12">
			<label>Easy Code: </label>
			${cliente.codigoAsignacion}
		</div>
	</g:if>
</div>


<div class="col-lg-12 col-md-12 col-xs-12">
	<h5> <b>Datos de Contácto</b></h5>
</div>
<div style="border-radius: 30px" class="panel panel-default col-lg-12 col-md-12 col-xs-12">
	<g:if test="${cliente.telefono1}">
		<div id="number1" class="form-group col-lg-4 col-md-6 col-xs-12">
			<label><span class="fa fa-mobile-phone"></span> Teléfono 1: </label>
			${utilitarios.Util.functionAsterisk(cliente.id.toString())}${cliente.telefono1}
			<g:select class="form-control" id="estadoTel1" name="estadoTel1" from="${['C CLIENTE DE VIAJE','C CLIENTE FALLECIDO','C CLIENTE VIVE FUERA DEL PAIS','C CONTESTA TERCERO','C TELEFONO DE REFERENCIA','C CLIENTE','N CLIENTE SIN TELEFONO','N GRABADORA','N NO CONTESTA','N TELEFONO AVERIADO','N TELEFONO EQUIVOCADO_NO ASIGNADO','N TONO OCUPADO','N NO MARCADO']}" noSelection="${['': '-- Seleccione --']}" optionValue="value" optionKey="value" />

		</div>
	</g:if>
	<g:if test="${cliente.telefono2 && cliente.telefono2.trim() != ''}">
		<div id="number2" class="form-group col-lg-4 col-md-6 col-xs-12">
			<label><span class="fa fa-phone"></span> Teléfono 2: </label>
			${utilitarios.Util.functionAsterisk(cliente.id.toString())}${cliente.telefono2}
			<g:select  class="form-control" id="estadoTel2" name="estadoTel2" noSelection="${['': '-- Seleccione --']}" from="${['C CLIENTE DE VIAJE','C CLIENTE FALLECIDO','C CLIENTE VIVE FUERA DEL PAIS','C CONTESTA TERCERO','C TELEFONO DE REFERENCIA','C CLIENTE','N CLIENTE SIN TELEFONO','N GRABADORA','N NO CONTESTA','N TELEFONO AVERIADO','N TELEFONO EQUIVOCADO_NO ASIGNADO','N TONO OCUPADO','N NO MARCADO']}" />
		</div>
	</g:if>
	<g:if test="${cliente.telefono3 && cliente.telefono3.trim() != ''}">
		<div id="number3" class="form-group col-lg-4 col-md-6 col-xs-12">
			<label><span class="fa fa-phone"></span> Teléfono 3: </label>
			${utilitarios.Util.functionAsterisk(cliente.id.toString())}${cliente.telefono3}
			<g:select  class="form-control" id="estadoTel3" name="estadoTel3" noSelection="${['': '-- Seleccione --']}" from="${['C CLIENTE DE VIAJE','C CLIENTE FALLECIDO','C CLIENTE VIVE FUERA DEL PAIS','C CONTESTA TERCERO','C TELEFONO DE REFERENCIA','C CLIENTE','N CLIENTE SIN TELEFONO','N GRABADORA','N NO CONTESTA','N TELEFONO AVERIADO','N TELEFONO EQUIVOCADO_NO ASIGNADO','N TONO OCUPADO','N NO MARCADO']}" />
		</div>
	</g:if>
	<g:if test="${cliente.telefono4 && cliente.telefono4.trim() != ''}">
		<div id="number4" class="form-group col-lg-4 col-md-6 col-xs-12">
			<label><span class="fa fa-phone"></span> Teléfono 4: </label>
			${utilitarios.Util.functionAsterisk(cliente.id.toString())}${cliente.telefono4}
			<g:select  class="form-control" id="estadoTel4" name="estadoTel4" noSelection="${['': '-- Seleccione --']}" from="${['C CLIENTE DE VIAJE','C CLIENTE FALLECIDO','C CLIENTE VIVE FUERA DEL PAIS','C CONTESTA TERCERO','C TELEFONO DE REFERENCIA','C CLIENTE','N CLIENTE SIN TELEFONO','N GRABADORA','N NO CONTESTA','N TELEFONO AVERIADO','N TELEFONO EQUIVOCADO_NO ASIGNADO','N TONO OCUPADO','N NO MARCADO']}" />
		</div>
	</g:if>
	<g:if test="${cliente.telefono5 && cliente.telefono5.trim() != ''}">
		<div id="number5" class="form-group col-lg-4 col-md-6 col-xs-12">
			<label><span class="fa fa-phone"></span> Teléfono 5: </label>
			${utilitarios.Util.functionAsterisk(cliente.id.toString())}${cliente.telefono5}
			<g:select  class="form-control" id="estadoTel5" name="estadoTel5" noSelection="${['': '-- Seleccione --']}" from="${['C CLIENTE DE VIAJE','C CLIENTE FALLECIDO','C CLIENTE VIVE FUERA DEL PAIS','C CONTESTA TERCERO','C TELEFONO DE REFERENCIA','C CLIENTE','N CLIENTE SIN TELEFONO','N GRABADORA','N NO CONTESTA','N TELEFONO AVERIADO','N TELEFONO EQUIVOCADO_NO ASIGNADO','N TONO OCUPADO','N NO MARCADO']}" />
		</div>
	</g:if>
	<g:if test="${cliente.telefono6 && cliente.telefono6.trim() != ''}">
		<div id="number6" class="form-group col-lg-4 col-md-6 col-xs-12">
			<label><span class="fa fa-phone"></span> Teléfono 6: </label>
			${utilitarios.Util.functionAsterisk(cliente.id.toString())}${cliente.telefono6}
			<g:select  class="form-control" id="estadoTel6" name="estadoTel6" noSelection="${['': '-- Seleccione --']}" from="${['C CLIENTE DE VIAJE','C CLIENTE FALLECIDO','C CLIENTE VIVE FUERA DEL PAIS','C CONTESTA TERCERO','C TELEFONO DE REFERENCIA','C CLIENTE','N CLIENTE SIN TELEFONO','N GRABADORA','N NO CONTESTA','N TELEFONO AVERIADO','N TELEFONO EQUIVOCADO_NO ASIGNADO','N TONO OCUPADO','N NO MARCADO']}" />
		</div>
	</g:if>
	<g:if test="${cliente.telefono7 && cliente.telefono7.trim() != ''}">
		<div id="number7" class="form-group col-lg-4 col-md-6 col-xs-12">
			<label><span class="fa fa-phone"></span> Teléfono 7: </label>
			${utilitarios.Util.functionAsterisk(cliente.id.toString())}${cliente.telefono7}
			<g:select  class="form-control" id="estadoTel7" name="estadoTel7" noSelection="${['': '-- Seleccione --']}" from="${['C CLIENTE DE VIAJE','C CLIENTE FALLECIDO','C CLIENTE VIVE FUERA DEL PAIS','C CONTESTA TERCERO','C TELEFONO DE REFERENCIA','C CLIENTE','N CLIENTE SIN TELEFONO','N GRABADORA','N NO CONTESTA','N TELEFONO AVERIADO','N TELEFONO EQUIVOCADO_NO ASIGNADO','N TONO OCUPADO','N NO MARCADO']}" />
		</div>
	</g:if>
	<g:if test="${cliente.telefono8 && cliente.telefono8.trim() != ''}">
		<div id="number8" class="form-group col-lg-4 col-md-6 col-xs-12">
			<label><span class="fa fa-phone"></span> Teléfono 8: </label>
			${utilitarios.Util.functionAsterisk(cliente.id.toString())}${cliente.telefono8}
			<g:select  class="form-control" id="estadoTel8" name="estadoTel8" noSelection="${['': '-- Seleccione --']}" from="${['C CLIENTE DE VIAJE','C CLIENTE FALLECIDO','C CLIENTE VIVE FUERA DEL PAIS','C CONTESTA TERCERO','C TELEFONO DE REFERENCIA','C CLIENTE','N CLIENTE SIN TELEFONO','N GRABADORA','N NO CONTESTA','N TELEFONO AVERIADO','N TELEFONO EQUIVOCADO_NO ASIGNADO','N TONO OCUPADO','N NO MARCADO']}" />
		</div>
	</g:if>
	<g:if test="${cliente.telefono9 && cliente.telefono9.trim() != ''}">
		<div id="number9" class="form-group col-lg-4 col-md-6 col-xs-12">
			<label><span class="fa fa-phone"></span> Teléfono 9: </label>
			${utilitarios.Util.functionAsterisk(cliente.id.toString())}${cliente.telefono9}
			<g:select  class="form-control" id="estadoTel9" name="estadoTel9" noSelection="${['': '-- Seleccione --']}" from="${['C CLIENTE DE VIAJE','C CLIENTE FALLECIDO','C CLIENTE VIVE FUERA DEL PAIS','C CONTESTA TERCERO','C TELEFONO DE REFERENCIA','C CLIENTE','N CLIENTE SIN TELEFONO','N GRABADORA','N NO CONTESTA','N TELEFONO AVERIADO','N TELEFONO EQUIVOCADO_NO ASIGNADO','N TONO OCUPADO','N NO MARCADO']}" />
		</div>
	</g:if>
	<g:if test="${cliente.telefono10 && cliente.telefono10.trim() != ''}">
		<div id="number10" class="form-group col-lg-4 col-md-6 col-xs-12">
			<label><span class="fa fa-phone"></span> Teléfono 10: </label>
			${utilitarios.Util.functionAsterisk(cliente.id.toString())}${cliente.telefono10}
			<g:select  class="form-control" id="estadoTel10" name="estadoTel10" noSelection="${['': '-- Seleccione --']}" from="${['C CLIENTE DE VIAJE','C CLIENTE FALLECIDO','C CLIENTE VIVE FUERA DEL PAIS','C CONTESTA TERCERO','C TELEFONO DE REFERENCIA','C CLIENTE','N CLIENTE SIN TELEFONO','N GRABADORA','N NO CONTESTA','N TELEFONO AVERIADO','N TELEFONO EQUIVOCADO_NO ASIGNADO','N TONO OCUPADO','N NO MARCADO']}" />
		</div>
	</g:if>
	<div class="col-lg-12 group-title">Datos Complementarios</div>
	<div class="col-lg-12 line"></div>
	<g:if test="${cliente.email && cliente.email.trim() != ''}">
		<div class="form-group col-lg-4 col-md-6 col-xs-12">
			<label><span class="fa fa-archive"></span> Email: </label>
			${cliente.email}
		</div>
	</g:if>
	<g:if test="${cliente.codigoAsignacion && cliente.codigoAsignacion.trim() != ''}">
		<div class="form-group col-lg-4 col-md-6 col-xs-12">
			<label><span class="fa fa-archive"></span> Easy Code: </label>
			${cliente.codigoAsignacion}
		</div>
	</g:if>

</div>

	<g:if test="${cliente.tipoProducto == 'TARJETA NORMAL'}">
		<div style="border-radius: 30px" class="panel panel-default col-lg-12 col-md-12 col-xs-12">
			<div class="panel panel-default col-md-12 col-md-offset-0">
				<iframe width="100%" height="550"
						src="http://www.plus-wireless.com/documents/SCRIPT_PRINCIPALES%20_TARJETA_NORMAL.pdf">
				</iframe>
			</div>
		</div>
	</g:if>

	<g:if test="${cliente.tipoProducto == 'PRIMERA TARJETA'}">
		<div style="border-radius: 30px" class="panel panel-default col-lg-12 col-md-12 col-xs-12">
			<div class="panel panel-default col-md-12 col-md-offset-0">
				<iframe width="100%" height="550"
						src="http://www.plus-wireless.com/documents/SCRIPT_PRINCIPALES_PRIMERA_TARJETA.pdf">
				</iframe>
			</div>
		</div>
	</g:if>

	<input type="hidden" name="idCliente" value="${cliente.id}" />
	<div style="border-radius: 30px" class="panel panel-default col-lg-12 col-md-12 col-xs-12">

		<div class="col-lg-4 col-md-6 col-xs-12 form-group">
			<label>Estado Gestion</label>
			<span class="required-indicator">*</span>
			<g:select class="form-control" name="estado" id="status"
					  from="${callcenter.Estado.list()}" optionKey="id"
					  optionValue="${{it.nombre	}}"
					  noSelection="${['': '-- Seleccione --']}" />
		</div>

		<div id="subStatusDiv" class="col-lg-4 col-md-6 col-xs-12 form-group">
			<label>Subestado Gestion</label>
			<span class="required-indicator">*</span>
			<g:select class="form-control" name="substatus" id="substatus" from="" noSelection="${['': '-- Seleccione --']}" />
		</div>

		<div id="subSubStatusDiv" class="form-group col-lg-4 col-md-6 col-xs-12">
			<label>Sub subestado</label>
			<span class="required-indicator">*</span>
			<g:select id="subSubStatus" class="form-control" name="subSubStatus" from="" optionKey="id"></g:select>
		</div>

		<div id="divMotivo" class="col-lg-4 col-md-6 col-xs-12 form-group">
			<label>Motivos No Acepta Seguro</label>
			<span class="required-indicator">*</span>
			<g:select class="form-control" name="motivoNoAceptaSeguro" id="motivoNoAceptaSeguro"
					  from="${callcenter.MotivosnoAceptaSeguro.list()}" optionKey="nombre"
					  optionValue="${{it.nombre	}}"
					  noSelection="${['': '-- Seleccione --']}" />
		</div>

		<div id="motivosSubSubEstadosDiv" class="col-lg-4 col-md-6 col-xs-12 form-group">
			<label>Tipo de Producto</label>
			<span class="required-indicator">*</span>
			<g:select id="motivosSubSubEstados" class="form-control" name="motivosSubSubEstados" from="" optionKey="id" noSelection="${['': '-- Seleccione --']}" ></g:select>
		</div>

		<div id="divDeseaNuevoProducto" class="col-lg-4 col-md-6 col-xs-12 form-group">
			<label>¿ Cual Producto ?</label>
			<span class="required-indicator">*</span>
			<g:textField class="form-control" name="deseaNuevoProducto"/>
		</div>

		<div id="recallDiv">
			<div class="col-lg-4 col-md-6 col-xs-12 form-group">
				<label>Agendamiento</label>
				<span class="required-indicator">*</span>
				<g:select class="form-control" name="agendamiento" id="agendamiento" from="${['AGENDAR PARA MI':'AGENDAR PARA MI', 'AGENDAR PARA CUALQUIERA':'AGENDAR PARA CUALQUIERA']}" optionKey="key"
						  optionValue="value"
						  noSelection="${['': '-- Seleccione --']}" />
			</div>
			<div class="col-lg-4 col-md-6 form-group">
				<label>Fecha Rellamada</label>
				<span class="required-indicator">*</span>
				<%--<g:textField id="recall" name="recall" class="reportrange form-control"/>--%>
				<div class='input-group time' id='datetimepicker1'>
					<input id="recall" name="recall" type='text' class="form-control" />
					<span class="input-group-addon">
						<span class="glyphicon glyphicon-calendar"></span>
					</span></div>
			</div>
		</div>

		<div id="telefonoContactadoDiv" >
			<div class="col-lg-4 col-md-6 form-group">
				<label style="color: red">Teléfono Contactado</label>
				<span class="required-indicator">*</span>
				<g:textField maxlength="10" minlength="9" id="telefonoContactado" name="telefonoContactado" class="form-control"/>
			</div>
			<div class="form-group col-lg-4 col-md-6 col-xs-12">
				<label >Estado Teléfono Contactado</label>
				<span class="required-indicator">*</span>
				<g:select class="form-control" id="estadoTelefonoContactado" name="estadoTelefonoContactado" from="" noSelection="${['': '-- Seleccione --']}" optionValue="value" optionKey="value" />

			</div>
		</div>
	</div>

	<div id="tipoAsistenciaDiv" style="border-radius: 30px" class="panel panel-default col-lg-12 col-md-12 col-xs-12">
		<div class="line"><h5>Crédito</h5></div>
		<div class="form-group col-lg-3 col-md-6 col-xs-12">
			<label>Crédito Aceptado</label>
			<span class="required-indicator"> *</span>
			<g:select class="form-control" id="creditoAceptado" name="creditoAceptado" from="${['OPT1': 'Opción 1', 'OPT2': 'Opción 2']}" noSelection="${['': '-- Seleccione --']}" optionKey="key" optionValue="value" />
		</div>
		<div class="form-group col-lg-3 col-md-6 col-xs-12">
			<label>Situación Laboral</label>
			<span class="required-indicator"> *</span>
			<g:select class="form-control" id="situacionLaboralCredito" name="situacionLaboralCredito" from="${['DEPENDIENTE': 'DEPENDIENTE', 'INDEPENDIENTE': 'INDEPENDIENTE']}" noSelection="${['': '-- Seleccione --']}" optionKey="key" optionValue="value" />
		</div>
		<div class="form-group col-lg-3 col-md-6 col-xs-12">
			<label>Firma documentos</label>
			<span class="required-indicator"> *</span>
			<g:select class="form-control" id="signatureDocuments" name="signatureDocuments" from="${['AGENCIA': 'AGENCIA', 'DOMICILIO': 'DOMICILIO', 'SIN AGENCIA': 'SIN AGENCIA', 'DIGITAL': 'DIGITAL']}" noSelection="${['': '-- Seleccione --']}" optionKey="key" optionValue="value" />
		</div>
		<div id="agenciaGroup">
			<div class="form-group col-lg-3 col-md-6 col-xs-12">
			<label>Provincia</label>
			<span class="required-indicator"> *</span>
			<g:select class="form-control" id="provinciaEntrega" name="provinciaEntrega" optionKey="id" noSelection="${['': '-- Seleccione --']}" from="${callcenter.Provincia.list(sort: "nombre", order: "asc")}"  optionValue="${{it.nombre}}"/>
		    </div>
			<div class="form-group col-lg-3 col-md-6 col-xs-12">
				<label>Ciudad</label>
				<span class="required-indicator"> *</span>
				<g:select class="form-control" id="ciudadEntrega" name="ciudadEntrega" optionKey="id" noSelection="${['': '-- Seleccione --']}" from="" optionValue="${{it.nombre}}"/>
			</div>
			<%--<div class="form-group col-lg-3 col-md-6 col-xs-12">
				<label>Parroquias</label>
				<span class="required-indicator"> *</span>
				<g:select class="form-control" id="parroquiaEntrega" name="parroquiaEntrega" optionKey="id" noSelection="${['': '-- Seleccione --']}" from="" optionValue="name"/>
			</div>--%>
			<div class="form-group col-lg-3 col-md-6 col-xs-12">
				<label>Agencias</label>
				<span class="required-indicator"> *</span>
				<g:select class="form-control" id="oficinaTarjeta" name="oficinaTarjeta" optionKey="id" noSelection="${['': '-- Seleccione --']}" from="" optionValue="name"/>
			</div>
			<div class="form-group col-lg-3 col-md-6 col-xs-12">
				<label>Fecha Desembolso</label>
				<span class="required-indicator">*</span>
				<%--<g:textField id="fechaAgendamiento" name="fechaAgendamiento" class="recall form-control"/>--%>

				<div class='input-group time' id='datetimepicker2'>
					<input id="fechaAgendamiento" name="fechaAgendamiento" type='text' class="form-control" />
					<span class="input-group-addon">
						<span class="glyphicon glyphicon-calendar"></span>
					</span>
				</div>
			</div>
			<div class="form-group col-lg-3 col-md-6 col-xs-12">
				<label>Teléfono Convencional</label>
				<span class="required-indicator">*</span>
				<g:textField maxlength="9" minlength="9" id="telefonoConvencionalCredito" name="telefonoConvencionalCredito" class="form-control"/>
			</div>
			<div class="form-group col-lg-3 col-md-6 col-xs-12">
				<label>Teléfono Celular</label>
				<span class="required-indicator">*</span>
				<g:textField maxlength="10" minlength="10" id="telefonoCelularCredito" name="telefonoCelularCredito" class="form-control"/>
			</div>
		</div>
		<div id="groupDomicilio">
			<div class="form-group col-lg-3 col-md-6 col-xs-12">
				<label>Provincia</label>
				<span class="required-indicator"> *</span>
				<g:select class="form-control" id="provinciaDomicilioFvt" name="provinciaDomicilioFvt" optionKey="id" noSelection="${['': '-- Seleccione --']}" from="${callcenter.Provincia.list(sort: "nombre", order: "asc")}"  optionValue="${{it.nombre}}"/>
			</div>
			<div class="form-group col-lg-3 col-md-6 col-xs-12">
				<label>Ciudad</label>
				<span class="required-indicator"> *</span>
				<g:select class="form-control" id="ciudadDomicilioFvt" name="ciudadDomicilioFvt" optionKey="id" noSelection="${['': '-- Seleccione --']}" from="" optionValue="${{it.nombre}}"/>
			</div>
		</div>
		<%--<div id="divFechaDesembolso" class="form-group col-lg-3 col-md-6 col-xs-12">
			<label>Fecha Desembolso</label>
			<span class="required-indicator">*</span>
			<div class='input-group time' id='datetimepicker4'>
				<input id="fechaAgendamiento2" name="fechaAgendamiento2" type='text' class="form-control" />
				<span class="input-group-addon">
					<span class="glyphicon glyphicon-calendar"></span>
				</span>
			</div>
		</div>--%>
		<div class="form-group col-lg-3 col-md-6 col-xs-12">
			<label>Correo Electronico</label>
			<span class="required-indicator">*</span>
			<g:textField id="correoCredito" name="correoCredito" class="form-control"/>
		</div>
		<div id="groupDigital">
			<div class="form-group col-lg-3 col-md-6 col-xs-12">
				<label>Dirección Cliente</label>
				<span class="required-indicator"> *</span>
				<g:select class="form-control" id="direccionCliente" name="direccionCliente" from="${['SI': 'SI', 'NO': 'NO']}" noSelection="${['': '-- Seleccione --']}" optionKey="key" optionValue="value" />
			</div>
			<div class="form-group col-lg-12 col-md-12 col-xs-12">
				<p><strong><i>PASOS PARA DESEMBOLSO CRÉDITO DIGITAL</i></strong></p>
				<p>1.	Ingresar a <strong><i>www.pichincha.com</i></strong></p>
				<p>2.	Opción Créditos, dar Clic en ingresar</p>
				<p>3.	Digita el número de cédula y el número celular o correo electrónico registrados en el banco.</p>
				<p>4.	Se le activa el botón comenzar, dar Clic.</p>
				<p>5.	Se genera un código de 6 dígitos, le llega al celular.</p>
				<p>6.	Digita el código y da clic en continuar.</p>
				<p>7.	Se le despliega la siguiente pantalla, en la que podrá simular el monto y plazo del crédito.</p>
				<p>8.	El crédito se desembolsa en línea.</p>
				<p>9.	Si el cliente no tiene aprobado un crédito digital, le aparecerá el siguiente mensaje.</p>
			</div>
	    </div>

	</div>

<%-- EMPIEZA GESTIÓN --%>
	<br>
	</div>
	<div style="border-radius: 30px" id="managementData" class="panelx panel-defaultx col-lg-12 col-md-12 col-xs-12">
		<div style="border-radius: 30px" class="panel panel-default col-lg-12 col-md-12 col-xs-12">
			<div class="col-lg-12 col-md-12 col-xs-12">
				<div class="line"><h5>Datos Personales</h5></div>

				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Cédula Titular</label>
					<span class="required-indicator"> *</span>
					<g:textField id="cedulaTitular" name="cedulaTitular" class="form-control" value="${cliente.identificacion?:''}"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Nombre 1</label>
					<span class="required-indicator"> *</span>
					<g:textField id="nombre1" name="nombre1" class="form-control" value="${cliente.primerNombre?:''}"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Nombre 2</label>
					<span class="required-indicator"> *</span>
					<g:textField id="nombre2" name="nombre2" class="form-control" value="${cliente.segundoNombre?:''}"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Apellido 1</label>
					<span class="required-indicator"> *</span>
					<g:textField id="apellido1" name="apellido1" class="form-control" value="${cliente.primerApellido?:''}"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Apellido 2</label>
					<span class="required-indicator"> *</span>
					<g:textField id="apellido2" name="apellido2" class="form-control" value="${cliente.segundoApellido?:''}"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Nacionalidad</label>
					<span class="required-indicator"> *</span>
					<g:textField id="nacionalidad" name="nacionalidad" class="form-control" />
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Género</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control" id="genero" name="genero" from="${['MASCULINO', 'FEMENINO']}" noSelection="${['': '-- Seleccione --']}" />
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Estado Civil</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control" id="estadoCivilGestion" name="estadoCivilGestion" from="${['DIVORCIADO', 'SOLTERO', 'CASADO', 'UNION LIBRE', 'VIUDO']}" noSelection="${['': '-- Seleccione --']}" />
				</div>

			</div>

			<div class="col-lg-12 col-md-12 col-xs-12">
				<div class="line"><h5>Datos de Origen</h5></div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>País Nacimiento</label>
					<span class="required-indicator"> *</span>
					<g:textField id="paisNacimiento" name="paisNacimiento" class="form-control" />
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Provincia Nacimiento</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control" id="provinciaNacimiento" name="provinciaNacimiento" optionKey="id" noSelection="${['': '-- Seleccione --']}" from="${callcenter.Provincia.list(sort: "nombre", order: "asc")}"  optionValue="${{it.nombre}}"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Ciudad Nacimiento</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control" id="ciudadNacimiento" name="ciudadNacimiento" optionKey="nombre" noSelection="${['': '-- Seleccione --']}" from="" optionValue="${{it.nombre}}"/>
				</div>
				<div class="form-group col-lg-2 col-md-4 col-xs-4">
					<label>Fecha Nacimiento</label>
					<span class="required-indicator">*</span>
					<g:textField id="fechaNacimientoGestion" name="fechaNacimientoGestion" class="form-control datepicker pointer"/>
				</div>
			</div>


			<div class="col-lg-12 col-md-12 col-xs-12">
				<div class="line"><h5>Datos de Domicilio</h5></div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Provincia Domicilio</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control" id="provinciaDomicilioGestion" name="provinciaDomicilioGestion" optionKey="id" noSelection="${['': '-- Seleccione --']}" from="${callcenter.Provincia.list(sort: "nombre", order: "asc")}"  optionValue="${{it.nombre}}"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Ciudad Domicilio</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control" id="ciudadDomicilioGestion" name="ciudadDomicilioGestion" optionKey="id" noSelection="${['': '-- Seleccione --']}" from="" optionValue="${{it.nombre}}"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Cantón Domicilio</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control" id="cantonDomicilio" name="cantonDomicilio" optionKey="id" noSelection="${['': '-- Seleccione --']}" from="" optionValue="${{it.nombre}}"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Parroquia Domicilio</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control" id="parroquiaDomicilio" name="parroquiaDomicilio" optionKey="id" noSelection="${['': '-- Seleccione --']}" from="" optionValue="${{it.nombre}}"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Sector Domicilio</label>
					<span class="required-indicator"> *</span>
					<g:textField id="sectorDomicilio" name="sectorDomicilio" class="form-control" />
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Calle Principal</label>
					<span class="required-indicator"> *</span>
					<g:textField id="callePrincipalDomicilio" name="callePrincipalDomicilio" class="form-control" />
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Nro Casa</label>
					<span class="required-indicator"> *</span>
					<g:textField id="numeroCasaDomicilio" name="numeroCasaDomicilio" class="form-control" />
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Calle Secundaria</label>
					<span class="required-indicator"> *</span>
					<g:textField id="calleSecundariaDomicilio" name="calleSecundariaDomicilio" class="form-control" />
				</div>
				<div class="form-group col-lg-9 col-md-12 col-xs-12">
					<label>Referencia Domicilio</label>
					<span class="required-indicator"> *</span>
					<g:textArea class="form-control" id="referenciaDomicilio"  name="referenciaDomicilio"/>
				</div>

				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Teléfono Domicilio <span class="required-indicator"> *</span>
						<g:textField maxlength="8" minlength="8" class="form-control" id="telefonoDomicilio" name="telefonoDomicilio"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Celular <span class="required-indicator"> *</span>
						<g:textField maxlength="10" minlength="8" class="form-control" id="celularGestion" name="celularGestion"/>
				</div>
				<div class="form-group col-lg-5 col-md-6 col-xs-12">
					<label>Email Personal</label>
					<span class="required-indicator"> *</span>
					<g:textField class="form-control" id="emailPersonal" name="emailPersonal"/>
				</div>
			</div>



			<div class="col-lg-12 col-md-12 col-xs-12">
				<div class="line"><h5>Dirección de Trabajo</h5></div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Provincia Trabajo</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control" id="provinciaTrabajoGestion" name="provinciaTrabajoGestion" optionKey="id" noSelection="${['': '-- Seleccione --']}" from="${callcenter.Provincia.list(sort: "nombre", order: "asc")}"  optionValue="${{it.nombre}}"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Ciudad Trabajo</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control" id="ciudadTrabajoGestion" name="ciudadTrabajoGestion" optionKey="id" noSelection="${['': '-- Seleccione --']}" from="" optionValue="${{it.nombre}}"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Cantón Trabajo</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control" id="cantonTrabajo" name="cantonTrabajo" optionKey="id" noSelection="${['': '-- Seleccione --']}" from="" optionValue="${{it.nombre}}"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Parroquia Trabajo</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control" id="parroquiaTrabajo" name="parroquiaTrabajo" optionKey="id" noSelection="${['': '-- Seleccione --']}" from="" optionValue="${{it.nombre}}"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Sector Trabajo</label>
					<span class="required-indicator"> *</span>
					<g:textField id="sectorTrabajo" name="sectorTrabajo" class="form-control" />
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Calle Principal</label>
					<span class="required-indicator"> *</span>
					<g:textField id="callePrincipalTrabajo" name="callePrincipalTrabajo" class="form-control" />
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Nro Casa</label>
					<span class="required-indicator"> *</span>
					<g:textField id="numeroCasaTrabajo" name="numeroCasaTrabajo" class="form-control" />
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Calle Secundaria</label>
					<span class="required-indicator"> *</span>
					<g:textField id="calleSecundariaTrabajo" name="calleSecundariaTrabajo" class="form-control" />
				</div>
				<div class="form-group col-lg-9 col-md-12 col-xs-12">
					<label>Referencia Domicilio</label>
					<span class="required-indicator"> *</span>
					<g:textArea class="form-control" id="referenciaTrabajo"  name="referenciaTrabajo"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Teléfono Trabajo <span class="required-indicator"> *</span></label>
					<g:textField maxlength="8" minlength="8" class="form-control" id="telefonoTrabajo" name="telefonoTrabajo"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Fax</label>
					<g:textField class="form-control" id="fax" name="fax"/>
				</div>
				<div class="form-group col-lg-5 col-md-6 col-xs-12">
					<label>Email Trabajo</label>
					<g:textField class="form-control" id="emailTrabajo" name="emailTrabajo"/>
				</div>
			</div>

			<div class="col-lg-12 col-md-12 col-xs-12">
				<div class="line"><h5>Datos de Entrega</h5></div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Entrega Estado de Cuenta</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control" id="entregaEstadoCuenta" name="entregaEstadoCuenta" from="${[1:'DOMICILIO', 2:'TRABAJO']}" noSelection="${['': '-- Seleccione --']}" optionKey="key" optionValue="value"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Lugar de Entrega</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control" id="lugarEntrega" name="lugarEntrega" from="${[1:'DOMICILIO', 2:'TRABAJO']}" noSelection="${['': '-- Seleccione --']}" optionKey="key" optionValue="value"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Teléfono Contacto <span class="required-indicator"> *</span></label>
					<g:textField maxlength="10" minlength="8" class="form-control" id="telefonoContacto" name="telefonoContacto"/>
				</div>
				<div class="form-group col-lg-2 col-md-4 col-xs-4">
					<label>Hora Contacto <span class="required-indicator"> *</span></label>
					<%--<g:textField id="timepicki" name="timepicki" class="form-control recall input-group"/>--%>
					<%--<g:select class="form-control input-group" id="horaContacto" name="horaContacto" from="${['07:00','08:00','09:00','10:00','11:00','12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00', '20:00', '21:00', '22:00']}" noSelection="${['': '        --:--']}" optionValue="value" />--%>


					<div class='input-group time' id='datetimepicker3'>
						<input id="horaContacto" name="horaContacto" type='text' class="form-control" onkeypress="return soloLetras(event)"/>
						<span class="input-group-addon pointer">
							<span class="glyphicon glyphicon-time"></span>
						</span>
					</div>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Nombre Recibe Tarjeta</label>
					<span class="required-indicator"> *</span>
					<g:textField class="form-control" id="nombreRecibeTarjeta" name="nombreRecibeTarjeta" value="${cliente.nombre}"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Horario Entrega Tarjeta</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control" id="horarioEntrega" name="horarioEntrega" from="${['MAÑANA', 'TARDE', 'NOCHE']}" noSelection="${['': '-- Seleccione --']}" />
				</div>
			</div>
			<div class="col-lg-12 col-md-12 col-xs-12">
				<div class="line"><h5>Datos Referencias</h5></div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Nombres de Referencia</label>
					<span class="required-indicator"> *</span>
					<g:textField class="form-control" id="nombresReferencia" name="nombresReferencia" />
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>País</label>
					<span class="required-indicator"> *</span>
					<g:textField class="form-control" id="pais" name="pais" />
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Provincia</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control" id="provincia" name="provincia" optionKey="id" noSelection="${['': '-- Seleccione --']}" from="${callcenter.Provincia.list(sort: "nombre", order: "asc")}"  optionValue="${{it.nombre}}"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Ciudad</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control" id="ciudad" name="ciudad" optionKey="nombre" noSelection="${['': '-- Seleccione --']}" from="" optionValue="${{it.nombre}}"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Teléfono<span class="required-indicator"> *</span></label>
					<g:textField maxlength="10" minlength="8" class="form-control" id="telefono" name="telefono"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Relación con Cliente</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control"  optionValue="value" id="relacionConCliente" name="relacionConCliente" from="${[ 'PADRE', 'MADRE', 'HIJO/HIJA', 'ABUELO/ABUELA', 'NUERA', 'YERNO', 'CÓNYUGE', 'CUÑADO / CUÑADA',
																																	'NIETO / NIETA', 'TIO / TIA', 'SOBRINO / SOBRINA', 'PRIMO / PRIMA', 'HERMANO / HERMANA'
																																	, 'SUEGRO/SUEGRA']}" noSelection="${['': '-- Seleccione --']}" />
				</div>
			</div>
			<div class="col-lg-12 col-md-12 col-xs-12">
				<div class="line"><h5>Tipo Cliente</h5></div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Tipo de Cliente</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control" id="tipoCliente" name="tipoCliente" from="${['I':'INDEPENDIENTE','D':'DEPENDIENTE']}" noSelection="${['': '-- Seleccione --']}" optionKey="key" optionValue="value" />
				</div>
			</div>
			<div id="dataIndependiente" class="col-lg-12 col-md-12 col-xs-12">
				<div class="line"><h5>Clientes Independientes</h5></div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Nombre Negocio</label>
					<span class="required-indicator"> *</span>
					<g:textField class="form-control" id="nombreNegocio" name="nombreNegocio"/>
				</div>
				<div class="form-group col-lg-2 col-md-4 col-xs-4">
					<label>Fecha Inicio Negocio</label>
					<span class="required-indicator">*</span>
					<g:textField id="fechaInicioNegocio" name="fechaInicioNegocio" class="form-control datepicker pointer"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Actividad Economica</label>
					<span class="required-indicator"> *</span>
					<g:textField class="form-control" id="actividadEconomica" name="actividadEconomica"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Ventas u Honorarios mesuales</label>
					<span class="required-indicator"> *</span>
					<g:textField class="form-control" id="ventasHonorariosMensuales" name="ventasHonorariosMensuales"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Costo Ventas</label>
					<span class="required-indicator"> *</span>
					<g:textField class="form-control" id="costoVentas" name="costoVentas"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Gastos Operativos</label>
					<span class="required-indicator"> *</span>
					<g:textField class="form-control" id="gastosOperativos" name="gastosOperativos"/>
				</div>
			</div>
			<div id="dataDependiente" class="col-lg-12 col-md-12 col-xs-12">
				<div class="line"><h5>Clientes Dependientes</h5></div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Situación Laboral</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control" id="situacionLaboral" name="situacionLaboral" from="${['DEPEND./EMPLEADO PÚBLICO', 'DEPEND./EMPLEADO PRIVADO']}" noSelection="${['': '-- Seleccione --']}" />
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Nombre Empresa</label>
					<span class="required-indicator"> *</span>
					<g:textField class="form-control" id="nombreEmpresaGestion" name="nombreEmpresaGestion"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Contrato</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control" id="contrato" name="contrato" from="${['FIJO','POR HORAS','TEMPORAL','A DESTAJO']}" noSelection="${['': '-- Seleccione --']}"  optionValue="value" />
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Cargo</label>
					<span class="required-indicator"> *</span>
					<g:textField class="form-control" id="cargo" name="cargo"/>
				</div>
				<div class="form-group col-lg-2 col-md-4 col-xs-4">
					<label>Fecha Inicio Trabajo</label>
					<span class="required-indicator">*</span>
					<g:textField id="fechaInicioTrabajoActual" name="fechaInicioTrabajoActual" class="form-control datepicker pointer"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Sueldo</label>
					<span class="required-indicator"> *</span>
					<g:textField class="form-control" id="sueldo" name="sueldo"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Gastos Familia</label>
					<span class="required-indicator"> *</span>
					<g:textField class="form-control" id="gastosFamilia" name="gastosFamilia"/>
				</div>
			</div>
			<div id="dataJubilado" class="col-lg-12 col-md-12 col-xs-12">
				<div class="line"><h5>Clientes Jubilados</h5></div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Situación Laboral Jubilado</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control" id="situacionLaboralJubilado" name="situacionLaboralJubilado" from="${['DEPEND./EMPLEADO PÚBLICO', 'DEPEND./EMPLEADO PRIVADO']}" noSelection="${['': '-- Seleccione --']}" />
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Nombre Empresa Jubilado</label>
					<span class="required-indicator"> *</span>
					<g:textField class="form-control" id="nombreEmpresaGestionJubilado" name="nombreEmpresaGestionJubilado"/>
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Contrato Jubilado</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control" id="contratoJubilado" name="contratoJubilado" from="${['FIJO','POR HORAS','TEMPORAL','A DESTAJO']}" noSelection="${['': '-- Seleccione --']}" optionValue="value" />
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Cargo Jubilado</label>
					<span class="required-indicator"> *</span>
					<g:textField class="form-control" id="cargoJubilado" name="cargoJubilado"/>
				</div>
				<div class="form-group col-lg-2 col-md-4 col-xs-4">
					<label>Fecha Ini. Jubilación</label>
					<span class="required-indicator">*</span>
					<g:textField id="fechaInicioTrabajoActualJubilado" name="fechaInicioTrabajoActualJubilado" class="form-control datepicker pointer"/>
				</div>
				<div class="form-group col-lg-2 col-md-4 col-xs-4">
					<label>Sueldo Jubilación</label>
					<span class="required-indicator"> *</span>
					<g:textField class="form-control" id="sueldoJubilado" name="sueldoJubilado"/>
				</div>
				<div class="form-group col-lg-2 col-md-4 col-xs-4">
					<label>Gastos Familia</label>
					<span class="required-indicator"> *</span>
					<g:textField class="form-control" id="gastosFamiliaJubilado" name="gastosFamiliaJubilado"/>
				</div>
			</div>

			<div class="col-lg-12 col-md-12 col-xs-12">
				<div class="line"><h5>Otros Datos</h5></div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Tipo Vivienda</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control" id="tipoVivienda" name="tipoVivienda" from="${['ALQUILER','PROPIA NO HIPOTECADA','VIVE CON FAMILIAR','ANTICRESIS','PRESTADA','PROPIA HIPOTECADA']}" noSelection="${['': '-- Seleccione --']}" optionValue="value" />
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Obligado Contabilidad</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control" id="obligadoContabilidad" name="obligadoContabilidad" from="${['SI','NO']}" noSelection="${['': '-- Seleccione --']}" optionValue="value" />
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Estado de Cuenta Digital</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control" id="estadoCuentaDigital" name="estadoCuentaDigital" from="${['SI','NO']}" noSelection="${['': '-- Seleccione --']}" optionValue="value" />
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Desea SMS</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control" id="sms" name="sms" from="${['SI','NO']}" noSelection="${['': '-- Seleccione --']}" optionValue="value" />
				</div>
				<div class="form-group col-lg-3 col-md-6 col-xs-12">
					<label>Seguro Desagravamen</label>
					<span class="required-indicator"> *</span>
					<g:select class="form-control" id="seguroDesgravamen" name="seguroDesgravamen" from="${['SI','NO']}" noSelection="${['': '-- Seleccione --']}" optionValue="value" />
				</div>
			</div>

		</div>

	</div>
	<div id="divPDP" style="border-radius: 30px" class="panel panel-default col-lg-12 col-md-12 col-xs-12">
		<div class="col-lg-12 col-md-12 col-xs-12">
			<div class="line"><h5>Datos PDP</h5></div>
			<div class="form-group col-lg-3 col-md-6 col-xs-12">
				<label>Provincia PDP</label>
				<span class="required-indicator"> *</span>
				<g:select class="form-control" id="provinciaNacimientoCU2" name="provinciaNacimientoCU2" optionKey="id" noSelection="${['': '-- Seleccione --']}" from="${callcenter.Provincia.list(sort: "nombre", order: "asc")}"  optionValue="${{it.nombre}}"/>
			</div>
			<div class="form-group col-lg-3 col-md-6 col-xs-12">
				<label>Ciudad PDP</label>
				<span class="required-indicator"> *</span>
				<g:select class="form-control" id="ciudadNacimientoCU2" name="ciudadNacimientoCU2" optionKey="id" noSelection="${['': '-- Seleccione --']}" from="" optionValue="${{it.nombre}}"/>
			</div>
		</div>
	</div>

<%-- FIN GESTION --%>

	<div style="border-radius: 30px" class="panel panel-default col-lg-12 col-md-12 col-xs-12">
		<div class="form-group col-lg-12 col-md-12 col-xs-12">
			<label>Observaciones</label>
			<g:textArea class="form-control" name="observaciones" value="${cliente?.observaciones}"/>
		</div>
	</div>

	<div class="col-lg-12 col-md-12 col-xs-12">
		<g:submitButton id="send" name="btnGuardarCliente" class="btn btn-primary" value="Guardar Gestión" />
	</div>
	<div class="line"></div>
</g:form>


<asset:javascript src="usogeneral/objetos.js" />
<asset:javascript src="gestion/gestionCliente1.js" />
<asset:javascript src="usogeneral/customdatetimepicker.js" />
<asset:javascript src="usogeneral/bootstrap-datepicker.min.js" />
<asset:javascript src="usogeneral/customdatepicker.js" />
<asset:javascript src="usogeneral/bootstrap-datepicker.es.min.js" />
<asset:javascript src="usogeneral/moment.min.js" />
<asset:javascript src="usogeneral/daterangepicker.js" />
<asset:stylesheet src="usogeneral/daterangepicker.css" />
<asset:stylesheet src="usogeneral/customdaterangepicker.css" />

<%--<asset:javascript src="gestion/gestionCliente1.js" />
<asset:javascript src="usogeneral/datetimepicker.js" />
<asset:javascript src="usogeneral/customdatetimepicker.js" />
<asset:javascript src="usogeneral/bootstrap-datepicker.min.js" />
<asset:javascript src="usogeneral/customdatepicker.js" />
<asset:javascript src="usogeneral/bootstrap-datepicker.es.min.js" />--%>

