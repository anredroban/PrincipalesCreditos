import callcenter.Clientes
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_principalesCreditos_clientesindex_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/clientes/index.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
printHtmlPart(1)
createTagBody(1, {->
printHtmlPart(0)
invokeTag('captureMeta','sitemesh',6,['gsp_sm_xmlClosingForEmptyTag':(""),'name':("layout"),'content':("main")],-1)
printHtmlPart(0)
invokeTag('set','g',8,['var':("entityName"),'value':(message(code: 'clientes.label', default: 'Clientes'))],-1)
printHtmlPart(0)
createTagBody(2, {->
createTagBody(3, {->
invokeTag('message','g',9,['code':("default.list.label"),'args':([entityName])],-1)
})
invokeTag('captureTitle','sitemesh',9,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',9,[:],2)
printHtmlPart(0)
})
invokeTag('captureHead','sitemesh',10,[:],1)
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(2)
invokeTag('message','g',13,['code':("default.link.skip.label"),'default':("Skip to content&hellip;")],-1)
printHtmlPart(3)
invokeTag('message','g',17,['code':("default.list.label"),'args':([entityName])],-1)
printHtmlPart(4)
if(true && (flash.message)) {
printHtmlPart(5)
expressionOut.print(flash.message)
printHtmlPart(6)
}
printHtmlPart(7)
invokeTag('sortableColumn','g',28,['property':("nombre"),'title':(message(code: 'clientes.nombre.label', default: 'Nombre Cliente'))],-1)
printHtmlPart(8)
invokeTag('sortableColumn','g',31,['property':("telefono"),'title':(message(code: 'clientes.telefono.label', default: 'Telefono'))],-1)
printHtmlPart(8)
invokeTag('sortableColumn','g',34,['property':("actividadEconomica"),'title':(message(code: 'clientes.celular.label', default: 'Celular'))],-1)
printHtmlPart(8)
invokeTag('sortableColumn','g',37,['property':("ciudad"),'title':(message(code: 'clientes.ciudad.label', default: 'Ciudad'))],-1)
printHtmlPart(9)
loop:{
int i = 0
for( cliente in (clienteList) ) {
printHtmlPart(10)
expressionOut.print((i % 2) == 0 ? 'even' : 'odd')
printHtmlPart(11)
createTagBody(3, {->
expressionOut.print(fieldValue(bean: cliente, field: "nombre"))
})
invokeTag('link','g',46,['action':("show"),'id':(cliente.id)],3)
printHtmlPart(12)
expressionOut.print(fieldValue(bean: cliente, field: "telefono"))
printHtmlPart(12)
expressionOut.print(fieldValue(bean: cliente, field: "celular"))
printHtmlPart(12)
expressionOut.print(fieldValue(bean: cliente, field: "ciudad"))
printHtmlPart(13)
i++
}
}
printHtmlPart(14)
invokeTag('paginate','g',65,['total':(clienteCount ?: 0)],-1)
printHtmlPart(15)
})
invokeTag('captureBody','sitemesh',68,[:],1)
printHtmlPart(16)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1493844622000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
