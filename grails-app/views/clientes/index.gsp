
<%@ page import="callcenter.Clientes"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName"
	value="${message(code: 'clientes.label', default: 'Clientes')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
	<a href="#list-clientes" class="skip" tabindex="-1"><g:message
			code="default.link.skip.label" default="Skip to content&hellip;" /></a>
	</div>
	<div id="list-clientes" class="content scaffold-list" role="main">
		<h1>
			<g:message code="default.list.label" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>
		<table class="table table-bordered table-hover table-striped">
			<thead>
				<tr>
					<g:sortableColumn property="nombre"
						title="${message(code: 'clientes.nombre.label', default: 'Nombre Cliente')}" />

					<g:sortableColumn property="telefono"
						title="${message(code: 'clientes.telefono.label', default: 'Telefono')}" />

					<g:sortableColumn property="actividadEconomica"
						title="${message(code: 'clientes.celular.label', default: 'Celular')}" />

					<g:sortableColumn property="ciudad"
						title="${message(code: 'clientes.ciudad.label', default: 'Ciudad')}" />

				</tr>
			</thead>
			<tbody>
				<g:each in="${clienteList}" status="i" var="cliente">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

						<td>
							<g:link action="show" id="${cliente.id}">${fieldValue(bean: cliente, field: "nombre")}</g:link>
						</td>

						<td>
							${fieldValue(bean: cliente, field: "telefono")}
						</td>

						<td>
							${fieldValue(bean: cliente, field: "celular")}
						</td>

						<td>
							${fieldValue(bean: cliente, field: "ciudad")}
						</td>
					</tr>
				</g:each>
			</tbody>
		</table>
		<div class="pagination">
			<g:paginate total="${clienteCount ?: 0}" />
		</div>
	</div>
</body>
</html>
