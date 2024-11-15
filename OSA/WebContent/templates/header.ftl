<!DOCTYPE HTML>
<html lang='de' dir='ltr'>
<head>
	<meta charset="utf-8" />
	<title>OnlineshopApp - ${pagetitle}</title>
	<link type="text/css" href="css/style.css" rel="stylesheet" media="screen" />
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css" />
	<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    
</head>
<body>
	<div id="logo" style="height: 70px;">Online shop APP <br>Software Engineering </div>
	<br>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
    <div class="navbar-nav">
      <a class="nav-item nav-link" href="MyDashboard">Dashboard</a>
      <a class="nav-item nav-link " href="AllProductsOverview">Product Overview <span class="sr-only"></span></a>
      <a class="nav-item nav-link " href="register_user">Registration</a>
      <a class="nav-item nav-link" href="<#if LoggedUser == "No Loggedin user"> register_user <#else> # </#if>" style="color:<#if LoggedUser == "No Loggedin user"> red <#else> green </#if>">
      <i class="fas fa-user"></i>
      ${LoggedUser}</a>
    </div>
  </div>
</nav>
<br>
    