<%@ page import="callcenter.Parroquia" %>



<div class="fieldcontain ${hasErrors(bean: parroquiaInstance, field: 'ciudad', 'error')} required">
	<label for="ciudad">
		<g:message code="parroquia.ciudad.label" default="Ciudad" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="ciudad" name="ciudad.id" from="${callcenter.Ciudad.list()}" optionKey="id" required="" value="${parroquiaInstance?.ciudad?.id}" class="many-to-one" optionValue="${{it.nombre}}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: parroquiaInstance, field: 'nombre', 'error')} required">
	<label for="nombre">
		<g:message code="parroquia.nombre.label" default="Nombre" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombre" required="" value="${parroquiaInstance?.nombre}"/>

</div>

