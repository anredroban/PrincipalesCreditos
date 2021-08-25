<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=${encoding}"/>
<title>Principales Credito</title>
<asset:stylesheet src="usuario/login.css" />
<asset:stylesheet src="usuario/login/style.css" />
<link rel="shortcut icon" href="${assetPath(src: 'favicon.png')}"
	type="image/png">
</head>
<body>
<div id="contenedor">
  <div class="wrap">
  		<g:form action="login">
  		<br><br>
  		<div>
  			<g:if test="${flash.errorMessage}">
  				<div style="color: red">${flash.errorMessage}</div>
  			</g:if>
  		</div>
  		<div>
			<center style="font-size: 24px; font-family: Poppins-Bold;">Tarjetas Principales CRÉDITO</center> <br>
			<center><img src="${assetPath(src: 'login.png')}" alt="AVATAR"></center>
			<br>
	  		<span style="color:#a6acaf; font-size: 15px;">Usuario</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input style="font-size: 12px" type="text" placeholder="usuario" name="usuario" required="" autofocus id="usuario" value="${usuario?.usuario}"><br><br>
	  		<span style="color: #a6acaf; font-size: 15px;">Password</span>&nbsp;&nbsp;<input style="font-size: 12px" type="password" placeholder="password" name="password" required="" id="password"><br><br>
	  		<div class="shake-horizontalx">
	  			<input style="font-size: 16px; font-weight: bold" type="submit" value="Ingresar" id="boton">
	  		</div>
  		</div>
  		</g:form>
  	</div>
  </div>
</body>
</html>