<!DOCTYPE html>
<html lang="en">

<head>
    <title>Reminder Management System</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css"  rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/reminderprathap.css">
    <script type="text/javascript" src="/js/reminderprathap.js"></script>
</head>

   <body>
       <header style="background-color:#fcd381">
           <nav class="navbar navbar-light">
               <div class="container ">
                   <a class="navbar-brand" href="/">Reminder Management System</a>
                   <a class="nav-link active" href="/reminder/All" aria-current="page"><input type="submit" name="submit" class="btn btn-warning btn-md" value="List All"></a>
                   <a class="nav-link active" href="/reminder/All Non-Renewable" aria-current="page"><input type="submit" name="submit" class="btn btn-warning btn-md" value="Non-Renewable"></a>
                   <a class="nav-link active" href="/reminder/All Renewable" aria-current="page"><input type="submit" name="submit" class="btn btn-warning btn-md" value="Renewable"></a>
                   <!--<a class="nav-link active" href="/reminder/Vehicle Insurance" aria-current="page"><input type="submit" name="submit" class="btn btn-info btn-md" value="Vehicle Insurance"></a>
                   <a class="nav-link active" href="/reminder/House Tax" aria-current="page"><input type="submit" name="submit" class="btn btn-info btn-md" value="House Tax"></a>
                   <a class="nav-link active" href="/reminder/Marriage Anniversary" aria-current="page"><input type="submit" name="submit" class="btn btn-info btn-md" value="Marriage Anniversary"></a>
                   <a class="nav-link active" href="/reminder/Vehicle Registration Certificate" aria-current="page"><input type="submit" name="submit" class="btn btn-info btn-md" value="Vehicle Registration Certificate"></a>
                   <a class="nav-link active" href="/reminder/Driving Licence" aria-current="page"><input type="submit" name="submit" class="btn btn-info btn-md" value="Driving Licence"></a>
                   <a class="nav-link active" href="/reminder/Birthday" aria-current="page"><input type="submit" name="submit" class="btn btn-info btn-md" value="Birthday"></a> -->
                   <a class="nav-link" href="/reminder/add"><input type="submit" name="submit" class="btn btn-warning btn-md" value="Add Reminder"></a>
                   <a class="nav-link" href="/"><input type="submit" name="submit" class="btn btn-warning btn-md" value="Logout"></a>
               </div>

               <div class="collapse navbar-collapse" id="navbarText">
               <ul class="navbar-nav mr-auto">
                 <li class="nav-item active">
                   <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
                 </li>
                 <li class="nav-item">
                   <a class="nav-link" href="#">Features</a>
                 </li>
                 <li class="nav-item">
                   <a class="nav-link" href="#">Pricing</a>
                 </li>
               </ul>
               <span class="navbar-text">
                 Navbar text with an inline element
               </span>
             </div>

           </nav>
       </header>