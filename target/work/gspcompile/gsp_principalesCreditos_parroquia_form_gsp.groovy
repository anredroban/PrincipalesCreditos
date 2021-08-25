import callcenter.Parroquia
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_principalesCreditos_parroquia_form_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/parroquia/_form.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
expressionOut.print(hasErrors(bean: parroquiaInstance, field: 'ciudad', 'error'))
printHtmlPart(1)
invokeTag('message','g',7,['code':("parroquia.ciudad.label"),'default':("Ciudad")],-1)
printHtmlPart(2)
invokeTag('select','g',10,['id':("ciudad"),'name':("ciudad.id"),'from':(callcenter.Ciudad.list()),'optionKey':("id"),'required':(""),'value':(parroquiaInstance?.ciudad?.id),'class':("many-to-one"),'optionValue':({it.nombre})],-1)
printHtmlPart(3)
expressionOut.print(hasErrors(bean: parroquiaInstance, field: 'nombre', 'error'))
printHtmlPart(4)
invokeTag('message','g',16,['code':("parroquia.nombre.label"),'default':("Nombre")],-1)
printHtmlPart(2)
invokeTag('textField','g',19,['name':("nombre"),'required':(""),'value':(parroquiaInstance?.nombre)],-1)
printHtmlPart(5)
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
