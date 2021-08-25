
<%@ page import="callcenter.Clientes"%>

<!DOCTYPE html>
<html>
<head>
<asset:javascript src="telefonia.js" />
<meta name="layout" content="main">
<g:set var="entityName"
	value="${message(code: 'clientes.label', default: 'Clientes')}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
	<g:hiddenField name="idUser" id="idUser" value="${cliente.id}" />
	<a href="#show-clientes" class="skip" tabindex="-1"><g:message
			code="default.link.skip.label" default="Skip to content&hellip;" /></a>
	<div class="nav" role="navigation">
		<ul>
			<li><g:link class="list" action="index">
					<g:message code="default.list.label" args="[entityName]" /></g:link>
			</li>
		</ul>
	</div>
	<div id="show-clientes" class="content scaffold-show" role="main">
		<h1>
			<g:message code="default.show.label" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>

		<ol>
			<li class="col-lg-4"><span id="nombre-label"
				class="property-label"><g:message
						code="clientes.nombre.label" default="Nombre" /></span><br> <span
				class="property-value" aria-labelledby="nombre-label"><g:fieldValue
						bean="${cliente}" field="nombre" /></span></li>
			<li class="col-lg-4"><span id="nombre-label"
				class="property-label"><g:message
						code="clientes.cedula.label" default="Cedula" /></span><br> <span
				class="property-value" aria-labelledby="cedula-label"><g:fieldValue
						bean="${cliente}" field="cedula" /></span></li>
			<li class="col-lg-4"><span id="cedula-label"
				class="property-label"><g:message
						code="clientes.telefono.label" default="Telefono" /></span><br> <span
				class="property-value" aria-labelledby="telefono-label"><g:fieldValue
						bean="${cliente}" field="telefono" /></span></li>
		</ol>



		<br> <br> <br> <br>

		<button class="btn btn-success" id="envio" type="button">Guardar</button>
	</div>
</body>
</html>
