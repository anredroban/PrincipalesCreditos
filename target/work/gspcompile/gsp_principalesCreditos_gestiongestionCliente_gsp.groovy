import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_principalesCreditos_gestiongestionCliente_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/gestion/gestionCliente.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
invokeTag('captureMeta','sitemesh',1,['gsp_sm_xmlClosingForEmptyTag':(""),'name':("layout"),'content':("main")],-1)
printHtmlPart(0)
invokeTag('stylesheet','asset',2,['src':("usogeneral/bootstrap-datepicker.min.css")],-1)
printHtmlPart(1)
createTagBody(1, {->
createClosureForHtmlPart(2, 2)
invokeTag('captureTitle','sitemesh',5,[:],2)
})
invokeTag('wrapTitleTag','sitemesh',5,[:],1)
printHtmlPart(3)
invokeTag('stylesheet','asset',7,['src':("gestion/gestionCliente.css")],-1)
printHtmlPart(4)
invokeTag('stylesheet','asset',10,['src':("cloudflare/bootstrap.min.css")],-1)
printHtmlPart(3)
invokeTag('stylesheet','asset',11,['src':("cloudflare/bootstrap-datetimepicker.min.css")],-1)
printHtmlPart(4)
invokeTag('javascript','asset',12,['src':("cloudflare/moment.min.js")],-1)
printHtmlPart(3)
invokeTag('javascript','asset',12,['src':("cloudflare/bootstrap.min.js")],-1)
printHtmlPart(3)
invokeTag('javascript','asset',12,['src':("cloudflare/bootstrap-datetimepicker.min.js")],-1)
printHtmlPart(5)
if(true && (cliente.registroExitoso == 'SI')) {
printHtmlPart(6)
}
printHtmlPart(7)
createTagBody(1, {->
printHtmlPart(8)
expressionOut.print(cliente.tipoProducto)
printHtmlPart(9)
expressionOut.print(cliente.nombre)
printHtmlPart(10)
expressionOut.print(cliente.identificacion)
printHtmlPart(11)
if(true && (cliente.segmento && cliente.segmento.trim() != '')) {
printHtmlPart(12)
expressionOut.print(cliente.segmento)
printHtmlPart(13)
}
printHtmlPart(14)
if(true && (cliente.subsegmento && cliente.subsegmento.trim() != '')) {
printHtmlPart(15)
expressionOut.print(cliente.subsegmento)
printHtmlPart(13)
}
printHtmlPart(14)
if(true && (cliente.tipoCuenta && cliente.tipoCuenta.trim() != '')) {
printHtmlPart(16)
expressionOut.print(cliente.tipoCuenta)
printHtmlPart(13)
}
printHtmlPart(14)
if(true && (cliente.numeroCuenta && cliente.numeroCuenta.trim() != '')) {
printHtmlPart(17)
expressionOut.print(utilitarios.Util.hideCardNumber(cliente.numeroCuenta))
printHtmlPart(13)
}
printHtmlPart(18)
if(true && (cliente.estadoCivil && cliente.estadoCivil.trim() != '')) {
printHtmlPart(19)
expressionOut.print(cliente.estadoCivil)
printHtmlPart(13)
}
printHtmlPart(14)
if(true && (cliente.genero && cliente.genero.trim() != '')) {
printHtmlPart(20)
expressionOut.print(cliente.genero)
printHtmlPart(13)
}
printHtmlPart(14)
if(true && (cliente.fechaNacimiento && cliente.fechaNacimiento.trim() != '')) {
printHtmlPart(21)
expressionOut.print(cliente.fechaNacimiento)
printHtmlPart(13)
}
printHtmlPart(18)
if(true && (cliente.provinciaDomicilio && cliente.provinciaDomicilio.trim() != '')) {
printHtmlPart(22)
expressionOut.print(cliente.provinciaDomicilio)
printHtmlPart(13)
}
printHtmlPart(18)
if(true && (cliente.ciudadDomicilio && cliente.ciudadDomicilio.trim() != '')) {
printHtmlPart(23)
expressionOut.print(cliente.ciudadDomicilio)
printHtmlPart(13)
}
printHtmlPart(18)
if(true && (cliente.direccionDomicilio && cliente.direccionDomicilio.trim() != '')) {
printHtmlPart(24)
expressionOut.print(cliente.direccionDomicilio)
printHtmlPart(13)
}
printHtmlPart(18)
if(true && (cliente.provinciaTrabajo && cliente.provinciaTrabajo.trim() != '')) {
printHtmlPart(25)
expressionOut.print(cliente.provinciaTrabajo)
printHtmlPart(13)
}
printHtmlPart(18)
if(true && (cliente.ciudadTrabajo && cliente.ciudadTrabajo.trim() != '')) {
printHtmlPart(26)
expressionOut.print(cliente.ciudadTrabajo)
printHtmlPart(13)
}
printHtmlPart(18)
if(true && (cliente.direccionTrabajo && cliente.direccionTrabajo.trim() != '')) {
printHtmlPart(27)
expressionOut.print(cliente.direccionTrabajo)
printHtmlPart(13)
}
printHtmlPart(18)
if(true && (cliente.perfilRiesgoEndeud && cliente.perfilRiesgoEndeud.trim() != '')) {
printHtmlPart(28)
expressionOut.print(cliente.perfilRiesgoEndeud)
printHtmlPart(13)
}
printHtmlPart(14)
if(true && (cliente.deudaProtegida && cliente.deudaProtegida.trim() != '')) {
printHtmlPart(29)
expressionOut.print(cliente.deudaProtegida)
printHtmlPart(13)
}
printHtmlPart(14)
if(true && (cliente.codigoMarca && cliente.codigoMarca.trim() != '')) {
printHtmlPart(30)
expressionOut.print(cliente.codigoMarca)
printHtmlPart(13)
}
printHtmlPart(14)
if(true && (cliente.planRecompensas && cliente.planRecompensas.trim() != '')) {
printHtmlPart(31)
expressionOut.print(cliente.planRecompensas)
printHtmlPart(13)
}
printHtmlPart(32)
if(true && (cliente.prioridadCampania == 'PRI TC')) {
printHtmlPart(33)
}
printHtmlPart(0)
if(true && (cliente.prioridadCampania == 'PRI CRD')) {
printHtmlPart(34)
}
printHtmlPart(35)
if(true && (cliente.credConsumEsc1 && cliente.credConsumEsc1.trim() != '')) {
printHtmlPart(36)
if(true && (cliente.credConsumEsc1 && cliente.credConsumEsc1.trim() != '')) {
printHtmlPart(37)
expressionOut.print(cliente.credConsumEsc1)
printHtmlPart(38)
}
printHtmlPart(39)
if(true && (cliente.cuotaConsumEsc1 && cliente.cuotaConsumEsc1.trim() != '')) {
printHtmlPart(40)
expressionOut.print(cliente.cuotaConsumEsc1)
printHtmlPart(38)
}
printHtmlPart(39)
if(true && (cliente.plazoConsumEsc1 && cliente.plazoConsumEsc1.trim() != '')) {
printHtmlPart(41)
expressionOut.print(cliente.plazoConsumEsc1)
printHtmlPart(38)
}
printHtmlPart(39)
if(true && (cliente.prodConsumEsc1 && cliente.prodConsumEsc1.trim() != '')) {
printHtmlPart(42)
expressionOut.print(cliente.prodConsumEsc1)
printHtmlPart(38)
}
printHtmlPart(39)
if(true && (cliente.garanteConsumEsc1 && cliente.garanteConsumEsc1.trim() != '')) {
printHtmlPart(43)
expressionOut.print(cliente.garanteConsumEsc1)
printHtmlPart(38)
}
printHtmlPart(39)
if(true && (cliente.tarjetaEsc1 && cliente.tarjetaEsc1.trim() != '')) {
printHtmlPart(44)
expressionOut.print(cliente.tarjetaEsc1)
printHtmlPart(38)
}
printHtmlPart(39)
if(true && (cliente.marcaTarjEsc1 && cliente.marcaTarjEsc1.trim() != '')) {
printHtmlPart(45)
expressionOut.print(cliente.marcaTarjEsc1)
printHtmlPart(38)
}
printHtmlPart(39)
if(true && (cliente.fliaTarjetaEsc1 && cliente.fliaTarjetaEsc1.trim() != '')) {
printHtmlPart(46)
expressionOut.print(cliente.fliaTarjetaEsc1)
printHtmlPart(38)
}
printHtmlPart(39)
if(true && (cliente.plast1TarjEsc1 && cliente.plast1TarjEsc1.trim() != '')) {
printHtmlPart(47)
expressionOut.print(cliente.plast1TarjEsc1)
printHtmlPart(38)
}
printHtmlPart(48)
}
printHtmlPart(3)
if(true && (cliente.credConsumExclusivo && cliente.credConsumExclusivo.trim() != '')) {
printHtmlPart(49)
if(true && (cliente.credConsumExclusivo && cliente.credConsumExclusivo.trim() != '')) {
printHtmlPart(50)
expressionOut.print(cliente.credConsumExclusivo)
printHtmlPart(38)
}
printHtmlPart(39)
if(true && (cliente.cuotaConsumExclusivo && cliente.cuotaConsumExclusivo.trim() != '')) {
printHtmlPart(51)
expressionOut.print(cliente.cuotaConsumExclusivo)
printHtmlPart(38)
}
printHtmlPart(39)
if(true && (cliente.plazoConsumExclusivo && cliente.plazoConsumExclusivo.trim() != '')) {
printHtmlPart(52)
expressionOut.print(cliente.plazoConsumExclusivo)
printHtmlPart(38)
}
printHtmlPart(39)
if(true && (cliente.prodConsumExclusivo && cliente.prodConsumExclusivo.trim() != '')) {
printHtmlPart(53)
expressionOut.print(cliente.prodConsumExclusivo)
printHtmlPart(38)
}
printHtmlPart(39)
if(true && (cliente.garanteConsumExclusivo && cliente.garanteConsumExclusivo.trim() != '')) {
printHtmlPart(54)
expressionOut.print(cliente.garanteConsumExclusivo)
printHtmlPart(38)
}
printHtmlPart(48)
}
printHtmlPart(4)
if(true && (cliente.tarjetaExclusiva && cliente.tarjetaExclusiva.trim() != '')) {
printHtmlPart(55)
if(true && (cliente.tarjetaExclusiva && cliente.tarjetaExclusiva.trim() != '')) {
printHtmlPart(56)
expressionOut.print(cliente.tarjetaExclusiva)
printHtmlPart(38)
}
printHtmlPart(39)
if(true && (cliente.marcaTarjetaExclusiva && cliente.marcaTarjetaExclusiva.trim() != '')) {
printHtmlPart(57)
expressionOut.print(cliente.marcaTarjetaExclusiva)
printHtmlPart(38)
}
printHtmlPart(39)
if(true && (cliente.fliaTarjetaExclusiva && cliente.fliaTarjetaExclusiva.trim() != '')) {
printHtmlPart(58)
expressionOut.print(cliente.fliaTarjetaExclusiva)
printHtmlPart(38)
}
printHtmlPart(39)
if(true && (cliente.plast1TarjExclusiva && cliente.plast1TarjExclusiva.trim() != '')) {
printHtmlPart(59)
expressionOut.print(cliente.plast1TarjExclusiva)
printHtmlPart(38)
}
printHtmlPart(39)
if(true && (cliente.prodTarjetaExclusiva && cliente.prodTarjetaExclusiva.trim() != '')) {
printHtmlPart(60)
expressionOut.print(cliente.prodTarjetaExclusiva)
printHtmlPart(38)
}
printHtmlPart(48)
}
printHtmlPart(61)
if(true && (cliente.codigoAsignacion && cliente.codigoAsignacion.trim() != '')) {
printHtmlPart(62)
expressionOut.print(cliente.codigoAsignacion)
printHtmlPart(13)
}
printHtmlPart(63)
if(true && (cliente.telefono1)) {
printHtmlPart(64)
expressionOut.print(utilitarios.Util.functionAsterisk(cliente.id.toString()))
expressionOut.print(cliente.telefono1)
printHtmlPart(65)
invokeTag('select','g',356,['class':("form-control"),'id':("estadoTel1"),'name':("estadoTel1"),'from':(['C CLIENTE DE VIAJE','C CLIENTE FALLECIDO','C CLIENTE VIVE FUERA DEL PAIS','C CONTESTA TERCERO','C TELEFONO DE REFERENCIA','C CLIENTE','N CLIENTE SIN TELEFONO','N GRABADORA','N NO CONTESTA','N TELEFONO AVERIADO','N TELEFONO EQUIVOCADO_NO ASIGNADO','N TONO OCUPADO','N NO MARCADO']),'noSelection':(['': '-- Seleccione --']),'optionValue':("value"),'optionKey':("value")],-1)
printHtmlPart(66)
}
printHtmlPart(14)
if(true && (cliente.telefono2 && cliente.telefono2.trim() != '')) {
printHtmlPart(67)
expressionOut.print(utilitarios.Util.functionAsterisk(cliente.id.toString()))
expressionOut.print(cliente.telefono2)
printHtmlPart(65)
invokeTag('select','g',369,['class':("form-control"),'id':("estadoTel2"),'name':("estadoTel2"),'noSelection':(['': '-- Seleccione --']),'from':(['C CLIENTE DE VIAJE','C CLIENTE FALLECIDO','C CLIENTE VIVE FUERA DEL PAIS','C CONTESTA TERCERO','C TELEFONO DE REFERENCIA','C CLIENTE','N CLIENTE SIN TELEFONO','N GRABADORA','N NO CONTESTA','N TELEFONO AVERIADO','N TELEFONO EQUIVOCADO_NO ASIGNADO','N TONO OCUPADO','N NO MARCADO'])],-1)
printHtmlPart(13)
}
printHtmlPart(14)
if(true && (cliente.telefono3 && cliente.telefono3.trim() != '')) {
printHtmlPart(68)
expressionOut.print(utilitarios.Util.functionAsterisk(cliente.id.toString()))
expressionOut.print(cliente.telefono3)
printHtmlPart(65)
invokeTag('select','g',377,['class':("form-control"),'id':("estadoTel3"),'name':("estadoTel3"),'noSelection':(['': '-- Seleccione --']),'from':(['C CLIENTE DE VIAJE','C CLIENTE FALLECIDO','C CLIENTE VIVE FUERA DEL PAIS','C CONTESTA TERCERO','C TELEFONO DE REFERENCIA','C CLIENTE','N CLIENTE SIN TELEFONO','N GRABADORA','N NO CONTESTA','N TELEFONO AVERIADO','N TELEFONO EQUIVOCADO_NO ASIGNADO','N TONO OCUPADO','N NO MARCADO'])],-1)
printHtmlPart(13)
}
printHtmlPart(14)
if(true && (cliente.telefono4 && cliente.telefono4.trim() != '')) {
printHtmlPart(69)
expressionOut.print(utilitarios.Util.functionAsterisk(cliente.id.toString()))
expressionOut.print(cliente.telefono4)
printHtmlPart(65)
invokeTag('select','g',384,['class':("form-control"),'id':("estadoTel4"),'name':("estadoTel4"),'noSelection':(['': '-- Seleccione --']),'from':(['C CLIENTE DE VIAJE','C CLIENTE FALLECIDO','C CLIENTE VIVE FUERA DEL PAIS','C CONTESTA TERCERO','C TELEFONO DE REFERENCIA','C CLIENTE','N CLIENTE SIN TELEFONO','N GRABADORA','N NO CONTESTA','N TELEFONO AVERIADO','N TELEFONO EQUIVOCADO_NO ASIGNADO','N TONO OCUPADO','N NO MARCADO'])],-1)
printHtmlPart(13)
}
printHtmlPart(14)
if(true && (cliente.telefono5 && cliente.telefono5.trim() != '')) {
printHtmlPart(70)
expressionOut.print(utilitarios.Util.functionAsterisk(cliente.id.toString()))
expressionOut.print(cliente.telefono5)
printHtmlPart(65)
invokeTag('select','g',391,['class':("form-control"),'id':("estadoTel5"),'name':("estadoTel5"),'noSelection':(['': '-- Seleccione --']),'from':(['C CLIENTE DE VIAJE','C CLIENTE FALLECIDO','C CLIENTE VIVE FUERA DEL PAIS','C CONTESTA TERCERO','C TELEFONO DE REFERENCIA','C CLIENTE','N CLIENTE SIN TELEFONO','N GRABADORA','N NO CONTESTA','N TELEFONO AVERIADO','N TELEFONO EQUIVOCADO_NO ASIGNADO','N TONO OCUPADO','N NO MARCADO'])],-1)
printHtmlPart(13)
}
printHtmlPart(14)
if(true && (cliente.telefono6 && cliente.telefono6.trim() != '')) {
printHtmlPart(71)
expressionOut.print(utilitarios.Util.functionAsterisk(cliente.id.toString()))
expressionOut.print(cliente.telefono6)
printHtmlPart(65)
invokeTag('select','g',398,['class':("form-control"),'id':("estadoTel6"),'name':("estadoTel6"),'noSelection':(['': '-- Seleccione --']),'from':(['C CLIENTE DE VIAJE','C CLIENTE FALLECIDO','C CLIENTE VIVE FUERA DEL PAIS','C CONTESTA TERCERO','C TELEFONO DE REFERENCIA','C CLIENTE','N CLIENTE SIN TELEFONO','N GRABADORA','N NO CONTESTA','N TELEFONO AVERIADO','N TELEFONO EQUIVOCADO_NO ASIGNADO','N TONO OCUPADO','N NO MARCADO'])],-1)
printHtmlPart(13)
}
printHtmlPart(14)
if(true && (cliente.telefono7 && cliente.telefono7.trim() != '')) {
printHtmlPart(72)
expressionOut.print(utilitarios.Util.functionAsterisk(cliente.id.toString()))
expressionOut.print(cliente.telefono7)
printHtmlPart(65)
invokeTag('select','g',405,['class':("form-control"),'id':("estadoTel7"),'name':("estadoTel7"),'noSelection':(['': '-- Seleccione --']),'from':(['C CLIENTE DE VIAJE','C CLIENTE FALLECIDO','C CLIENTE VIVE FUERA DEL PAIS','C CONTESTA TERCERO','C TELEFONO DE REFERENCIA','C CLIENTE','N CLIENTE SIN TELEFONO','N GRABADORA','N NO CONTESTA','N TELEFONO AVERIADO','N TELEFONO EQUIVOCADO_NO ASIGNADO','N TONO OCUPADO','N NO MARCADO'])],-1)
printHtmlPart(13)
}
printHtmlPart(14)
if(true && (cliente.telefono8 && cliente.telefono8.trim() != '')) {
printHtmlPart(73)
expressionOut.print(utilitarios.Util.functionAsterisk(cliente.id.toString()))
expressionOut.print(cliente.telefono8)
printHtmlPart(65)
invokeTag('select','g',412,['class':("form-control"),'id':("estadoTel8"),'name':("estadoTel8"),'noSelection':(['': '-- Seleccione --']),'from':(['C CLIENTE DE VIAJE','C CLIENTE FALLECIDO','C CLIENTE VIVE FUERA DEL PAIS','C CONTESTA TERCERO','C TELEFONO DE REFERENCIA','C CLIENTE','N CLIENTE SIN TELEFONO','N GRABADORA','N NO CONTESTA','N TELEFONO AVERIADO','N TELEFONO EQUIVOCADO_NO ASIGNADO','N TONO OCUPADO','N NO MARCADO'])],-1)
printHtmlPart(13)
}
printHtmlPart(14)
if(true && (cliente.telefono9 && cliente.telefono9.trim() != '')) {
printHtmlPart(74)
expressionOut.print(utilitarios.Util.functionAsterisk(cliente.id.toString()))
expressionOut.print(cliente.telefono9)
printHtmlPart(65)
invokeTag('select','g',419,['class':("form-control"),'id':("estadoTel9"),'name':("estadoTel9"),'noSelection':(['': '-- Seleccione --']),'from':(['C CLIENTE DE VIAJE','C CLIENTE FALLECIDO','C CLIENTE VIVE FUERA DEL PAIS','C CONTESTA TERCERO','C TELEFONO DE REFERENCIA','C CLIENTE','N CLIENTE SIN TELEFONO','N GRABADORA','N NO CONTESTA','N TELEFONO AVERIADO','N TELEFONO EQUIVOCADO_NO ASIGNADO','N TONO OCUPADO','N NO MARCADO'])],-1)
printHtmlPart(13)
}
printHtmlPart(14)
if(true && (cliente.telefono10 && cliente.telefono10.trim() != '')) {
printHtmlPart(75)
expressionOut.print(utilitarios.Util.functionAsterisk(cliente.id.toString()))
expressionOut.print(cliente.telefono10)
printHtmlPart(65)
invokeTag('select','g',426,['class':("form-control"),'id':("estadoTel10"),'name':("estadoTel10"),'noSelection':(['': '-- Seleccione --']),'from':(['C CLIENTE DE VIAJE','C CLIENTE FALLECIDO','C CLIENTE VIVE FUERA DEL PAIS','C CONTESTA TERCERO','C TELEFONO DE REFERENCIA','C CLIENTE','N CLIENTE SIN TELEFONO','N GRABADORA','N NO CONTESTA','N TELEFONO AVERIADO','N TELEFONO EQUIVOCADO_NO ASIGNADO','N TONO OCUPADO','N NO MARCADO'])],-1)
printHtmlPart(13)
}
printHtmlPart(76)
if(true && (cliente.email && cliente.email.trim() != '')) {
printHtmlPart(77)
expressionOut.print(cliente.email)
printHtmlPart(13)
}
printHtmlPart(14)
if(true && (cliente.codigoAsignacion && cliente.codigoAsignacion.trim() != '')) {
printHtmlPart(78)
expressionOut.print(cliente.codigoAsignacion)
printHtmlPart(13)
}
printHtmlPart(79)
if(true && (cliente.tipoProducto == 'TARJETA NORMAL')) {
printHtmlPart(80)
}
printHtmlPart(18)
if(true && (cliente.tipoProducto == 'PRIMERA TARJETA')) {
printHtmlPart(81)
}
printHtmlPart(82)
expressionOut.print(cliente.id)
printHtmlPart(83)
invokeTag('select','g',461,['class':("form-control"),'name':("estado"),'id':("status"),'from':(callcenter.Estado.list()),'optionKey':("id"),'optionValue':({it.nombre	}),'noSelection':(['': '-- Seleccione --'])],-1)
printHtmlPart(84)
invokeTag('select','g',467,['class':("form-control"),'name':("substatus"),'id':("substatus"),'from':(""),'noSelection':(['': '-- Seleccione --'])],-1)
printHtmlPart(85)
invokeTag('select','g',474,['id':("subSubStatus"),'class':("form-control"),'name':("subSubStatus"),'from':(""),'optionKey':("id")],-1)
printHtmlPart(86)
invokeTag('select','g',485,['class':("form-control"),'name':("motivoNoAceptaSeguro"),'id':("motivoNoAceptaSeguro"),'from':(callcenter.MotivosnoAceptaSeguro.list()),'optionKey':("nombre"),'optionValue':({it.nombre	}),'noSelection':(['': '-- Seleccione --'])],-1)
printHtmlPart(87)
invokeTag('select','g',493,['id':("motivosSubSubEstados"),'class':("form-control"),'name':("motivosSubSubEstados"),'from':(""),'optionKey':("id"),'noSelection':(['': '-- Seleccione --'])],-1)
printHtmlPart(88)
invokeTag('textField','g',498,['class':("form-control"),'name':("deseaNuevoProducto")],-1)
printHtmlPart(89)
invokeTag('select','g',507,['class':("form-control"),'name':("agendamiento"),'id':("agendamiento"),'from':(['AGENDAR PARA MI':'AGENDAR PARA MI', 'AGENDAR PARA CUALQUIERA':'AGENDAR PARA CUALQUIERA']),'optionKey':("key"),'optionValue':("value"),'noSelection':(['': '-- Seleccione --'])],-1)
printHtmlPart(90)
invokeTag('textField','g',522,['maxlength':("10"),'minlength':("9"),'id':("telefonoContactado"),'name':("telefonoContactado"),'class':("form-control")],-1)
printHtmlPart(91)
invokeTag('select','g',529,['class':("form-control"),'id':("estadoTelefonoContactado"),'name':("estadoTelefonoContactado"),'from':(""),'noSelection':(['': '-- Seleccione --']),'optionValue':("value"),'optionKey':("value")],-1)
printHtmlPart(92)
invokeTag('select','g',542,['class':("form-control"),'id':("creditoAceptado"),'name':("creditoAceptado"),'from':(['OPT1': 'Opción 1', 'OPT2': 'Opción 2']),'noSelection':(['': '-- Seleccione --']),'optionKey':("key"),'optionValue':("value")],-1)
printHtmlPart(93)
invokeTag('select','g',547,['class':("form-control"),'id':("situacionLaboralCredito"),'name':("situacionLaboralCredito"),'from':(['DEPENDIENTE': 'DEPENDIENTE', 'INDEPENDIENTE': 'INDEPENDIENTE']),'noSelection':(['': '-- Seleccione --']),'optionKey':("key"),'optionValue':("value")],-1)
printHtmlPart(94)
invokeTag('select','g',558,['class':("form-control"),'id':("signatureDocuments"),'name':("signatureDocuments"),'from':(['AGENCIA': 'AGENCIA', 'DOMICILIO': 'DOMICILIO', 'SIN AGENCIA': 'SIN AGENCIA', 'DIGITAL': 'DIGITAL']),'noSelection':(['': '-- Seleccione --']),'optionKey':("key"),'optionValue':("value")],-1)
printHtmlPart(95)
invokeTag('select','g',563,['class':("form-control"),'id':("provinciaEntrega"),'name':("provinciaEntrega"),'optionKey':("id"),'noSelection':(['': '-- Seleccione --']),'from':(callcenter.Provincia.list(sort: "nombre", order: "asc")),'optionValue':({it.nombre})],-1)
printHtmlPart(96)
invokeTag('select','g',568,['class':("form-control"),'id':("ciudadEntrega"),'name':("ciudadEntrega"),'optionKey':("id"),'noSelection':(['': '-- Seleccione --']),'from':(""),'optionValue':({it.nombre})],-1)
printHtmlPart(97)
invokeTag('select','g',571,['class':("form-control"),'id':("oficinaTarjeta"),'name':("oficinaTarjeta"),'optionKey':("id"),'noSelection':(['': '-- Seleccione --']),'from':(""),'optionValue':("name")],-1)
printHtmlPart(98)
invokeTag('textField','g',581,['maxlength':("9"),'minlength':("9"),'id':("telefonoConvencionalCredito"),'name':("telefonoConvencionalCredito"),'class':("form-control")],-1)
printHtmlPart(99)
invokeTag('textField','g',585,['maxlength':("10"),'minlength':("10"),'id':("telefonoCelularCredito"),'name':("telefonoCelularCredito"),'class':("form-control")],-1)
printHtmlPart(100)
invokeTag('select','g',593,['class':("form-control"),'id':("provinciaDomicilioFvt"),'name':("provinciaDomicilioFvt"),'optionKey':("id"),'noSelection':(['': '-- Seleccione --']),'from':(callcenter.Provincia.list(sort: "nombre", order: "asc")),'optionValue':({it.nombre})],-1)
printHtmlPart(101)
invokeTag('select','g',599,['class':("form-control"),'id':("ciudadDomicilioFvt"),'name':("ciudadDomicilioFvt"),'optionKey':("id"),'noSelection':(['': '-- Seleccione --']),'from':(""),'optionValue':({it.nombre})],-1)
printHtmlPart(102)
invokeTag('textField','g',606,['id':("correoCredito"),'name':("correoCredito"),'class':("form-control")],-1)
printHtmlPart(103)
invokeTag('select','g',611,['class':("form-control"),'id':("direccionCliente"),'name':("direccionCliente"),'from':(['SI': 'SI', 'NO': 'NO']),'noSelection':(['': '-- Seleccione --']),'optionKey':("key"),'optionValue':("value")],-1)
printHtmlPart(104)
invokeTag('textField','g',639,['id':("cedulaTitular"),'name':("cedulaTitular"),'class':("form-control"),'value':(cliente.identificacion?:'')],-1)
printHtmlPart(105)
invokeTag('textField','g',645,['id':("nombre1"),'name':("nombre1"),'class':("form-control"),'value':(cliente.primerNombre?:'')],-1)
printHtmlPart(106)
invokeTag('textField','g',648,['id':("nombre2"),'name':("nombre2"),'class':("form-control"),'value':(cliente.segundoNombre?:'')],-1)
printHtmlPart(107)
invokeTag('textField','g',651,['id':("apellido1"),'name':("apellido1"),'class':("form-control"),'value':(cliente.primerApellido?:'')],-1)
printHtmlPart(108)
invokeTag('textField','g',655,['id':("apellido2"),'name':("apellido2"),'class':("form-control"),'value':(cliente.segundoApellido?:'')],-1)
printHtmlPart(109)
invokeTag('textField','g',663,['id':("nacionalidad"),'name':("nacionalidad"),'class':("form-control")],-1)
printHtmlPart(110)
invokeTag('select','g',668,['class':("form-control"),'id':("genero"),'name':("genero"),'from':(['MASCULINO', 'FEMENINO']),'noSelection':(['': '-- Seleccione --'])],-1)
printHtmlPart(111)
invokeTag('select','g',676,['class':("form-control"),'id':("estadoCivilGestion"),'name':("estadoCivilGestion"),'from':(['DIVORCIADO', 'SOLTERO', 'CASADO', 'UNION LIBRE', 'VIUDO']),'noSelection':(['': '-- Seleccione --'])],-1)
printHtmlPart(112)
invokeTag('textField','g',683,['id':("paisNacimiento"),'name':("paisNacimiento"),'class':("form-control")],-1)
printHtmlPart(113)
invokeTag('select','g',691,['class':("form-control"),'id':("provinciaNacimiento"),'name':("provinciaNacimiento"),'optionKey':("id"),'noSelection':(['': '-- Seleccione --']),'from':(callcenter.Provincia.list(sort: "nombre", order: "asc")),'optionValue':({it.nombre})],-1)
printHtmlPart(114)
invokeTag('select','g',697,['class':("form-control"),'id':("ciudadNacimiento"),'name':("ciudadNacimiento"),'optionKey':("nombre"),'noSelection':(['': '-- Seleccione --']),'from':(""),'optionValue':({it.nombre})],-1)
printHtmlPart(115)
invokeTag('textField','g',703,['id':("fechaNacimientoGestion"),'name':("fechaNacimientoGestion"),'class':("form-control datepicker pointer")],-1)
printHtmlPart(116)
invokeTag('select','g',713,['class':("form-control"),'id':("provinciaDomicilioGestion"),'name':("provinciaDomicilioGestion"),'optionKey':("id"),'noSelection':(['': '-- Seleccione --']),'from':(callcenter.Provincia.list(sort: "nombre", order: "asc")),'optionValue':({it.nombre})],-1)
printHtmlPart(117)
invokeTag('select','g',721,['class':("form-control"),'id':("ciudadDomicilioGestion"),'name':("ciudadDomicilioGestion"),'optionKey':("id"),'noSelection':(['': '-- Seleccione --']),'from':(""),'optionValue':({it.nombre})],-1)
printHtmlPart(118)
invokeTag('select','g',724,['class':("form-control"),'id':("cantonDomicilio"),'name':("cantonDomicilio"),'optionKey':("id"),'noSelection':(['': '-- Seleccione --']),'from':(""),'optionValue':({it.nombre})],-1)
printHtmlPart(119)
invokeTag('select','g',729,['class':("form-control"),'id':("parroquiaDomicilio"),'name':("parroquiaDomicilio"),'optionKey':("id"),'noSelection':(['': '-- Seleccione --']),'from':(""),'optionValue':({it.nombre})],-1)
printHtmlPart(120)
invokeTag('textField','g',734,['id':("sectorDomicilio"),'name':("sectorDomicilio"),'class':("form-control")],-1)
printHtmlPart(121)
invokeTag('textField','g',741,['id':("callePrincipalDomicilio"),'name':("callePrincipalDomicilio"),'class':("form-control")],-1)
printHtmlPart(122)
invokeTag('textField','g',744,['id':("numeroCasaDomicilio"),'name':("numeroCasaDomicilio"),'class':("form-control")],-1)
printHtmlPart(123)
invokeTag('textField','g',748,['id':("calleSecundariaDomicilio"),'name':("calleSecundariaDomicilio"),'class':("form-control")],-1)
printHtmlPart(124)
invokeTag('textArea','g',751,['class':("form-control"),'id':("referenciaDomicilio"),'name':("referenciaDomicilio")],-1)
printHtmlPart(125)
invokeTag('textField','g',754,['maxlength':("8"),'minlength':("8"),'class':("form-control"),'id':("telefonoDomicilio"),'name':("telefonoDomicilio")],-1)
printHtmlPart(126)
invokeTag('textField','g',759,['maxlength':("10"),'minlength':("8"),'class':("form-control"),'id':("celularGestion"),'name':("celularGestion")],-1)
printHtmlPart(127)
invokeTag('textField','g',763,['class':("form-control"),'id':("emailPersonal"),'name':("emailPersonal")],-1)
printHtmlPart(128)
invokeTag('select','g',774,['class':("form-control"),'id':("provinciaTrabajoGestion"),'name':("provinciaTrabajoGestion"),'optionKey':("id"),'noSelection':(['': '-- Seleccione --']),'from':(callcenter.Provincia.list(sort: "nombre", order: "asc")),'optionValue':({it.nombre})],-1)
printHtmlPart(129)
invokeTag('select','g',779,['class':("form-control"),'id':("ciudadTrabajoGestion"),'name':("ciudadTrabajoGestion"),'optionKey':("id"),'noSelection':(['': '-- Seleccione --']),'from':(""),'optionValue':({it.nombre})],-1)
printHtmlPart(130)
invokeTag('select','g',787,['class':("form-control"),'id':("cantonTrabajo"),'name':("cantonTrabajo"),'optionKey':("id"),'noSelection':(['': '-- Seleccione --']),'from':(""),'optionValue':({it.nombre})],-1)
printHtmlPart(131)
invokeTag('select','g',793,['class':("form-control"),'id':("parroquiaTrabajo"),'name':("parroquiaTrabajo"),'optionKey':("id"),'noSelection':(['': '-- Seleccione --']),'from':(""),'optionValue':({it.nombre})],-1)
printHtmlPart(132)
invokeTag('textField','g',797,['id':("sectorTrabajo"),'name':("sectorTrabajo"),'class':("form-control")],-1)
printHtmlPart(121)
invokeTag('textField','g',806,['id':("callePrincipalTrabajo"),'name':("callePrincipalTrabajo"),'class':("form-control")],-1)
printHtmlPart(122)
invokeTag('textField','g',809,['id':("numeroCasaTrabajo"),'name':("numeroCasaTrabajo"),'class':("form-control")],-1)
printHtmlPart(123)
invokeTag('textField','g',813,['id':("calleSecundariaTrabajo"),'name':("calleSecundariaTrabajo"),'class':("form-control")],-1)
printHtmlPart(124)
invokeTag('textArea','g',816,['class':("form-control"),'id':("referenciaTrabajo"),'name':("referenciaTrabajo")],-1)
printHtmlPart(133)
invokeTag('textField','g',819,['maxlength':("8"),'minlength':("8"),'class':("form-control"),'id':("telefonoTrabajo"),'name':("telefonoTrabajo")],-1)
printHtmlPart(134)
invokeTag('textField','g',823,['class':("form-control"),'id':("fax"),'name':("fax")],-1)
printHtmlPart(135)
invokeTag('textField','g',824,['class':("form-control"),'id':("emailTrabajo"),'name':("emailTrabajo")],-1)
printHtmlPart(136)
invokeTag('select','g',834,['class':("form-control"),'id':("entregaEstadoCuenta"),'name':("entregaEstadoCuenta"),'from':([1:'DOMICILIO', 2:'TRABAJO']),'noSelection':(['': '-- Seleccione --']),'optionKey':("key"),'optionValue':("value")],-1)
printHtmlPart(137)
invokeTag('select','g',841,['class':("form-control"),'id':("lugarEntrega"),'name':("lugarEntrega"),'from':([1:'DOMICILIO', 2:'TRABAJO']),'noSelection':(['': '-- Seleccione --']),'optionKey':("key"),'optionValue':("value")],-1)
printHtmlPart(138)
invokeTag('textField','g',847,['maxlength':("10"),'minlength':("8"),'class':("form-control"),'id':("telefonoContacto"),'name':("telefonoContacto")],-1)
printHtmlPart(139)
invokeTag('textField','g',861,['class':("form-control"),'id':("nombreRecibeTarjeta"),'name':("nombreRecibeTarjeta"),'value':(cliente.nombre)],-1)
printHtmlPart(140)
invokeTag('select','g',870,['class':("form-control"),'id':("horarioEntrega"),'name':("horarioEntrega"),'from':(['MAÑANA', 'TARDE', 'NOCHE']),'noSelection':(['': '-- Seleccione --'])],-1)
printHtmlPart(141)
invokeTag('textField','g',875,['class':("form-control"),'id':("nombresReferencia"),'name':("nombresReferencia")],-1)
printHtmlPart(142)
invokeTag('textField','g',877,['class':("form-control"),'id':("pais"),'name':("pais")],-1)
printHtmlPart(143)
invokeTag('select','g',883,['class':("form-control"),'id':("provincia"),'name':("provincia"),'optionKey':("id"),'noSelection':(['': '-- Seleccione --']),'from':(callcenter.Provincia.list(sort: "nombre", order: "asc")),'optionValue':({it.nombre})],-1)
printHtmlPart(144)
invokeTag('select','g',884,['class':("form-control"),'id':("ciudad"),'name':("ciudad"),'optionKey':("nombre"),'noSelection':(['': '-- Seleccione --']),'from':(""),'optionValue':({it.nombre})],-1)
printHtmlPart(145)
invokeTag('textField','g',888,['maxlength':("10"),'minlength':("8"),'class':("form-control"),'id':("telefono"),'name':("telefono")],-1)
printHtmlPart(146)
invokeTag('select','g',901,['class':("form-control"),'optionValue':("value"),'id':("relacionConCliente"),'name':("relacionConCliente"),'from':([ 'PADRE', 'MADRE', 'HIJO/HIJA', 'ABUELO/ABUELA', 'NUERA', 'YERNO', 'CÓNYUGE', 'CUÑADO / CUÑADA',
																																	'NIETO / NIETA', 'TIO / TIA', 'SOBRINO / SOBRINA', 'PRIMO / PRIMA', 'HERMANO / HERMANA'
																																	, 'SUEGRO/SUEGRA']),'noSelection':(['': '-- Seleccione --'])],-1)
printHtmlPart(147)
invokeTag('select','g',910,['class':("form-control"),'id':("tipoCliente"),'name':("tipoCliente"),'from':(['I':'INDEPENDIENTE','D':'DEPENDIENTE']),'noSelection':(['': '-- Seleccione --']),'optionKey':("key"),'optionValue':("value")],-1)
printHtmlPart(148)
invokeTag('textField','g',918,['class':("form-control"),'id':("nombreNegocio"),'name':("nombreNegocio")],-1)
printHtmlPart(149)
invokeTag('textField','g',920,['id':("fechaInicioNegocio"),'name':("fechaInicioNegocio"),'class':("form-control datepicker pointer")],-1)
printHtmlPart(150)
invokeTag('textField','g',925,['class':("form-control"),'id':("actividadEconomica"),'name':("actividadEconomica")],-1)
printHtmlPart(151)
invokeTag('textField','g',929,['class':("form-control"),'id':("ventasHonorariosMensuales"),'name':("ventasHonorariosMensuales")],-1)
printHtmlPart(152)
invokeTag('textField','g',934,['class':("form-control"),'id':("costoVentas"),'name':("costoVentas")],-1)
printHtmlPart(153)
invokeTag('textField','g',935,['class':("form-control"),'id':("gastosOperativos"),'name':("gastosOperativos")],-1)
printHtmlPart(154)
invokeTag('select','g',944,['class':("form-control"),'id':("situacionLaboral"),'name':("situacionLaboral"),'from':(['DEPEND./EMPLEADO PÚBLICO', 'DEPEND./EMPLEADO PRIVADO']),'noSelection':(['': '-- Seleccione --'])],-1)
printHtmlPart(155)
invokeTag('textField','g',948,['class':("form-control"),'id':("nombreEmpresaGestion"),'name':("nombreEmpresaGestion")],-1)
printHtmlPart(156)
invokeTag('select','g',955,['class':("form-control"),'id':("contrato"),'name':("contrato"),'from':(['FIJO','POR HORAS','TEMPORAL','A DESTAJO']),'noSelection':(['': '-- Seleccione --']),'optionValue':("value")],-1)
printHtmlPart(157)
invokeTag('textField','g',959,['class':("form-control"),'id':("cargo"),'name':("cargo")],-1)
printHtmlPart(158)
invokeTag('textField','g',964,['id':("fechaInicioTrabajoActual"),'name':("fechaInicioTrabajoActual"),'class':("form-control datepicker pointer")],-1)
printHtmlPart(159)
invokeTag('textField','g',967,['class':("form-control"),'id':("sueldo"),'name':("sueldo")],-1)
printHtmlPart(160)
invokeTag('textField','g',972,['class':("form-control"),'id':("gastosFamilia"),'name':("gastosFamilia")],-1)
printHtmlPart(161)
invokeTag('select','g',984,['class':("form-control"),'id':("situacionLaboralJubilado"),'name':("situacionLaboralJubilado"),'from':(['DEPEND./EMPLEADO PÚBLICO', 'DEPEND./EMPLEADO PRIVADO']),'noSelection':(['': '-- Seleccione --'])],-1)
printHtmlPart(162)
invokeTag('textField','g',987,['class':("form-control"),'id':("nombreEmpresaGestionJubilado"),'name':("nombreEmpresaGestionJubilado")],-1)
printHtmlPart(163)
invokeTag('select','g',994,['class':("form-control"),'id':("contratoJubilado"),'name':("contratoJubilado"),'from':(['FIJO','POR HORAS','TEMPORAL','A DESTAJO']),'noSelection':(['': '-- Seleccione --']),'optionValue':("value")],-1)
printHtmlPart(164)
invokeTag('textField','g',997,['class':("form-control"),'id':("cargoJubilado"),'name':("cargoJubilado")],-1)
printHtmlPart(165)
invokeTag('textField','g',1004,['id':("fechaInicioTrabajoActualJubilado"),'name':("fechaInicioTrabajoActualJubilado"),'class':("form-control datepicker pointer")],-1)
printHtmlPart(166)
invokeTag('textField','g',1008,['class':("form-control"),'id':("sueldoJubilado"),'name':("sueldoJubilado")],-1)
printHtmlPart(167)
invokeTag('textField','g',1014,['class':("form-control"),'id':("gastosFamiliaJubilado"),'name':("gastosFamiliaJubilado")],-1)
printHtmlPart(168)
invokeTag('select','g',1023,['class':("form-control"),'id':("tipoVivienda"),'name':("tipoVivienda"),'from':(['ALQUILER','PROPIA NO HIPOTECADA','VIVE CON FAMILIAR','ANTICRESIS','PRESTADA','PROPIA HIPOTECADA']),'noSelection':(['': '-- Seleccione --']),'optionValue':("value")],-1)
printHtmlPart(169)
invokeTag('select','g',1028,['class':("form-control"),'id':("obligadoContabilidad"),'name':("obligadoContabilidad"),'from':(['SI','NO']),'noSelection':(['': '-- Seleccione --']),'optionValue':("value")],-1)
printHtmlPart(170)
invokeTag('select','g',1033,['class':("form-control"),'id':("estadoCuentaDigital"),'name':("estadoCuentaDigital"),'from':(['SI','NO']),'noSelection':(['': '-- Seleccione --']),'optionValue':("value")],-1)
printHtmlPart(171)
invokeTag('select','g',1039,['class':("form-control"),'id':("sms"),'name':("sms"),'from':(['SI','NO']),'noSelection':(['': '-- Seleccione --']),'optionValue':("value")],-1)
printHtmlPart(172)
invokeTag('select','g',1045,['class':("form-control"),'id':("seguroDesgravamen"),'name':("seguroDesgravamen"),'from':(['SI','NO']),'noSelection':(['': '-- Seleccione --']),'optionValue':("value")],-1)
printHtmlPart(173)
invokeTag('select','g',1059,['class':("form-control"),'id':("provinciaNacimientoCU2"),'name':("provinciaNacimientoCU2"),'optionKey':("id"),'noSelection':(['': '-- Seleccione --']),'from':(callcenter.Provincia.list(sort: "nombre", order: "asc")),'optionValue':({it.nombre})],-1)
printHtmlPart(174)
invokeTag('select','g',1062,['class':("form-control"),'id':("ciudadNacimientoCU2"),'name':("ciudadNacimientoCU2"),'optionKey':("id"),'noSelection':(['': '-- Seleccione --']),'from':(""),'optionValue':({it.nombre})],-1)
printHtmlPart(175)
invokeTag('textArea','g',1067,['class':("form-control"),'name':("observaciones"),'value':(cliente?.observaciones)],-1)
printHtmlPart(176)
invokeTag('submitButton','g',1071,['id':("send"),'name':("btnGuardarCliente"),'class':("btn btn-primary"),'value':("Guardar Gestión")],-1)
printHtmlPart(177)
})
invokeTag('form','g',1072,['action':("guardarCliente")],1)
printHtmlPart(178)
invokeTag('javascript','asset',1072,['src':("usogeneral/objetos.js")],-1)
printHtmlPart(0)
invokeTag('javascript','asset',1072,['src':("gestion/gestionCliente1.js")],-1)
printHtmlPart(0)
invokeTag('javascript','asset',1074,['src':("usogeneral/customdatetimepicker.js")],-1)
printHtmlPart(0)
invokeTag('javascript','asset',1075,['src':("usogeneral/bootstrap-datepicker.min.js")],-1)
printHtmlPart(0)
invokeTag('javascript','asset',1076,['src':("usogeneral/customdatepicker.js")],-1)
printHtmlPart(0)
invokeTag('javascript','asset',1077,['src':("usogeneral/bootstrap-datepicker.es.min.js")],-1)
printHtmlPart(0)
invokeTag('javascript','asset',1077,['src':("usogeneral/moment.min.js")],-1)
printHtmlPart(0)
invokeTag('javascript','asset',1079,['src':("usogeneral/daterangepicker.js")],-1)
printHtmlPart(0)
invokeTag('stylesheet','asset',1080,['src':("usogeneral/daterangepicker.css")],-1)
printHtmlPart(0)
invokeTag('stylesheet','asset',1081,['src':("usogeneral/customdaterangepicker.css")],-1)
printHtmlPart(4)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1612377159121L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
