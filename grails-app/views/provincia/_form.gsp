<%@ page import="callcenter.Provincia" %>



<div class="fieldcontain ${hasErrors(bean: provinciaInstance, field: 'nombre', 'error')} required">
	<label for="nombre">
		<g:message code="provincia.nombre.label" default="Nombre" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombre" required="" value="${provinciaInstance?.nombre}"/>

</div>

