<%@ page import="callcenter.Subestado" %>



<div class="fieldcontain ${hasErrors(bean: subestadoInstance, field: 'rellamar', 'error')} required">
	<label for="rellamar">
		<g:message code="subestado.rellamar.label" default="Rellamar" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="rellamar" from="${subestadoInstance.constraints.rellamar.inList}" required="" value="${subestadoInstance?.rellamar}" valueMessagePrefix="subestado.rellamar"/>

</div>

<div class="fieldcontain ${hasErrors(bean: subestadoInstance, field: 'estado', 'error')} required">
	<label for="estado">
		<g:message code="subestado.estado.label" default="Estado" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="estado" name="estado.id" from="${callcenter.Estado.list()}" optionKey="id" required="" value="${subestadoInstance?.estado?.id}" class="many-to-one" optionValue="${{it.nombre}}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: subestadoInstance, field: 'nombre', 'error')} required">
	<label for="nombre">
		<g:message code="subestado.nombre.label" default="Nombre" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombre" required="" value="${subestadoInstance?.nombre}"/>

</div>

