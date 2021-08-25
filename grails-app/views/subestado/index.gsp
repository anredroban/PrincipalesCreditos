
<%@ page import="callcenter.Subestado" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'subestado.label', default: 'Subestado')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-subestado" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav sombra" role="navigation">
			<ul>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-subestado" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table class="table table-bordered table-hover table-striped">
			<thead>
					<tr>
					
						<g:sortableColumn property="rellamar" title="${message(code: 'subestado.rellamar.label', default: 'Rellamar')}" />
					
						<th><g:message code="subestado.estado.label" default="Estado" /></th>
					
						<g:sortableColumn property="nombre" title="${message(code: 'subestado.nombre.label', default: 'Nombre')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${subestadoInstanceList}" status="i" var="subestadoInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${subestadoInstance.id}">${fieldValue(bean: subestadoInstance, field: "rellamar")}</g:link></td>
					
						<td>${fieldValue(bean: subestadoInstance, field: "estado.nombre")}</td>
					
						<td>${fieldValue(bean: subestadoInstance, field: "nombre")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${subestadoInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
