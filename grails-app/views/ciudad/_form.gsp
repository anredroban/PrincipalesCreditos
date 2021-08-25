<%@ page import="callcenter.Ciudad" %>



<div class="fieldcontain ${hasErrors(bean: ciudadInstance, field: 'estado', 'error')} required">
	<label for="estado">
		<g:message code="ciudad.estado.label" default="Estado" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="estado" required="" from="${['ACTIVO', 'INACTIVO']}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: ciudadInstance, field: 'nombre', 'error')} required">
	<label for="nombre">
		<g:message code="ciudad.nombre.label" default="Nombre" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombre" required="" value="${ciudadInstance?.nombre}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: ciudadInstance, field: 'provincia', 'error')} required">
	<label for="provincia">
		<g:message code="ciudad.provincia.label" default="Provincia" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="provincia" name="provincia.id" from="${callcenter.Provincia.list()}" optionKey="id" required="" value="${ciudadInstance?.provincia?.id}" class="many-to-one" optionValue="${{it.nombre}}"/>

</div>

