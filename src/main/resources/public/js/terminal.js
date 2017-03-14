function Header ( icono, texto ) {
	document.write("<div id='HEADER' align='left'>");
		document.write("<img src='/img/ico_"+icono+".png' alt='POI' class='ico icoMediano'>");
		document.write("<font class='textoHeader fuente-Oswald'>"+texto+" <\/font>");
	document.write("<\/div>");
}
function Footer ( ) {
	document.write("<div id='FOOTER' align='right'>");
		document.write("<font class='textoFooter fuente-Ubuntu'>en casa andaba <\/font>");
		document.write("<img src='/img/ico_home.png' alt='POI' class='ico icoChico'>");
		document.write("<img src='/img/ico_done.png' alt='POI' class='ico icoChico'>");
	document.write("<\/div>");
}
function NavBar ( tipo ) {
	document.write("<div id='NAVBAR' align='right'>");
	document.write("<button type='button' class='botonNavBar botonGris fuente-RobotoC' OnClick=\"history.back()\">ATRAS<\/button>");
	if (tipo == "admin") {
		document.write("<button type='button' class='botonNavBar botonGris fuente-RobotoC' OnClick=\"parent.location='/PerfilAdministrador'\">MENU<\/button>");
		document.write("<button type='button' class='botonNavBar botonRojo fuente-RobotoC' OnClick=\"parent.location='/'\">SALIR<\/button>");
	}
	document.write("<\/div>");
}
	
function habilitarSelect(){
	$( "#filtroNombre" ).hide();
	$( "#tipos" ).show();
	$( "input[name=botonFiltrar" ).show();
}

function habilitarTexto(){
	$( "#filtroNombre" ).show();
	$( "#tipos" ).hide();
	$( "input[name=botonFiltrar" ).show();
}