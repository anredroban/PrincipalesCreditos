<%@ page import="callcenter.Motivosnoacepta" %>



<div class="fieldcontain ${hasErrors(bean: motivosnoaceptaInstance, field: 'descripcion', 'error')} required">
	<label for="descripcion">
		<g:message code="motivosnoacepta.descripcion.label" default="Descripcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="descripcion" required="" value="${motivosnoaceptaInstance?.descripcion}"/>

</div>

